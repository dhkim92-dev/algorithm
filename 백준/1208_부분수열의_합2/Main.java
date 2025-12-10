import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, S;

    private int[] arr;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        S = Integer.parseInt(tokens[1]);
        arr = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray(); 
    }

    private void calcSubsets(int[] arr, int start, int end, int[] subsets) {
        int length = end - start;
        for (int i = 0; i < (1 << length); i++) {
            int sum = 0;
            for (int j = 0; j < length; j++) {
                if ((i & (1 << j)) != 0) {
                    sum += arr[start + j];
                }
            }
            subsets[i] = sum;
        }
        Arrays.sort(subsets);
    }

    private int lowerBound(int[] a, int target) {
        int lo = -1;
        int hi = a.length;

        while ( lo + 1  < hi ) {
            int mid = lo + (hi - lo) / 2;
            if ( a[mid] < target ) {
                lo = mid;
            } else {
                hi = mid;
            } 
        }

        return hi;
    }

    private int upperBound(int[] a, int target) {
        int lo = -1;
        int hi = a.length;

        while ( lo + 1  < hi ) {
            int mid = lo + (hi - lo) / 2;
            if ( a[mid] <= target ) {
                lo = mid;
            } else {
                hi = mid;
            } 
        }

        return hi;
    }

    public void run() throws IOException {
        long answer = 0;
        int[] frontSubsets = new int[1 << (N / 2)];
        int[] backSubsets = new int[1 << (N - N / 2)];
        calcSubsets(arr, 0, N / 2, frontSubsets);
        calcSubsets(arr, N / 2, N, backSubsets);

        // System.out.println("frontSubsets: " + Arrays.toString(frontSubsets));
        // System.out.println("backSubsets: " + Arrays.toString(backSubsets));

        for ( int frontSub : frontSubsets ) {
            int startIdx = lowerBound(backSubsets, S - frontSub);
            int endIdx = upperBound(backSubsets, S - frontSub);
            answer += (long)(endIdx - startIdx);
        }

        if ( S == 0 ) { 
            answer--;
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

