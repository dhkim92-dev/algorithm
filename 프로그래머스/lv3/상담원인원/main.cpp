#include <string>
#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <queue>

using namespace std;

const int inf = 1e10;

struct Time {
    int start;
    int time;
};

class Solution {
    int n, k;
    vector<vector<int>> reqs;
    vector<vector<Time>> tasks;
    vector<vector<int>> delays;

    void classify_tasks() {
        for(auto &task : reqs) {
            tasks[task[2]].push_back({task[0], task[1]});
        }
    }

    int calc_delay(int mentors, int type) {
        if( tasks[type].size() <= mentors ) {
            return 0;
        }
        
        int total_delay = 0;
        priority_queue<int, vector<int>, greater<int>> pq;
        int remain_mentors = mentors;

        for(Time &tm : tasks[type]) {
            if(remain_mentors > 0) {
                // 즉시 상담 가능한 경우
                pq.push(tm.start + tm.time);
                remain_mentors -= 1;
            } else {
                int last_time = pq.top();
                pq.pop();

                if(last_time > tm.start) {
                    // 상담 요청 시각이 가장 빠른 상담 종료 시각이 더 크다면
                    total_delay += last_time - tm.start; // 지연 시간을 업데이트
                    pq.push(last_time + tm.time); // 다음 상담 종료 시각 입력
                }else{
                    pq.push(tm.start + tm.time); // 다음 상담 종료 시각 입력
                }
            }
        }

        return total_delay;
    }

    int get_min_delay() {
        int left = n - k;
        vector<int> mentors(k+1, 1);

        // 175 65 25
        // 20 0 0
        // 85 0 0

        int target_index = -1;
        while(left > 0) {
            // delta를 구한다
            vector<int> delta(k+1, 0);
            int mx_delta = 0;
            int target=0;
            for(int i = 1 ; i <= k ; i++) {
                delta[i] = abs(delays[i][mentors[i]+1] - delays[i][mentors[i]]);

                if(mx_delta < delta[i]) {
                    target = i;
                    mx_delta = delta[i];
                }
            }

            mentors[target]++;
            left--;
        }
        
        int delay = 0;
        // cout << "nr_mentors\n";
        
        // for(auto nr : mentors) {
            // cout << nr << " ";
        // }
        // cout << endl;
        
        // cout << "delays\n";
        for(int i = 1 ; i <= k ; i++) {
            delay += delays[i][mentors[i]];
            // cout << delays[i][mentors[i]] << " ";
        }
        // cout << endl;

        return delay;
    }


public :
    Solution(int k, int n, vector<vector<int>>&reqs) : k(k), n(n), reqs(reqs) {
        tasks.resize(k+1);
        delays.resize(k+1, vector<int>(n - k + 2, 0));
    }

    int run() {
        classify_tasks();
        
        // cout << "costs\n";
        for(int type = 1 ; type <= k ; type++) {
            // cout << "0 ";
            for(int nr_mentors = 1 ; nr_mentors <= n -k + 1 ; nr_mentors++) {
                delays[type][nr_mentors] = calc_delay(nr_mentors, type);
                // cout << delays[type][nr_mentors] << " ";
            }
            // cout << endl;
        }

        int answer = 0;
        
        answer = get_min_delay();

        return answer;
    }
};

// 1~k번의 상담유형
// n명의 멘토
// reqs => 상담요청 [a, b, c] 시각 a 부터 b분 동안 c 유형의 싱담을 요청함
// ret 상담 최소 소요 시간을 반환
int solution(int k, int n, vector<vector<int>> reqs) {
    int answer = 0;

    // 가장 대기 시간이 긴 유형을 구하고
    //
    Solution sol(k, n, reqs);
    answer = sol.run();
    return answer;
}
