#include <string>
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

const int inf = 1e9;

int solution(int temperature, int t1, int t2, int a, int b, vector<int> onboard) {
    int answer = 1e9;
    
    temperature+=10;
    t1+=10;
    t2+=10;
    
    vector<vector<int>> dp(onboard.size(), vector<int>(51,inf));
    dp[0][temperature] = 0;
    
    for(int i = 0 ; i < onboard.size() - 1 ; i++) {        
        for(int j = 0 ; j <= 50 ; j++) {
            if(onboard[i] == 1 && (j < t1 || j > t2)) continue; // 승객이 탔는데 희망온도 밖인 경우
             
            // 에어컨 off
            // int nxt = j;
            if(j < temperature && j < 50) {
                // nxt = j + 1;
                dp[i+1][j+1] = min(dp[i][j], dp[i+1][j+1]);
            }else if(j > temperature && j > 0) {
                // nxt = j - 1;
                dp[i+1][j-1] = min(dp[i][j], dp[i+1][j-1]);
            }else{
                dp[i+1][j] = min(dp[i][j], dp[i+1][j]);
            }
            // dp[i+1][nxt] = min(dp[i][j], dp[i+1][nxt]);
            
            // 현재 온도가 희망온도가 아닌 경우
            if(j > 0) {
                dp[i+1][j-1] = min(dp[i][j] + a, dp[i+1][j-1]);
            }
            if( j < 50) {
                dp[i+1][j+1] = min(dp[i][j] + a, dp[i+1][j+1]);
            }
            
            dp[i+1][j] = min(dp[i][j] + b, dp[i+1][j]);
        }
    }

    for(int i = 0 ; i <= 50 ; i++) {
        if(onboard[onboard.size()-1]==1 && (i<t1 || i>t2)) continue;
        answer = min(dp[onboard.size()-1][i], answer);
    }

    return answer;
}
