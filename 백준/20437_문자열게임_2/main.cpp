#include <iostream>
#include <string>
#include <algorithm>
#include <vector>

using namespace std;

int T;

struct Problem{
    string s;
    int w;
};


void solution(int T, vector<Problem> problems){
    int n = problems.size();

    
    for(auto problem : problems){
        string target = problem.s;
        int K = problem.w;
        
        vector<int> alphas[26];
        for(int i = 0 ; i < target.length() ; i++){
            alphas[target[i] - 'a'].push_back(i);
        }

        int cond3 = INT32_MAX;
        int cond4 = -1;
        for(int i = 0 ; i < 26 ; i++){
            if(alphas[i].size() >= K){
                int l = 0;
                int r = K-1;

                int c3 = alphas[i][r] - alphas[i][l] + 1;
                int c4 = c3;

                while(r < alphas[i].size() - 1){
                    l++;
                    r++;
                    int new_length = alphas[i][r] - alphas[i][l] + 1;
                    c3 = min(new_length, c3);
                    c4 = max(new_length, c4);
                }
                cond3 = min(cond3, c3);
                cond4 = max(cond4, c4);
            }
        }

        if(cond3 == INT32_MAX) {
            cout << -1 << endl;
        }else{
            cout << cond3 << " " << cond4 << endl;
        }


    }
}

int main(void){
    vector<Problem> inputs;
    cin >> T;


    for(int i = 0 ; i < T ; i++){
        Problem p ;
        cin >> p.s;
        cin >> p.w;
        inputs.push_back(p);
    }

    solution(T, inputs);

    return 0;
};

