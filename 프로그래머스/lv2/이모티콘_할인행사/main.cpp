
#include <string>
#include <vector>
#include <algorithm>

using namespace std;
int rates[7];

void dfs(int idx, vector<vector<int>> &users, vector<int> &emoticons, vector<int> &ans)
{
    if(idx == emoticons.size()){
        int cost=0;
        int members=0;
        
        for(int i = 0 ; i < users.size() ; i++){
            int user_cost = 0;
            for(int j = 0 ; j < emoticons.size() ;j++){
                if(rates[j] >= users[i][0]){
                    user_cost += emoticons[j] * (100-rates[j]) / 100;
                }
            }
            
            (user_cost >= users[i][1]) ? members++ : cost+=user_cost;
        }
        ans = max(ans, {members, cost});
        return;
    }
    for(int rate = 10 ; rate <= 40 ; rate+=10){
        rates[idx] = rate;
        dfs(idx + 1, users, emoticons, ans);
    }
}

vector<int> solution(vector<vector<int>> users, vector<int> emoticons) {
    vector<int> answer(2,0);
    dfs(0, users, emoticons, answer);
    return answer;
}