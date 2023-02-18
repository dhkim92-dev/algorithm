#include <string>
#include <vector>
#include <algorithm>

using namespace std;

void search(int start, int length, vector<int> &weak, vector<int>& dist, vector<bool> &visit, int &answer)
{
    bool is_visit_all = true;

    for(auto v : visit){
        if(!v){
            is_visit_all = false;
            break;
        }
    }

    if(is_visit_all){
        answer = min(answer, start);
        return ;
    }

    if(start==dist.size()){
        return ;
    }

    for(int i = 0 ; i < weak.size() ; i++){
        if(!visit[i]){
            int count = 0;
            for(int j = 0 ; j < weak.size() ; j++){
                int distance = weak[j] - weak[i];
                if(distance < 0) distance += n;
                if(distance <= dist[start]){
                    visit[j] = true;
                    
                }
            }
        }
    }
}



int solution(int n, vector<int> weak, vector<int> dist) {
    int answer = 1e9;
    vector<bool> visit(weak.size(), false);
    sort(dist.begin(), dist.end());
    search(0, n, weak, dist, visit, answer);

    return answer;
}
