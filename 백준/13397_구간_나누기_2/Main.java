
import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private final int N;

    private final int M;

    private final int[] arr;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        this.N = Integer.parseInt(tokens[0]);
        this.M = Integer.parseInt(tokens[1]);
        this.arr = new int[N];
        tokens = reader.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }
    }

    private boolean check(int mid) {
        int count = 0;

        int mx=arr[0], mn=arr[0];
        for (int i = 0; i < N; i++) {
            mx = Math.max(mx, arr[i]);
            mn = Math.min(mn, arr[i]);
            if (mx - mn <= mid) {
                continue;
            }
            count++;
            mx = arr[i];
            mn = arr[i];
        }
        return count >= M;
    }

    private void simulate() throws IOException {
        int lo = -1;
        int hi = 10001;

        while ( lo + 1 < hi ) {
            int mid = lo + (hi - lo) / 2;
            if ( check(mid) ) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        writer.write(String.valueOf(hi)+"\n");
    }

    public void run() throws IOException {
        simulate();
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


