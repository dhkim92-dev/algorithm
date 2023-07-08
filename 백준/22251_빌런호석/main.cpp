#include <iostream>
#include <string>
#include <vector>

using namespace std;
/*
   1
 0   2
   3
 4   6
   5
*/

int led[10][7] ={
    {1, 1, 1, 0, 1, 1, 1}, // 0
    {0, 0, 1, 0, 0, 0, 1}, // 1
    {0, 1, 1, 1, 1, 1, 0}, // 2
    {0, 1, 1, 1, 0, 1, 1}, // 3
    {1, 0, 1, 1, 0, 0, 1}, // 4
    {1, 1, 0, 1, 0, 1, 1}, // 5
    {1, 1, 0, 1, 1, 1, 1}, // 6
    {0, 1, 1, 0, 0, 0, 1}, // 7
    {1, 1, 1, 1, 1, 1, 1}, // 8
    {1, 1, 1, 1, 0, 1, 1}  // 9
};


class Solution{
private :
    int N,K,P,X; 
    // N 층까지 있다.
    // K 자리 까지 표기
    // 최대 P개 까지 변경 가능하다.
    // 현재층은 X이다.

    void init() {
        cin >> N >> K >> P >> X;
    }

    int simulation() {
        int answer = 0 ;
        

        // i번째 층에서 X층으로 P번 안에 변경 가능한지 확인한다.
        for(int i = 1 ; i <= N ; i++) {
            if(i == X) continue;
            int cnt = 0 ;
            int from = i, to = X;

            for(int j = 0 ; j < K ; j++) {
                for(int k = 0 ; k < 7 ; k++) {
                    if(led[from % 10][k] != led[to % 10][k]) cnt++;
                }
                from/=10;
                to/=10;
            }

            if(cnt <= P) answer++;

        }

        return answer;
    }

public:
    
    void run() {
        init();
        int answer = simulation();
        cout << answer << endl;
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}

