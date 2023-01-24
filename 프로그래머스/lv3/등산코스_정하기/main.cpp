#include <unordered_set>
#include <algorithm>
#include <string>
#include <vector>
#include <queue>

using namespace std;

vector<bool> summit_table(50001, false);

struct Edge{
    int idx;
    int cost;
    
    bool operator < (const Edge &e) const {
        return cost > e.cost;
    }
};

bool is_summit(int index){
    //return summits.find(index) != summits.end();
    return summit_table[index];
}

void make_graph(vector<vector<int>> &paths, vector<vector<Edge>> &graph){
    for(auto &path : paths){
        graph[path[0]].push_back({path[1], path[2]});
        graph[path[1]].push_back({path[0], path[2]});
    }
}

vector<int> find_path(
    int n,
	vector<int> &gates,
    vector<int> &summits,
    vector<vector<Edge>>& graph
){
    priority_queue<Edge> pq;
    //unordered_set<int> h_summits(summits.begin(), summits.end());
    vector<int> intensity(n+1, 1e9);
	int summit_index=0, summit_intensity=1e9;
    
    for(auto &gate : gates){
    	pq.push({gate, 0});
        intensity[gate] = 0;
    }
    
    while(!pq.empty()){
    	Edge cur = pq.top();
        pq.pop();
        
        if(is_summit(cur.idx)) continue;
        if(intensity[cur.idx] < cur.cost) continue;
        
        for(auto &nxt : graph[cur.idx]){
            int nxt_intensity = max(cur.cost, nxt.cost);
            
            if(nxt_intensity < intensity[nxt.idx]){
            	intensity[nxt.idx] = nxt_intensity;
                pq.push({nxt.idx, intensity[nxt.idx]});
            }
        }
    }
    
    for(auto summit : summits){
    	if(intensity[summit] < summit_intensity){
        	summit_intensity = intensity[summit];
            summit_index = summit;
        }
    }
    
    return {summit_index, summit_intensity};
}

vector<int> solution(int n, vector<vector<int>> paths, vector<int> gates, vector<int> summits) {
    vector<vector<Edge>> graph(n+1);
    make_graph(paths, graph);
    sort(summits.begin(), summits.end());
    for(auto summit : summits){
    	summit_table[summit] = true;
    }
    return find_path(n, gates, summits, graph);
}