import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;
    private long[] dp;
    private final long MOD = 1_000_000_000;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        dp = new long[N+3];
    }

    private long bottomUp() {
        dp[0] = 0;
        dp[1] = 0;
        dp[2] = 1;

        for ( int i = 3 ; i <= N ; i++ ) {
            // A, B 두 사람이 서로 선물을 교환하는 경우
            // dp[i] = dp[i-2] * (n-1) 
            //
            // A는 B에게 줬지만, B가 A가 아닌 다른 사람에게 선물을 준 경우
            // dp[i] = dp[i-1] * (n-1)
            dp[i] = ((dp[i-2] + dp[i-1]) * (i-1)) % MOD;
        }

        return dp[N];
    }


    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(bottomUp());
        System.out.println(sb.toString());
    }
}
