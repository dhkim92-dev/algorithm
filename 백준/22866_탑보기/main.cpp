#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

enum DIRECTION {
    LEFT = 0,
    RIGHT
};

struct Result {
    size_t count=0;
    int nearest=NULL;
};

struct Building {
    int build_no;
    int height;
};

class Solution {
    int N;
    vector<int> heights;
    vector<Result> showns;

    void init() {
        cin >> N;
        heights.resize(N+1);
        showns.resize(N+1);

        for(int i = 0 ; i < N ; i++) {
            int h;
            cin >> h;
            heights[i+1] = h;
        }
    }

    void pop_stack(stack<Building> &st, int height) {
        while(!st.empty() && st.top().height <= height) {
            st.pop();
        }
    }

    void check_left() {
        // cout << "left\n";
        stack<Building> st;

        for(int i = 1 ; i <= N ; i++) {
            int current = heights[i];
            pop_stack(st, current);
            showns[i].count += st.size();

            if(!st.empty()) {
                //처음 좌측 건물만 확인하면서 진행하는 경우 스택 최상단이 가장 가까운 건물 번호
                showns[i].nearest = st.top().build_no; 
            }

            st.push({i, heights[i]});
        }
    }

    void check_right() {
        stack<Building> st;
        for(int i = N ; i > 0 ; i--) {
            int current = heights[i];
            pop_stack(st, current);
            showns[i].count += st.size();

            if(!st.empty()) {
                // 우측 건물을 확인하며 진행하는 경우 좌측 보이는 건물과의 거리를 계산해서 거리를 비교해야한다.
                if(showns[i].nearest == NULL) {
                    showns[i].nearest = st.top().build_no;
                }else if((i-showns[i].nearest) > (st.top().build_no - i)) {
                    showns[i].nearest = st.top().build_no;
                }
            }

            st.push({i, heights[i]});
        }
    }

    void simulate() {
        check_left();
        check_right();
    }

    void print() {
        for(int i = 1 ; i <= N ; i++) {
            size_t count = showns[i].count;
            int nearest = showns[i].nearest;
            if(count >= 1) {
                cout << count << " " << nearest;
            }else{
                cout << 0;
            }
            cout << "\n";
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