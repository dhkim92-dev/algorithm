#include <string>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

long long solution(int cap, int n, vector<int> deliveries, vector<int> pickups)
{
    long long answer = 0;
    stack<int> d, p; // 
    int count = 0;
        
    for(auto i : deliveries) 
        d.push(i);
    for(auto i : pickups) 
        p.push(i); 
    
    while(!d.empty() && (d.top() == 0))
        d.pop();
    while(!p.empty() && (p.top() == 0))
        p.pop();
    
    while(!(d.empty() && p.empty())){
        answer += max(d.size() * 2 , p.size() * 2);
        count = 0;
        
        while(!d.empty() && count <= cap){
            if(d.top() + count <= cap){
                count += d.top();
            }else{
                d.top() -= (cap - count);
                break;
            }
        	d.pop();
        }
        
        count = 0;
        
        while(!p.empty() && count <= cap){
            if(p.top() + count <= cap){
                count += p.top();
            }else{
                p.top() -= (cap - count);
                break;
            }
        	p.pop();
        }
    }
    
    return answer;
}
