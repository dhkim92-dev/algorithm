#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int weights[10][10]={
//   0  1  2  3  4  5  6  7  8  9
    {1, 7, 6, 7, 5, 4, 5, 3, 2, 3}, // 0에서부터 거리
    {7, 1, 2, 4, 2, 3, 5, 4, 5, 6}, // 1에서부터 거리
    {6, 2, 1, 2, 3, 2, 3, 5, 4, 5}, // 2에서부터 거리
    {7, 4, 2, 1, 5, 3, 2, 6, 5, 4}, // 3에서부터 거리
    {5, 2, 3, 5, 1, 2, 4, 2, 3, 5}, // 4에서부터 거리
    {4, 3, 2, 3, 2, 1, 2, 3, 2, 3}, // 5에서부터 거리
    {5, 5, 3, 2, 4, 2, 1, 5, 3, 2}, // 6에서부터 거리
    {3, 4, 5, 6, 2, 3, 5, 1, 2, 4}, // 7에서부터 거리
    {2, 5, 4, 5, 3, 2, 3, 2, 1, 2}, // 8에서부터 거리
    {3, 6, 5, 4, 5, 3, 2, 4, 2, 1} // 9에서부터 거리
};

int next_value(string &numbers, vector<vector<vector<int>>>& cache, int idx, int left, int right){
    if(numbers.length() == idx) return 0;

    if(cache[idx][left][right] != 1e9) return cache[idx][left][right];

    int target = static_cast<int>(numbers[idx] - '0');

    if(left == target || right == target){
        return 1 + next_value(numbers,cache, idx+1, left,right);
    }

    cache[idx][left][right] = 
        min(
            next_value(numbers, cache, idx+1, target, right) + weights[left][target], 
            next_value(numbers, cache, idx+1, left, target) + weights[right][target]
        );
    return cache[idx][left][right];
}

int solution(string numbers) {
    int answer = 0;
    vector<vector<vector<int>>> cache(numbers.size(), vector<vector<int>>(10, vector<int>(10, 1e9)));
    int left = 4;
    int right = 6;

    //cache[0][4][6] = 0;
    return next_value(numbers, cache, 0, 4, 6);   
}