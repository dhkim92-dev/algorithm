import java.io.*;
import java.util.*;

class Solution {

    private int H, W, N;

    private int[][] grid;

    private final int[] dr = {0, 1, 0, -1};

    private final int[] dc = {1, 0, -1, 0};

    private int sr, sc;

    private boolean[][] visited;

    static class Status {
        int time;
        int r, c;
        int level;

        Status(int time, int r, int c, int level) {
            this.time = time;
            this.r = r;
            this.c = c;
            this.level = level;
        }
    }


    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        H = Integer.parseInt(tokens[0]);
        W = Integer.parseInt(tokens[1]);
        N = Integer.parseInt(tokens[2]);
        grid = new int[H][W];
        for ( int i = 0 ; i < H ; ++i ) {
            String line = reader.readLine();

            for( int j = 0 ; j < W ; ++j ) {
                char ch = line.charAt(j);
                if (ch == 'S') {
                    sr = i;
                    sc = j;
                    grid[i][j] = 0;
                } else if (ch == '.') {
                    grid[i][j] = 0;
                } else if ( ch == 'X' ) {
                    grid[i][j] = -1;
                } else {
                    grid[i][j] = ch - '0';
                }
            }
        }

        visited = new boolean[H][W];
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < H && c >= 0 && c < W;
    }

    private int[] bfs(int r, int c, int target, int hp) {
        Queue<Status> q = new LinkedList<>();
        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }
        int time = Integer.MAX_VALUE;
        int nextr=0,nextc=0;
        visited[r][c] = true;
        q.add(new Status(0, r, c, hp));

        while ( !q.isEmpty() ) {
            Status cur = q.poll();
//            System.out.println("Visiting: " + cur.r + "," + cur.c + " at time " + cur.time + " with level " + cur.level);
            if (cur.level == target) {
                time = cur.time;
                nextr = cur.r;
                nextc = cur.c;
                break;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

//                System.out.println("    Checking: " + nr + "," + nc);

                if (!isInRange(nr, nc)
                    || visited[nr][nc]
                    || grid[nr][nc] == -1 ) continue;
                visited[nr][nc] = true;
                // 빈칸이거나 치즈가 있거나
                if ( grid[nr][nc] == target ) {
                    q.add(new Status(cur.time + 1, nr, nc, cur.level+1));
                } else  {
                    q.add(new Status(cur.time + 1, nr, nc, cur.level));
                }
            }
        }

//        System.out.println("Time to reach + " + target + " cheese: " + time + " from " + r + "," + c);

        return new int[] {time, nextr, nextc};
    }

    private int simulate() {
        int time = 0;
        int hp = 0;

        for ( int i = 1 ; i <= N ; ++i ) {
            int[] result = bfs(sr, sc, i, hp);
            time += result[0];
            sr = result[1];
            sc = result[2];
            hp++;
        }

        return time;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
