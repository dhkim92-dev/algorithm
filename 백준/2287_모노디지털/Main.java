
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int K, N;
    private int[] targets;

    public Solution(BufferedReader reader) throws IOException {
        K = Integer.parseInt(reader.readLine());
        N = Integer.parseInt(reader.readLine());

        targets = new int[N];

        for(int i = 0 ; i < N ; i++) {
            targets[i] = Integer.parseInt(reader.readLine());
        }
    }


    public void run () {
        StringBuilder sb = new StringBuilder();
        Set<Integer>[] dp = new Set[9];

        for (int i = 0 ; i < 9 ; i++) {
            dp[i] = new HashSet<>();
        }

        for (int i = 1 ; i <= 8 ; i++) {
            int target = Integer.parseInt(String.valueOf(K).repeat(i));
            dp[i].add(target);
        }

        for (int i = 2 ; i <= 8 ; i++) {
            for (int j = 1 ; j < i ; j++) {
                for (int a : dp[j]) {
                    for (int b : dp[i-j]) {
                        dp[i].add(a+b);
                        dp[i].add(a-b);
                        dp[i].add(a*b);

                        if(b != 0) {
                            dp[i].add(a/b);
                        }
                    }
                }
            }
        }

        for (int target : targets) {
            boolean found = false;

            for (int i = 1 ; i <= 8 ; i++) {
                if(dp[i].contains(target)) {
                    sb.append(i).append("\n");
                    found = true;
                    break;
                }
            }

            if(!found) {
                sb.append("NO\n");
            }
        }

        System.out.println(sb.toString());
    }
}


