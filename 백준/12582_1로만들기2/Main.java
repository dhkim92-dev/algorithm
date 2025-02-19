package algorithm;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
    }

    public void run () {
        StringBuilder sb = new StringBuilder();

        // 3으로 나누어지면 3으로 나누고
        // 2로 나누어 떨이지면 1로 나눈다
        // 1을 뺀다.
        // 1을 만드는데 필요한 연산 횟수
        int limit = 1000001;

        int[] dp = new int[limit];
        Arrays.fill(dp, limit);

        dp[0] = 0;
        dp[1] = 0;

        for (int i = 0 ; i <= N ; i++) {
            // 가능한 다음 수 => i * 2 + 1, i* 3 + 1
            int next1 = i*2;
            int next2 = i*3;
            int next3 = i + 1;
            //System.out.println("from : " + i + " to : " + next1 + " " + next2 + " " + next3);

            if(next1 < limit) {
                dp[next1] = Math.min(dp[next1], dp[i] + 1);
            }

            if(next2 < limit) {
                dp[next2] = Math.min(dp[next2], dp[i] + 1);
            }

            if(next3 < limit) {
                dp[next3] = Math.min(dp[next3], dp[i] + 1);
            }
        }

//        for(int i = 0 ; i <= N ; i++) {
//            System.out.println("dp[" + i + "] = " + dp[i]);
//        }

        int answer = dp[N];
        sb.append(answer + "\n");
        // N을 만드는데 필요한 연산 횟수는 구했기 때문에
        // 이제 dp배열을 역추적하여 경로를 찾는다.

        int start = N;

        while(start > 1) {
            sb.append(start + " ");

            if ((start % 3 == 0) && (dp[start / 3] == dp[start] - 1)) {
                start /= 3;
            } else if((start % 2 == 0) && (dp[start/2] == dp[start] - 1)) {
                start /= 2;
            } else if(dp[start - 1] == (dp[start] - 1)) {
                start -= 1;
            }
        }
        sb.append(1);

        System.out.println(sb.toString());
    }
}


