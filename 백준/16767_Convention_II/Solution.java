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
            return Integer.compare(a.seniority, b.seniority);
        });

        Arrays.sort(cows, (a, b) -> {
            if ( a.arriveAt == b.arriveAt ) {
                return Integer.compare(a.seniority, b.seniority);
            }
            return Integer.compare(a.arriveAt, b.arriveAt);
        });
        int maxDelay = -1;
        int finishAt = cows[0].arriveAt + cows[0].spend;
        int[] delay = new int[N+1];

        for ( int i = 1 ; i < cows.length ; ++i ) {
            // 일단 목초지를 선점한 소의 퇴장 시간보다 먼저 도착한 소들은 대기열에 넣는다.
            if ( cows[i].arriveAt < finishAt ) {
                pq.offer(cows[i]);
                continue;
            }

            // 선점된 목초지의 퇴장 시간보다 늦게 온 소가 있다면 우선 우선순위 큐에서 소 하나를 꺼내서 처리한다.

            if ( !pq.isEmpty() ) {
                Cow current = pq.poll();
                maxDelay = Math.max(maxDelay, finishAt - current.arriveAt);
                finishAt += current.spend;
                i--;
            } else {
                // 만약 대기열이 비어있다면, 현재 소는 목초지에 바로 들어갈 수 있다.
                finishAt = cows[i].arriveAt + cows[i].spend;
            }
        }

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
