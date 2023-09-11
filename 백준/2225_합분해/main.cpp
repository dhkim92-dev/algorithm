#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

#define DIVIDER 1000000000

class Solution {
    int N, K;
    vector<vector<int32_t>> dp;

    void init() {
        cin >> N >> K;
        dp.resize(201, vector<int32_t>(201, 0));
    }

    void simulate() {
        // 0~N 까지의 정수 K개를 더하여
        // 합이 N이 되는 경우의 수를 구하라
        // dp[i][j] => i개의 수를 이용하여 j를 만드는 경우의 수
        // dp[i][j] = sigma( dp[i-1][j-L] ) where L in range(0, j);

        for(int i = 0 ; i <= N ; i++) {
            dp[1][i] = 1;
        }


        for(int k = 1 ; k <= K ; k++) {
            for(int i = 0 ; i <= N ; i++) {
                for(int l = 0 ; l <= i ; l++) {
                    dp[k][i] += dp[k-1][i-l];
                    dp[k][i] %= DIVIDER;
                }
                
            }
        }

    }

    void print() {
        cout << dp[K][N] << endl;
    
    }

public :
    void run() {
        init();
        simulate();
        print();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
