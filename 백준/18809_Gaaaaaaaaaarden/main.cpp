#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

#define LAKE 0
#define IMPOSSIBLE 1
#define POSSIBLE 2
#define RED_DRUG 1
#define GREEN_DRUG 2

struct Pos {
    int r, c;
    
    Pos operator + (const Pos &p) const {
        return {r + p.r, c + p.c};
    }

    bool operator < (const Pos &p) const {
        if(r == p.r) {
            return c < p.c;
        }

        return r < p.r;
    }
};

Pos dirs[4] = {
    {-1, 0}, 
    {0, 1},
    {1, 0},
    {0, -1}
};

struct DrugInfo{
    int t;
    int drug_type;
    Pos p;
};


class Solution {
    int N, M, G, R;
    vector<vector<int>> garden;
    vector<vector<int>> visited;
    vector<Pos> candidates;
    vector<bool> gp, rp;

    int mx_flowers = 0;

    void init() {
        cin >> N >> M >> G >> R;

        garden.resize(N, vector<int>(M, 0));
        visited.resize(N, vector<int>(M, 0));

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                cin >> garden[i][j];

                if(garden[i][j] == POSSIBLE) {
                    candidates.push_back({i, j});
                }
            }
        }

        for(int i = 0 ; i < candidates.size() ; i++) {
            gp.push_back( i < G ); // total.Combination.G
            rp.push_back( i < R );
        }
        sort(gp.begin(), gp.end());
        sort(rp.begin(), rp.end());
    }

    bool is_in_range(int r, int c) {
        return (0<= r && r < N) && (0<=c && c < M);
    } 

    bool is_possible(int r, int c) {
        return garden[r][c] == POSSIBLE;
    }

    void reset_visited() {
        for(auto &row : visited) {
            fill(row.begin(), row.end(), 0);
        }
    }

    void print_garden() {
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                cout << garden[i][j] << " ";
            }
            cout << "\n";
        }
    }

    void print_cadidates(vector<Pos> &c, string name) {
        cout << "candidates of " << name << endl;
        for(auto p : c) {
            cout << "    " << p.r << ", " << p.c << endl;
        }
        cout << endl;
    }

    void print_perm(vector<bool> &p, string name) {
        cout << "permutation of " << name << endl;
        cout << "   ";
        for(auto e : p) {
            cout << e << " ";
        }
        cout << endl;
    }

    void bfs(vector<Pos> &greens, vector<Pos> &reds) {
        int flower_count = 0;
        reset_visited();
        queue<DrugInfo> q;
        vector<vector<int>> reach_time(N, vector<int>(M, 1e9));

        for(auto g : greens) {
            q.push({0, GREEN_DRUG, g});
            reach_time[g.r][g.c] = 0;
            visited[g.r][g.c] = GREEN_DRUG;
        }

        for(auto r : reds) {
            q.push({0, RED_DRUG, r});
            visited[r.r][r.c] = RED_DRUG;
            reach_time[r.r][r.c] = 0;
        }

        while(!q.empty()) {
            auto cur = q.front();
            // cout << "cur : " << cur.p.r << ", " << cur.p.c << " with type : " << cur.drug_type << endl;
            q.pop();

            if(visited[cur.p.r][cur.p.c]==3) {
                // 꽃이 피었다면 확산 중지.
                continue;
            }

            for(int i = 0 ; i < 4 ; i++) {
                DrugInfo nxt = {cur.t + 1, cur.drug_type, cur.p + dirs[i]};
                // cout << "   explore nxt : " << nxt.p.r << " , " << nxt.p.c << " testing..." <<endl;

                if(!is_in_range(nxt.p.r, nxt.p.c)) continue; // 범위 밖이면 패스

                if(garden[nxt.p.r][nxt.p.c] == LAKE) continue; // 호수면 패스

                if(visited[nxt.p.r][nxt.p.c] == 3) continue; // 꽃이 피었으면 패스

                if(visited[nxt.p.r][nxt.p.c] == nxt.drug_type) continue; // 같은 종류의 배양액이 사용된 토지인 경우 패스

                if(reach_time[nxt.p.r][nxt.p.c] < nxt.t) continue; // 가능하지만 이미 이전 턴에 배양액이 선점한 토지의 경우 패스

                // cout << "   explore nxt : " << nxt.p.r << " , " << nxt.p.c << " complete!" <<endl;

                q.push(nxt);
                visited[nxt.p.r][nxt.p.c] += nxt.drug_type;
                reach_time[nxt.p.r][nxt.p.c] = nxt.t;

                if(visited[nxt.p.r][nxt.p.c] == 3) flower_count++;
            }
        }

        mx_flowers = max(mx_flowers, flower_count);
    }


    void simulate() {
        // print_garden();
        do {
            vector<Pos> greens;
            for(int i = 0 ; i < candidates.size() ; i++) {
                if(gp[i]) greens.push_back(candidates[i]); 
            }

            // print_perm(gp, "greens");
            do {
                vector<Pos> reds;
                // print_perm(rp, "reds");

                for(int i = 0 ; i < candidates.size() ; i++) {
                    if(gp[i]) {
                        continue;
                    }
                    if(rp[i]) reds.push_back(candidates[i]);
                }

                if( (greens.size() + reds.size()) == (R + G)) {
                    // print_cadidates(greens, "greens");
                    // print_cadidates(reds, "reds");
                    // cout << "run!\n";
                    bfs(greens,reds);
                }
            } while(next_permutation(rp.begin(), rp.end()));
        } while(next_permutation(gp.begin(), gp.end()));
    }

public :
    void run() {
        init();
        simulate();
        cout << mx_flowers << endl;
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}