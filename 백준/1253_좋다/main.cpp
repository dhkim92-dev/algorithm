#include <iostream>
#include <string>
#include <vector>
#include <unordered_set>

using namespace std;

class Solution {
private :
    int N;
    vector<int> numbers;
    unordered_set<int> goods;

    void init() {
        cin >> N;
        numbers.resize(N);

        for(int i = 0 ; i < N ; i++) {
            cin >> numbers[i];
        }
    }

    void get_good_numbers() {
        for(int i = 0 ; i < N - 1 ; i++) {
            for(int j = i + 1 ; j < N ; j++) {
                int good_num = numbers[i] + numbers[j];
                
                if(good_num == numbers[i] || good_num == numbers[j]) continue;
                goods.insert(numbers[i] + numbers[j]);
            }
        }
    }

    int count_good_numbers() {
        int cnt = 0;
        for(auto number : numbers) {
            if(goods.count(number)) {
                cnt++;
            }
        }
        return cnt;
    }

public :
    void run(){
        int answer = 0;
        init();
        get_good_numbers();
        answer = count_good_numbers();
        cout << answer << endl;
    }
};

int main(void)
{
    Solution sol;
    sol.run();

    return 0;
}
