#include <string>
#include <vector>
#include <cmath>

using namespace std;

int solution(int storey) {
    int answer = 0;
    
    // 1의 자리와 10의 자리만 비교,
    // 1의 자리가 5 초과이면 10의자리 한자리 올려주고
    // answer 에 10-일의자리 값을 해준 값을 더해준다.
    // 1의 자리수가 5이면 10의자리 수에 따라 결정한다. 10의 자리수가 5 이상이면 10의자리 증가
    // 그 외에는 answer 에 1의자리 수를 더한다.
    // storey > 0 인 동안 반복
    while(storey){
        int one = storey % 10;
        int ten = (storey / 10) % 10;
        
        if(one > 5){
            answer += 10 - one;
            storey += 10;
        }else if(one == 5){
            if(ten >= 5){
                storey += 10;
            }
            answer += 5;
        }else{
            answer += one;
        }
        
        storey = storey / 10;
    }
    
    return answer;
}
