#include <string>
#include <vector>
#include <memory.h>
#include <sstream>
#include <algorithm>

using namespace std;

int parents[50*50+1];
string cells[50*50+1];

void init_parents()
{
    //memset(parents, -1, sizeof(int)*(50*50+1));
    for(int i = 0 ; i < 50*50+1 ; i++){
        parents[i]=i;
    }
}

int find(int idx)
{
    //if(parents[idx] == idx ) return idx;
    //return find(parents[idx]);
    if(parents[idx]!=idx) parents[idx] = find(parents[idx]);
    return parents[idx];
}

void merge(int r1, int c1, int r2, int c2)
{
    int root1 = find(r1*50+c1);
    int root2 = find(r2*50+c2);
    if (root1 == root2) return;
    
    
    if((cells[root1].empty()) && (!cells[root2].empty())){
        parents[root1] = root2;
        cells[root1] = cells[root2];
    }else{
        cells[root2] = cells[root1];
        parents[root2] = root1;
    }
}

void unmerge(int r, int c){
    int idx = r*50 + c;
    int root = find(idx);
    string value = cells[root];
    vector<int> nodes;
    
    for(int i = 0 ; i < 2500 ; i++){
        int root2 = find(i);
        if(root2 == root){
            nodes.push_back(i);
        }
    }
    
    for(auto i : nodes){
        parents[i] = i;
        cells[i] = "";
    }
    
    cells[idx] = value;
}

void update(int r, int c, string value)
{
    int index = r*50 + c;
    int root = find(index);
    cells[root] = value;
}

void replace(string from, string to)
{
    for(int i = 0 ; i < 2500 ; i++){
        //int root = find(i);
        if(cells[i] == from){
            cells[i] = to;
        }
    }
}

string print(int r, int c)
{
    int idx = find(r*50 + c);
    if(cells[idx].empty()) return "EMPTY";
    return cells[idx];
}

void init_cells(){
    for(int i = 0 ; i <2500 ; i++) cells[i] = "";
}

vector<string> solution(vector<string> commands) {
    vector<string> answer;
    init_parents();
    init_cells();
    
    for(string command : commands){
        stringstream ss;
    	ss.str(command);
        string cmd;
        ss >> cmd;
        string value, from, to;
        int r, c, r1,c1,r2,c2;
        
        if(cmd=="UPDATE"){
                if(count(command.begin(), command.end(), ' ') == 3){
                    ss >> r >> c >> value;
                    r--; c--;
                    update(r, c, value);
                }else{
                    ss >> from >> to;
                    replace(from, to);
                }
        }else if(cmd == "MERGE"){
                ss >> r1 >> c1 >> r2 >> c2;
                r1--;c1--;r2--;c2--;
                merge(r1,c1, r2,c2);
        }else if(cmd == "UNMERGE"){
                ss >> r >> c;
                r--; c--;
                unmerge(r, c);
        }else {
                ss >> r >> c;
            	r--;
                c--;
                string val = print(r,c);
                answer.push_back(val);
        }
    }
    
    return answer;
}
