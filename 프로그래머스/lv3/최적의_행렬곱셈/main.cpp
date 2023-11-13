#include <string>
#include <vector>
#include <iostream>
#include <memory.h>
#include <algorithm>

using namespace std;

int cache[200][200]={0,};

int dfs(int l, int r, vector<vector<int>> &ms)
{
	if(l==r) return 0;

	if(cache[l][r] != -1) return cache[l][r];
	cache[l][r] = 0x01<<30;
	
	for(int i = l ; i < r ; i++){
		cache[l][r] = min( cache[l][r],  dfs(l, i, ms) + dfs(i+1, r, ms) + ms[l][0] * ms[i][1] * ms[r][1] );
	}
	return cache[l][r];
}

int solution(vector<vector<int>> matrix_sizes) {
	vector<vector<int>> &ms = matrix_sizes;
	memset(cache, -1, sizeof(cache));
	int N = matrix_sizes.size();
	//접근방법 1 2d dynamic programming
	// cache[p][q] = p부터 q까지의 행렬곱 최소 연산 수
	// cache[p][q] = min(  cache[p][q], X);

    return dfs(0, N - 1, matrix_sizes);
}