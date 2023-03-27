#include <iostream>
#include <vector>
#include <stack>


using namespace std;

int N;

struct Tower{
    int height;
    int index;
};

void search(vector<Tower> &towers){
    stack<Tower> s;

    s.push(towers[0]);
    vector<int> answer;
    answer.push_back(0);

    for(int i = 0 ; i < towers.size() ; i++){
        Tower cur = towers[i];
        
        while(!s.empty()){
            if(cur.height < s.top().height){
                cout << s.top().index << " ";
                break;
            }
            s.pop();
        }

        if(s.empty()){
            cout << 0 << " ";
        }
        s.push(cur);
        answer.push_back(cur.index);
    }
    
    cout <<endl;
}

int main(void)
{
    vector<Tower> towers;

    cin >> N;

    towers.resize(N);

    for(int i = 0 ; i < N ; i++){
        cin >> towers[i].height;
        towers[i].index = i+1;
    }

    search(towers);

    return 0;
}
