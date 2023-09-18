#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

class Solution {
    int32_t hit, space, miss;
    string x, y;
    vector<vector<int32_t>> dp;

    void init() {
        cin >> hit >> space >> miss;
        cin >> x;
        cin >> y;

        //if(x.length() > y.length()) {
        //    string tmp = x;
        //    x = y;
        //    y = tmp;
        //}
        dp.resize(x.length() + 1, vector<int32_t>(y.length() + 1, 0));
    }

    void simulate() {
        //dp[i][j] = x의 i번째 문자와, y의 문자 까지를 비교했을때 최대 점수
        
        for(int i = 1 ; i <= x.length() ; i++) {
            dp[i][0] = dp[i-1][0] + space;
        }

        for(int i = 1 ; i <= y.length() ; i++) {
            dp[0][i] = dp[0][i-1] + space;
        }

        for(int i = 1 ; i <= x.length() ; i++) {
            for(int j = 1 ; j <= y.length() ; j++) {
                int32_t s = (x[i-1] == y[j-1]) ? dp[i-1][j-1] + hit : dp[i-1][j-1] + miss;
                dp[i][j] = max( dp[i-1][j] + space, dp[i][j-1] + space );
                dp[i][j] = max(dp[i][j], s);
            }
        }
    } 

    void print() {
        cout << dp[x.length()][y.length()] << endl;
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
