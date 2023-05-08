#include <iostream>
#include <vector>

using namespace std;

void solve() 
{
    int nr_coins;
    cin >> nr_coins;
    vector<int> coins(nr_coins);

    for(int i = 0 ; i < nr_coins ; i++){
        cin >> coins[i];
    }

    long long target;
    cin >> target; 

    vector<int> dp(target+1, 0);

    dp[0] = 1;
    
    for(auto coin : coins){
        for(int j = 0 ; j <= target ; j++){
            if(j >= coin){
                dp[j] += dp[j-coin];
            }
        }
    }

    cout << dp[target] << endl;
}

void solution(int nr_testcase)
{
    for(int i = 0 ; i < nr_testcase ; i++){
        solve();
    }
}

int main(void)
{
    int nr_testcase;

    cin >> nr_testcase;

    solution(nr_testcase);

    return 0;
}
