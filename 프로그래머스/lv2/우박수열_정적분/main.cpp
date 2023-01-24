#include <string>
#include <vector>
#include <iostream>

using namespace std;

void get_y_points(vector<int>& y, int x){
    y.push_back(x);
    while(x!=1){
        if(x&0x01){
            x = x*3 + 1;
            y.push_back(x);
            //cout << "x : " << x << endl;
        }else{
            x = x/2;
            y.push_back(x);
            //cout << "x : " << x << endl;
        }
    }
}

void calc_area(vector<double>& area, vector<int> &y, int from, int to){
    if(from < to){
        area.push_back(-1.0);
    }else{
        double integral = 0.0f;
        for(int i = from ; i < to ; i++){
            integral+= (y[i] + y[i+1])/(double)2.0;
        }
        area.push_back(integral);
    }
}

vector<double> solution(int k, vector<vector<int>> ranges) {
    vector<double> answer;
    vector<int> y;
    vector<double> area;

    get_y_points(y, k);
    area.resize(y.size() - 1, 0.0);
    
    int end_offset = y.size();

    for(auto &range : ranges){
        calc_area(answer, y, range[0], end_offset + range[1] - 1);
    }

    return answer;
}

int main(void){
    solution(5, {{0,0},{0,-1},{2,-3},{3,-3}});
}