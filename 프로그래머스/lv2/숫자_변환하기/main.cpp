#include <vector>
#include <string>
#include <unordered_set>
#include <queue>

using namespace std;

int solution(int x, int y, int n)
{
    int answer = 0 ;
    queue<int> q;
    unordered_set<int> hs;
    
    q.push(x);

    while(!q.empty()){
        int qsize = q.size();
        
        for(int i = 0 ; i < qsize ; i++){
            int val = q.front();
            q.pop();
            int nadd = val+n;
            int xx2 = val*2;
            int xx3 = val*3;

            if(val == y){
                return answer;
            }

            if( nadd <= y && hs.find(nadd) == hs.end()){
                hs.insert(nadd);
                q.push(nadd);
            }
            if( xx2 <= y && hs.find(xx2) == hs.end() ){
                hs.insert(xx2);
                q.push(xx2);
            }
            if(xx3 <= y && hs.find(xx3) == hs.end()){
                hs.insert(xx3);
                q.push(xx3);
            }
        }

        answer++;
    }

    return -1;
}

int main(void)
{


    return 0;
}
