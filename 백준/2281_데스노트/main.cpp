#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

#define MOD 987654321

class Solution {
    int32_t N, M;
    vector<int32_t> inputs;
    vector<vector<int32_t>> dp;
    int32_t answer;

    void init() {
        cin >> N >> M;
        inputs.resize(N);

        for(int i = 0 ; i < N ; i++) {
            cin >> inputs[i];
        }
        dp.resize(N+1, vector<int32_t>(M+2, -1));
    }

    /**
    * index => 현재 이름 인덱스
    * used => 현재 이용중인 줄의 사용한 칸 수
    */
    int32_t dfs(int32_t index, int32_t used) {
        if(index == N) return 0; // 마지막 줄은 계산에서 제외한다.
        if(dp[index][used] != -1) return dp[index][used]; // 최소값이 이미 존재한다면 반환

        // 다음 줄에 작성하는 경우
        int32_t left = M - used + 1; // 현재 라인의 남은 칸 수
        dp[index][used] = dfs(index + 1, inputs[index] + 1) + left * left;
               
        // 현재 줄에 작성하는 경우
        if(used + inputs[index] <= M) {
            dp[index][used] = min( dfs(index+1, used + inputs[index] + 1), dp[index][used]);
        }

        return dp[index][used];
    }

    void simulate() {
        answer = dfs(1, inputs[0] + 1);
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
