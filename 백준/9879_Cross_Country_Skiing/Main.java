import java.io.*;
import java.util.*;

class Solution {

    private int R, C;
    private int[][] board;
    private List<Item> waypoints;
    private static final int[] dr = {-1, 0, 1, 0};
    private static final int[] dc = {0, 1, 0, -1};
    
    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        board = new int[R][C];
        waypoints = new ArrayList<>();

        for (int i = 0; i < R; i++) {
            tokens = reader.readLine().split(" ");
            for (int j = 0; j < C; j++) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        for (int i = 0; i < R; i++) {
            tokens = reader.readLine().split(" ");
            for (int j = 0; j < C; j++) {
                if (tokens[j].charAt(0) == '1') {
                    waypoints.add(new Item(i, j));
                }
            }
        }

        // System.out.println("Waypoints : " + waypoints.size());
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    private boolean isPossible(int difficulty, boolean[][] visited) {
        Queue<Item> queue = new LinkedList<>();
        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }

        queue.add(waypoints.get(0));
        visited[waypoints.get(0).r][waypoints.get(0).c] = true;

        while ( !queue.isEmpty() ) {
            Item current = queue.poll();

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];

                if ( !isInRange(nr, nc) || visited[nr][nc] ) {
                    continue;
                }

                int diff = Math.abs((int)board[current.r][current.c] - (int)board[nr][nc]);

                if ( diff <= difficulty ) {
                    visited[nr][nc] = true;
                    queue.add(new Item(nr, nc));
                }
            }
        }

        for ( int i = 1 ; i < waypoints.size() ; ++i ) {
            Item waypoint = waypoints.get(i);
            if ( !visited[waypoint.r][waypoint.c] ) {
                return false;
            }
        }

        return true;
     }

    private int explore() {
        int answer = Integer.MAX_VALUE;
        int left = -1;
        int right = 1_000_000_001;
        boolean[][] visited = new boolean[R][C];

        while ( left + 1 < right ) {
            int mid = left + (right - left) / 2;

            if ( isPossible(mid, visited) ) {
                right = mid;
                answer = Math.min(answer, mid);
            } else {
                left = mid;
            }
        }

        return answer;
    }

    public void run () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(explore()).append("\n");
        System.out.println(sb.toString());
    }

    static class Item {
        int r,c;

        public Item(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
