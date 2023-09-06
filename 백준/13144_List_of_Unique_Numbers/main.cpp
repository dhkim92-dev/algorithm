#include <iostream>
#include <vector>

using namespace std;

class Solution {
    vector<int> arr;
    int N;
    long long answer = 0;
    vector<bool> used;
    
    void init() {
        cin >> N;
        arr.resize(N);
        used.resize(N+1, false);
        for(int i = 0 ; i < N ; i++) cin >> arr[i];
    }

    void simulate() {
        answer = 0;
        int left = 0, right = 0;

        for(left = 0 ; left < N ; left++) {
            while(right < N) {
                if( used[ arr[right] ] ) break;
                used[arr[right]] = true;
                right++;
            }

            answer += (right - left);
            used[arr[left]] = false;
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