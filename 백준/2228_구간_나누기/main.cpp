#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

#define MIN_VAL -987654321

class Solution {
    int32_t N, M;
    //vector<int32_t> arr;
    vector<int32_t> psum;
    int32_t dp[101][51];

    void init() {
        cin >> N >> M;
        //arr.resize(N+1, 0);
        psum.resize(N+1, 0);

        for(int i = 1 ; i <= N ; i++) {
            int val;
            cin >> val;
            psum[i] = psum[i-1] + val;
        }

        for(int i = 0 ; i <= 100 ; i++) {
            for(int j = 1 ; j <= 50 ; j++) {
                dp[i][j] = MIN_VAL;
            }
        }
    }

    void simulate() {
        // dp[i][j] = i개의 수를 j개 구간으로 나누었을 때 최대값
        // dp[i][j] = dp[i-1][j] if i is not in j
        // dp[i][j] = max( d[[i-2][j-1] + i, dp[i-4][j-1] + (i + i - 2), dp[i-6][j-1] + sigma[i, k]( i - k))

        dp[1][1] = psum[1];

        for(int i = 2 ; i <= N ; i++) {
            for(int j = 1 ; j<= M ; j++) {
                dp[i][j] = dp[i-1][j];

                if(j == 1) {
                    dp[i][j] = max(dp[i][j], psum[i]);
                }

                for(int k = 0 ; k <= i - 2 ; k++) {
                    dp[i][j] = max(dp[i][j], dp[k][j-1] + psum[i]  - psum[k+1]);
                }
            }
        }
    }

    void print() {
        cout << dp[N][M] << endl;
    }

public:
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
