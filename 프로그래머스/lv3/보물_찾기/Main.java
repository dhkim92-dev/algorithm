import java.util.function.Function;

class Solution {

    public int solution(int[] depth, int money, Function<Integer, Integer> func) {
        int n = depth.length;
        
        
        // 관찰1. 내가 어떤 전략을 세우더라도 최악의 경우가 되도록 재배치가 가능하다는 것이 힌트
        // => Minimax 계열 문제임 
        
        int[][] dp = new int[n][n]; // dp[l][r] => 구간 [l, r] 에 대해 답을 찾는데 들어가는 최악의 비용을 최소화 한 결과 
        int[][] root = new int[n][n]; // root[l][r] => 최악의 비용을 최대한 줄이기 위해 어떤 노드를 먼저 탐색해야하는지 기록 (트리의 루트)
        
        for(int i = 0 ; i < n ; ++i ) {
            dp[i][i] = depth[i];
            root[i][i] = i;
        }
        
        for ( int len = 2 ; len <= n ; ++len ) {
            for ( int l = 0 ; l + len - 1 < n ; l++ ) {
                int r = l + len - 1;
                dp[l][r] = Integer.MAX_VALUE;
                
                for ( int k = l ; k <= r ; ++k ) {
                    int leftCost = (k>l) ? dp[l][k-1] : 0;
                    int rightCost = (k < r) ? dp[k+1][r] : 0;
                    int cost = depth[k] + Math.max(leftCost, rightCost);
                    
                    if ( cost < dp[l][r] ) {
                        dp[l][r] = cost;
                        root[l][r] = k;
                    }
                }
            }
        }
        
        if ( dp[0][n-1] > money ) {
            return -1;
        }
        
        int lo = 0;
        int hi = n-1;
        
        while( lo <= hi ) {
            int k = root[lo][hi];
            int res = func.apply(k+1);
            
            if ( res == 0) {
                return k+1;
            } else if ( res == -1 ){
                hi = k - 1;
            } else {
                lo = k + 1;
            }
        }
        
        return -1;
    }
}