#include <iostream>
#include <vector>
#include <queue>
#include <deque>

using namespace std;

int bfs(int from, int to){
    deque<int> dq;
    dq.push_back(from);
    vector<int> dist(100001, 0);
    dist[from] = 1;

    while(!dq.empty()){
        int cur = dq.front();
        dq.pop_front();

        if(cur == to){
            return dist[cur] - 1;
        }  

        // 순간이동
        int nxt = cur * 2;
        if(nxt < 100001 && !dist[nxt]){
            dq.push_front(nxt);
            dist[nxt] = dist[cur];
        }
        // 우측으로 한칸 이동
        nxt = cur + 1;
        if(nxt < 100001 && !dist[nxt] ){
            dq.push_back(nxt);
            dist[nxt] = dist[cur] + 1;
        }

        // 좌측으로 한칸 이동
        nxt = cur - 1;
        if(nxt >= 0 && !dist[nxt]){
            dq.push_back(nxt);
            dist[nxt] = dist[cur]+1;
        }
    }
}

int main(void)
{
    int n, k;
    cin >> n >> k;
    cout << bfs(n, k) << endl; 
    return 0;
}
