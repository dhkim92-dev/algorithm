
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;
    private int[][] board;
    private static final int[] dr = {-1, 0, 1, 0};
    private static final int[] dc = {0, 1, 0, -1};
    int total = 0;


    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        board = new int[N][N];
        for(int i = 0 ; i < N ; i++) {
            String[] line = reader.readLine().split(" ");
            for(int j = 0 ; j < N ; j++) {
                board[i][j] = Integer.parseInt(line[j]);
                total += board[i][j];
            }
        }
    }


    private int check(int r, int c, int d1, int d2) {
        boolean[][] borderLine = new boolean[N][N];

        for(int i = 0; i <= d1 ; i++) {
            borderLine[r+i][c-i] = true;
            borderLine[r + d2 + i][c + d2 - i] = true;
        }

        for(int i = 0 ; i <= d2 ; i++) {
            borderLine[r+i][c+i] =true;
            borderLine[r + d1 + i][c - d1 + i] = true;
        }

        int[] area = new int[5];

        for(int i = 0 ; i < r + d1 ; i++) {
            for(int j = 0 ; j <= c ; j++) {
                if(borderLine[i][j]) break;
                area[0] += board[i][j];
            }
        }

        for(int i = 0 ; i <= r + d2 ; i++) {
            for(int j = N-1 ; j > c ; j--) {
                if(borderLine[i][j]) break;
                area[1] += board[i][j];
            }
        }

        for(int i = r + d1 ; i < N ; i++) {
            for(int j = 0 ; j < c - d1 + d2 ; j++) {
                if(borderLine[i][j]) break;
                area[2] += board[i][j];
            }
        }

        for(int i = r + d2 + 1 ; i < N ; i++) {
            for(int j = N-1 ; j >= c - d1 + d2 ; j--) {
                if(borderLine[i][j]) break;
                area[3] += board[i][j];
            }
        }

        area[4] = total - (area[0] + area[1] + area[2] + area[3]);
        Arrays.sort(area);

        return area[4] - area[0];
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        int answer = Integer.MAX_VALUE;

        for(int r = 0 ; r < N ; r++) {
            for(int c = 0 ; c < N ; c++) {
                for(int d1 = 1 ; d1 < N ; d1++) {
                    for(int d2 = 1 ; d2 < N ; d2++) {
                        if( r + d1 + d2 >= N ) continue;
                        if( c - d1 < 0 || c + d2 >= N ) continue;

                        answer = Math.min(answer, check(r, c, d1, d2));
                    }
                }
            }
        }

        sb.append(answer);
        System.out.println(sb.toString());
    }
}

