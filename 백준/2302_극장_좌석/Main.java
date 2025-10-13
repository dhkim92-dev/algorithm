import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private List<Integer> fixed;

    private int[] dp;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        N = Integer.parseInt(reader.readLine());
        M = Integer.parseInt(reader.readLine());
        fixed = new ArrayList<>();

        for ( int i = 0 ; i < M ; ++i ) {
            fixed.add( Integer.parseInt(reader.readLine()) );
        }
        dp = new int[41];
    }

    private void calcDp() {
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2 ; i < 41 ; ++i) {
            dp[i] = dp[i-1] + dp[i-2];
            //System.out.println("dp["+i+"] : " + dp[i]);
        }

    }

    public void run() throws IOException {
        // 고정된 점들을 기준으로 list를 분할한다.
        int start = 1;
        calcDp();
        List<Integer> segments = new ArrayList<>();

        for ( int elem : fixed ) {
            int segmentCount = elem - start;
            segments.add(segmentCount);
            start = elem+1;
        }

        if ( start < N ) {
            segments.add(N - start + 1);
        }

        long answer = 1;
        
        for ( int num : segments ) {
            //System.out.println("segment count : " + num);
            answer *= (long)dp[num];
        }

        writer.write(String.valueOf(answer));
        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}

