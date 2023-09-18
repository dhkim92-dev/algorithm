#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

#define DISABLED -1

struct Pos {
    int32_t r, c;

    Pos operator + (const Pos & p) const {
        return {r + p.r, c + p.c};
    }

    Pos operator - (const Pos & p) const {
        return {r - p.r, c - p.c};
    }

    bool operator == (const Pos &p) const {
        return (r == p.r) && (c == p.c);
    }
};

Pos dirs[2] = {
    {1, 0}, // 남
    {0, 1} // 동
};

class Solution {
    int32_t N, M, K;
    vector<vector<int64_t>> dp;
    vector<vector<vector<Pos>>> disabled;

    void init() {
        cin >> N >> M >> K;
        dp.resize(N+2, vector<int64_t>(M+2, 0));
        disabled.resize(N+2, vector<vector<Pos>>(M+2));

        for(int32_t i = 0 ; i < K ; i++) {
            Pos s, e;
            cin >> s.r >> s.c >> e.r >> e.c;
            disabled[s.r][s.c].push_back(e);
            disabled[e.r][e.c].push_back(s);
        }
    }

    bool is_in_range(Pos &p) {
        return (0 <= p.r && p.r <= N) && (0 <= p.c && p.c <= M);
    }

    void simulate() {
        dp[0][0] = 1;
        for(int r = 0 ; r <= N ; r++) {
            for(int c = 0 ; c <= M ; c++) {
                Pos start = {r, c};
                Pos n = start - dirs[0];
                Pos w = start - dirs[1];

                if(is_in_range(n)) {
                    if(find(disabled[r][c].begin(), disabled[r][c].end(), n) == disabled[r][c].end()) {
                        dp[r][c] += dp[r-1][c];
                    }
                }

                if(is_in_range(w)) {
                    if(find(disabled[r][c].begin(), disabled[r][c].end(), w) == disabled[r][c].end()) {
                        dp[r][c] += dp[r][c-1];
                    }
                }
            }
        }
    } 

    void print() {
        cout << dp[N][M] << endl;
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
