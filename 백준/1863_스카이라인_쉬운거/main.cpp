#include <iostream>
#include <vector>
#include <stack>

using namespace std;

int n;

int main(void)
{
    int n = 0;
    int answer = 0;
    stack<int> s;

    cin >> n;

    int x, y;   
    for(int i = 0 ; i < n ; i++){
        cin >> x >> y;

        // 스택 최상단 값보다 큰 값만 스택에 넣는다.
        // 만약 스택 최상단 값보다 작은 값이 들어오면 스택 내의 모든 높이들은 건물로 확정된다.

        while(!s.empty() && s.top() > y){
            if(s.top()){
                answer++;
            }
            s.pop();
        }

        if(!s.empty() && s.top() == y) continue;
        s.push(y);
    }

    while(!s.empty()){
        if(s.top()) answer++;
        s.pop();
    }

    cout << answer << endl;

    return 0;
}
