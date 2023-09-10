#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

struct Coin {
    int price;
    int count;
};

class Solution {
    int N; // 코인 종류
    vector<Coin> coins;
    bool dp[500001]={0,};
    int half = 0;
    
    void init() {
        coins.clear();
        cin >> N;
        coins.resize(N);
        memset(dp, 0x00, sizeof(dp));
        dp[0] = true;
        half = 0;

        for(int i = 0 ; i < N ; i++) {
            int price, cnt;
            cin >> price >> cnt;
            coins[i].price = price;
            coins[i].count = cnt;
        }
    }

    int total_value() {
        int total = 0;

        for(auto &coin : coins) {
            total += coin.price*coin.count;
        }

        return total;
    }

    void simulate() {
        int total = total_value();
        half = total / 2;

        if(total % 2 == 1) {
            return ;
        }

        sort(coins.begin(), coins.end(), [&](Coin &a, Coin &b) {
            return a.price > b.price;
        });

        for(int i = 0 ; i < N ; i++) {
            for(int j = half ; j >= coins[i].price ; j--) {
                if(dp[j - coins[i].price]) {
                    for(int k = 0 ; k < coins[i].count ; k++) {
                        int expect = j + k * coins[i].price;

                        if(expect > half) break;
                        dp[expect] = true;
                    }
                }
            }
        }
    }

    void print() {
        cout << dp[half] << endl;
    }

public :
    void run() {
        for(int i = 0 ; i < 3 ; i++){
            init();
            simulate();
            print();
        }
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}