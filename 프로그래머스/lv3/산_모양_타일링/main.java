class Solution {
    static int MOD = 10007;
    
    public int solution(int n, int[] tops) {
        int[][] dp = new int[2][tops.length];
        dp[0][0] = 1;
        dp[1][0] = 2 + tops[0];
        
        for(int i = 1 ; i < tops.length ; i++) {
            dp[0][i] = dp[0][i-1] + dp[1][i-1] % MOD;
            dp[1][i] = dp[0][i-1] * (1 + tops[i]) +
                dp[1][i-1] * ( 2 + tops[i] ) 
                % MOD;
        }
        
        return dp[0][tops.length-1] + dp[1][tops.length - 1] % MOD;
    }
}
