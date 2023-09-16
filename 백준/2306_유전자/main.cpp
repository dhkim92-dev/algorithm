#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

class Solution {
    string s;
    size_t N;
    int32_t dp[501][501];

    void init() {
        cin >> s;
        N = s.length();
        memset(dp, 0x00, sizeof(dp));
    }

    void simulate() {
        // X가 KOI 유전자라면, 
        // aXt , gXc 가 KOI 유전자
        // KOI 유전자 둘을 연결해도 KOI 유전자
        // KOI 유전자 최대 길이 구하기
        // dp[i][j] = > i~-j 문자열에서 최대 길이
        for(int stride = 1 ; stride < N ; stride++) {
            for(int start = 0 ; start + stride < N ; start++) {
                int end = start + stride;

                if( (s[start] == 'a' && s[end] == 't' ) || (s[start] == 'g' && s[end] == 'c' )) {
                    dp[start][end] = dp[start+1][end-1] + 2;
                }

                for(int mid = start ; mid < end ; mid++) {
                    dp[start][end] = max( dp[start][end], dp[start][mid] + dp[mid+1][end] );
                }
            }
        }
    }

    void print() {
        cout << dp[0][s.length()-1] << endl;
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
