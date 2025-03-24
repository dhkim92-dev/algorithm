#include <string>
#include <vector>

using namespace std;

vector<vector<int>> clock_copy;

int solution(vector<vector<int>> clockHands) {
    int answer = 0;
    std::copy(clockHands.begin(), clockHands.end(), back_inserter(clock_copy));

    return answer;
}
