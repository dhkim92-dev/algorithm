#include <iostream>
#include <vector>
#include <algorithm>
#include <memory.h>
#include <unordered_map>

using namespace std;

class Solution {
    int N;
    int K;
    vector<int> arr;
    vector<int> psum;
    unordered_map<int, long long> count_map;
    
    long long answer = 0;

    void init() {
        cin >> N >> K;
        psum.resize(N+1, 0);
    }

    int is_target(int i, int j) {
        return static_cast<int>( (psum[j] - psum[i-1]) == K);
    }

    void simulate() {
        count_map[0] = 1;
        for(int i = 1 ; i <= N ; i++) {
            int num = 0;
            cin >> num;
            psum[i] = psum[i-1] + num;
            answer += count_map[psum[i] - K];
            count_map[psum[i]]++;
        }
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
