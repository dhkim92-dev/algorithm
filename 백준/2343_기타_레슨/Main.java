import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private int[] lectures;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        lectures = new int[N];
        tokens = reader.readLine().split(" ");
        for ( int i = 0 ; i < N ; ++i ) {
            lectures[i] = Integer.parseInt(tokens[i]);
        }
    }

    public void run() throws IOException {
        int answer = Integer.MAX_VALUE;
        // 최소 길이는 강의의 최대 길이
        int lo = Arrays.stream(lectures).max().getAsInt() - 1;
        int hi = 100_000 * 10_000 + 1;

        while ( lo + 1 < hi ) {
            int capacity = (lo + hi) / 2;
            int tempSum = 0;
            int cnt = 0;

            // System.out.printf("lo : %d, hi : %d, capacity : %d  ", lo, hi, capacity);
            for ( int i = 0 ; i < N ; ++i ) {
                if ( tempSum + lectures[i] > capacity ) {
                    cnt++;
                    tempSum = 0;
                }
                tempSum += lectures[i];
            }

            if ( tempSum > 0 ) {
                cnt++;
            }
            // System.out.println("    cnt : " + cnt);

            // 전체 오버레이 개수가 M 초과하면 용량을 늘려야한다.
            if ( cnt > M ) {
                lo = capacity;
            } else {
                hi = capacity;
                answer = Math.min(capacity, answer);
            }
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

