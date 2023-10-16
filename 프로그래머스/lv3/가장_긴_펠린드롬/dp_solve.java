import java.util.*;

class Solution
{   
    public int solution(String s)
    {
        boolean[][] dp = new boolean[s.length()+1][s.length()+1];
        int answer = 1;
        int limit = s.length();
        
        for(int i = 0 ; i <= limit ; i++) {
            for(int j = 0 ; j <= limit ; j++) {
                dp[i][j] = false;
            }
        }
        
        for(int i = 0 ; i < limit ; i++) {
            dp[i][i] = true;
        }
        
        for(int i = 0 ; i < limit - 1 ; i++) {
            if(s.charAt(i) == s.charAt(i+1)) {
                dp[i][i+1] = true;
                answer = 2;
            }
        }
        
        for(int length = 3 ; length <= limit ; length++) {
            for(int start = 0; start + length <= limit ; start++) {
                int to = start + length - 1;
                
                if((s.charAt(start) == s.charAt(to)) && (dp[start+1][to-1])) {
                    answer = Math.max(answer, length);
                    dp[start][to] = true;
                } 
            }
        }
        
        return answer;
    }
}
