#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <cstring>

using namespace std;

#define MAX_SIZE 105

int sticker[MAX_SIZE][MAX_SIZE];
int N,M,K,r, c;

void init_sticker() {
    cin >> r >> c ;
    memset(sticker, 0x00, sizeof(sticker));

    for(int i = 0 ; i < r ; i++) {
        for(int j = 0 ; j < c ; j++) {
            cin >> sticker[i][j];
        }
    }
}

void rotate_sticker() {
    int tmp[MAX_SIZE][MAX_SIZE]={0,};

    /**
    * 01 
    * 01   000
    * 01   111
    */

    for(int i = 0 ; i < c ; i++) {
        for(int j = 0 ; j < r ; j++) 
            tmp[i][j] = sticker[r-j-1][i]; 
    }

    for(int i = 0 ; i < c ; i++){
        for(int j = 0 ; j < r ; j++)
            sticker[i][j] = tmp[i][j];
    }

    swap(r, c);
}

void print_sticker() {
    cout << "-----print-----\n";
    cout << "r : " << r << " c : " << c << endl;
    for(int i = 0 ; i < r ; i++) {
        for(int j = 0 ; j < c ; j++) {
            cout << sticker[i][j];
        }
        cout << endl;
    }
}

bool attachable(vector<vector<int>>& board, int y, int x) {
    for(int i = 0 ; i < r ; i++) {
        for(int j = 0 ; j < c ; j++) {
            if(sticker[i][j] == 1 && board[i+y][j+x] == 1) {
                return false;
            }
        }
    }
    return true;
}

void attach(vector<vector<int>> &board, int y, int x) {
    for(int i = 0 ; i < r ; i++) {
        for(int j = 0 ; j < c ; j++) {
            if(sticker[i][j]==1) board[y+i][x+j] = sticker[i][j];
        }
    }
}

bool try_attach(vector<vector<int>> &board) {
    for(int i = 0 ; i < N-r+1 ; i++) {
        for(int j = 0 ; j < M-c+1 ; j++) {
            if(attachable(board, i, j)){
                attach(board, i, j);
                return true;
            }
        }
    }

    return false;
}

int count_empty_space(int N, int M, vector<vector<int>> &board) {
    int count = 0;
    for(int i = 0 ; i < N ; i++) {
        for(int j = 0 ; j < M ; j++) {
            count += static_cast<int>(board[i][j]==1);
        }
    }

    return count;
}

void run(vector<vector<int>> &board) {
    init_sticker();
    
    if(!try_attach(board)){
        for(int i = 0 ; i < 3 ; i++) {
            rotate_sticker();
            if(try_attach(board)) break;
        }
    }
}

void solution(vector<vector<int>>& board) {
    for(int i = 0 ; i < K ; i++) {
        run(board);
    }
    cout << count_empty_space(N, M, board) << endl; 
}


int main(void)
{
    cin >> N >> M >> K;
    vector<vector<int>> board(N, vector<int>(M, 0));

    solution(board);

    return 0;
}
