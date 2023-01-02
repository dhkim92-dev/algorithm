#include <string>
#include <vector>
#include <algorithm>

using namespace std;

class Solution{
    private :
    vector<vector<int>> &board;
    vector<vector<int>> &target;
    int rs, cs;
    int answer = 11;
    
    void flip_row(int idx){
        for(int i = 0 ; i < cs ; i++) board[idx][i] = !board[idx][i];
    }

    int is_possible_column(int idx){
        int cnt = 0 ;

        for(int i = 0 ; i < rs ; i++){
            cnt += (board[i][idx] == target[i][idx]);
        }

        if(cnt == 0){
            return 1; // 타겟과 현재의 상태가 정반대인경우
        }else if(cnt == rs){
            return 0; // 타겟과 현재의 상태가 동일한 경우
        }

        return -1;
    }

    void dfs(int r, int c, int cnt){
        if(r==rs){
            bool possible = true;
            for(int i = 0 ; i < cs ; i++){
                int ret = is_possible_column(i);
                if(ret == -1) {
                    possible = false;  
                    continue;
                }
                cnt += ret;
            }

            if(possible) answer = min(answer, cnt);
            return ;
        }else{
            dfs(r+1, c, cnt); //행 유지
            flip_row(r);
            dfs(r+1, c, cnt +1); //행 뒤집기
            flip_row(r);
        }
    }

    public:
    Solution(vector<vector<int>> &board, vector<vector<int>> &target) : board(board),target(target){
        rs = target.size();
        cs = target[0].size();
    }

    int run(){
        dfs(0,0,0);
        return (answer == 11) ? -1 : answer;
    }
};

int solution(vector<vector<int>> beginning, vector<vector<int>> target) {
    int answer = 0;

    Solution sol(beginning, target);
    answer = sol.run();
    return answer;
}
