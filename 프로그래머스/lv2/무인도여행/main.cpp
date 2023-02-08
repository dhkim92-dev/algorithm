#include <string>
#include <vector>
#include <queue>
#include <algorithm>
#include <cctype>
#include <iostream>

using namespace std;

struct Pos{
    int r,c;
    
    Pos operator + (const Pos &p) const {
        return {r + p.r, c + p.c};
    }
};

Pos dirs[4]={
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1}
};

void init_map(vector<string> &maps, vector<vector<int>> &map)
{
    int rs = maps.size();
    int cs = maps[0].length();
    
    map.resize(rs, vector<int>(cs, 0));
    
    for(int r = 0 ; r < rs ; r++){
        for(int c = 0 ; c < cs ; c++){
            if(isdigit(maps[r][c]))
            	map[r][c] = static_cast<int>(maps[r][c]-'0');
        }
    }
}

bool is_in_area(int r, int c, int rs, int cs){
    return ((0<=r) && (r < rs)) && ((0<=c) && (c < cs));
}

int explore_island_area(vector<vector<int>> &map, vector<vector<bool>> &visit, int r, int c)
{
    queue<Pos> q;
    
    q.push({r,c});
    visit[r][c] = true;
    int rs = map.size();
    int cs = map[0].size();
    int area = 0;
    
    while(!q.empty()){
        Pos cur = q.front();
        q.pop();
        area += map[cur.r][cur.c];
        for(int i = 0 ; i < 4 ; i++){
            Pos nxt = cur + dirs[i];
            
            if(!is_in_area(nxt.r, nxt.c, rs, cs)) continue;
            if(visit[nxt.r][nxt.c]) continue;
            if(map[nxt.r][nxt.c] == 0) continue;
            
            q.push(nxt);
            visit[nxt.r][nxt.c] = true;
        }
    }
    
    return area;
}

vector<int> solution(vector<string> maps) {
    //cout << "maps size : " << maps.size() << ", " << maps[0].length() << endl;
    vector<int> answer;
    
    vector<vector<int>> map;
    init_map(maps, map);
    //cout << "map size : " << map.size() << ", "  << map[0].size() << endl;
    
    vector<vector<bool>> visit(map.size(), vector<bool>(map[0].size(), false));
    //cout << "visit size : " << visit.size() << ", " << visit[0].size() << endl;
    
    for(int i = 0 ; i < visit.size() ; i++){
        for(int j = 0 ; j < visit[0].size() ; j++){
            if(map[i][j]==0){
                visit[i][j]=true;
            }
        }
    }
    

    for(int i = 0 ; i < map.size() ; i++){
        for(int j = 0 ; j < map[0].size() ; j++){
            if(!visit[i][j]){
                answer.push_back(explore_island_area(map, visit, i, j));
            }
        }
    }
    
    if(answer.empty()){
        return {-1};
    }
    
    sort(answer.begin(), answer.end());
    
    return answer;
}
