#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

class Solution {
    int64_t N, L, I;
    vector<int64_t> targets;
    int64_t dp[32][32];
    string answer;

    void init() {
        cin >> N >> L >> I;
        memset(dp, -1, sizeof(dp));
    }

    /**
    * 길이 n 1의 개수가 m개 이하인 이진 수 개수
    */
    int64_t binary_count(int32_t n, int32_t m) {
        if(n == 0 || m == 0) return 1;
        if(dp[n][m] != -1) return dp[n][m];

        dp[n][m] = binary_count(n-1, m);
        
        if(m > 0) {
            dp[n][m] += binary_count(n-1, m-1);
        }

        return dp[n][m];
    }

    void findNumber(int64_t n, int64_t l, int64_t cnt) {
        if(n == 0) return ;

        if(l == 0) {
            for(int i = 0 ; i < n ; i++) {
                answer += '0';
            }
            return ;
        }

        int64_t num = binary_count(n-1, l);

        if(num >= cnt) {
            answer += '0';
            findNumber(n-1, l, cnt);
        } else {
            answer += '1';
            findNumber(n-1, l-1, cnt - num);
        }
    }

    void simulate() {
        // N자리 2진수, L개 이하의 1을 가진 2진수 중 크기 순 정렬 했을때 I번째
        findNumber(N, L, I);
    } 

    void print() {
        cout << answer << endl;
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
