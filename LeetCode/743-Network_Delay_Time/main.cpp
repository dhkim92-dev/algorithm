struct Edge{
    int index;
    int delay;

    bool operator < (const Edge &e) const {
        return delay > e.delay;
    }
};

class Solution {
private :
    vector<vector<Edge>> graph;

    void init_graph(vector<vector<int>> &times){
        for(auto time : times){
            graph[time[0]].push_back({time[1], time[2]});
        }
    }

    int dijk(int n, int start){
        vector<int> delays(n+1, 1e9);
        priority_queue<Edge> pq;
        pq.push({start, 0});
        delays[start]=0;

        while(!pq.empty()){
            int cur_node = pq.top().index;
            int cur_delay = pq.top().delay;
            pq.pop();

            if(cur_delay > delays[cur_node]) continue;

            for(int i = 0 ; i < graph[cur_node].size() ; i++){
                int next_node = graph[cur_node][i].index;
                int next_delay = cur_delay + graph[cur_node][i].delay;

                if(next_delay < delays[next_node]){
                    pq.push({next_node, next_delay});
                    delays[next_node] = next_delay;
                }
            }
        }

        int max_delay = 0;
        for(int i = 1 ; i < delays.size() ; i++){
            max_delay = max(delays[i], max_delay);
        }

        if(max_delay == 1e9) return -1;
        return max_delay;
    }

public:
    int networkDelayTime(vector<vector<int>>& times, int n, int k) {
        graph.resize(n+1);
        init_graph(times);    
        if(times.size() < k) return -1;
        return dijk(n, k);
    }
};