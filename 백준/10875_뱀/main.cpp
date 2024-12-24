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

struct Line {
    Pos p0, p1;
    bool is_vertical;

    Line(Pos p0, Pos p1) : p0(p0), p1(p1) {
        is_vertical = p0.r != p1.r;
    } 
};


class Solution {
    int L, N;
    queue<Rotate> rotates_queue;
    vector<vector<Line>> lines; // [0][index] => horizontal , [1][index] = vertical;

    Pos head;
    int head_dir = 1;

    void rotate_head(char c) {
        if(c == 'L') {
            head_dir--;
            head_dir = (head_dir < 0) ? 3 : head_dir;
        }else{
            head_dir = (head_dir+1) % 4;
        }
    }

    void init() {
        cin >> L;
        cin >> N;

        head = {(2*L+1)/2, (2*L+1)/2};
    }

    int simulate() {
        int spent_time = 0;
        bool dead = false;
        for(int i = 0 ; i < N ; i++) {
            Rotate r;
            cin >> r.t >> r.c;
            rotates_queue.push(r);

            int length = get_line(r.t, dead);            
            spent_time += length;
            if(dead) break;

            rotate_head(head);
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