#include <iostream>
#include <vector>
#include <string>

using namespace std;

int count_char(const char &target, const string &board){
    int cnt = 0;

    for(int i = 0 ; i < 9 ; i++){
        cnt += (board[i] == target);
    }

    return cnt;
}

bool check_win(const char &target, const string &board)
{
    for(int i = 0 ; i < 3 ; i++){
        int row = i*3;
        if(board[row] == target && board[row] == board[row+1] && board[row+1] == board[row+2]){
            return true;
        }
    }

    for(int i = 0 ; i < 3 ; i++){
        if(board[i] == target && board[i] == board[3+i] && board[3+i] == board[6+i]){
            return true;
        }
    }

    // 대각선 top_left to bottom_right
    
    if(board[0] == target && board[0] == board[4] && board[4] == board[8])
        return true; 

    // 대각선 top_right to bottom_left; 
    if(board[2] == target && board[2] == board[4] && board[4] == board[6])
        return true;

    return false;
}

int main(void)
{
    while(true){
        string board;
        cin >> board;
        if(board == "end") break;

        int x_cnt = count_char('X', board);
        int o_cnt = count_char('O', board);

        bool x_winnable = check_win('X', board);
        bool o_winnable = check_win('O', board);

        if(x_winnable && !o_winnable && x_cnt - o_cnt == 1){
            cout << "valid" << endl;
        }else if(!x_winnable && o_winnable && x_cnt -o_cnt ==0){
            cout << "valid" << endl;
        }else if(!x_winnable && !o_winnable && x_cnt == 5 && o_cnt == 4){
            cout << "valid" << endl;
        }else{
            cout << "invalid" << endl;
        }
    }

    return 0;
}
