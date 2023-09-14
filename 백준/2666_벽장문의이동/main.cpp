#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

class Solution {
    int32_t N, K;
    int32_t opened[2];
    int32_t arr[21]={0,};
    int32_t dp[21][21][21]; // dp[1번문 위치][2번문 위치][현재 커서];

    void init() {
        cin >> N ;
        cin >> opened[0] >> opened[1];
        cin>> K;
        memset(dp, -1, sizeof(dp));

        for(int32_t i = 0 ; i < K ; i++) {
            cin >> arr[i+1];
        }
    }

    int32_t dfs(int32_t o1, int32_t o2, int32_t current) {
        if(current == K + 1) return 0;

        int32_t &val = dp[o1][o2][current];
        if(val != -1)
            return val;
        val = 0;
        val = min( abs(arr[current] - o1) + dfs(arr[current], o2, current+1),  // 1번 문을 이동
                   abs(arr[current] - o2) + dfs(o1, arr[current], current+1)); // 2번 문을 이동

        return val;
    }

    void simulate() {
        cout << dfs(opened[0], opened[1], 1) << endl;;
    }

public:
    void run() {
        init();
        simulate();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
