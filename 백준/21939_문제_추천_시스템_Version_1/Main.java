
import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private static class Problem implements Comparable<Problem> {
        int id;
        int level;

        public Problem(int id, int level) {
            this.id = id;
            this.level = level;
        }

        @Override
        public int compareTo(Problem o) {
            if (this.level == o.level) {
                return Integer.compare(this.id, o.id);
            }
            return Integer.compare(this.level, o.level);
        }
    }

    PriorityQueue<Problem> maxHeap;

    PriorityQueue<Problem> minHeap;

    Map<Integer, Problem> problems;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        N = Integer.parseInt(reader.readLine());
        problems = new HashMap<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            String[] tokens = reader.readLine().split(" ");
            int id = Integer.parseInt(tokens[0]);
            int level = Integer.parseInt(tokens[1]);
            Problem problem = new Problem(id, level);
            problems.put(id, problem);
            maxHeap.offer(problem);
            minHeap.offer(problem);
        }

        M = Integer.parseInt(reader.readLine());
    }

    public void run() throws IOException {

        for ( int i = 0 ; i < M ; ++i ) {
            String[] tokens = reader.readLine().split(" ");
            String command = tokens[0];

            if (command.equals("add")) {
                int id = Integer.parseInt(tokens[1]);
                int level = Integer.parseInt(tokens[2]);
                Problem problem = new Problem(id, level);
                problems.put(id, problem);
                maxHeap.offer(problem);
                minHeap.offer(problem);
            } else if (command.equals("solved")) {
                int id = Integer.parseInt(tokens[1]);
                problems.remove(id);
            } else if (command.equals("recommend")) {
                int x = Integer.parseInt(tokens[1]);
                if (x == 1) {
                    while ( !maxHeap.isEmpty() ) {
                        if ( !problems.containsKey(maxHeap.peek().id) ) {
                            maxHeap.poll();
                            continue;
                        }

                        if ( problems.get(maxHeap.peek().id) != null && maxHeap.peek().level != problems.get(maxHeap.peek().id).level ) {
                            maxHeap.poll();
                            continue;
                        }
                        break;
                    }
                    writer.write(String.valueOf(maxHeap.peek().id));
                    writer.newLine();
                } else if (x == -1) {
                    while ( !minHeap.isEmpty() ) {
                        if ( !problems.containsKey(minHeap.peek().id) ) {
                            minHeap.poll();
                            continue;
                        }

                        if ( problems.get(minHeap.peek().id) != null && minHeap.peek().level != problems.get(minHeap.peek().id).level ) {
                            minHeap.poll();
                            continue;
                        }
                        break;
                    }
                    writer.write(String.valueOf(minHeap.peek().id));
                    writer.newLine();
                }
            }
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

