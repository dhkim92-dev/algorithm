#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

#define INF 1e9

class Solution {
    int32_t N;
    int32_t na, nb, answer;
    vector<int32_t> a,b;
    vector<vector<vector<int32_t>>> dp;
    
    void init() {
        cin >> N;
        a.resize(N, 0);
        b.resize(N, 0); 
        na = 0; 
        nb = 0;

        int32_t p;
        for(int i = 0 ; i < N ; i++) {
            cin >> p;
            if(p!=0) {
                a[++na] = p;
            }
        }

        for(int i = 0 ; i < N ; i++) {
            cin >> p;
            if(p!=0) {
                b[++nb] = p ;
            }
        }

        dp.resize(N+1, vector<vector<int32_t>>(N+1, vector<int32_t>(N+1, -1 * INF)));
    }

    int32_t dfs(int32_t i, int32_t j, int32_t k) {
        if(i > k || j > k) return -1 * INF;
        if(!i || !j || !k) return 0;
        if(dp[i][j][k] != -1 * INF) return dp[i][j][k];

        int32_t mx = max(dfs(i-1,j,k-1), dfs(i, j-1, k-1));
        dp[i][j][k] = max(mx, dfs(i-1, j-1, k-1) + a[i] * b[j]);
        //cout << "dp["<<i<<"]["<<j<<"]["<<k<<"] : " << dp[i][j][k] << endl;

        return dp[i][j][k];
    }

    void simulate() {
        // dp[i][j][k] => ith number of a and jth number of b put on k then max value;
        // dp[i][j][k] = dp[i-1][j-1][k-1] + a[i] * b[j]
        answer = dfs(na, nb, N);
    }

    void print() {
        cout << answer << endl;
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
