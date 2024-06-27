import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

public class Main {

  public static class Solution {

    int N, S;

    int[][] moles; // moles[i][j]  i번째 두더지의 정보, [x, y, t];

    int[] dp;

    public Solution(int N, int S, int[][] moles) throws IOException {
      this.N = N;
      this.S = S;
      this.moles = moles;
    }

    double dist(int x0, int y0, int x1, int y1) {
      return Math.sqrt( Math.pow( x1-x0 , 2) + Math.pow(y1-y0, 2) );
    }

    int search(int i) {
      // 이 함수는 i번째 두더지를 잡는다고 고려했을 때 최대 잡은 횟수를 반환한다.
      //if(i > N) return 0;
//      System.out.println("i : " + i);

      if(dp[i] != -1) return dp[i];

      dp[i] = 0;

      int curX = moles[i][0];
      int curY = moles[i][1];
      int curT = moles[i][2];
//      System.out.println("Cur : " + curX + ", " + curY + ", " + curT);
      for(int nxt = i + 1 ; nxt <= N ; nxt++) {
        int moleX = moles[nxt][0];
        int moleY = moles[nxt][1];
        int moleT = moles[nxt][2];
//        System.out.println("##### Nxt : " + moleX + ", " +moleY + "," + moleT);
        double dst = dist(curX,curY,moleX,moleY);
//        System.out.println("distance to nxt : " + nxt + " is " + dst);

        if( (dst / (double)S)  <= moleT - curT ) {
//          System.out.println("distance / S : " + dst/(double)S);
          // 이 경우 탐색이 가능하다.
          dp[i] = Math.max( dp[i], search(nxt) + 1 );
        }
      }

      return dp[i];
    }

    int run() {
      Arrays.sort(moles, (a, b) -> {
        return a[2] - b[2];
      }); // 우선 시간순으로 정렬을 한다.

      dp = new int[N+1]; // dp[i] 는 i번째 두더지까지 고려했을 때 잡을 수 있는 최대 두더지 수
      Arrays.fill(dp, -1);

      return search(0);
    }
  }
  public static void main(String[] args) throws IOException {
    int N, S;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] fl = br.readLine().split(" ");
    N = Integer.parseInt(fl[0]);
    S = Integer.parseInt(fl[1]);
    int[][] moles = new int [N+1][3];
    moles[0][0] = 0;
    moles[0][1] = 0;
    moles[0][2] = 0;

    for(int i = 1 ; i <= N ; i++) {
      StringTokenizer tokenizer = new StringTokenizer(br.readLine());
      moles[i][0] = Integer.parseInt(tokenizer.nextToken());
      moles[i][1] = Integer.parseInt(tokenizer.nextToken());
      moles[i][2] = Integer.parseInt(tokenizer.nextToken());
    }

    br.close();
    Solution sol = new Solution(N, S, moles);
    System.out.println(sol.run());
  }
}
