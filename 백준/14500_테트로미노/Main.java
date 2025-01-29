import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int R, C;

    private int[][] board;

    private boolean visited[][];

    private int[] dr = {0, 0, 1, -1};

    private int[] dc = {1, -1, 0, 0};

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        board = new int[R][C];
        visited = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            board[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }

    private int dfs(int depth, int r, int c, int sum) {
        if(depth == 3) {
            return sum;
        }

        int max = 0;
        for(int i = 0 ; i < 4 ; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if(nr < 0 || nr >= R || nc < 0 || nc >= C) {
                continue;
            }

            if(visited[nr][nc]) {
                continue;
            }

            if(depth == 1) {
                visited[nr][nc] = true;
                max = Math.max(max, dfs(depth + 1, r, c, sum + board[nr][nc]));
                visited[nr][nc] = false;
            }

            visited[nr][nc] = true;
            max = Math.max(max, dfs(depth + 1, nr, nc, sum + board[nr][nc]));
            visited[nr][nc] = false;
        }

        return max;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        int max = Integer.MIN_VALUE;

        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                visited[r][c] = true;
                max = Math.max(max, dfs(0, r, c, board[r][c]));
                visited[r][c] = false;
            }
        }
        sb.append(max);

        System.out.println(sb.toString());
    }
}
