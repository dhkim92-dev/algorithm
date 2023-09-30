// 2023-09-30
// 2253 점프
// 분류 다이나믹 프로그래밍

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int32_t dp[10002][151];

class Solution {
    int32_t N, M;
    int32_t answer = 1e9;

    void init() {
        cin >> N >> M;

        for(int32_t i = 0 ; i <= N ; i++) {
            for(int32_t j = 0 ; j <=  150 ; j++) {
                dp[i][j] = answer;
            }
        }

        for(int i = 0 ; i < M ; i++) {
            int32_t inp;
            cin >> inp;
            for(int j = 0 ; j <= 150 ; j++) {
                dp[inp][j] = -1;
            }
        }
    }

    void simulate() {
        // dp[i][j] => i번째 돌에 속도 j로 도달하는 경우
        dp[1][0] = 1;

        for(int32_t i = 1 ; i <= N ; i++) {
            for(int32_t j = 0 ; j <= 150 ; j++) {
                if(dp[i][j] != 1e9 && dp[i][j] != -1) {
                    // dp[i][j] 에 도달하는 경우가 존재한다면
                    int32_t accel = j+1;
                    int32_t deaccel = j-1;

                    if(accel <= 150 && i + accel <= N && dp[i+accel][0] != -1) {
                        dp[i+accel][accel] = min(dp[i+accel][accel],dp[i][j] + 1);
                        // cout << "dp[" << i + accel << "][" << accel << "] updated.\n";
                    }

                    if(i + deaccel <= N && 0 < deaccel  && deaccel <= 150 && dp[i+deaccel][0] != -1) {
                        dp[i+deaccel][deaccel] = min(dp[i+deaccel][deaccel],dp[i][j] + 1);
                        // cout << "dp[" << i + deaccel << "][" << deaccel << "] updated.\n";
                    }

                    if(i + j <= N && dp[i+j][0] != -1 ) {
                        dp[i+j][j] = min(dp[i+j][j], dp[i][j] + 1);
                        // cout << "dp[" << i + j << "][" << j << "] updated.\n";
                    }
                }
            }
        }

        for(int32_t i = 1 ; i <= 150 ; i++) {
            if(dp[N][i] == 0) continue;
            answer = min(dp[N][i], answer);
        }
    }

    void print() {
        if(answer == 1e9 || answer == -1) {
            cout << -1 << endl;
        } else {
            cout << answer - 1 << endl;
        }
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
