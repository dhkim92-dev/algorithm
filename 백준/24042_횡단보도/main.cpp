#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>
#include <queue>
#include <stdint.h>

using namespace std;

struct Signal {
    int32_t node;
    int64_t dist;

    bool operator < (const Signal &s) const {
        return dist > s.dist;
    }
};

class Solution{
    int N, M;
    vector<vector<Signal>> adj;
    vector<int64_t> dist;

    void init() {
        cin >> N >> M;
        adj.resize(N+1);
        dist.resize(N+1, INT64_MAX);
        
        for(int64_t t = 0 ; t < M ; t++) {
            int from, to;
            cin >> from >> to;
            adj[from].push_back({to, t});
            adj[to].push_back({from, t});
        }
    }

    void simulate() {
        priority_queue<Signal> pq;
        dist[1] = 0;
        dist[0] = 0;
        pq.push({1, 0});

        while(!pq.empty()) {
            auto cur = pq.top();
            pq.pop();

            if(cur.node == N) break;
            
            for(auto nxt : adj[cur.node]) {
                int64_t nxt_dist = cur.dist + ((nxt.dist - cur.dist) % M + M)%M + 1;

                if(dist[nxt.node] > nxt_dist) {
                    dist[nxt.node] = nxt_dist;
                    pq.push({nxt.node, nxt_dist});
                }
            }
        }
    }

    void print() {
        cout << dist[N] << endl;
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