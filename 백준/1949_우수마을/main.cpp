#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
    int32_t N;
    vector<int32_t> population;
    vector<vector<int32_t>> adj;
    vector<vector<int32_t>> dp; 
    vector<bool> visited;
    
    void init() {
        cin >> N;
        population.resize(N+1);
        visited.resize(N+1, false);
        dp.resize(N+1, vector<int32_t>(2, 0));
        adj.resize(N+1);

        for(int i = 0 ; i < N ; i++) {
            cin >> population[i+1];
        }

        for(int i = 0 ; i < N - 1 ; i++) {
            int32_t from, to;
            cin >> from >> to;
            adj[from].push_back(to);
            adj[to].push_back(from);
        }
    }

    void dfs(int idx) {
        visited[idx] = true;

        dp[idx][0] = 0;
        dp[idx][1] = population[idx];

        for(size_t i = 0 ; i < adj[idx].size() ; i++) {
            int32_t nxt = adj[idx][i];

            if(visited[nxt]) continue;
            dfs(nxt);

            dp[idx][0] += max( dp[nxt][0], dp[nxt][1] );
            dp[idx][1] += dp[nxt][0];
        }
    }

    void simulate() {
        // dp[i][0] = i번째 마을을 방문하지 않았을 때 최대값 
        // dp[i][1] = i번째 마을을 방문했을때 최대값 
        dfs(1);
    }

    void print() {
        cout << max(dp[1][0], dp[1][1]) << endl;
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
