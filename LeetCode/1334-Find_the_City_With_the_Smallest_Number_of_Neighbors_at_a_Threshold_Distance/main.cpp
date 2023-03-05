struct Edge{
    int index;
    int dist;

    bool operator < (const Edge &e) const {
        return dist > e.dist;
    }
};

class Solution {
private:
    vector<vector<Edge>> graph;

    void init_graph(vector<vector<int>> &edges){
        for(auto edge : edges){
            graph[edge[0]].push_back({edge[1], edge[2]});
            graph[edge[1]].push_back({edge[0], edge[2]});
        }        
    }

    void dijk(int n, int start, int thres, vector<int> &dists){
        priority_queue<Edge> pq;
        pq.push({start, 0});
        dists[start] = 0;

        while(!pq.empty()){
            Edge cur = pq.top();
            pq.pop();

            for(int i = 0 ; i < graph[cur.index].size() ; i++){
                Edge nxt = graph[cur.index][i];

                if(dists[nxt.index] > dists[cur.index] + nxt.dist){
                    dists[nxt.index] = dists[cur.index] + nxt.dist;
                    nxt.dist = dists[nxt.index];
                    pq.push(nxt);
                }
            }
        }
    }

public:
    int findTheCity(int n, vector<vector<int>>& edges, int distanceThreshold) {
        graph.resize(n);        
        init_graph(edges);
        vector<vector<int>> dists(n, vector<int>(n, 1e9));
        
        for(int i = 0 ; i < n ; i++){
            dijk(n, i, distanceThreshold, dists[i]);
        }

        int min_count = n;
        int city_index = -1;
        for(int i = 0 ; i < n ; i++){
            int cnt = 0;
            for(int j = 0 ; j < n ; j++){
                if(i==j) continue;
                if(dists[i][j] <= distanceThreshold) cnt++;
            }

            if(min_count <= cnt){
                city_index = i;
                min_count = cnt;
            }
        }

        return city_index;
    }
};