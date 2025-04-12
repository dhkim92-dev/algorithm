
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, Q;
    private int[][] board;
    private int[] L;
    private int R, C;

    private int[] dx = {-1, 0, 1, 0};
    private int[] dy = {0, -1, 0, 1};

    static class Pos {
        int r, c, cnt;

        Pos(int r, int c, int cnt) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
        }
    };

    public Solution(BufferedReader reader) throws IOException {
        StringTokenizer st = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        R = (int)Math.pow(2, N);
        C = (int)Math.pow(2, N);

        board = new int[R][C];
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(reader.readLine());
            for (int j = 0; j < C; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        L = new int[Q];
        st = new StringTokenizer(reader.readLine());
        for (int i = 0; i < Q; i++) {
            L[i] = (int)Math.pow(2, Integer.parseInt(st.nextToken()));
        }
    }

    /**
     * Top Left 좌표 (r, c) 에서부터 시작하는 length x length 크기의 sub grid 를 시계방향으로 90도 회전한다.
     * @param r sub grid 의 시작 행
     * @param c sub grid 의 시작 열
     * @param length sub grid 의 길이
     */
    private void rotateSubGrid(int[][] tmp, int r, int c, int length) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                tmp[j][length - 1 - i] = board[r + i][c + j];
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                board[r + i][c + j] = tmp[i][j];
            }
        }
    }

    private void rotateGrids(int length) {
        int[][] tmp = new int[length][length];
        for(int r = 0 ; r < R ; r += length) {
            for(int c = 0 ; c < C ; c += length) {
                rotateSubGrid(tmp, r, c, length);
            }
        }
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    private void reduceIce(int[][] cached, int r, int c) {
        int cnt = 0;
        for ( int i = 0;  i < 4 ; i++ ) {
            int nr = r + dx[i];
            int nc = c + dy[i];
            if ( isInRange(nr, nc) && board[nr][nc] > 0 ) {
                cnt++;
            }
        }

        if ( cnt < 3 ) {
            cached[r][c]--;
        }
    }

    private void reduceIces() {
        int[][] cached = new int[R][C];

        for ( int i = 0 ; i < R ; i++ ) {
            for ( int j = 0 ; j < C ; j++ ) {
                cached[i][j] = board[i][j];
            }
        }

        for ( int i = 0 ; i < R ; i++ ) {
            for ( int j = 0 ; j < C ; j++ ) {
                if ( board[i][j] == 0) continue;
                reduceIce(cached, i, j);
            }
        }

        for ( int i = 0 ; i < R ; i++ ) {
            for ( int j = 0 ; j < C ; j++ ) {
                board[i][j] = cached[i][j];
            }
        }
    }

    private int countRemainIces() {
        int cnt = 0;

        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if ( board[i][j] > 0 ) {
                    cnt += board[i][j];
                }
            }
        }
    
        return cnt;
    }

    private int dfs(boolean[][] visited, int r, int c) {
        if ( board[r][c] == 0 ) return 0;

        visited[r][c] = true;
        int cnt = 1;

        for (int i = 0; i < 4; i++) {
            int nr = r + dx[i];
            int nc = c + dy[i];

            if (isInRange(nr, nc) && !visited[nr][nc] && board[nr][nc] > 0) {
                cnt += dfs(visited, nr, nc);
            }
        }

        return cnt;
    }

    private int getBiggestIceSize() {
        boolean [][] visited = new boolean[R][C];
        int max = 0;

        for ( int i = 0 ; i < R ; ++i ) {
            for ( int j = 0 ; j < C ; ++j ) {
                if ( visited[i][j] || board[i][j] == 0 ) continue;

                max = Math.max(max, dfs(visited, i, j));
            }
        }

        return max;
    }

    private void printBoard() {
        System.out.println("print board");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public void run () {
        StringBuilder sb = new StringBuilder();

        // printBoard();
        for(int i = 0 ; i < Q; i++) {
            int l = L[i];
            rotateGrids(l);
            reduceIces();
        }

        sb.append(countRemainIces())
          .append("\n")
          .append(getBiggestIceSize());

        System.out.println(sb.toString());
    }
}

