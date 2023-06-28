#include <iostream>
#include <vector>

// 플로이드 와샬 문제.
// a 물체에서 b물체까지 a,b가 아닌 임의의 물체 c를 경유하여 도착할 수 있다면
// 비교가 가능.

using namespace std;

int main(void)
{
    int N, M;
    cin >> N;
    cin >> M;
    
    vector<vector<bool>> route(N, vector<bool>(N, false));

    int from, to;
    for(int i = 0 ; i < M ; i++) {
        cin >> from >> to;
        route[from-1][to-1] = true;
    }

    for(int i = 0 ; i < N ; i++) { // 경유지
        for(int j = 0 ; j < N ; j++) { // 출발지
            for(int k = 0 ; k < N ; k++) { // 도착지
                if(route[j][i] && route[i][k]){
                    route[j][k] = true;
                }
            }
        }
    }

    //for(int i = 0 ; i < N ; i++) {
    //    for(int j = 0 ; j < N ; j++) {
    //        cout << route[i][j] << " ";
    //    }
    //    cout << endl;
    //}

    
    for(int i = 0 ; i < N ; i++) {
        int cnt = 0;
        for(int j = 0 ; j < N ; j++) {
            if(i == j) continue;
            if(!route[i][j] && !route[j][i]) cnt++;
        }
        cout << cnt << endl;
    }

    return 0;
}
