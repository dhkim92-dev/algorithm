#include <iostream>
#include <vector>
#include <string>
#include <queue>
#include <algorithm>

using namespace std;

int N;
vector<vector<int>> childs;

int dfs(int idx) {
    // 각 노드는 직속부하가 순수하게 자신의 하위트리에 전파하는데 걸리는 시간을
    // 구한다.
    int spent_time = 0;
    vector<int> subtree;
    int max_time = childs[idx].size(); // 직속 부하 수에 따른 최대 전파 시간

    for(int i = 0 ; i < childs[idx].size() ; i++) {
        subtree.push_back( dfs(childs[idx][i]));
    }

    sort(subtree.begin(), subtree.end());
    // 각 직속부하의 전파 시간을 오름차순으로 정렬한다.

    for(int i = 0 ; i < subtree.size() ; i++) {
        spent_time = max(spent_time, subtree[i] + max_time);
        max_time--;
    }

    return spent_time;
}


void print_childs() {
    for(int i = 0 ; i < N ; i++) {
        cout << "childs of " << i << " : ";

        for(int j = 0 ; j < childs[i].size() ; j++) {
            cout << childs[i][j] << ", ";
        }
        cout << endl;
    }
}

int main(void)
{
    cin >> N ;
    childs.resize(N);
   
    int idx;
    cin >> idx;

    for(int i = 1 ; i < N ; i++) {
        cin >> idx;
        childs[idx].push_back(i);
    }

    //print_childs();
    cout << dfs(0) << endl;

    return 0;
}
