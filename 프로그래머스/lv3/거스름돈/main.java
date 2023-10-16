import java.util.*;

class Solution {
    static int MOD = 1000000007;
    
    public int solution(int n, int[] money) {
        int answer = 0;
        int[] dp = new int[n+1]; //dp[n] 은 n원을 거슬러주는 방법의 수
        dp[0] = 1;
        Arrays.setAll(dp, p->p % money[0] == 0 ? 1 : 0); 
        
        for(int i = 1 ; i < money.length ; i++) {
            int target = money[i];
            
            for(int j = target ; j <= n ; j++) {
                dp[j] += dp[j - target] % MOD;
            }
        }
        
        answer = dp[n];
        
        return answer;
    }
}
