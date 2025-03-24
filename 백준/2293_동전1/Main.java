import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int n, k;
    private int[] coins;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        n = Integer.parseInt(tokens[0]);
        k = Integer.parseInt(tokens[1]);

        coins = new int[n];

        for ( int i = 0 ; i < n ; i++ ) {
            coins[i] = Integer.parseInt(reader.readLine());
        }
    }

    private int bottomUp() {
        int[] dp = new int[k+1];
        dp[0] = 1;

        for ( int i = 0 ; i < n ; i++) {
            for ( int j = 1 ; j <= k ; j++) {
                if ( j < coins[i] ) continue;
                dp[j] += dp[j - coins[i]];
            }
        }

        return dp[k];
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.append(bottomUp()));
    }
}

