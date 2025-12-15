
import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int T;

    private long[] gx;

    private long[] fx;

    private boolean[] isPrime;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        T = Integer.parseInt(reader.readLine());
        gx = new long[1_000_001];
        fx = new long[1_000_001];
    }

    private void processFx() {
        fx[0] = 0;
        Arrays.fill(fx, 1);
        int limit = (int)Math.sqrt(1_000_000);

        for ( int i = 2 ; i <= 1_000_000 ; ++i ) {
            for ( int j = i ; j <= 1_000_000 ; j+=i) {
                fx[j] += i;
            }
        }
    }

    private void processGx() {
        gx[0] = 0;
        for ( int i = 1 ; i <= 1_000_000 ; ++i ) {
            gx[i] = gx[i - 1] + fx[i];
        }
    }

    public void run() throws IOException {
        processFx();
        processGx();

        for (int i = 0 ; i < T ; ++i) {
            writer.write(gx[Integer.parseInt(reader.readLine())] + "\n");
        }

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

