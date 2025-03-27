import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private String dna;


    public Solution(BufferedReader reader) throws IOException {
        dna = reader.readLine();
    }

    private long bottomUp() {
        // at, gc both are KOI
        // if X is KOI and Y is KOI then XY also KOI
        // if X is KOI then aXt,gXc also KOI
        //
        int N = dna.length();
        int[][] dp = new int[N+1][N+1];

        for ( int stride = 1 ; stride < N ; stride++ ) {
            for ( int start = 0 ; start + stride < N ; start++ ) {
                int end = start + stride;
                char fch = dna.charAt(start);
                char lch = dna.charAt(end);

                if( ( fch == 'a' && lch == 't' ) || ( fch == 'g' && lch == 'c' ) ) {
                    dp[start][end] = dp[start + 1][end - 1] + 2;
                }

                for(int mid = start ; mid < end ; mid++) {
                    dp[start][end] = Math.max(dp[start][end], dp[start][mid] + dp[mid+1][end]);
                }
                
            }
        }

        return dp[0][N-1];
    }


    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(bottomUp());
        System.out.println(sb.toString());
    }
}

