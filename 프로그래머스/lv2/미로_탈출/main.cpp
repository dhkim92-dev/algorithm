#include <string>
#include <vector>
#include <queue>

using namespace std;

struct Pos{
    int r,c, dist;
    
    Pos operator + (const Pos& p) const {return {r+p.r, c+p.c, dist+p.dist};}
    
    bool operator == (const Pos &p) const {
         return (r==p.r) && (c==p.c);
    }
};

Pos dirs[4]={
    {1,0,1},
    {0,1,1},
    {-1,0,1},
    {0,-1,1}
};

Pos search_point(vector<string> &board, char target){
    Pos p;
    for(int i=0 ; i<board.size() ; i++){
        for(int j=0 ; j<board[0].size() ; j++){
            if(board[i][j] == target){
                p.r=i;
                p.c=j;
                p.dist=0;
                break;
            }
        }
    }
    
    return p;
}

bool range_check(Pos &p, int rs, int cs){
    return (0<=p.r && p.r < rs)&&(0<=p.c && p.c <cs);
}

int bfs(vector<string> &board, Pos src, Pos dst){
    int rs=board.size();
    int cs=board[0].size();
    queue<Pos> q;
    vector<vector<bool>> visited(rs, vector<bool>(cs, false));
    
    q.push(src);
    visited[src.r][src.c]=true;
    
    while(!q.empty()){
        Pos c=q.front();
        q.pop();
        
        if(c == dst){
             return c.dist;
        }
        
        for(int i=0 ; i<4 ; i++){
            Pos nxt = c+dirs[i];
            if(!range_check(nxt, rs, cs)) continue;
            
            if(board[nxt.r][nxt.c] == 'X') continue;
            if(visited[nxt.r][nxt.c]) continue;
            
            visited[nxt.r][nxt.c]=true;
            q.push(nxt);
        }
    }
    
    return -1;
}

int solution(vector<string> board) {
    int answer = 0;
    Pos start = search_point(board, 'S');
    Pos lever = search_point(board, 'L');
    Pos dest = search_point(board, 'E');
    
    int d0 = bfs(board, start, lever);
    int d1 = bfs(board, lever, dest);
    
    if((d0 == -1 )|| (d1 == -1)) return -1;
    
    return d0 + d1;
}
