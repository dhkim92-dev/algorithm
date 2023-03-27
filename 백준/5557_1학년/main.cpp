#include <iostream>
#include <vector>
#include <string>

using namespace std;

long long solution(vector<int> &inputs, int N){
    int target = inputs[N];
    vector<vector<long long>> dp(101, vector<long long>(21,0));
    
    dp[1][inputs[1]] = 1; 

    for(int i = 2 ; i <= N ; i++){
        for(int j = 0 ; j <= 20 ; j++){
            if(dp[i-1][j]){
                if(j + inputs[i] <= 20){
                    dp[i][j + inputs[i]] += dp[i-1][j];
                }       
                if( j - inputs[i] >= 0 ){
                    dp[i][j - inputs[i]] += dp[i-1][j];
                }
            }
        }
    }
    return dp[N-1][target];
}

int main(void)
{
    int N;
    vector<int> inputs(101);
    cin >> N;

    for(int i = 0;  i < N ; i++){
        cin >> inputs[i+1];
    }

    cout << solution(inputs, N) << endl;

    return 0;
}
