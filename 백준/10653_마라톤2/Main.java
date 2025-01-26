import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, K;

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public static int distance(Pos a, Pos b) {
            return Math.abs(a.r - b.r) + Math.abs(a.c - b.c);
        }
    }

    private Pos[] checkpoints;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        K = Integer.parseInt(tokens[1]);
        checkpoints = new Pos[N];
        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            checkpoints[i] = new Pos(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        }
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        // N 개의 체크포인트 (3 <= N <= 500)
        // K 개를 건너 뜀. (K < 500)
//        int[][] dists = new int[N][N]; // dists[i][j] = i -> j 까지의 거리
//
//        for(int i = 0 ; i < N ; i++) {
//            for(int j = 0 ; j < N ; j++) {
//                dists[i][j] = Pos.distance(checkpoints[i], checkpoints[j]);
//            }
//        }
//
        int[][] dp = new int[K+1][N]; // dp[k][i] = i번째 체크포인트까지 j개를 건너 뛰었을 때 최소 거리
        // 모든 체크포인트는 기본적으로 순서대로 방문해야한다.
        for(int i = 0 ; i <= K ; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;

        int minDist, tmp;
        for(int i = 1 ; i < N ; i++) {
            for(int j = 0 ; j <= K ; j++) {
                if(i - j > 0) {
                    minDist = Integer.MAX_VALUE;
                    for(int k = 0 ; k<= j ; k++) {
                        tmp = dp[j-k][i-k-1];
                        if(tmp == -1) continue;
                        int dist = Math.abs(checkpoints[i].r - checkpoints[i-k-1].r) + Math.abs(checkpoints[i].c - checkpoints[i-k-1].c);
                        minDist = Math.min(minDist, tmp + dist);
                    }
                    dp[j][i] = minDist;
                }
            }
        }

        int res = dp[0][N-1];
        for(int i = 1 ; i <= K ; i++) {
            if(dp[i][N-1] == -1) continue;
            res = Math.min(res, dp[i][N-1]);
        }
        sb.append(res);
        System.out.println(sb);
    }
}
