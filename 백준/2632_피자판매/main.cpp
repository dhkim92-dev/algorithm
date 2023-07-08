#include <iostream>
#include <vector>
#include <algorithm>
#include <unordered_map>

using namespace std;

class Solution{
private:
    vector<int> pizza_a;
    vector<int> pizza_b;
    unordered_map<int, int> a_cnt;
    unordered_map<int, int> b_cnt;
    int target;

    void init() {
        cin >> target;
        int a,b;
        cin >> a >> b;
        pizza_a.resize(a, 0);
        pizza_b.resize(b, 0);
        
        int total = 0;

        for(int i = 0 ; i < a ; i++) {
            cin >> pizza_a[i];
            total += pizza_a[i];
        }
        a_cnt[total] = 1;

        total = 0;
        for(int i = 0 ; i < b ; i++) {
            cin >> pizza_b[i];
            total += pizza_b[i];
        }

        b_cnt[total] = 1;
    }

    void make_cases(vector<int> &pizza, unordered_map<int, int> &cnt) {
        // 모두 담는 케이스는 init() 에서 처리했으므로 [0, size - 1] 범위의 piece 수 만큼
        // 고른 경우의 수를 저장한다.
        int size = pizza.size();
        
        int sum = 0;
        for(int i = 0 ; i < size ; i++) {
            sum = 0;
            for(int nr_piece = 0 ; nr_piece < size - 1 ; nr_piece++) {
                sum += pizza[(i + nr_piece) % size];
                cnt[sum]++;
            }
        }
    }

public:
    void run() {
        int answer = 0;
        init();
        make_cases(pizza_a, a_cnt);
        make_cases(pizza_b, b_cnt);

        // A만 고르는 경우의 수 
        if(a_cnt.find(target) != a_cnt.end()) {
            answer += a_cnt[target];
        }
        // B만 고르는 경우의 수 
        if(b_cnt.find(target) != b_cnt.end()) {
            answer += b_cnt[target];
        }

        // A+B 의 조합으로 고르는 경우의 수
        
        for(auto it = a_cnt.begin() ; it != a_cnt.end() ; it++) {
            int val = it->first;

            if(val >= target) continue;

            answer += a_cnt[val] * b_cnt[target-val];
        }

        cout << answer << endl;
    }
};



int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}
