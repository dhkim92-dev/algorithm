import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

public class Main {

  public static class Solution {
    String s;

    boolean[] alphaExist = new boolean[26];

    int[] firstIndex = new int[26];

    int[] lastIndex = new int[26];

    int[][] dp;

    public Solution(String s) {
      this.s = s;
      dp = new int[26][s.length()];
      for(int i = 0 ; i < 26 ; i++) {
        for(int j = 0 ; j < s.length() ; j++) {
          dp[i][j] = -1;
        }
      }

      Arrays.fill(alphaExist, false);
      Arrays.fill(firstIndex, Integer.MAX_VALUE);
      Arrays.fill(lastIndex, -1);

      for(int i = 0 ; i < s.length() ; i++) {
        int ch = s.charAt(i) - 'a';
        alphaExist[ch] = true;
        firstIndex[ch] =  Math.min(firstIndex[ch], i);
        lastIndex[ch] = Math.max(lastIndex[ch], i);
      }
    }

    public static int cost(int from, int to, int first, int last) {
      // from에서 first와 last를 거쳐 to로 가는데 드는 비용
      if (first == -1 || last == -1) return 0;

      return Math.abs(from - first) +
              Math.abs(last - first) +
              Math.abs(last - to);
    }

    public int search(int ch, int cursor) {
      if (ch == 26) return 0;

      if (dp[ch][cursor] != -1) {
        return dp[ch][cursor]; // 이미 계산된 결과가 있으면 반환
      }

      dp[ch][cursor] = Integer.MAX_VALUE;
      int from = firstIndex[ch];
      int to = lastIndex[ch];

      if (alphaExist[ch]) {
        for (int i = 0; i < s.length(); i++) {
          dp[ch][cursor] = Math.min(dp[ch][cursor], search(ch + 1, i) +
                  Math.min(cost(cursor, i, from, to),
                          cost(cursor, i, to, from))
          );
        }
      } else {
        dp[ch][cursor] = search(ch + 1, cursor);
      }

      return dp[ch][cursor];
    }
  }
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String s = br.readLine();
    Solution sol = new Solution(s);
    int answer = sol.search(0, 0);
    System.out.println(answer + s.length());
    br.close();
  }
}
