#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
    int N;
    vector<int> buildings;

    int answer = 0;

    void init() {
        cin >> N;
        buildings.resize(N);

        for(int i = 0 ; i < N ; i++) {
            cin >> buildings[i];
        }
    }

    int check_left(int idx) {
        //좌측 기울기는 계속 작아져야한다.
        double mn_gradient = 1000000000.0; // 현재까지 탐색한 최소 기울기
        int cnt = 0;

        for(int i = idx - 1 ; i >= 0 ; i--) {
            double gradient = (buildings[idx] - buildings[i])/static_cast<double>(idx - i);

            if(gradient < mn_gradient) { // 이번 기울기가 현재까지 탐색한 최소 기울기보다 작다면
                mn_gradient = gradient;
                cnt++;
            }
        }

        return cnt;
    }

    int check_right(int idx) {
        double mx_gradient = -1000000000.0; // 현재까지 탐색한 최대 기울기
        int cnt = 0;

        for(int i = idx + 1 ; i < N ; i++) {
            double gradient = (buildings[i] - buildings[idx])/static_cast<double>(i - idx);

            if(gradient > mx_gradient) {
                mx_gradient = gradient;
                cnt++;
            }
        }

        return cnt;
    }

    void simulate() {
        for(int idx = 0 ; idx < N ; idx++){
            int cnt = check_left(idx) + check_right(idx);
            answer = max(answer, cnt);
        }
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