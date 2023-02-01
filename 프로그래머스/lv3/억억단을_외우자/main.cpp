#include <string>
#include <vector>
#include <algorithm>

using namespace std;

struct Divisors{
    int number;
    int count;

    bool operator< (const Divisors& nxt) const {
        if(count != nxt.count) return count > nxt.count;
        return number < nxt.number;
    }
};

vector<int> solution(int e, vector<int> starts) {
    vector<int> answer;
    vector<Divisors> divisors(e+1);

    for(int i = 1 ; i <= e ; i++){
        divisors[i].number = i;
        divisors[i].count = 2; // 1과 자기자신으로 무조건 나누어짐
    }

    divisors[1].count = 1;

    for(int i = 2 ; i <= e ; i++){
        for(int j = i ; j <= e ; j++){
            long long limit = i*j;
            if(limit > e) break;
            divisors[limit].count += (i == j) ? 1 : 2;
        }
    }

    sort(divisors.begin() + 1, divisors.end());

    for(int s : starts){
        for(int i = 1 ; i<=e ; i++){
            if(divisors[i].number >= s && divisors[i].number <= e){
                answer.push_back(divisors[i].number);
                break;
            }
        }
    }

    return answer;
}
