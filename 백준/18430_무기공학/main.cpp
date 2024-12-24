#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

int N, M;
vector<vector<int>> board;
vector<vector<bool>> visited;

int types[4][2][2] = {
    {
        {0, -1}, 
        {1, 0}
    },
    {
        {0, -1}, 
        {-1, 0}
    },
    {
        {0, -1},
        {1, 0}
    },
    {
        {0, 1}, 
        {1, 0}
    }
};


void init_board() {
    cin >> N >> M;

    board.resize(N, vector<int>(M, 0));
    visited.resize(N, vector<bool>(M, false));

    for(int i = 0 ; i < N ; i++) {
        for(int j = 0 ; j < M ; j++) {
            cin >> board[i][j];
        }
    }
}

bool is_in_range(int y, int x) {
    return (y >= 0 && y < N) && ( x >= 0 && x < M);
}

bool is_available(int y, int x, int type) {
    int y0 = y + types[type][0][0];
    int x0 = x + types[type][0][1];
    int y1 = y + types[type][1][0];
    int x1 = x + types[type][1][1];

    return is_in_range(y0, x0) && is_in_range(y, x) && is_in_range(y1, x1);
}

bool is_visitable(int y, int x, int type) {
    int y0 = y + types[type][0][0];
    int x0 = x + types[type][0][1];
    int y1 = y + types[type][1][0];
    int x1 = x + types[type][1][1];

    return !visited[y0][x0] && !visited[y][x] && !visited[y1][x1];
}

void set_visited(int y, int x, int type, bool val) {
    int y0 = y + types[type][0][0];
    int x0 = x + types[type][0][1];
    int y1 = y + types[type][1][0];
    int x1 = x + types[type][1][1];
    
    visited[y0][x0] = val;
    visited[y][x] = val;
    visited[y1][x1] = val;
}

int calc(int y, int x, int type) {
    int y0 = y + types[type][0][0];
    int x0 = x + types[type][0][1];
    int y1 = y + types[type][1][0];
    int x1 = x + types[type][1][1];

    return board[y0][x0] + board[y][x]*2 + board[y1][x1];
}

void dfs(int y, int x, int current_value, int &answer) {
    if(x == M) {
        x = 0;
        y++;
    }

    if(y == N) {
        answer = max(current_value, answer);
        return ;
    }

    for(int i = 0 ; i < 4 ; i++) {
        if(!is_available(y, x, i)) continue;
        if(!is_visitable(y, x, i)) continue;
        set_visited(y, x, i, true);
        int value = calc(y,x,i);
        dfs(y, x+1, current_value + value, answer);
        set_visited(y, x, i, false);
    }
    dfs(y, x+1, current_value, answer);
}

int main(void)
{
    init_board();
    int answer = 0;
    dfs(0, 0, 0, answer);
    cout << answer << endl;
    return 0;
}
