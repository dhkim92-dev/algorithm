package algorithm;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;

    private int F;

    private int[] dp = new int[45];

    private int[] sum = new int[45];


    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        F = Integer.parseInt(reader.readLine());
    }

    public void run () {
        int answer = 0;

        dp[0] = 1;
        dp[1] = 1;
        sum[0] = 0;
        sum[1] = 1;

        for(int i = 2 ; i <= N ; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        for(int i = 2 ; i <= N ; i++) {
            sum[i] = sum[i - 1] + dp[i-1];
        }

        int left = F-1;
        int right = N-F;

        answer = dp[left] * dp[right];

        for(int i = 1 ; i <= left ; i++) {
            answer += ((sum[i] * dp[left-i]) + (dp[i-1] * sum[left-i])) * dp[right];
        }

        for(int i = 1 ; i <= right ; i++) {
            answer += ((sum[i] * dp[right-i]) + (dp[i-1] * sum[right-i])) * dp[left];
        }

        System.out.println(answer);
    }
}


