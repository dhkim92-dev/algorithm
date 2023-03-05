#include <vector>
#include <queue>

using namespace std;

struct Edge{
    int index;
    double probs;

    bool operator < (const Edge &e) const {
        return probs < e.probs;
    }
};

class Solution {
private:
    vector<vector<Edge>> graph;

    void init_graph(vector<vector<int>>& edges, vector<double>& probs){
        for(int i = 0 ; i < edges.size() ; i++){
            int from = edges[i][0];
            int to = edges[i][1];
            double prob = probs[i];            

            graph[from].push_back({to, prob});
            graph[to].push_back({from, prob});
        }
    }

    vector<double> dijk(int n, int start, int end){
        vector<double> probs(n, 0.0);
        vector<bool> visited(n, false);
        priority_queue<Edge> pq;
        pq.push({start, 1.0});

        while(!pq.empty()){
            int cur_node = pq.top().index;
            double cur_probs = pq.top().probs;
            pq.pop();
            if(cur_probs < probs[cur_node]) continue;

            for(int i = 0 ; i < graph[cur_node].size() ; i++){
                int next_node = graph[cur_node][i].index;
                double next_probs = cur_probs * graph[cur_node][i].probs;
                
                if(next_probs > probs[next_node]){
                    probs[next_node] = next_probs;
                    pq.push({next_node, next_probs});
                }
            }
        }

        return probs;
    }

public:
    double maxProbability(int n, vector<vector<int>>& edges, vector<double>& succProb, int start, int end) {
        graph.resize(n);
        init_graph(edges, succProb);
        vector<double> probs = dijk(n, start, end);

        if(probs[end] == 0.0) return 0;
        return probs[end];
    }
};