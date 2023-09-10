#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

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
    {0,-1}
};

class Solution{
    int N, M;
    vector<vector<int>> terrain;

    void init() {
        cin >> N >> M;
        terrain.resize(N+1, vector<int>(M+1, 0));

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                cin >> terrain[i+1][j+1];
            }
        }
    }

    void simulate() {
        // 첫행은 좌 -> 우 움직임만 가능하다.
        for(int i = 1 ; i <= M ; i++){
            terrain[1][i] += terrain[1][i-1];
        }

        for(int i = 2 ; i <= N ; i++) {
            vector<int> ltor; 
            vector<int> rtol; 
            copy(terrain[i].begin(), terrain[i].end(), back_inserter(ltor)) ;
            copy(terrain[i].begin(), terrain[i].end(), back_inserter(rtol)) ;

            //좌측에서 우측으로 진행
            for(int j = 1 ; j <= M ; j++) {
                if(j == 1) {
                    // 첫번째 열은 위에서 내려오는 경우만
                    ltor[j] += terrain[i-1][j];
                }else{
                    // 나머지는 좌측에서 우측으로 진행하는 경우와 위에서 아래로 내려오는 경우
                    ltor[j] += max( terrain[i-1][j], ltor[j-1] );
                }
            }

            //우측에서 좌측으로 진행
            for(int j = M ; j >= 1 ; j--) {
                if(j == M) {
                    rtol[j] += terrain[i-1][j];
                }else{
                    rtol[j] += max( terrain[i-1][j], rtol[j+1] );
                }
            }

            for(int j = 1 ; j <= M ; j++) {
                terrain[i][j] = max(ltor[j], rtol[j]);
            }
        }
    }

    void print() {
        cout << terrain[N][M] << endl;
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