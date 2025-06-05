package algorithm;
import java.io.*;
import java.util.*;

class Solution {

    private int m, n;

    private char[][] grid;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        m = Integer.parseInt(tokens[0]);
        n = Integer.parseInt(tokens[1]);

        grid = new char[m][];
        for ( int i = 0 ; i < m ; ++i ) {
            grid[i] =  reader.readLine().toCharArray();
        }
    }

    private boolean isPossible() {
        for ( int r = 0 ; r < m ; ++r ) {
            for ( int c = 0 ; c < n ; ++c ) {
                if ( grid[r][c] != 'R' ) continue;

                for ( int y = r ; y < m ; ++y ) {
                    for ( int x = c ; x < n ; ++x ) {
                        if ( grid[y][x] == 'B' ) return false;
                    }
                }
            }
        }
        return true;
    }

    private void calcMaxColumnHeight(int[] maxColumnHeight) {
        Arrays.fill(maxColumnHeight, -1);

        for ( int r = 0 ; r < m ; ++ r ) {
            for ( int c = 0 ; c < n ; ++c ) {
                if ( grid[r][c] != 'B' ) continue;
                for(int i = 0 ; i <= c ; ++i ) {
                    maxColumnHeight[i] = Math.max(maxColumnHeight[i], r);
                }
            }
        }

    }

    private long solve() {
        if ( !isPossible() ) {
            return 0;
        }

        int[] maxColumnHeight = new int[n];
        calcMaxColumnHeight(maxColumnHeight);

        long[] dp = new long[m+1];
        dp[m] = 1;

        for ( int c = 0 ; c < n ; ++c ) {
            long[] nxt = new long[m+1];

            for ( int rLimit = maxColumnHeight[c] ; rLimit < m ; ++rLimit ) {
                if ( rLimit >= 0 && grid[rLimit][c] == 'R' ) {
                    break;
                }
                for ( int x = rLimit + 1 ; x <= m ; ++x ) {
                    nxt[rLimit+1] += dp[x];
                }
            }
            dp = nxt;
        }

        long answer = 0;

        for ( int r = 0 ; r <= m ; ++r ) {
            answer += dp[r];
        }

        return answer;
    }

    public void run () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(solve())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
