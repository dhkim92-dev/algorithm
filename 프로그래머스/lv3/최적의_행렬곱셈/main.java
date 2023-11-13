import java.util.*;

class Solution {
    int INF = Integer.MAX_VALUE;
    int answer = 0;
    int[][] cache;
    
    int dfs(int left, int right, int [][] matrixSizes) {
        if(left == right) {
            return 0;
        }
        
        if(cache[left][right] != -1) {
            return cache[left][right];
        }
        
        cache[left][right] = 0x01<<30;
        
        for(int i = left ; i < right ; i++) {
            cache[left][right] = Math.min(
                cache[left][right],
                dfs(left, i, matrixSizes) + dfs(i+1, right, matrixSizes) + matrixSizes[left][0] * matrixSizes[i][1] * matrixSizes[right][1]
            );
        }
        
        return cache[left][right];
    }
    
    public int solution(int[][] matrixSizes) {
        this.cache = new int[matrixSizes.length][matrixSizes.length];
        
        for(int i = 0 ; i < cache.length ; i ++) {
            for(int j = 0  ; j < cache.length ; j++) {
                cache[i][j] = -1;
            }
        }
        
        answer = dfs(0, matrixSizes.length - 1, matrixSizes);
        
        return answer;
    }
}