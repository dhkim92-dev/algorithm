#include <iostream>
#include <vector>
#include <algorithm>
#include <stdint.h>
#include <memory.h>

using namespace std;

class Solution {
    int N;
    //vector< vector<int32_t> > dp; // 최대 1.2 Mb
    int32_t min_dp[3]={0,0,0};
    int32_t min_tmp[3]={0,0,0};
    int32_t max_dp[3] = {0,0,0};
    int32_t max_tmp[3] = {0,0,0};

    void init() {
        cin >> N;
    }

    void simulate() {
        cin >> max_dp[0] >> max_dp[1] >> max_dp[2];
        min_dp[0]=max_dp[0];
        min_dp[1]=max_dp[1];
        min_dp[2]=max_dp[2];
        memcpy(max_tmp, max_dp, sizeof(max_dp));
        memcpy(min_tmp, min_dp, sizeof(min_dp));

        for(int i = 0 ; i < N-1 ; i++) {
            int a,b,c;
            cin >> a >> b >> c;
            max_dp[0] = a + max(max_tmp[0],max_tmp[1]);
            max_dp[1] = b + max(max(max_tmp[0],max_tmp[1]), max_tmp[2]);
            max_dp[2] = c + max(max_tmp[1],max_tmp[2]);
            min_dp[0] = a + min(min_tmp[0],min_tmp[1]);
            min_dp[1] = b + min(min(min_tmp[0],min_tmp[1]), min_tmp[2]);
            min_dp[2] = c + min(min_tmp[1], min_tmp[2]);
            memcpy(max_tmp, max_dp, sizeof(max_dp));
            memcpy(min_tmp, min_dp, sizeof(min_dp));
        }

        cout << max( max(max_dp[0], max_dp[1]), max_dp[2] ) << " " << min(min(min_dp[0], min_dp[1]), min_dp[2]) << endl;
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
