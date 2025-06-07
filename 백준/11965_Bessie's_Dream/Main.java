import java.io.*;
import java.util.*;

class Solution {

    private int N, M;

    private int[][] board;

    private static final int RED = 0;

    private static final int PINK = 1;

    private static final int ORANGE = 2;

    private static final int BLUE = 3;

    private static final int PURPLE = 4;

    private static final int[] dr = {0, 1, 0, -1};

    private static final int[] dc = {1, 0, -1, 0};

    static class Status {
        int r, c, t;
        boolean smell;

        Status(int r, int c, int t, boolean orangeSmell) {
            this.r = r;
            this.c = c;
            this.t = t;
            this.smell = orangeSmell;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        board = new int[N][M];

        for ( int r = 0 ; r < N ; ++r ) {
            tokens = reader.readLine().split(" ");
            for ( int c = 0; c < M ; ++c ) {
                board[r][c] = Integer.parseInt(tokens[c]);
            }
        }
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }

    private boolean isPassable(int r, int c, boolean hasSmell) {
        if ( !isInRange(r, c) ) return false;
        if ( board[r][c] == RED ) return false;
        if ( board[r][c] == BLUE ) return hasSmell;
        return true;
    }

    private int simulate() {
        int time = Integer.MAX_VALUE;
//        Queue<Status> q = new LinkedList<>();
        PriorityQueue<Status> q = new PriorityQueue<>((a, b) -> {
            return a.t - b.t;
        });
        q.add(new Status(0, 0, 0, false));

        int[][][] visited = new int[N][M][2];
        for ( int[][] plane : visited ) {
            for ( int[] row : plane ) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
        }

        while ( !q.isEmpty() ) {
            Status cur = q.poll();
            int flag = cur.smell ? 1 : 0;

            if ( cur.r == N - 1 && cur.c == M - 1) {
                return cur.t;
            }

            if ( visited[cur.r][cur.c][flag] != Integer.MAX_VALUE ) {
                continue;
            }
            visited[cur.r][cur.c][flag] = cur.t;

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                int nd = cur.t + 1;
                boolean nxtSmell = cur.smell;

                if ( !isPassable(nr, nc, cur.smell) ) continue;

                if ( board[nr][nc] == PURPLE ) {
                    while ( isPassable(nr + dr[i], nc + dc[i],  cur.smell ) && board[nr][nc] == PURPLE ) {
                        nr += dr[i];
                        nc += dc[i];
                        nd++;
                        nxtSmell = false;
                    }
                }

                if ( board[nr][nc] == ORANGE ) {
                    nxtSmell = true;
                }
                if ( visited[nr][nc][nxtSmell ? 1 : 0] <= nd ) continue;
                q.add(new Status(nr, nc, nd, nxtSmell));
            }
        }

        return time == Integer.MAX_VALUE ? -1 : time;
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
