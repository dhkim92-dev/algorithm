#include <string>
#include <vector>
#include <iostream>
#include <memory.h>
#include <algorithm>

using namespace std;

vector<vector<int>> dp(100061, vector<int>(2)={123456,0});

void update(int target, int base, int count)
{
    if(dp[target+base][0] == dp[target][0]+1){
        dp[target+base][1] = max(dp[target+base][1], dp[target][1]+count);
    }else if(dp[target+base][0] > dp[target][0]+1){
        dp[target+base][0] = dp[target][0]+1;
        dp[target+base][1] = dp[target][1]+count;
    }
}

void solve(int target)
{
    dp[0][0] = 0;
    for(int i = 0 ; i <= target ; i++){
        for(int base = 1 ; base <= 20 ; base++){
            update(i, base, 1); // single
            update(i, base*2, 0); // double
            update(i, base*3, 0); // triple
        }
        update(i, 50, 1); //bull
    }
}

vector<int> solution(int target) {
    vector<int> answer(2,0);
    //memset(dp, 0x00, sizeof(int)*100001*2);
    solve(target);
    answer[0]=dp[target][0];
    answer[1]=dp[target][1];
    return answer;
}
