import java.io.*;
import java.util.*;

class Solution {

    private int N;

    private String input;

    private int[][] board;


    private static final int EMPTY = 0;

    private static final int FENCE = 1;

    private static final int[] dr = {1, 0, -1, 0};

    private static final int[] dc = {0, 1, 0, -1};

    private int curR = 2000, curC = 2000;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine().trim());
        input = reader.readLine().trim();
        board = new int[4010][4010];
    }

    private void init() {
        for ( int i = 0 ; i < N ; ++i ) {
            char ch = input.charAt(i);
            for ( int j = 0 ; j < 2 ; ++j ) {
                int r, c;
                if (ch == 'N') {
                    r = curR + dr[0];
                    c = curC + dc[0];
                } else if (ch == 'E') {
                    r = curR + dr[1];
                    c = curC + dc[1];
                } else if (ch == 'S') {
                    r = curR + dr[2];
                    c = curC + dc[2];
                } else {
                    r = curR + dr[3];
                    c = curC + dc[3];
                }
                board[r][c] = FENCE;
                curR = r;
                curC = c;
            }
        }
    }

    private void bfs(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        board[r][c] = FENCE;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];
                if (nr < 0 || nr > 4000 || nc < 0 || nc > 4000 || board[nr][nc] == FENCE) continue;
                q.offer(new int[]{nr, nc});
                board[nr][nc] = FENCE;
            }
        }
    }

    private int simulate() {
        init();
        int answer = 0;
        for ( int i = 0 ; i <= 4000 ; ++i ) {
            for ( int j = 0 ; j <= 4000 ; ++j ) {
                if (board[i][j] == FENCE) continue;
                bfs(i, j);
                answer++;
            }
        }

        return answer-1;
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
