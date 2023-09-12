#include <iostream>
#include <vector>
#include <algorithm>

#define INF 987654321

using namespace std;

class Solution {
    int32_t N;
    vector<vector<int32_t>> w; // w == 0 인 경우 갈 수 없는 길이다.
    vector<vector<int32_t>> dp;
    int32_t visited_all;
    int32_t answer = INT32_MAX;
    
    void init() {
        cin >> N;
        w.resize(N, vector<int32_t>(N, 0));
        dp.resize(N, vector<int32_t>((0x01<<16), -1));

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                cin >> w[i][j];
            }
        }
        visited_all = (0x01 << N) - 1; // 1111 1111 1111 1111
    }

    int32_t simulate(int32_t cur, int32_t bit) {
        if(bit == visited_all) {
            return (w[cur][0] == 0) ?  INF : w[cur][0];
        }

        if(dp[cur][bit] != -1) {
            return dp[cur][bit];
        }

        dp[cur][bit] = INF;

        for(int i = 0 ; i < N ; i++) {
           if( w[cur][i] == 0 ) continue; // 경로가 존재하지 않는 경우
           if( (bit & (0x01 << i))  == (0x01 << i)) continue; // 이미 방문한 경우

           dp[cur][bit] = min(dp[cur][bit], w[cur][i] + simulate(i, bit | 0x01 << i) ); 
        }

        return dp[cur][bit];
    }

    void print() {
        cout << answer << endl;
    }
public:
    void run() {
        init();
        answer = simulate(0, 1);
        print();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
