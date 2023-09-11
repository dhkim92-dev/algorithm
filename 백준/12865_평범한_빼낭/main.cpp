#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Element {
    int w=0;
    int v=0;
};

class Solution {
    int N, K;
    vector< vector<int> > dp;
    vector<Element> elements;

    void init() {
        cin >> N >> K;
        dp.resize( N+1, vector<int>(K+1, 0) );
        elements.resize(N+1);

        for(int i = 0 ; i < N ; i++) {
            int w,v;
            cin >> w >> v;
            elements[i+1].w = w;
            elements[i+1].v = v;
        }
    }

    void simulate() {
        // dp[i][j] = i 개의 물건을 담았을때 무게 한도 j 내에서 최고가치
        for(int i = 1 ; i <= N ; i++) {
            for(int j = 1 ; j <= K ; j++) {
                if( j - elements[i].w  >= 0) {
                    dp[i][j] = max(dp[i-1][j],  dp[i-1][j - elements[i].w] + elements[i].v);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
    }

    void print() {
        cout << dp[N][K] << endl;
    }

public:
    void run() {
        init();
        simulate();
        print();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}