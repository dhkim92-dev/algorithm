#include <iostream>
#include <vector>

using namespace std;

void turn_on_lamp(int index, string &status, int limit)
{
    for(int i = index - 1 ; i <= index + 1 ; i++){
        if(i>=0 && i < limit){
            status[i] = status[i] == '0' ? '1' : '0';
        }
    }
}

int simulate(bool first_on, const string &status, const string &target)
{
    string s = status;
    int limit = status.length();
    int cnt = 0;

    if(first_on){
        turn_on_lamp(0, s, limit);
        cnt++;
    }

    for(int i = 1 ; i < limit ; i++){
        if(s[i-1] != target[i-1]){
            turn_on_lamp(i, s, limit);
            cnt++;
        }
    }

    if(s == target){
        return cnt;
    }else{
        return 1e9;
    }
}

int solution(int n, string &status, const string &target)
{
    int answer = 1e9;

    answer = min(answer, simulate(false, status, target));
    answer = min(answer, simulate(true, status, target));
    
    return answer == 1e9 ? -1 : answer;
}

int main(void)
{
    int n;
    cin >> n;
    
    string status;
    string target;

    for(int i = 0 ; i < n ; i++){
        cin >> status;
        cin >> target;
    }

    cout << solution(n, status, target) << endl;

    return 0;
}
