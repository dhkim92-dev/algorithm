#include <iostream>
#include <vector>
#include <queue>
#include <cstring>

using namespace std;

bool visited[100][100];

struct Pos{
    int r,c;

    bool operator == (const Pos &p) const {
        return r == p.r && c == p.c;
    }

    Pos operator + (const Pos & p) const {
        return {r + p.r, c + p.c};
    }
};

Pos dirs[4] = {
    {1, 0},
    {0, 1},
    {-1, 0},
    {0, -1}
};

void init_fields(vector<vector<vector<int>>> & fields, int r){
    for(int i = 0 ; i < r ; i++){
        int r0,c0, r1, c1;
        cin >> r0 >> c0 >> r1 >> c1;
        r0--;
        c0--;
        r1--;
        c1--;
        Pos dir = {r1 - r0, c1 - c0};
        
        for(int j = 0 ; j < 4 ; j++){
            if(dir==dirs[j]){
                fields[r1][c1][j] = 1;
                fields[r0][c0][(j+2) %4] = 1;
            }
        }
    }

}

void init_cows(vector<Pos> &cows, int k){
    for(int i = 0 ; i < k ; i++){
        Pos p;
        cin >> p.r >> p.c;
        p.r--;
        p.c--;
        //cout << "cow position : " << p.r << ", " << p.c << endl;
        cows.push_back(p); 
    }
}

void search(int start, vector<Pos> &cows, vector<vector<vector<int>>> &fields)
{
    queue<Pos> q;
    q.push(cows[start]);
    //cout << "start from " << cows[start].r << ", " << cows[start].c << endl;
    visited[cows[start].r][cows[start].c] = true;

    int rs = fields.size();
    int cs = fields[0].size();

    auto is_in_range = [rs, cs](int r, int c){
        return (0 <= r && r < rs) && (0 <= c && c < cs);
    };

    while(!q.empty()){
        Pos cur = q.front();
        //cout << "current search position " << cur.r << ", " << cur.c << endl;
        //cout << "visit["  << cur.r << "]["<<cur.c << "] = " << visited[cur.r][cur.c] << endl;
        q.pop();

        for(int i = 0 ; i < 4 ; i++){
            Pos nxt = cur + dirs[i];
            if(!is_in_range(nxt.r, nxt.c)) continue;
            if(fields[nxt.r][nxt.c][i]==1) continue; // 길이 있다면 패스
            if(visited[nxt.r][nxt.c]) continue;

            q.push(nxt);
            visited[nxt.r][nxt.c] = true;
        }
    }
}

void solution(int n, int k, int r){
    vector<vector<vector<int>>> fields(n, vector<vector<int>>(n, vector<int>(4, 0)));
    vector<Pos> cows;
    int answer = 0;

    init_fields(fields, r);
    init_cows(cows, k);

    for(int i = 0 ; i < cows.size() ; i++){
        memset(visited, false, sizeof(visited));
        search(i, cows,  fields);
        
        for(int j = i+1 ; j < cows.size() ; j++){
            // cout << "cow " << cows[i].r << ", " << cows[i].c  << endl; 
            // cout << "visited["  << cows[j].r << "]["<<cows[j].c << "] = " << visited[cows[j].r][cows[j].c] << endl;
            if(!visited[cows[j].r][cows[j].c]){
                answer++;
                // cout << " can not meet with cow " << j << endl;
            }else{
                // cout << " can meet with cow " << j << endl;
            }
        }
    }

    cout << answer << endl;
}

int main(void)
{
    int n,k,r;
    cin >> n >> k >> r;
    solution(n, k, r);
    return 0;
}