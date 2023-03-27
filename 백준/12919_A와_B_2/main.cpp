#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

string tmp;

void dfs(string s, string t){
    if(s==t){
        cout << 1 << endl;
        exit(0);
    }

    if(s.length() >= t.length()) return;

    if(t[t.length() - 1] == 'A'){
        tmp = t;
        tmp.erase(tmp.length() - 1);
        dfs(s, tmp);
    }

    if(t[0] == 'B'){
        tmp = t;
        tmp.erase(tmp.begin());
        std::reverse(tmp.begin(), tmp.end());
        dfs(s,tmp);
    }
}

int main (void)
{
    string s, t;

    cin >> s;
    cin >> t;
    dfs(s,t);   
    cout << 0 << endl;
    return 0;
}
