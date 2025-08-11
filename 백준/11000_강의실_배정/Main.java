import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    static class Schedule {
        int st;
        int et;

        public Schedule(int st, int et) {
            this.st = st;
            this.et = et;
        }
    }

    private List<Schedule> schedules;

    private int N;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        this.schedules = new ArrayList<>();
        this.N = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < N; i++) {
            String[] input = reader.readLine().trim().split(" ");
            int st = Integer.parseInt(input[0]);
            int et = Integer.parseInt(input[1]);
            schedules.add(new Schedule(st, et));
        }
    }

    public void run() throws IOException {
        Collections.sort(schedules, (a,b) -> {
            if ( a.st == b.st ) {
                return a.et - b.et;
            }
            return a.st - b.st;
        });

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(schedules.get(0).et);

        for (int i = 1 ; i < N; i++) {
            if ( pq.peek() <= schedules.get(i).st ) {
                pq.poll();
            }
            pq.add(schedules.get(i).et);
        }
        int answer = pq.size();

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


