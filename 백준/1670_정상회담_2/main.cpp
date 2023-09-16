#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

#define MOD 987654321

class Solution {
    int32_t N;
    vector<int64_t> dp;

    void init() {
        cin >> N;
        dp.resize(N+1, 0);
    }

    void simulate() {
        // dp[i] = i명일 때 악수하는 경우의 수 
        // dp[i] = (i-1)명이 악수하는 경우의 수 + (i-2) 명이 악수하는 경우의 수 
        dp[0] = 1;
        
        for(int i = 2 ; i <= N ; i+=2) {
            for(int j = 0 ; j < i ; j+=2) {
                dp[i] = (dp[i] + (dp[j] * dp[i-j-2]) % MOD) % MOD;
            }
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
