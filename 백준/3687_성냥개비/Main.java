import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;
    private int[] matches;
    private int[] requires = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6}; // requires[i] = i 모양 숫자를 만들기 위한 성냥 개수

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        matches = new int[N];
        for(int i = 0 ; i < N ; i++) {
            matches[i] = Integer.parseInt(reader.readLine());
        }
    }

    private String getMaxNumber(int match) {
        // 1을 100자리까지 붙이면 Long의 범위를 넘어선다.
        // 문자열로 풀이해야함.
        StringBuilder sb = new StringBuilder();

        // 최대 값을 만들기 위해서는 무조건 1을 최대한 많이 쓰는게 좋다.
        // 자리수가 많아야 더 큰 값이기 때문
        // 남은 성냥의 수가 짝수일 때는 무조건 1을 문자열에 더하고
        // 남은 성냥의 수가 홀수일 때는 7을 먼저 더하고 나머지는 1을 더한다.
        int leftMatch = match;
        while(leftMatch > 0) {
            if(leftMatch % 2 == 0) {
                sb.append("1");
                leftMatch -= 2;
            } else {
                sb.append("7");
                leftMatch -= 3;
            }
        }
        return sb.toString();
    }

    private long getMinNumber(int match) {
//        System.out.println("match : " + match);
        // 최소값은 DP로 구해도 long 범위 내로 해결
        long[] dp = new long[101]; // dp[i] = i개의 성냥으로 만들 수 있는 최소 숫자
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = -1;
        dp[1] = -1;
        dp[2] = 1;
        dp[3] = 7;
        dp[4] = 4;
        dp[5] = 2;
        dp[6] = 6;
        dp[7] = 8;

        for(int i = 8 ; i <= match ; i++) {
            for(int j = 0 ; j < 10 ; j++) {
                if(dp[i - requires[j]] != -1) {
                    dp[i] = Math.min(dp[i], dp[i - requires[j]] * 10 + j);
                }
            }
        }

        return dp[match];
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < N ; i++) {
            sb.append(getMinNumber(matches[i]))
                    .append(" ")
                    .append(getMaxNumber(matches[i]))
                    .append("\n");
        }

        System.out.println(sb);
    }
}


