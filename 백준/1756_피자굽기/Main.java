import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private int[] diameters;

    private int[] drops;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        diameters = new int[N];
        drops = new int[M];

        tokens = reader.readLine().split(" ");
        for ( int i = 0 ; i < N ; i++ ) {
            diameters[i] = Integer.parseInt(tokens[i]);
        }

        tokens = reader.readLine().split(" ");
        for (int i = 0; i < M; i++) {
            drops[i] = Integer.parseInt(tokens[i]);
        }
    }


    private void simulate() throws IOException {
        int answer = 0;
        int count = 0;

        for ( int i = 1 ; i < N ; ++i ) {
            diameters[i] = Math.min(diameters[i], diameters[i - 1]);
        }

//        System.out.println(Arrays.toString(diameters));

        int lo = -1;
        int hi = N;
        for ( count = 0 ; count < M ; count++ ) {
            lo = -1;
//            System.out.println("search from lo: " + lo + " to hi: " + hi + " for drop: " + drops[count]);
            while (lo + 1 < hi) {
                int mid = lo + (hi - lo) / 2;

                if (diameters[mid] >= drops[count]) {
                    lo = mid;
                } else {
                    hi = mid;
                }
            }

//            System.out.println("Drop: " + drops[count] + " placed at : " + (hi-1));
            hi = hi - 1;
            answer = hi;
            if( hi < 0 ) {
                count = 0;
                break;
            }
        }
        if ( count == 0 ) {
            answer = 0;
        } else {
            answer+=1;
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
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}


