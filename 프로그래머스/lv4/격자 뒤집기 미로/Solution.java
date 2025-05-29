import java.util.*;
import java.io.*;

class Solution {
    
    private int[][][] grid;
    
    private int R, C;
    
    int countBit(int target) {
        int cnt = 0;
        
        // 최대 길이는 14
        for ( int i = 0 ; i < 14 ; ++i ) {
            int bit = 0x01 << i;
            
            if ( (target & bit) == bit ) {
                cnt++;
            } 
        }
        
        return cnt;
    }
    
    private int flipCard(int rowMask, int k, boolean allEven) {
        int cost = countBit(rowMask) * k;
        int score = -cost;
        int loss = Integer.MAX_VALUE;
        
        // r, c가 모두 양수로 구성되어 있는 경우 
        for ( int c = 0 ; c < C ; ++c ) {
            int[] sum = {0, -k};
            int[] min = {Integer.MAX_VALUE, Integer.MAX_VALUE};
            
            for ( int r = 0 ; r < R ; ++r ) {
                int rowBit = (0x01) << r;
                int rowFlip = (rowMask & rowBit) == rowBit ? 1 : 0;
                
                for ( int colFlip = 0 ; colFlip < 2 ; ++colFlip ) {
                    int cellFlip = rowFlip ^ colFlip;
                    sum[colFlip] += grid[cellFlip][r][c];
                    
                    // R, C 가 모두 짝수이면, r 또는 c 중 하나만 홀수인 경우를 추려야함
                    if ( allEven && (r + c) % 2 == 1) {
                        min[colFlip] = Math.min(min[colFlip], grid[cellFlip][r][c]);
                    }
                    
                }
            }
            
            score += Math.max(sum[0], sum[1]);
            
            if ( allEven ) {
                int rowLoss = Math.max(sum[0], sum[1]) - Math.max(sum[0] - min[0], sum[1] - min[1]);
                loss = Math.min(rowLoss, loss);
            }
        }
        
        
        return allEven ? score - loss : score;
    }
    
    public int solution(int[][] visible, int[][] hidden, int k) {
        int answer = 0;
        R = visible.length;
        C = visible[0].length;
        int maxCases = (0x01 << visible.length);
        grid = new int[2][R][C];
        grid[0] = visible;
        grid[1] = hidden;
        boolean allEven = (R % 2 == 0) && ( C % 2 == 0);
        
        for ( int bit = 0 ; bit < maxCases ; ++bit ) {
            answer = Math.max(answer, flipCard(bit, k, allEven));
        }
        
        
        return answer;
    }
}
