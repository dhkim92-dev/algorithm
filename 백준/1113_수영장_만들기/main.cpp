#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
#include <string>

using namespace std;

struct Pos {
    int r,c;

    Pos operator + (const Pos &p) const {
        return {r + p.r, c + p.c};
    }
};

Pos dirs[4] = {
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1}
};

class Solution {
    int N, M;
    vector<vector<int>> ground;
    vector<vector<int>> water;
    vector<vector<bool>> visited;
    int max_height = -1;
    int min_height = 11;

    void init() {
        cin >> N >> M;
        ground.resize(N, vector<int>(M, 0));
        
        for(int i = 0 ; i < N ; i++) {
            string s;
            cin >> s;

            for(size_t j = 0 ; j < s.length() ; j++) {
                ground[i][j] = s[j] - '0';
                max_height = max(max_height, ground[i][j]);
                min_height = min(min_height, ground[i][j]);
            }
        }

        water.resize(N, vector<int>(M, 0));
        visited.resize(N, vector<bool>(M, false));

        for(int i = 1 ; i < N-1 ; i++) {
            for(int j = 1 ; j < M-1 ; j++) {
                water[i][j] = max_height - ground[i][j];
            }
        }
    }

    void reset_visited() {
        for(auto & v : visited) {
            std::fill(v.begin(), v.end(), false);
        }
    }

    bool is_in_range(int r, int c) {
        return (r >= 0 & r < N) && ( c >= 0 && c < M);
    }

    void bfs(int r, int c, int height) {
        queue<Pos> q;
        q.push({r, c});
        visited[r][c] = true;

        while(!q.empty()) {
            Pos cur = q.front();
            q.pop();
            water[cur.r][cur.c]--;

            for(auto dir : dirs) {
                Pos nxt = cur + dir;

                if(visited[nxt.r][nxt.c]) continue;
                if(!is_in_range(nxt.r, nxt.c)) continue;
                if(water[nxt.r][nxt.c] + ground[nxt.r][nxt.c] == height && water[nxt.r][nxt.c] > 0) { // 물이 존재하고 땅높이 + 물 높이가 현재 탐색 높이에 해당하는 경우
                    visited[nxt.r][nxt.c] = true;
                    q.push(nxt);
                }
            }
        }
    }

    void simulate(int h) {
        reset_visited();
        for(int i = 1 ; i < N - 1 ; i++) {
            for(int j = 1 ; j < M -1 ; j++) {
                Pos cur = {i, j};
                if(water[i][j] > 0 && !visited[i][j]) { // 물이 존재하고, 방문하지 않았다면
                    for(Pos dir : dirs) { // 주변 네 방향을 탐색한다.
                        Pos nxt = cur + dir; 
 
                        if( (ground[nxt.r][nxt.c] + water[nxt.r][nxt.c]) < (ground[cur.r][cur.c] + water[cur.r][cur.c]) ) { // 주변 물 높이 + 땅 높이가 현재 물 높이 + 땅 높이보다 낮은 경우 탐색
                            bfs(cur.r, cur.c, h);
                            break;
                        }
                    }
                }
            }
        }
    }

    int count() {
        int cnt = 0;
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                cnt += water[i][j];
            }
        }

        return cnt;
    }

    void print() {
        cout << count() << endl;
    }

    void print_ground() {
        cout << "---------------ground-----------------\n";
        for(auto & g : ground) {
            for(auto v : g) {
                cout << v << ' ';
            }
            cout << endl;
        }
        cout << "------------------------------------\n";
    }


    void print_water() {
        cout << "---------------water-----------------\n";
        for(auto & w : water) {
            for(auto v : w) {
                cout << v << ' ';
            }
            cout << endl;
        }
        cout << "------------------------------------\n";
    }

public:
    void run() {
        init();
        // print_ground();
        // print_water();
        for(int i = max_height ; i > min_height ; i--) {
            simulate(i);
            // print_ground();
            // print_water();
        }
        print();
    }
};


int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}

