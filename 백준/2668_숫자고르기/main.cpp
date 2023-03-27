#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

using namespace std;



void dfs(int start, int nxt, bool &valid, vector<int> &value, vector<bool>& visited, set<int> &targets)
{
    if(visited[nxt]){
        if(start == nxt){
            valid = true;
            targets.insert(nxt);
        }
        return ;
    }

    visited[nxt] = true;
    dfs(start, value[nxt], valid, value, visited, targets);
    if(valid){
        targets.insert(nxt);
        //targets.insert(value[nxt]);
    }
}

void solution(vector<int> &value)
{
    vector<bool> visited(value.size(), false);
    set<int> targets;

    int answer = -1;
    
    for(int i = 1 ; i < value.size() ; i++){
        visited[i] = true;
        bool is_valid = false;
        dfs(i, value[i], is_valid, value, visited, targets);
        fill(visited.begin(), visited.end(), false);
    }

    cout << targets.size() << endl;
    for(auto c : targets){
        cout << c << endl;
    }
}

int main(void)
{
    int n;
    cin >> n;

    vector<int> value(n+1);

    for(int i = 1 ; i <= n ; i++){
        cin >> value[i];
    }

    solution(value);

    return 0;
}
