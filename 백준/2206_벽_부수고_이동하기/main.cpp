#include <iostream>
#include <vector>
#include <queue>
#include <climits>
#include <string>

using namespace std;

struct Pos {
    int r, c;
    bool broken;

    Pos operator + (const Pos &p) const {
        return {r + p.r, c + p.c, broken};
    }
};

Pos dirs[4] = {
    {-1, 0, false},
    {0, 1, false},
    {1, 0, false},
    {0, -1, false}
};

class Solution {
    int answer = INT32_MAX;
    vector<vector<int>> board;
    vector<vector<int>> dist[2];
    int N, M;

    bool is_in_range(Pos &p) {
        return (0 <= p.r && p.r < N) && (0 <= p.c && p.c < M);
    }

    void init() {
        cin >> N >> M;
        board.resize(N, vector<int>(M, 0)); // 0 이동가능 1 이동 불가
        dist[0].resize(N, vector<int>(M, 0)); // 벽을 깨지 않고 이동
        dist[1].resize(N, vector<int>(M, 0));  // 벽을 깨고 이동

        string l;
        for(int r = 0 ; r < N ; r++) {
            cin >> l;
            for(int c = 0 ; c < l.length() ; c++) {
                board[r][c] = l[c] - '0';
            }
        }
    }

    int bfs() {
        queue<Pos> q;
        q.push({0, 0, false});
        dist[0][0][0] = 1;

        while(!q.empty()) {
            Pos cur = q.front();
            q.pop();

            if(cur.r == N-1 && cur.c == M-1) {
                return dist[(int)cur.broken][cur.r][cur.c];
            }

            for(int i = 0 ; i < 4 ; i++) {
                Pos nxt = cur + dirs[i];

                if(!is_in_range(nxt)) continue; // 범위 밖

                if(board[nxt.r][nxt.c] == 0 && !dist[(int)cur.broken][nxt.r][nxt.c]) {
                    // 벽이 아닌 경우
                    dist[(int)cur.broken][nxt.r][nxt.c] = dist[cur.broken][cur.r][cur.c] + 1;
                    q.push(nxt);
                }else if(board[nxt.r][nxt.c] && !cur.broken && !dist[1][nxt.r][nxt.c] ){
                    //벽인 경우
                    dist[1][nxt.r][nxt.c] = dist[0][cur.r][cur.c] + 1;
                    nxt.broken = true;
                    q.push(nxt);
                }

            }
        }

        return -1;
    }

    void simulate() {
        answer = bfs();
    }

    void print() {
        cout << answer << endl;
    }
public :
    void run() {
        init();
        simulate();
        print();
    }
};

int main(void) {
    Solution sol;
    sol.run();
    return 0;
}