#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

#define MOD 1000000000

class Solution {
    int32_t N;
    int32_t dp[1000001];

    void init() {
        cin >> N;
        memset(dp, 0x00, sizeof(dp));
    }
    
    void simulate() {
        // dp[i] 는  i명이 선물을 나누는 경우 
        dp[1] = 0;
        dp[2] = 1;
        dp[3] = 2;

        for(int i = 4 ; i <= N ; i++) {
            dp[i] = ((i-1)*(dp[i-1] + dp[i-2])) % MOD;
        }
    }

    void print() {
        cout << dp[N] << endl;
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
