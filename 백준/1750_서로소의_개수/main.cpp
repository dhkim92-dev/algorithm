#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
#include <memory.h>

using namespace std;

const int32_t MOD = 10000003; 
int32_t dp[51][100001];

class Solution {
    int32_t N;
    vector<int32_t> arr;
    int32_t answer = 0;

    void init() {
        cin >> N;
        arr.resize(N);
        memset(dp, 0, sizeof(dp));

        for(int32_t i = 0 ; i < N ; i++) {
            cin >> arr[i];
            dp[i][arr[i]] = 1;
        }
    }

    void simulate() {
        // n개의 수로 이루어진 수열이 주어지고,
        // 수열의 수를 1개 이상 뽑을 때 수들의 gcd가 1인 경우
        // dp[i][j] => i번째 수까지 뽑은 수열들의 조합에서 gcd가 j인 경우의 수
        // 

        for(int i = 1 ; i < N ; i++) {
            for(int j = 1 ; j <= 100000 ; j++) {
                dp[i][j] = (dp[i][j] + dp[i-1][j]) % MOD;
                int32_t g = gcd(arr[i], j);
                dp[i][g] = (dp[i][g] + dp[i-1][j]) % MOD;
            }
        }
        answer = dp[N-1][1];
    } 

    void print() {
        cout << answer << endl;
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
