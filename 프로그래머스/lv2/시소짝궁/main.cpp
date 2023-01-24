#include <string>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;


// 2x = 2y 1
// 2x = 3y 2/3
// 2x = 4y 1/2
// 3x = 2y 3/2
// 3x = 4y 3/4
// 4x = 2y 2
// 4x = 3y 4/3


long long solution(vector<int> weights) {
    long long answer = 0;
    unordered_map<double ,int> um;
    sort(weights.begin(), weights.end());

    for(auto weight : weights){
        double dw = static_cast<double>(weight);

        if(um.find(dw)!=um.end()){
            answer += um[dw];
        }

        um[dw] += 1;
        um[dw*3/2] += 1;
        um[dw*4/2] += 1;
        um[dw*4/3] += 1;
    }
        
    return answer;
}