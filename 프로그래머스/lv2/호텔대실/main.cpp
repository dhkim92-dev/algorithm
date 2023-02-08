#include <string>
#include <vector>
#include <algorithm>
#include <string.h>
#include <iostream>

using namespace std;

int convert_time_string_to_minutes(string &s)
{
	return 60*stoi(s.substr(0,2)) + stoi(s.substr(3,2));
}

void mark(vector<int> &timeline, int from, int to)
{
    timeline[from]++;
    timeline[to+10]--;
}

void prefix_sum(vector<int> &arr)
{
    for(int i = 1 ; i < arr.size() ; i++){
        arr[i] = arr[i-1] + arr[i];
    }
}

int solution(vector<vector<string>> book_time) {
    int answer = 0;
    vector<int> timeline( 24*60+10, 0);
    
    for(auto book : book_time){
		int from = convert_time_string_to_minutes(book[0]);
        int to = convert_time_string_to_minutes(book[1]);
     	mark(timeline,from,to);   
    }
    
    prefix_sum(timeline);
    
	for(auto i : timeline){
        answer = max(answer, i);
    }
    
    return answer;
}
