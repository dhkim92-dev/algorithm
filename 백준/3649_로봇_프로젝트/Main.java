import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private static final int NANO_PER_CENTI = 10_000_000;

    private int holeSize;

    private int nrBlocks;

    private int[] blockSizes;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
    }

    private int exists(int lo, int hi, int goal) throws IOException {
//        System.out.println("    search range(" + lo + " - " + hi + ")" + " goal : " + goal);
        while ( lo + 1 < hi ) {
            int mid = lo + (hi - lo) / 2;
//            System.out.println("    mid : " + mid + " blockSize : " + blockSizes[mid]);
            if ( blockSizes[mid] < goal ) {
                 lo = mid;
//                System.out.println("    lo : " + lo);
            } else {
                hi = mid;
//                System.out.println("    hi : " + hi);
            }
        }

        return hi;
    }

    private void simulate() throws IOException {
        Arrays.sort(blockSizes);

        if ( blockSizes.length == 1 ) {
            writer.write("danger\n");
            return;
        }

        int from = 0;
        int to = 0;

//        writer.write("holeSize : " + holeSize + " blocks : " + Arrays.toString(blockSizes) + "\n");;
//        System.out.println("holeSize : " + holeSize + " blocks : " + Arrays.toString(blockSizes));
        for ( int i = 0 ; i < blockSizes.length ; ++i ) {
            int cur = blockSizes[i];
//            System.out.println("current[" + i + " : " + cur + "\n");
            int goal = holeSize - cur;

            int idx = exists(i, blockSizes.length , goal);
//            System.out.println("binary search result : " + idx + "\n");
            if ( idx <= i || idx >= blockSizes.length ) continue;
            if ( blockSizes[idx] != goal ) continue;
            from = cur;
            to = holeSize- cur;
            writer.write("yes " + from + " " + to + "\n");
            return;
        }

        writer.write("danger\n");
    }

    public void run() throws IOException {
        String line;
        while ( (line = reader.readLine()) != null) {
            if ( line.isEmpty() || line.equals("\0") || line.equals("\n") ) break;
            holeSize = Integer.parseInt(line) * NANO_PER_CENTI;
            nrBlocks = Integer.parseInt(reader.readLine().trim());
            if ( nrBlocks == 0 ) {
                writer.write("danger\n");
                continue;
            }
            blockSizes = new int[nrBlocks];
            for (int i = 0; i < nrBlocks; i++) {
                blockSizes[i] = Integer.parseInt(reader.readLine());
            }
            simulate();
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


