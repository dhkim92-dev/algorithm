#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Pos {
    int r, c;
};

class Solution {
    int C, R, L, K;
    int answer = 0;
    vector<Pos> drop_points;

    int check_area(int r, int c) {
        int r_limit = r + L;
        int c_limit = c + L;

        int cnt = 0;

        for(auto &p : drop_points) {
            if((r <= p.r && p.r <= r + L) && (c <= p.c && p.c <= c + L) ) {
                cnt++;
            }
        }

        return cnt;
    }

    void simulate() {
        for(auto &p0 : drop_points) {
            for(auto &p1 : drop_points) {
                answer = max(answer, check_area(p0.r, p1.c));
            }
        }
    }

    void init() {
        cin >> C >> R >> L >> K;

        for(int i = 0 ; i < K ; i++) {
            int r, c;
            cin >> c >> r;
            drop_points.push_back({r, c});
        }
    }

    void print() {
        cout << K-answer << endl;
    }

public :
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