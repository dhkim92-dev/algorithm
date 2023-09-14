#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

struct Node {
    int32_t node;
    int32_t cost;
};

class Solution {
    int32_t N, M, K; // N은 도시 수, M개 도시를 지나는 경로를 구해야하며, ,K는 항공로 개수
    vector< vector<Node> > nodes;
    int32_t dp[301][301];
    int32_t answer = 0;

    void init() {
        cin >> N >> M >> K;
        memset(dp, 0x00, sizeof(dp));
        nodes.resize(N+1);

        for(int i = 0 ; i < K ; i++) {
            int32_t a,b,c;
            cin >> a >> b >> c;
            if(a > b) continue;
            nodes[a].push_back({b, c});
        }

        for(int i = 0 ; i < nodes[1].size() ; i++) {
            Node nxt = nodes[1][i];
            dp[2][nxt.node] = max( dp[2][nxt.node], nxt.cost);
        }
    }


    void simulate() {
        // dp[i][j] => i개의 도시를 방문했고, 1~j번까지 도시 사이를 기준으로 구한 최대값
        
        for(int i = 2 ; i < M ; i++) {
            for(int j = 1 ; j <= N ; j++) {
                for(auto &nxt : nodes[j]) {
                    if(dp[i][j] != 0) {
                        dp[i+1][nxt.node] = max(dp[i+1][nxt.node], dp[i][j] + nxt.cost);
                    }
                }
            }
        }

        for(int i = 0 ; i <= M ; i++) {
            answer = max(answer, dp[i][N]);
        }
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
