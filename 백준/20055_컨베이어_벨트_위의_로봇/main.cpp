#include <iostream>
#include <string>
#include <algorithm>
#include <queue>

using namespace std;

int N, K;
int arr[1001];
int l, r;

queue<int> robots;
bool visited[1001] = {false,};
int cnt=0;

void _rotate()
{
    l--;
    r--;
    if(l<1) l = 2*N;
    if(r<1) r = 2*N;
}

void move_robots(){
    int qsize = robots.size();
    for(int i = 0 ; i < qsize ; i++){
        int cur = robots.front();
        robots.pop();

        visited[cur] = false;
        if(cur==r) continue;

        int nxt = cur + 1;

        if(nxt > 2*N){
            nxt = 1;
        }

        if(arr[nxt] >= 1 && !visited[nxt]){
            arr[nxt]--;
            if(arr[nxt]==0){
                cnt++;
            }

            if(nxt == r){
                continue;
            }

            visited[nxt] = true;
            robots.push(nxt);
        }
        else{
            visited[cur] = true;
            robots.push(cur);
        }
    }
}

void put_robots(){
    if((!visited[l]) && arr[l] >= 1){
        visited[l] = true;
        arr[l]--;
        robots.push(l);

        if(arr[l] == 0) cnt++;
    }
}

int main(void)
{
    cin >> N >> K;

    for(int i = 0 ; i<2*N ; i++){
        cin >> arr[i+1];
    }

    l = 1 ;
    r = N;

    // rotate
    int round = 0;
    while(cnt < K){
        round++;
        _rotate();
        move_robots();
        put_robots();
    }

    cout << round << endl;

    return 0;
}
