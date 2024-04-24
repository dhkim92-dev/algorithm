#include <vector>

using namespace std;
constexpr int MOD = 10007;

int solution(int n, vector<int> tops) {
    
    vector<vector<int>> dp(2, vector<int>(tops.size(), 0));
    dp[0][0] = 1;
    dp[1][0] = 2 + tops[0];
    
    for(int i = 1 ; i < tops.size() ; i++) {
        dp[0][i] = (dp[0][i-1] + dp[1][i-1]) % MOD;
        dp[1][i] = ((dp[0][i-1] * (1 + tops[i])) + 
            (dp[1][i-1] * (2 + tops[i])))
            % MOD;
    }
    
    return (dp[0][tops.size() - 1] + dp[1][tops.size()-1])%MOD;
}
