#include <iostream>
#include <vector>
#include <string>
#include <queue>

using namespace std;

struct Pos {
    int r, c;

    Pos operator + (const Pos & p) const {
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
private :
    int R, C;
    vector<vector<int>> board;
    vector<vector<bool>> convex_hull;
    vector<vector<bool>> targets;
    int nr_cheese;

    void init() {
        cin >> R >> C;

        board.resize(R, vector<int>(C, 0));
        convex_hull.resize(R, vector<bool>(C, true));
        targets.resize(R, vector<bool>(C, false));
        nr_cheese = 0;

        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                cin >> board[i][j];
                if(board[i][j] == 1) {
                    nr_cheese++;
                }
            }
        }
    }

    void reset_convex_hull() {
        for(auto &row : convex_hull) {
            std::fill(row.begin(), row.end(), true);
        }
    }

    void reset_targets() { 
        for(auto &row : targets){
            std::fill(row.begin(), row.end(), false);
        }
    }

    bool is_in_range(Pos &p) {
        return (p.r >= 0 && p.r < R) && (p.c >= 0 && p.c < C);
    }

    void find_convex_area() {
        reset_convex_hull();
        queue<Pos> q;
        q.push({0, 0});
        vector<vector<bool>> visited(R, vector<bool>(C, false));
        visited[0][0] = true;
        convex_hull[0][0] = false;

        while(!q.empty()) {
            Pos cur = q.front();
            q.pop();

            for(int i = 0 ; i < 4 ; i++) {
                Pos nxt = cur + dirs[i];

                if(!is_in_range(nxt)) continue;
                if(visited[nxt.r][nxt.c]) continue;
                if(board[nxt.r][nxt.c] == 1) continue;
                q.push(nxt);
                visited[nxt.r][nxt.c] = true;
                convex_hull[nxt.r][nxt.c] = false;
            }
        }
    }

    void mark_if_target(int r, int c) {
        Pos p = {r, c};
        int cnt = 0;

        for(int i = 0 ; i < 4 ; i++) {
            Pos nxt = p + dirs[i];

            if(!convex_hull[nxt.r][nxt.c]) {
                cnt++;
                if(cnt == 2) {
                    targets[r][c] = true;
                    return ;
                }
            }
        }
    }

    void mark_target_cheese() {
        reset_targets();
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if(board[i][j] == 1) {
                    mark_if_target(i, j);
                }
            }
        }
    }

    void erase_target_cheeses() {
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if(targets[i][j] == true) {
                    board[i][j] = 0;
                    nr_cheese--;
                }
            }
        }
    }

    void print_board(int turn) {
        cout << "###########turn " << turn << "###########\n";
        cout << "nr_cheese : " << nr_cheese << "\n";
        for(auto & row : board) {
            for(auto &col : row) {
                cout << col << " ";
            }
            cout << "\n";
        }
        cout << "##################################\n";
    }

public:
    void run() {
        init();
        int turn = 0;

        while(nr_cheese > 0) {
            find_convex_area();
            mark_target_cheese();
            erase_target_cheeses();
            turn++;
        }

        cout << turn << endl;
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
