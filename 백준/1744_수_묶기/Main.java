import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N;

    private PriorityQueue<Integer> positives;

    private PriorityQueue<Integer> negatives;

    boolean hasZero = false;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        this.N = Integer.parseInt(reader.readLine());
        this.positives = new PriorityQueue<>(Collections.reverseOrder()); // 최대힙
        this.negatives = new PriorityQueue<>(); // 최소힙

        for (int i = 0 ; i < N ; ++i) {
            int num = Integer.parseInt(reader.readLine());
            if (num > 0) {
                positives.add(num);
            } else if (num < 0) {
                negatives.add(num);
            } else {
                hasZero = true;
            }
        }
    }

    public void run() throws IOException {
        int answer = 0;

        while ( positives.size() >= 2 ) {
            int a = positives.poll();
            int b = positives.poll();
            if ( a == 1 || b == 1) {
                answer += (a + b);
            } else {
                answer += (a * b);
            }
        }

        while ( negatives.size() >= 2) {
            int a = negatives.poll();
            int b = negatives.poll();
            answer += (a * b);
        }

        if ( positives.size() == 1 ) {
            answer += positives.poll();
        }

        if ( negatives.size() == 1 ) {
            if ( !hasZero )  {
                answer += negatives.poll();
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
