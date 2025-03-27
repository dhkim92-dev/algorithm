import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;
    private final int MOD = 987_654_321;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
    }

    private int bottomUp() {
        long[] dp = new long[N+5];
        dp[0] = 1;
        dp[2] = 1;
        dp[4] = 2;

        for ( int i = 6 ; i <= N ; i+=2 ) {
            dp[i] = 0; 
            // dp[0] * dp[i - 2]
            // dp[2] * dp[i - 4]
            // dp[4] * dp[i - 6]
            // ...
            //
            for ( int k = 2 ; i - k >= 0 ; k += 2 ) {
                dp[i] = ((dp[k-2] * dp[i - k]) % MOD + dp[i] ) % MOD;
            }
        }

        return (int)dp[N];
    }


    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(bottomUp());
        System.out.println(sb.toString());
    }
}

