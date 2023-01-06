#include <string>
#include <vector>
#include <iostream>
#include <cmath>

using namespace std;

struct Pos{
    int r, int c;
};

Pos moves[4] = {
    { 1,  0},
    { 0, -1},
    { 0,  1},
    {-1,  0}
};

class Solution{
private:
    int n,m,x,y,r,c,k;
    char values[4] = {'d', 'l', 'r', 'u'};

    bool row_check(int r){
        return (1 <= r && r <= n);
    }

    bool col_check(int c){
        return (1 <= c && c <= m);
    }

    bool is_movable(Pos p){
        return row_check(p.r) && col_check(p.c);
    }

    vector<int> calc_delta(){
        int dr = x - r;
        int dc = y - c;
        return {x-r, y-c}; 
    }

    int calc_remain_move(int dr, int dc){
        dr = abs(dr);
        dc = abs(dc);

        return k - (dr + dc);
    }

    bool has_solution(int count){
        return (count % 2 == 0);
    }

    string explore(){
        string answer = "";
        
        for(int i = 0 ; i < k ; i++){
            for(int i = 0 ; i < 4 ; i++){
                
            }
        }
    }

public:
    Solution(int n, int m, int x, int y, int r, int c, int k) : 
        n(n), m(m), x(x), y(y), r(r),c(c), k(k)
    {}

    string run(){
        string answer = "";
        vector<int> delta = calc_delta();
        int remain = calc_remain_move(delta[0], delta[1]);
        
        if(!has_solution(remain)){
            return "impossible";
        }


        return answer;
    }
};

string solution(int n, int m, int x, int y, int r, int c ,int k)
{
    string answer = "";
    cout << "answer : " << answer << endl;
    return answer;
}

int main(void)
{
    solution(3,4,2,3,3,1,5);
    return 0;
}
