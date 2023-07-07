#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <queue>
#include <set>

using namespace std;

#define WHITE 0
#define RED 1
#define BLUE 2

struct Pos {
    int r,c;

    Pos operator + (const Pos &p) const {
        return {r + p.r, c + p.c};      
    }
};

struct Horse {
    Pos p;
    int dir;
    int index;

    bool operator == (const Horse p) const {
        return this->index == p.index;
    }
};

Pos dirs[4] = {
    {0, 1},
    {0, -1},
    {-1, 0},
    {1, 0}
};

int reverse_dir[4] = {1, 0, 3, 2};

class Solution {
private:
    int R, C, K;
    vector<Horse*> horses;
    vector<vector<int>> board;
    int nr_horses;
    vector<vector<vector<Horse*>>> h_board;

    void print_board() {
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                cout << h_board[i][j].size() << " ";
            }
            cout << "\n";
        }
    }
    
    void init(){
        cin >> R >> K;
        C = R;

        board.resize(R, vector<int>(C, 0));

        h_board.resize(R, vector<vector<Horse *>>(C));
        nr_horses = K;

        for(int i = 0 ; i < R ; i++) {
            for(int j =0  ; j < C ; j++) {
                cin >> board[i][j];
            }
        }

        for(int i = 0 ; i < K ; i++) {
            Horse* h = new Horse();
            cin >> h->p.r >> h->p.c >> h->dir;
            h->p.r--;
            h->p.c--;
            h->index = i;
            h->dir--;
            horses.push_back(h);
            h_board[h->p.r][h->p.c].push_back(h);
        }
    }

    bool is_in_range(Pos p) {
        return (p.r >=0 && p.r < R) && (p.c >= 0 && p.c < C);
    }

    bool is_bottom(Horse *h) {
        //if (h == *h_board[h->p.r][h->p.c].begin()) {
        //    cout << "horse " << h->index << " is bottom\n";
        //}
        return h == *h_board[h->p.r][h->p.c].begin();
    }

    void move_red(Pos from, Pos to) {
        reverse(h_board[from.r][from.c].begin(), h_board[from.r][from.c].end());
        for(Horse *h : h_board[from.r][from.c]) {
            h->p = to;
        }

        copy(h_board[from.r][from.c].begin(), h_board[from.r][from.c].end(), back_inserter(h_board[to.r][to.c]));
        h_board[from.r][from.c].clear();
    }

    void move_white(Pos from, Pos to) {
        for(Horse *h : h_board[from.r][from.c]) {
            h->p = to;
        }

        copy(h_board[from.r][from.c].begin(), h_board[from.r][from.c].end(), back_inserter(h_board[to.r][to.c]));
        h_board[from.r][from.c].clear();

    }

    void turn_over(Pos p) {
        h_board[p.r][p.c][0]->dir = reverse_dir[h_board[p.r][p.c][0]->dir];
    }

    void move_horse(int idx){
        Pos nxt = horses[idx]->p + dirs[horses[idx]->dir];

        int color = BLUE;
        if(is_in_range(nxt)) {
            color = board[nxt.r][nxt.c];
        }

        if(color == RED){
            move_red(horses[idx]->p, nxt);
        }else if(color == WHITE){
            move_white(horses[idx]->p, nxt);
        }else{
            turn_over(horses[idx]->p);
            nxt = horses[idx]->p + dirs[horses[idx]->dir];
            if(!is_in_range(nxt)) return ;
            color = board[nxt.r][nxt.c];
            if(color == RED){
                move_red(horses[idx]->p, nxt);
            }else if(color == WHITE){
                move_white(horses[idx]->p, nxt);
            }
        }
    }

    bool end_game() {
        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                if(h_board[r][c].size() >= 4) return true;
            }
        }
        return false;
    }

    void process_turn() {
        for(int i = 0 ; i < K ; i++) {
            if(!is_bottom(horses[i])) continue;
            move_horse(i);
        }
    }

    void print_horse() {
        for(Horse * h : horses) {
            cout << "horse[" << h->index << "] r : " << h->p.r << " c : " << h->p.c << " dir : " << h->dir << endl;
        }
    }

public:

    void run() {
        int turn = 0 ;
        init();

        //print_board();
        //print_horse();
        while(!end_game()) {
            if(turn > 1000) {
                turn = -1;
                break;
            }
            process_turn();
            turn++;
            //cout << "turn : "<< turn << endl;
            //print_board();
            //print_horse();
        }

        cout << turn << endl;
    }

    ~Solution() {
        for(Horse *h : horses) {
            delete h;
        }
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
