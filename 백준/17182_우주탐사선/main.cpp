#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int N, K;

struct Node {
    int weight;
};

vector<vector<int>> dist;
vector<bool> visited;

void dfs(int idx, int d, int nr_visit, int &answer) {
    if(answer < d) {
        return ;
    }

    if(nr_visit == N) {
        answer = min(answer, d);
        return ;
    }

    for(int i = 0 ; i < N ; i++) {
        if(visited[i]) continue;
        visited[i] = true;
        dfs(i, d + dist[idx][i], nr_visit + 1, answer);
        visited[i] = false;
    }
}

int main(void)
{
    cin >> N >> K;
    dist.resize(N, vector<int>(N, 0));
    visited.resize(N, false);

    for(int i = 0 ; i < N ; i++) {
        for(int j = 0 ; j < N ; j++) {
            cin >> dist[i][j];
        }
    }

    for(int k = 0 ; k < N ; k++) {
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                if(dist[i][k] + dist[k][j] < dist[i][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }

    int answer = 1e9;

    dfs(K, 0, 0, answer);

    cout << answer << endl;

    return 0;
}
