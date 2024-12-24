#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

class Solution {
    int n;

    void init() {
        cin >> n;
    }

    int solve() {
        int64_t cur = 0;
        int64_t dist = 0;
        cin >> cur >> dist;
        dist = dist-cur;
        cur = 0;
        int64_t i = 1;

        for(i = 1; i * i < dist ; i++) {}

        int answer = i * 2;

        return answer;
    }

public :
    void run() {
        init();
        for(int j = 0 ; j < n ; j++)
            cout << solve() << endl;
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}