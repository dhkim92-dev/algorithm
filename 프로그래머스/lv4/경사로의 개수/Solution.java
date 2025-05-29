import java.util.*;
import java.io.*;

class Solution {
    
    private int[][] grid;
    
    private int R, C, L;
    
    private int[] d;
    
    private int k;
    
    private long[][][] dp;
    
    private long[][][] path;
    
    private long MOD = 1_000_000_007;
    
    private int[] dr = {-1, 0, 1, 0};
    
    private int[] dc = {0, 1, 0, -1};
    
    private void init(int[][] grid, int[] d, int k) {
    	this.grid = grid;
        this.R = grid.length;
        this.C = grid[0].length;
        this.L = this.R * this.C;
        this.d = d;
        this.k = k;
        this.dp = new long[d.length + 1][L][L];
    }
    
    private long[][] matmul(long[][] m, long[][] n) {
        int M = m.length;
        int N = n[0].length;
        int C = m[0].length;
        long[][] res = new long[M][N];
        
        for ( int i = 0 ; i < M ; ++i ) {
            for ( int j = 0 ; j < N ; ++j ) {
            	for ( int k = 0 ; k < C ; ++k ) {
                    res[i][j] += m[i][k] * n[k][j];
                    res[i][j] %= MOD;
                }
            }
        }
        
        return res;
    }
    
    private boolean isInRange(int r, int c) {
        return 0 <= r && r < R && 0 <= c && c < C;
    }
    
    private void findPath(int offset, int turn) {
        int r = offset / C;
        int c = offset % C;
        
        for ( int i = 0 ; i < 4 ; ++i ) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            int nL = nr * C + nc;
            
            if ( !isInRange(nr, nc) || grid[nr][nc] - grid[r][c] != d[turn-1] ) continue;
            
            for ( int j = 0 ; j < L ; ++j ) {
                dp[turn][j][nL] += dp[turn-1][j][offset];
                dp[turn][j][nL] %= MOD;
            }
        }
    }
    
    private long[][] getIdentity(int n) {
        long[][] mat = new long[n][n];
        
        for ( int i = 0 ; i < n ; ++i ) {
            mat[i][i] = 1;
        }
        
        return mat;
    }

    private long[][] matpow(long[][] m, int k) {
        if ( k == 1) {
            return m;
        }
        
        long[][] mat = matpow(m, k/2);
        mat = matmul(mat, mat);
        
        if ( k % 2 == 1 ) {
            return matmul(mat, m);
        } else {
            return mat;
        }
    }
    
    public int solution(int[][] grid, int[] d, int k) {
        int answer = 0;
        init(grid, d, k);
        
        for ( int i = 0 ; i < L ; ++i ) dp[0][i][i] = 1;
        
        for ( int turn = 1 ; turn <= d.length ; ++turn ) {
            for ( int start = 0 ; start < L ; ++start) {
                findPath(start,  turn);
            }
        }
        
        long[][] mat = getIdentity(L);
        mat = matpow(dp[d.length], k);
        
        for ( int i = 0 ; i < L ; ++i ) {
            for ( int j = 0 ; j < L ; ++j ) {
                answer += mat[i][j];
                answer %= MOD;
            }
        }
        
        return answer;
    }
}
