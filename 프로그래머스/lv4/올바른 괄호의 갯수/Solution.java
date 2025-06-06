class Solution {
    public int solution(int n) {
        int[] dp = new int[n+1];
        int answer = 0;
        
        dp[0] = 1;
        dp[1] = 1;
        
        for ( int i = 2 ; i <= n ; ++i ) {
            dp[i] = 0;
            
            for ( int j = 0 ; j < i ; j++ ) {
                dp[i] += dp[j] * dp[i-j-1];
            }
        }
        
        return dp[n];
    }
}
