import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private int N;

    private int[] arr;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        this.N = Integer.parseInt(reader.readLine());
        this.arr = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private int binarySearch(int[] seq, int target) {
        int lo = -1;
        int hi = seq.length;

        while ( lo + 1 < hi ) {
            int mid = lo + (hi - lo) / 2;
            if ( seq[mid] < target ) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        return hi;
    }

    public void run() throws IOException {
        int answer = 0;
        int[] seq = new int[N];
        Arrays.fill(seq, Integer.MAX_VALUE);
        seq[0] = arr[0];
        answer = 1;

        for ( int i = 1 ; i < N ; ++i ) {
            int index = binarySearch(seq, arr[i]);
            seq[index] = arr[i];
        }

        for ( int i = 0 ; i < N ; ++i ) {
            if ( seq[i] == Integer.MAX_VALUE ) {
                break;
            }
            answer = i + 1;
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
