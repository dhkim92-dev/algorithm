#include <iostream>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

int N, M, H;
vector<vector<int>> blocks;

void string_to_blocks(int idx, string s) {
    stringstream ss(s);
    string num;
    
    while(getline(ss, num, ' ')){
        blocks[idx].push_back(stoi(num));
    }
}

void dfs(int index, int height, int& answer){

    if(height > H) {
        return ;
    }

    if(height == H) {
        answer = (answer + 1) % 10007;
        return ;
    }

    if(index > N) return ;

    for(int i = 0 ; i < blocks[index].size() ; i++) {
        dfs(index + 1, (height + blocks[index][i]), answer);
    }

    dfs(index + 1, height, answer); // 블록을 사용하지 않는 경우
}

int main(void)
{
    cin >> N >> M >> H;
    //cout << "N : " << N << " M : " << M << " H : "<< H << endl;
    blocks.resize(N+1);
    string s;

    for(int i = 0 ; i <= N ; i++) {
        string s;
        getline(cin, s);
        //cout << s << endl;
        string_to_blocks(i, s);
    }
    int answer = 0;
    dfs(1, 0, answer);

    cout << answer << endl;

    return 0;
}

