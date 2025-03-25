
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    
    private int N, M;
    private boolean[] impossible;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        impossible = new boolean[10001];

        for ( int i = 0 ; i < M ; i++ ) {
            int stone = Integer.parseInt(reader.readLine());
            impossible[stone] = true;
        }
    }

    private int solve() {
        int[][] dp = new int[10001][151];
        int answer = 987654321;

        for(int[] row : dp) Arrays.fill(row, answer);

        for(int i = 0 ; i < impossible.length ; i++) {
            if(impossible[i]) {
                Arrays.fill(dp[i], -1);
            }
        }

        dp[1][0] = 0;

        for ( int i = 1 ; i <= N ; i++ ) {
            for(int speed = 0 ; speed < 151 ; speed++) {
                if(dp[i][speed] != answer && dp[i][speed] != -1) {

                    //
                    int decrease = speed - 1;
                    int keep = speed;
                    int increase = speed + 1;

                    // 감소한 속도가 0 이상이면 수행
                    if(decrease > 0 && i+decrease <=N && dp[i+decrease][decrease] != -1) {
                        dp[i + decrease][decrease] = Math.min(dp[i+decrease][decrease], dp[i][speed] + 1);
                    }

                    // 현재 속도로 진행하는 경우
                    if(keep > 0 && i+keep <= N && dp[i+keep][keep] != -1) {
                        dp[i+keep][keep] = Math.min(dp[i+keep][keep], dp[i][speed] + 1);
                    }

                    // 증가한 속도로 진행하는 경우
                    if(increase > 0 && i + increase <= N && dp[i+increase][increase] != -1) {
                        dp[i+increase][increase] = Math.min(dp[i+increase][increase], dp[i][speed] + 1);
                    }
                }
            }
        }

        int inf = answer;
        for(int i = 0 ; i < 151 ; i++) {
            //System.out.printf("dp[%d][%d] = %d\n", N, i, dp[N][i]);
            if ( dp[N][i] == -1 || dp[N][i] == inf ) continue;
            answer = Math.min(answer, dp[N][i]); 
        }

        return answer == inf ? -1 : answer;
    }


    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(solve());
        System.out.println(sb.toString());
    }
}
