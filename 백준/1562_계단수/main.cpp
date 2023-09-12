#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

#define MOD 1000000000

class Solution {
    int32_t N;
    int32_t answer=0;
    int32_t dp[101][10][0x01<<10]={0, };

    void init() {
        cin >> N;
        memset(dp, 0x00, sizeof(dp));
    }

    void simulate() {
        // dp[i][j][bit] = 길이가 i인 숫자 중 끝이 j로 끝나는 계단 수 숫자
        // bit는 0~9까지의 숫자중 사용된 숫자의 bit를 나타낸다.
        // 각 비트는 좌측에서부터 9,8,7,...0 을 나타낸다.

        dp[1][0][1] = 0;

        for(int32_t i = 1 ; i < 10 ; i++) dp[1][i][1 << i] = 1;

        for(int32_t i = 2 ; i <= N ; i++) {
            for(int32_t j = 0 ; j < 10 ; j++) {
                for(int32_t bit = 0 ; bit < (0x01 << 10) ; bit++) {
                    int32_t cbit = bit | (0x01 << j);
                    if(j == 0) {
                        // 끝이 0으로 끝나는 경우 이전 값이 1이어야 한다. 
                        dp[i][j][cbit] = (dp[i][j][cbit] + dp[i-1][1][bit]) % MOD;
                    } else if(j == 9) {
                        // 끝이 9로 끝나는 경우 이전 값이 8이어야 한다. 
                        dp[i][j][cbit] = (dp[i][j][cbit] + dp[i-1][8][bit]) % MOD;
                    } else {
                        dp[i][j][cbit] = (dp[i][j][cbit] + dp[i-1][j-1][bit]) % MOD;
                        dp[i][j][cbit] = (dp[i][j][cbit] + dp[i-1][j+1][bit]) % MOD;
                    }
                }
            }
        }
    }

    void print() {
        int64_t tmp = 0;
        for(int i = 0 ; i < 10 ; i++) {
            tmp += dp[N][i][(0x01<<10) - 1];
        }

        answer = tmp % MOD;

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
