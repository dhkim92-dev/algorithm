#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>

using namespace std;

class Solution {
    int32_t N;
    vector<int32_t> students;
    int32_t dp[1001];
    int32_t answer;

    void init() {
        cin >> N;
        students.resize(N+1);
        memset(dp, 0x00, sizeof(dp));

        for(int i = 1 ; i <= N ; i++) {
            cin >> students[i];
        }
    }

    void simulate() {
        // students 는 나이 순으로 정렬된 점수 정보.
        // dp[i] => i번째 학생까지 조를 나눴을 경우 최대 점수
        // dp[0] = 0;
        // dp[1] = 0;
        
        for(int i = 1 ; i <= N ; i++) {
            int32_t mn, mx;
            mn = 10001;
            mx = 0;

            for(int j = i ; j > 0 ; j--) {
                mx = max(mx, students[j]);
                mn = min(mn, students[j]);
                dp[i] = max(dp[i], dp[j-1] + mx - mn );
            }
        }
        answer = dp[N];
    }

    void print() {
        cout << answer << endl;
    }

public :
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
