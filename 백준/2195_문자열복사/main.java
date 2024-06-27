import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

public class Main {

  public static class Solution {

    String S, P;

    public Solution(String s, String p) throws IOException {
      S = s;
      P = p;
    }

    public int run() {
      int copyCallCount = 0;

      for(int pCursor = 0 ; pCursor < P.length() ; ) {
//        System.out.println("PCursor : " + pCursor);
        int maxLength = 0;

        for(int j = 0 ; j < S.length() ; j++) {
          int length = 0;
          for(int k = j ; k < S.length() ; k++) {
            if(pCursor+length >= P.length()) break;
            if(P.charAt(pCursor + length) == S.charAt(k)) {
              length++;
            }else {
              break;
            }
          }

          maxLength = Math.max(length, maxLength);
        }

        if(maxLength == 0) break;
//        System.out.println("Call at pCursor : " + pCursor + " with length : " + maxLength);
        copyCallCount++;
        pCursor+=maxLength;
      }

      return copyCallCount;
    }
  }
  public static void main(String[] args) throws IOException {
    String S, P;

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    S = br.readLine();
    P = br.readLine();

    br.close();
    Solution sol = new Solution(S, P);
    System.out.println(sol.run());
  }
}
