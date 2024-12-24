#include <iostream>
#include <vector>

using namespace std;


int calc(int n){
    if(n % 2 == 1) {
        return 0;
    }

    vector<int> dp(n+1, 0);

    dp[2] = 3;
    dp[4] = dp[2] * 3 + 2;
    dp[6] =  dp[4] * 3 + 2;

    for(int i = 6 ; )

}

void solution()
{
    int n;
    cin >> n ;

    cout << calc(n) << endl;
}


int main(void)
{
    solution();
    return 0;
}
