#include <string>
#include <vector>
#include <queue>

#define INT_MAX 0x01<<29

using namespace std;

struct Node{
    int id;
    int dist;
};

class Solution{
private:
    int n;
    vector<vector<int>> roads;
    vector<vector<int>> graph;
    vector<int> sources;
    int destination;
    
    vector<int> bfs(int start){
        vector<int> dists(n+1, INT_MAX);
        queue<Node> q;
        q.push({start, 0});
        vector<bool> visited(n+1, false);
        visited[start] = true;
        dists[start] = 0;
        
        while(!q.empty()){
            Node cur = q.front();
            q.pop();
            
            dists[cur.id] = (dists[cur.id] > cur.dist) ? cur.dist : dists[cur.id];
            
            for(int i = 0 ; i < graph[cur.id].size() ; i++){
                Node nxt = {graph[cur.id][i] ,cur.dist + 1};
                
                if(visited[nxt.id]) continue;
                visited[nxt.id] = true;
                q.push(nxt);
            }
        }
       
        return dists;
    }
    
    void build_graph(){
        graph.resize(n+1);
        
        for(int i = 0 ; i < roads.size() ; i++){
            int from = roads[i][0];
            int to = roads[i][1];
            graph[from].push_back(to);
            graph[to].push_back(from);
        }
    }
    
public: 
    Solution(int n, vector<vector<int>> roads, vector<int> sources, int destination) : 
        n(n), roads(roads), sources(sources), destination(destination)
    {
        build_graph();    
    }
    
    vector<int> run(){
        vector<int> dists = bfs(destination); 
        vector<int> answer;
        for(auto s : sources){
            if(dists[s] == INT_MAX){
                answer.push_back(-1);
            }else{
                answer.push_back(dists[s]);
            }
        }
        return answer;
    }
};

vector<int> solution(int n, vector<vector<int>> roads, vector<int> sources, int destination) {
    Solution sol(n, roads, sources, destination);
    vector<int> answer = sol.run();
    return answer;
}
