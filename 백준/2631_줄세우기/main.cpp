#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
    int N;
    vector<int> targets;
    vector<int> dp;
    int answer = 1e9;
    
    void init() {
        cin >> N;
        targets.resize(N+1, 0);
        dp.resize(N+1, 1);

        for(int i = 0 ; i < N ; i++) {
            cin >> targets[i+1];
        } 
    }

    void print() {
        cout << answer << endl;
    }

    void simulate() {
        for(int i = 1 ; i <= N ; i++) {
            for(int j = 1 ; j <= i ; j++) {
                if(targets[j] < targets[i]) {
                    // targets[j] 보다 targets[i]가 크다면
                    // dp[j] = dp[j] + 1 이 된다.
                    // dp[i] = max(dp[i] , dp[j] + 1);
                    dp[i] = max(dp[i], dp[j]+1);
                }
            }
        }

        sort(dp.begin(), dp.end());
        answer = N - dp.back();
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