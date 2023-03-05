#include <string>
#include <vector>
#include <cmath>

using namespace std;

long long solution(vector<int> sequence) {
    vector<vector<long long>> cache(sequence.size(), vector<long long>(2, 0));
    int n = sequence.size();
    long long answer = max(sequence[0], -sequence[0]);
    cache[0][0] = sequence[0];
    cache[0][1] = -sequence[0];

    for(int i = 1 ; i < n ; ++i){
        cache[i][0] = max(cache[i-1][1] + sequence[i],
                          static_cast<long long>(sequence[i]));
        cache[i][1] = max(cache[i-1][0] - sequence[i], 
                          static_cast<long long>(-sequence[i]));
        answer = max(answer, max(cache[i][0], cache[i][1]));
    }
    
    return answer;
}

