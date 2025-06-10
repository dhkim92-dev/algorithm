import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N;

    private int[] inputs;

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
        this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
        this.N = Integer.parseInt(reader.readLine().trim());
        this.inputs = new int[N+1];
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine().trim());

        for ( int i = 1 ; i <= N; i++ ) {
            inputs[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private int lowerBound(int[] arr, int lo, int hi, int target) {
        //int mid = lo + (hi - lo) / 2;
        int mid = 0;
        while ( lo + 1 < hi ) {
            mid = lo + (hi - lo) / 2;
            if ( arr[mid] < target ) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        return hi;
    }

    private void simulate() throws IOException {
        int[] dp = new int[N+1];
        int answer = 0;
        dp[0] = 0;
//        System.out.print("inputs : ");
//        System.out.print(Arrays.toString(inputs));
//        System.out.println();
        for ( int i = 1 ; i <= N ; ++i ) {
//            System.out.println("inputs[" + i + "] : " + inputs[i] + " max length : " + answer);
            if ( inputs[i] > dp[answer] ) {
                dp[++answer] = inputs[i];
//                System.out.println("inputs[" + i + "] is greater than dp[" + (answer-1) + "]");
            } else {
                int idx = lowerBound(dp, 0, answer, inputs[i]);
//                System.out.println("dp["+idx+"] is updated to inputs[" + i + "] : " + inputs[i]);
                dp[idx] = inputs[i];
            }
//            System.out.print("dp : ");
//            System.out.print(Arrays.toString(dp));
//            System.out.println();
        }
        writer.write(String.valueOf(answer));
        writer.write("\n");
    }

    public void run() throws IOException {
        simulate();
        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}

