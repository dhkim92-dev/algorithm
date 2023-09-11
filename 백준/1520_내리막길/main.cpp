// DFS + DP 혼합 문제

#include <iostream>
#include <vector>
#include <climits>
#include <stdint.h>


using namespace std;

struct Pos {
    int32_t r, c;

    Pos operator + (const Pos & p) const {
        return {r + p.r, c + p.c};
    }
};

Pos dirs[4] = {
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1}
};

class Solution {
    int32_t R, C;
    vector<vector<int32_t>> board;
    vector<vector<int32_t>> dp;

    bool is_in_range(Pos p) {
        return (1 <= p.r && p.r <= R) && (1 <= p.c && p.c <= C); 
    }

    void init() {
        cin >> R >> C;
        board.resize(R+1, vector<int32_t>(C+1, 0));
        dp.resize(R+1, vector<int32_t>(C+1, -1));

        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                cin >> board[i+1][j+1];
            }
        }
    }

    int32_t dfs(int r, int c) {
        if(r == R && c == C) {
            return 1;
        }

        if(dp[r][c] != -1) {
            return dp[r][c];
        }

        dp[r][c] = 0;
        Pos cur = {r, c};

        for(int i = 0 ; i < 4 ; i++) {
            Pos nxt = cur + dirs[i];

            if(!is_in_range(nxt)) continue;
            if( board[nxt.r][nxt.c] >= board[r][c] ) continue;

            dp[r][c] += dfs(nxt.r, nxt.c);
        }

        return dp[r][c];
    }

    void simulate() {
        // 항상 높이가 높은곳에서 낮은곳으로만 이동 가능하다.
        // dp[y][x] = > y,x 에서 N, M  까지 도달 가능한 경우의 수
        dp[R][C] = dfs(1, 1);
    }

    void print() {
        cout << dp[R][C] << endl;
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
