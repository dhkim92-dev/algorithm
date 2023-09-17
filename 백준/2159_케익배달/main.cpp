#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

#define INF 1e14
#define DIR_CNT 5

struct Pos {
    int x, y;

    Pos operator + (const Pos &p) const {
        return {x + p.x , y +p.y};
    }
};

Pos dirs[DIR_CNT] = {
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1},
    {0, 0}
};

int32_t m_dist(Pos a, Pos b) {
    return abs(a.x - b.x) + abs(a.y - b.y);
}

bool is_in_range(Pos p) {
    return (0<=p.x && p.x <= 100000) && (0<=p.y && p.y <= 100000);
}

class Solution {
    int32_t N;
    Pos start;
    vector<Pos> clients;
    vector<vector<int64_t>> dp;
    int64_t answer=0;

    void init() {
        cin >> N;
        clients.resize(N);
        
        cin >> start.x >> start.y;

        for(int32_t i = 0 ; i < N ; i++) {
            cin >> clients[i].x >> clients[i].y;
        }

        dp.resize(N+1, vector<int64_t>(5, INF));
    }

    void simulate() {
        // Bottom Up
        // dp[i][j] => clients[i]의 수령지점 j에 도달하는 최소 거리 
        // dp[i][j] = dp[i-1][k] + m_dist(clients[i-1] + dirs[p], clients[i] + dirs[k]) k in range(0, DIR_CNT), p in range(0, DIR_CNT);
        
        // 1. 시작점에서 첫번째 고객 위치의 수령가능 지점 DIR_CNT개의 최소 거리를 구한다.
        for(int i = 0 ; i < DIR_CNT ; i++) {
            if(!is_in_range(clients[0] + dirs[i])) continue;
            dp[0][i] = m_dist(start, clients[0] + dirs[i]);
        }
        // 2. 다음 고객의 각 수령 위치 별로 이전 고객의 각 수령 포인트 별 거리를 구해 최단 거리를 갱신한다.
        for(int i = 1 ; i < N ; i++) {
            for(int j = 0 ; j < DIR_CNT ; j++) {
                Pos cur = clients[i-1] + dirs[j];
                Pos nxt = clients[i];
                
                if(!is_in_range(cur)) continue;
                for(int k = 0 ; k < DIR_CNT ; k++) {
                    Pos _nxt = nxt + dirs[k];
                    if(!is_in_range(_nxt)) continue;
                    dp[i][k] = min(dp[i][k], dp[i-1][j] + m_dist(cur, _nxt));
                }
            }
        }

        answer = INF;

        for(int i = 0 ; i < DIR_CNT ; i++) {
            answer = min(answer, dp[N-1][i]);
        }
    }

    void print() {
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
