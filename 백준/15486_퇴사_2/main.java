import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

public class Main {

  public static class Solution {

    int N;

    int[] spent;

    int[] cost;

    public Solution() throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      N = Integer.parseInt(br.readLine());
      spent = new int[N];
      cost = new int[N];

      for(int i = 0 ; i < N ; i++) {
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        spent[i] = Integer.parseInt(tokenizer.nextToken());
        cost[i] = Integer.parseInt(tokenizer.nextToken());
      }
      br.close();
    }

    public int run() {
      int maxCost = 0;
      int[] dp = new int[N+1];
      // dp[i] = i번째 일 까지 얻을 수 있는 최대 수익

      for(int i = 0 ; i < N ; i++) {
        maxCost = Math.max(maxCost, dp[i]);
        if(i + spent[i] > N) continue;
        dp[i + spent[i]] = Math.max( maxCost + cost[i],  dp[i+spent[i]]);
      }
//      maxCost = 0;
//      for(int v : dp) {
//        maxCost = Math.max(v, maxCost);
//      }


      return maxCost;
    }
  }
  public static void main(String[] args) throws IOException {
    Solution sol = new Solution();
    System.out.println(sol.run());
  }
}
