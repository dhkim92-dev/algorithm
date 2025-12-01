import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N;

    private List<Long> data;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        N = Integer.parseInt(reader.readLine());
        data = new ArrayList<>();

        String[] tokens = reader.readLine().split(" ");
        for ( int i = 0 ; i < N ; ++i ) {
            data.add(Long.parseLong(tokens[i]));
        }
    }

    private long search(long[] candidates, int sIdx) {
        candidates[0] = data.get(sIdx);
        long score = Long.MAX_VALUE;
        int left = sIdx + 1;
        int right = N - 1;

        while ( left < right ) {
            long sec = data.get(left);
            long thr = data.get(right);
            long  sum = candidates[0] + sec + thr;
            long absSum = Math.abs(sum);
            // System.out.println(" sum : " + sum + " absSum : " + absSum);

            if ( absSum < score ) {
                score = absSum;
                candidates[1] = sec;
                candidates[2] = thr;
            }

            if ( sum == 0 ) {
                break;
            } else if ( sum < 0 ) {
                left++;
            } else {
                right--;
            }
        }

        return score;
    }

    public void run() throws IOException {
        long[] answer = new long[3];
        Collections.sort(data);
        long currentMax = Long.MAX_VALUE;

        long[] tmp = new long[3];
        for ( int i = 0 ; i < N ; ++i ) {
            long score = search(tmp, i);
            if (  score < currentMax ) {
                currentMax = score;
                answer[0] = tmp[0];
                answer[1] = tmp[1];
                answer[2] = tmp[2];
            }
        }
        writer.write(String.format("%d %d %d\n", answer[0], answer[1], answer[2]));
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

