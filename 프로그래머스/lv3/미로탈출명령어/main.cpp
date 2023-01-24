#include <string>
#include <vector>
#include <iostream>
#include <cmath>

using namespace std;

int dr[4] = {1,0,0,-1};
int dc[4] = {0,-1,1,0};
string priority = "dlru";
int N, M;

int calc_delta(int x, int y, int r, int c)
{
    return abs(r-x) + abs(c-y);
}

bool is_movable(int x, int y)
{
    return ((x >= 1) && (x<=N)) && ((y>=1) && (y<=M));
}

string solution(int n, int m, int x, int y, int r, int c ,int k)
{
    string answer = "";
    N = n;
    M = m;
    int delta = calc_delta(x,y,r,c);
    
    if(k < delta) return "impossible";
    if((abs(k-delta)%2) == 1) return "impossible";

    while(k--){
        for(int i = 0 ; i < 4 ; i++){
            int nr = x + dr[i];
            int nc = y + dc[i];

            if(!is_movable(nr,nc)) continue;

            int new_delta = calc_delta(nr, nc, r, c);

            if(new_delta > k || (abs(new_delta - k) % 2==1)) continue;

            x = nr;
            y = nc;
            answer += priority[i];
            break;
        }
    }

    return answer;
}

int main(void)
{
    solution(3,4,2,3,3,1,5);
    return 0;
}
