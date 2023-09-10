#include <iostream>
#include <deque>
#include <algorithm>

using namespace std;

class Solution{
    int N;
    int a, b;
    deque<int> arr;

    void init() {
        cin >> N >> a >> b;
    }

    void simulate() {
        // L 에서 볼 수 있는 건물의 수가 a개 라면, 전체에서 건물 arr[0] 의 높이보다 높은 건물은 a-1개이다.
        // R 에서 볼 수 있는 건물의 수가 b개 라면, 전체에서 건물 arr[N-1] 의 높이보다 높은 건물은 b-1 개이다.
        // a+b-1 <= N 이어야 출력이 가능하다.
        // 출력은 사전순으로 정렬되어야 한다.

        if(a+b-1 > N) {
            return ;
        }

        // 우선 1 ~ a-1 까지의 값을 arr에 push
        // 그 후 b와 a의 최대값을 push
        // b-1 ~ 1 까지 push
        // 배열 가장 앞의 값을 저장해두고 제외
        // N-1 크기만큼 1의 push
        // front 다시 insert

        for(int i = 1 ; i <= a-1 ; i++) {
            arr.push_back(i);
        }
       
        arr.push_back(max(a, b));
       
        for(int i = b - 1 ; i >= 1 ; i--) {
            arr.push_back(i);
        }

        int front = arr.front();
        arr.pop_front();

        while(arr.size() < N - 1) {
            arr.push_front(1);
        }
        arr.push_front(front);
    }

    void print() {
        if(arr.empty()) {
            cout << -1 << endl;
        }else {
            for(auto i : arr) {
                cout << i << " ";
            }
            cout << endl;
        }
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