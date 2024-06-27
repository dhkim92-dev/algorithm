import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

  public static class Solution {

    int[][] seeds;

    int N;

    int M;

    int[][] board;

    public Solution(int n, int m, int[][] s) {
      N = n;
      M = m;
      seeds = s;
    }

    void spread() {
      // 씨를 뿌린다.
      for (int i = 0; i < M; i++) {
        int r = seeds[i][0];
        int c = seeds[i][1];
        int len = seeds[i][2];
        int type = seeds[i][3];

        for (int n = r; n < r + len; n++) {
          for (int m = c; m < c + len; m++) {
            board[n][m] = type;
          }
        }
      }

//      for(int i = 0 ; i < N ; i++) {
//        for(int j = 0 ; j < N ; j++) {
//          System.out.print(board[i][j] + " ");
//        }
//        System.out.println();
//      }
    }

    int calc(int t1, int t2) {
      int maxLength = Integer.MIN_VALUE;
      int[][] dp = new int[N][N];

      for(int i = 0 ; i < N ; i++) {
        for(int j = 0 ; j < N ; j++) {
          if(board[i][j] == t1 || board[i][j] == t2) {
            dp[i][j] = 1;
          }
        }
      }

      for(int i = 1 ; i < N ; i++) {
        for(int j = 1; j < N ; j++) {
          if(dp[i][j] == 0) continue;
          dp[i][j] = Math.min(Math.min( dp[i-1][j-1], dp[i][j-1]), dp[i-1][j]) + 1;
          maxLength = Math.max(dp[i][j], maxLength);
        }
      }
      return maxLength;
    }

    public int run() {
      board = new int[N][N];
      int answer = Integer.MIN_VALUE;
      spread();

      for(int i = 1 ; i < 7 ; i++) {
        for(int j = i + 1 ; j <= 7 ; j++) {
          answer = Math.max(answer, calc(i, j));
        }
      }

      return answer*answer;
    }
  }
  public static void main(String[] args) throws IOException {
    int N, M;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer token = new StringTokenizer(br.readLine());
    N = Integer.parseInt(token.nextToken());
    M = Integer.parseInt(token.nextToken());

    int[][] seeds = new int[M][4];

    for(int i = 0 ; i < M ; i++) {
      String[] line = br.readLine().split(" ");
      for(int j = 0 ; j <4 ; j++) {
        seeds[i][j] = Integer.parseInt(line[j]);
      }
    }

    Solution sol = new Solution(N, M, seeds);

    System.out.println(sol.run());

    br.close();
  }
}
