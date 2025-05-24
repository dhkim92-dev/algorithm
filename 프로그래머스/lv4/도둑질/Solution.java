import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[] money) {
        int answer = 0;
        
        int[] dp = new int[money.length + 1];
        
        // 첫 집을 터는 경우
        dp[0] = money[0];
        dp[1] = money[0];
        
        for ( int i = 2 ; i < money.length - 1 ; ++i ) {
            dp[i] = Math.max(dp[i-2] + money[i], dp[i-1]);
        }
        answer = Math.max(answer, dp[money.length-2]);
        
        Arrays.fill(dp, 0);
        dp[0] = 0;
        dp[1] = money[1];
        
        for ( int i = 2 ; i < money.length ; ++i ) {
            dp[i] = Math.max(dp[i-2] + money[i], dp[i-1]);
        }
        answer = Math.max(answer, dp[money.length - 1]);
        
        return answer;
    }
}
