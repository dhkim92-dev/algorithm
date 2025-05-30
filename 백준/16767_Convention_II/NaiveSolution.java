import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    static class Cow {
        int seniority;
        int arriveAt;
        int spend;

        public Cow(int seniority, int arrivalTime, int spend) {
            this.seniority = seniority;
            this.arriveAt = arrivalTime;
            this.spend = spend;
        }
    }

    private int N;

    private Cow[] cows;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine().trim());
        cows = new Cow[N];

        for ( int i = 0 ; i < N ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            int arriveAt= Integer.parseInt(tokens[0]);
            int spend = Integer.parseInt(tokens[1]);
            cows[i] = new Cow(i + 1, arriveAt, spend);
        }
    }

    private int simulate() {
        PriorityQueue<Cow> pq = new PriorityQueue<>((a, b) -> {
            if (a.arriveAt != b.arriveAt) return Integer.compare(a.arriveAt, b.arriveAt);
            return Integer.compare(a.seniority, b.seniority);
        });

        pq.addAll(Arrays.stream(cows)
          .collect(Collectors.toList()));

        int[] delayed = new int[N + 1];
        int finishAt = 0;

        while ( !pq.isEmpty() ) {
            Cow cur = pq.poll();

            if ( cur.arriveAt >= finishAt ) {
                finishAt = cur.arriveAt + cur.spend;
                continue;
            }

            delayed[cur.seniority] += finishAt - cur.arriveAt;
            cur.arriveAt = finishAt;
            pq.add(cur);
        }
        int maxDelay = Arrays.stream(delayed)
          .max()
          .orElse(0);

        return maxDelay;
    }

    public void run () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
