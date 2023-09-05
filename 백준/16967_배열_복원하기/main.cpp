#include <iostream>
#include <vector>

using namespace std;

class Solution {
    int H, W;
    int X, Y;
    vector<vector<int>> arr;
    vector<vector<int>> target;

    bool is_intersect_area(int r, int c) {
        return (r >= X && r < H) && (c >= Y && c < W);
    }

    void fill_not_intersection_area() {
        for(int i = 0 ; i < H ; i++) {
            for(int j = 0 ; j < W ; j++) {
                if(!is_intersect_area(i, j)) {
                    target[i][j] = arr[i][j];
                }
            }
        }
    }

    void fill_intersection_area() {
        for(int r = X ; r < H ; r++) {
            for(int c = Y ; c < W ; c++) {
                target[r][c] = arr[r][c] - target[r-X][c-Y];
            }
        }
    }

    void calc() {
        fill_not_intersection_area();
        fill_intersection_area();
    }

    void print() {
        for(auto row : target) {
            for(auto value : row) {
                cout << value << " ";
            }
            cout << "\n";
        }
    }

    void init() {
        cin >> H >> W >> X >> Y;
        arr.resize(H+X, vector<int>(W+Y, 0));
        target.resize(H, vector<int>(W, 0));

        for(int i = 0 ; i < H+X ; i++) {
            for(int j = 0 ; j < W+Y ; j++) {
                cin >> arr[i][j];
            }
        }
    }

public:
    void run() {
        init();
        calc();
        print();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
