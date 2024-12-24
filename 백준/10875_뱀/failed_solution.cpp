// 실패한 솔루션
// 메모리 초과
// 원인 : input L 에 의해 정해지는 배열의 크기가 4바이트 기준 4 * 10^8 이라 이미 메모리 초과

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

struct Pos {
    int r, c;

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

struct Rotate {
    int t;
    char c;
};

class Snake {
    int head_dir = 1;
    Pos head;

public : 

    void set_head(int r, int c) {
        head.r = r;
        head.c = c;
    }

    Pos get_head() {
        return head;
    }

    int get_head_dir() {
        return head_dir;
    }

    void rotate(char c) {
        //cout << "snake rotate : " << c << endl;
        if(c == 'L'){
            head_dir--;
            if(head_dir < 0) {
                head_dir = 3;
            }
        }else {
            head_dir = (head_dir+1)%4;
        }
    }

    int grow_up(vector<vector<int>> &board, int dt, bool &dead) {
        int spent_time = 0;

        for(spent_time = 1 ; spent_time <= dt ; spent_time++) {
            Pos nxt = head + dirs[head_dir];

            if(nxt.r < 0 || nxt.r > board.size() || nxt.c < 0 || nxt.c > board.size()){
                dead = true;
                break;
            }

            board[nxt.r][nxt.c]++;

            if(board[nxt.r][nxt.c] > 1) {
                dead = true;
                break;
            }
            head = nxt;
        }

        return spent_time > dt ? dt : spent_time;
    }
};

class Solution {
    int L, N;
    int board_size;

    Snake snake;

    vector<vector<int>> board;
    queue<Rotate> rotates_queue;

    void init() {
        cin >> L;
        cin >> N;
        board_size = 2*L + 1;
        board.resize(board_size, vector<int>(board_size, 0));

        for(int i = 0 ; i < N ; i++) {
            Rotate r;
            cin >> r.t >> r.c;
            rotates_queue.push(r);
        }

        snake.set_head(board_size/2, board_size/2);
        board[board_size/2][board_size/2] = 1;
    }

    int simulate() {
        int spent_time = 0;
        bool dead = false;

        //print_board();
        while(!rotates_queue.empty()) {
            Rotate r = rotates_queue.front();
            rotates_queue.pop();
            spent_time += snake.grow_up(board, r.t, dead);
            //print_board();
            if(dead) break;
            snake.rotate(r.c);
        }

        if(!dead) {
            spent_time += snake.grow_up(board, board_size, dead);
        }

        return spent_time;
    }

    void print_board() {
        cout << "------------------board status---------------\n";
        for(auto &r : board) {
            for(auto &c : r) {
                cout << c << " ";
            }
            cout << endl;
        }
    }

public:
    void run() {
        init();
        cout << simulate() << endl;
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}