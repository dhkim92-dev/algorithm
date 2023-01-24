#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <iostream>

using namespace std;

int solution(vector<vector<int>> scores) {
    vector<int> wanho = scores[0];
    vector<int> ranks;
    int target = wanho[0] + wanho[1];
    int max_val = 0;
    sort(scores.begin(), scores.end(), [](vector<int> &a, vector<int> &b){
        return a[0] == b[0] ? a[1] < b[1] : a[0] > b[0];
    });
    
    for(auto& score : scores){
        if(score[1] < max_val){
            if(score == wanho) return -1;
        }else{
            ranks.push_back(score[0] + score[1]);
            max_val = max(max_val, score[1]);
        }
    }
    
    sort(ranks.begin(), ranks.end(), greater<int>());
    
    auto iter = lower_bound(ranks.begin(), ranks.end(), target, greater<int>());
    
    return iter - ranks.begin() + 1;
}