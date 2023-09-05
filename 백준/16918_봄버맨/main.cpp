#include <iostream>
#include <vector>
#include <queue>

using namespace std;

struct Pos {
    int r, c;
};

Pos dirs[4] = {
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1}
};

class Solution {
    int R,C,N;
    vector<vector<int>> board;

    void init() {
        cin >> R >> C >> N;

        board.resize(R, vector<int>(C, 0));

        for(int i = 0 ; i < R ; i++) {
            string s;
            cin >> s;
            for(int j = 0 ; j < s.length() ; j++) {
                board[i][j] = (s[j] == 'O') ? 3 : 0;
            }
        }
    }

    bool is_bomb(int r, int c) {
        return board[r][c] > 0;
    }

    bool is_in_range(int r, int c) {
        return (r >= 0 && r < R) && (c >=0 && c < C);
    }

    void setup_bomb(int n) {
        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                board[r][c] = (board[r][c] == 0) ? n+3 : board[r][c]; 
            }
        }
    }

    void boom(int n) {
        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                if(board[r][c] == n) {
                    board[r][c] = 0;
                    for(int i = 0 ; i < 4 ; i++) {
                        int nr = r + dirs[i].r;
                        int nc = c + dirs[i].c;

                        if(!is_in_range(nr, nc)) continue;
                        if(board[nr][nc] != n)
                            board[nr][nc] = 0;
                    }
                }
            }
        }
    }

    void simulate() {
        //처음 1초는 아무것도 하지 않는다.
        for(int i = 2 ; i <= N ; i++) {
            if(i % 2 == 0) {
                // 짝수 초에는 폭탄을 설치한다.
                setup_bomb(i);
            } else {
                boom(i);
            }
        }
    }

    void print() {
        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                char ch = is_bomb(r, c) ? 'O' : '.';
                cout << ch;
            }
            cout << "\n";
        }
    }

    void print_bomb() {
        cout << "-------------timer-----------\n";
        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                cout << board[r][c];
            }
            cout << "\n";
        }
    }

public:
    void run() {
        init();
        simulate();
        print();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
