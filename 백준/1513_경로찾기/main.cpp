#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

#define MOD 1000007
int32_t dp[51][51][51][51]={0, };

class Solution {
    int32_t N, M, C;
    //vector<vector<int32_t>> board;
    int32_t board[51][51];

    void init() {
        cin >> N >> M >> C;
        //board.resize(N+1, vector<int32_t>(M+1, 0));
        memset(dp, 0x00, sizeof(dp));
        memset(board, 0x00, sizeof(board));
        dp[1][1][0][0] = 1;

        for(int i = 1 ; i <= C ; i++) {
            int32_t r,c;
            cin >> r >> c;
            board[r][c] = i;
            if(r == 1 && c == 1) { // 출발지가 오락실이라면 
                dp[1][1][i][1] = 1;
                dp[1][1][0][0] = 0;
            }
        }
    }

    void simulate() {
        // dp[r][c][i][j] = r,c 위치, i, 방문한 오락실 번호 최대값, j 방문한 오락실 수

        for(int r = 1 ; r <= N ; r++) {
            for(int c = 1 ; c <= M ; c++) {
                if(r == 1 && c == 1) continue;

                if(board[r][c] > 0) {
                    // 오락실인 경우
                    int32_t target = board[r][c];
                    for(int i = 0 ; i < target ; i++) {
                        for(int j = 0 ; j <= i ; j++) {
                            dp[r][c][target][j+1] += dp[r-1][c][i][j] + dp[r][c-1][i][j];
                            dp[r][c][target][j+1] %= MOD;
                        }
                    }
                }else{
                    // 오락실이 아닌 경우 
                    for(int i = 0 ; i <= C ; i++) {
                        for(int j = 0 ; j <= i ; j++) {
                            dp[r][c][i][j] = dp[r-1][c][i][j] + dp[r][c-1][i][j];
                            dp[r][c][i][j] %= MOD;
                        }
                    }
                }
            }
        }
    } 

    void print() {
        for(int i = 0 ; i <= C ; i++) {
            int32_t answer = 0;

            for(int j = 0 ; j <= C ; j++) {
                answer += dp[N][M][j][i];
                answer %= MOD;
            }
            cout << answer << " ";
        }

        cout << endl;
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
