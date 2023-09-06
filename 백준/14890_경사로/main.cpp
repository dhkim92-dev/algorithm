#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

 class Solution {
    int N, L, mx_path = 0;
    vector<vector<int>> board;
    vector<bool> visited;

    void init() {
        cin >> N >> L;
        board.resize(N, vector<int>(N, 0));

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0;  j < N ; j++) {
                cin >> board[i][j];
            }
        }
        visited.resize(N, false);
    }

    void col_copy(vector<int> &dst, int col) {
        for(int i = 0 ; i < N ; i++) {
            dst[i] = board[i][col];
        }
    }

    void row_copy(vector<int> &dst, int row) {
        // copy(board[row].begin(), board[row].end(), dst.begin());
        for(int i = 0 ; i < N ; i++) {
            dst[i] = board[row][i];
        }
    }

    bool check(vector<int> &path) {
        // cout << "check start.\n";
        fill(visited.begin(), visited.end(), false);

        int length = 1;
        bool possible = true;
        for(int i = 1 ; i < N ; i++) {
            // cout << "current cursor : " << i << endl;
            int diff = path[i] - path[i-1];
            
            if(diff < -1 || diff > 1) {
                possible = false;
                break;
            }

            if(diff == 0) {
                length++;
                continue;
            }else if(diff == 1) { // 높이가 높아진 경우
                if(length < L) {
                    possible=false;
                    break;
                }

                for(int j = 0 ; j < L ; j++) {
                    if(visited[i-j-1]) {
                        possible = false;
                        break;
                    }
                    visited[i-j-1] = true;
                }
                if(!possible) break;
                length = 1;
            }else if(diff == -1) { // 높이가 낮아진 경우
                // 현재 위치부터 L 길이 만큼 추가 된 지점이 범위 안에 있는가
                if( (i + L) > N) {
                    possible = false;
                    break;
                }

                for(int j = 0 ; j < L ; j++) {
                    if((path[i] != path[i+j]) || visited[i+j]) {
                        possible=false;
                        break;
                    }

                    visited[i+j] = true;
                }
                
                if(!possible) break;

                i = i + L - 1; // 탐색 지점 옮김
                length = 1;
            }
        } 

        return possible;
    }

    void print_path(vector<int> &path) {
        for(auto i : path) {
            cout << i << " ";
        }
        cout << endl;
    }

    void simulate() {
        vector<int> path(N, 0);

        for(int i = 0 ; i < N ; i++) {
            row_copy(path, i);
            if(check(path)) {
                // cout << "row " << i << " is possible.\n";
                // print_path(path);
                mx_path++;
            }

            col_copy(path, i);
            if(check(path)) {
                // cout << "col " << i << " is possible.\n";
                // print_path(path);
                mx_path++;
            }
        }
    }

    void print() {
        cout << mx_path << endl;
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