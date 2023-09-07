#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

struct TechInfo {
    int building_no;
    int build_time;
    int complete_time = 0;

    // vector<int> depend_on;
    int depend_count = 0;
    vector<int> required_by;

    bool operator < (const TechInfo & di) const {
        return build_time < di.build_time;
    }
};

class Solution {
    int T, N, K; // T testcase, N # buildings, K # rules
    int target;
    int answer = 900000000;
    vector<TechInfo> techs;
    void init() {
        cin >> T;
    }

    void init_test_case() {
        cin >> N >> K;
        techs.clear();
        techs.resize(N + 1);

        for(int i = 0 ; i < N ; i++) {
            int t = 0;
            cin >> t;
            techs[i+1].build_time = t;
            techs[i+1].building_no = i+1;
            techs[i+1].complete_time = t;
        }

        for(int i = 0 ; i < K ; i++) {
            int building_no, required;
            cin >> required >> building_no;
            // techs[building_no].depend_on.push_back(required);
            techs[building_no].depend_count++;
            techs[required].required_by.push_back(building_no);
        }

        cin >> target;
    }

    void push_no_dependency_techs(queue<int> &q) {
        for(int i = 1 ; i < techs.size() ; i++) {
            if(techs[i].depend_count == 0) {
                q.push(i);
            }
        }
    }

    void reverse_search() {
        // 역순 탐색을 시작한다.
        queue< int > q;
        push_no_dependency_techs(q);

        while(!q.empty()) {
            int cur = q.front();
            q.pop();

            for(int nxt : techs[cur].required_by) {
                techs[nxt].complete_time = 
                    max(techs[cur].complete_time + techs[nxt].build_time, 
                        techs[nxt].complete_time);
                techs[nxt].depend_count--;
                if(techs[nxt].depend_count == 0)
                    q.push(nxt);
            }
        }
        // cout << "explore end\n";
    }

    void run_test() {
        reverse_search();
    }

    void simulate() {
        init_test_case();
        run_test();
        print();
    }

    void print() {
        cout << techs[target].complete_time << endl;
    }

public :
    void run() {
        init();
        for(int i = 0 ; i < T ; i++) {
            simulate();
        }
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}