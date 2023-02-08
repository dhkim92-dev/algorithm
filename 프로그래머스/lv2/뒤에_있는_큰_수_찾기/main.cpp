#include <string>
#include <vector>
#include <stack>
#include <iostream>

using namespace std;


vector<int> solution(vector<int> numbers) {
    vector<int> answer(numbers.size(), 0);

    stack<int> st;

    for(int i = 0 ; i < numbers.size() ; i++){
        if(st.empty()){
            st.push(i);
            continue;
        }

        if(numbers[st.top()] >= numbers[i]){
            st.push(i);
        }else{
            while(!st.empty()){
                if(numbers[st.top()] < numbers[i]){
                    answer[st.top()] = numbers[i];
                    st.pop();
                    
                    if(st.empty()){
                        st.push(i);
                        break;
                    }
                }else{
                    st.push(i);
                    break;
                }
            }
        }
    }

    while(!st.empty()){
        answer[st.top()] = -1;
        st.pop();
    }

    return answer;
}
