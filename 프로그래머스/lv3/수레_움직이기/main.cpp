#include <string>
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

#define EMPTY 0
#define WALL 5
#define RED 0
#define BLUE 1
#define INFINITE 1e9

struct Pos {
    int r, c;
    
    Pos operator + (const Pos &p) const {
        return {r + p.r , c + p.c};
    }
    
    bool operator == (const Pos &p) const {
        return (r == p.r) && (c == p.c);
    }
    
    bool operator != (const Pos &p) const {
        return (r != p.r) || (c != p.c);
    }
    
    void print() {
        cout << "{" << r << ", " << c << "}\n";
    }
};

Pos dirs[4] = {
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1}
};

class Solution {
    vector<vector<int>> &maze;
    vector<vector<bool>> visited[2];
    Pos vehicles[2];
    Pos goals[2];
    int answer = INFINITE;
    size_t rs,cs;
    
    void init() {
        rs = maze.size();
        cs = maze[0].size();
        visited[0].resize(rs, vector<bool>(cs, false));
        visited[1].resize(rs, vector<bool>(cs, false));
        
        for(size_t i = 0 ; i < rs ; i++) {
            for(size_t j = 0 ; j < cs ; j++) {
                if(maze[i][j] == 1) {
                    vehicles[RED].r = i;
                    vehicles[RED].c = j;
                }else if(maze[i][j] == 2) {
                    vehicles[BLUE].r = i;
                    vehicles[BLUE].c = j;
                }else if(maze[i][j] == 3) {
                    goals[RED].r = i;
                    goals[RED].c = j;
                }else if(maze[i][j] == 4) {
                    goals[BLUE].r = i;
                    goals[BLUE].c = j;
                }
            }
        } 
    }
    
    bool is_in_range(Pos p) {
        return (0 <= p.r && p.r < rs) && (0 <= p.c && p.c < cs);
    }
    
    bool is_block(Pos p) {
        return maze[p.r][p.c] == WALL;
    }
    
    bool is_visit(vector<vector<bool>> &target, Pos &p) {
        //cout << "visited : " << target[p.r][p.c] << endl;
        return target[p.r][p.c];
    }
    
    void set_visit(vector<vector<bool>> &target, Pos &p, bool value) {
        target[p.r][p.c] = value;
    }
    
    // red : 빨강 수레 위치
    // blue : 파랑 수레 위치
    // turn : 움직인 턴의 수 
    void play(Pos red, Pos blue, int turn) {
        if(red == goals[RED] && blue == goals[BLUE]) {
            //cout << "answer updated\n";
            answer = min(turn, answer);
            return;
        }
        
        for(int i = 0 ; i < 4 ; i++) {
            Pos nxt_red = red, nxt_blue = blue;
            bool red_stay = true, blue_stay = true;
            
            if(nxt_red != goals[RED]) {
                // 아직 빨강 수레가 목적지에 도착하지 않은 경우
                // 새로운 위치를 시도하기 위해 업데이트 한다.
                nxt_red = red + dirs[i];
                red_stay = false;
            }
            
            //cout << " nxt red : ";
            //nxt_red.print();
            
            if( !red_stay && !is_in_range(nxt_red) ) {
                //cout << "red is not in range." << endl;    
                continue;
            }
            
            if( !red_stay && is_visit(visited[RED], nxt_red) ) {
                //cout << "red is already visited." << endl;    
                continue;
            }
            
            if( !red_stay && is_block(nxt_red) ) {
                //cout << "red current blocked." << endl;    
                continue;
            }
            
            for(int j = 0 ; j < 4 ; j++) {
                if(nxt_blue != goals[BLUE]) {
                    // 아직 파랑 수레가 목적지에 도착하지 않은 경우
                    // 새로운 위치를 시도하기 위해 업데이트 한다.
                    nxt_blue = blue + dirs[j];
                    blue_stay = false;
                }
                
                //cout << " nxt blue : ";
                //nxt_blue.print();
                
                if( !blue_stay && !is_in_range(nxt_blue) ) {
                    //cout << "blue is not in range.\n";
                    continue;
                }
                
                if( !blue_stay && is_visit(visited[BLUE], nxt_blue) ) {
                    //cout << "blue is already visited.\n";
                    continue;
                }
                
                if( !blue_stay && is_block(nxt_blue) ) {
                    //cout << "blue is blocked.\n";
                    continue;
                }
                
                if(red == nxt_blue && blue == nxt_red) {
                    //cout << "red blue swap is impossible.\n";
                    continue;
                }
                
                if( nxt_red == nxt_blue ) {
                    continue;
                }
                
                set_visit(visited[RED], nxt_red, true);
                set_visit(visited[BLUE], nxt_blue, true);
                play(nxt_red, nxt_blue, turn + 1);
                set_visit(visited[RED], nxt_red, false);
                set_visit(visited[BLUE], nxt_blue, false);
            }
        }
    }
    
    public:
    
    Solution(vector<vector<int>> &maze) : maze(maze) {
        init();
    }
    
    int run() {
        visited[RED][vehicles[RED].r][vehicles[RED].c] = true;
        visited[BLUE][vehicles[BLUE].r][vehicles[BLUE].c] = true;
        //cout <<"red start position : {" <<  vehicles[RED].r << ", " << vehicles[RED].c << "}" << endl; 
        //cout <<"blue start position : {" <<  vehicles[BLUE].r << ", " << vehicles[BLUE].c << "}" << endl; 
        play(vehicles[RED], vehicles[BLUE], 0);
        return (answer == INFINITE) ? 0 : answer;
    }
};

int solution(vector<vector<int>> maze) {
    Solution sol(maze);
    return sol.run();
}
