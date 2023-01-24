#include <string>
#include <vector>
#include <algorithm>

using namespace std;

void calc_max(vector<vector<int>> &problems, int &alp, int &cop)
{
    for(auto &p : problems){
        alp = max(alp, p[0]);
        cop = max(cop, p[1]);
    }
}

int solution(int alp, int cop, vector<vector<int>> problems) {
    int answer = 0, alp_max=0, cop_max = 0;

    calc_max(problems, alp_max, cop_max);
    vector< vector<int> > cache(alp_max+2, vector<int>(cop_max+2, INT32_MAX));

    if(alp >= alp_max && cop >= cop_max){
        return 0;
    }

    if(alp >= alp_max){
        alp = alp_max;
    }

    if(cop >= cop_max){
        cop = cop_max;
    }

    cache[alp][cop] = 0;

    for(int a = alp ; a <= alp_max ; a++){
        for(int c = cop ; c <= cop_max ; c++){
            cache[a+1][c] = min( cache[a+1][c], cache[a][c]+1); // 코딩력 공부 1시간
            cache[a][c+1] = min(cache[a][c+1], cache[a][c]+1); // 알고력 공부 1시간
            for(auto &p : problems){
                // p[0] = alp_req
                // p[1] = cop_req
                // p[2] = alp_rwd
                // p[3] = cop_rwd
                // p[4] = cost

                if(a >= p[0] && c >= p[1]){ // 문제를 풀 수 있는 경우
                    int nxt_alp = (a + p[2]) ;
                    int nxt_cop = (c + p[3]) ;
                    int nxt_val = cache[a][c] + p[4];

                    if(nxt_alp >= alp_max && nxt_cop >= cop_max){
                        cache[alp_max][cop_max] = min(cache[alp_max][cop_max], nxt_val);
                    }else if(nxt_alp >= alp_max){
                        cache[alp_max][nxt_cop] = min(cache[alp_max][nxt_cop], nxt_val); 
                    }else if(nxt_cop >= cop_max){
                        cache[nxt_alp][cop_max] = min(cache[nxt_alp][cop_max], nxt_val);
                    }else{
                        cache[nxt_alp][nxt_cop] = min(cache[nxt_alp][nxt_cop], nxt_val);
                    }
                }
            }
        }
    } 

    return cache[alp_max][cop_max];
}