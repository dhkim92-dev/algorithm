#include <iostream>
#include <vector>

using namespace std;

int M, N;
vector<vector<int>> board;
vector<vector<int>> growth;
vector<int> prefix;

void init_board() {
    board.resize(M+1, vector<int>(M,0));
}

void init_growth() {
    int sz = 2*M-1;
    growth.resize(N, vector<int>(sz, 0));

}

void init_prefix() {
    int sz = (2*M - 1);
    prefix.resize(sz, 0);

    for(int i = 0 ; i < N ; i++) {
        int count;
        for(int j = 0 ; j < 3 ; j++){
            cin >> count;
            prefix[i][j] = count;
        }
    }
}

void process(int day) {
    int sz = 2*M
}

void solution() {
    int sz = 2*M-1;
    int mid_index = sz / 2;
}

int main(void) {
    cin >> M >> N;

    init_board();
    init_growth();
    solution();

    return 0;
}
