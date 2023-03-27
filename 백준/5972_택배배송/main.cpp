#include <iostream>
#include <queue>
#include <vector>

using namespace std;

struct Node{
    int to;
    int cost;

    Node operator + (const Node& r){
        return {to + r.to, cost + r.cost};
    }

    bool operator < (const Node &r) const {
        return cost > r.cost;
    }
};

int dijk(vector<vector<Node>> &routes, int src, int dst){
    int dist = 0;
    
    priority_queue<Node> pq;
    vector<int> dists(dst+1, 1e9);

    pq.push({src, 0});
    dists[src] = 0;

    while(!pq.empty()){
        Node cur = pq.top();
        pq.pop();

        for(int i = 0 ; i < routes[cur.to].size() ; i++){
            Node nxt = routes[cur.to][i];
            int nxt_dist = dists[cur.to] + nxt.cost;
            if(dists[nxt.to] <= nxt_dist) continue;
            dists[nxt.to] = nxt_dist;
            pq.push(nxt);
        }
    }

    return dists[dst];
}

int main(void){
    int n, m;

    cin >> n >> m;

    vector<vector<Node>> routes(n+1);

    for(int i = 0 ; i < m ; i++){
        int from, to, cost;
        cin >> from >> to >> cost;
        routes[from].push_back({to, cost});
        routes[to].push_back({from, cost});
    }

    cout << dijk(routes, 1, n) << endl;;

    return 0;
}
