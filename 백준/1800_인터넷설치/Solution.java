import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

class Solution {

    static class Item implements Comparable<Item> {
        int index;
        int cost;

        Item(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        @Override
        public int compareTo(Item o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private int N, P, K;
    private int answer = Integer.MAX_VALUE;
    private List<Item>[] graph;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        P = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);

        graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for ( int i = 0 ; i < P ; i++ ) {
            tokens = reader.readLine().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            int cost = Integer.parseInt(tokens[2]);
            graph[u].add(new Item(v, cost));
            graph[v].add(new Item(u, cost));
        }
    }

    private boolean dajik(int mid, boolean[] visited, int[] dists) {
        Arrays.fill(visited, false);
        Arrays.fill(dists, Integer.MAX_VALUE);
        PriorityQueue<Item> pq = new PriorityQueue<>();
        visited[1] = true;
        pq.add(new Item(1, 0));
        dists[1] = 0;

        while ( !pq.isEmpty() ) {
            Item cur = pq.poll();

            if ( cur.cost > mid ) {
                continue;
            }

            graph[cur.index].forEach( next -> {
                if ( visited[next.index] ) {
                    return;
                }

                if ( dists[next.index] > cur.cost + (next.cost < mid ? 0 : 1) ) {
                    dists[next.index] = cur.cost + (next.cost < mid ? 0 : 1);
                    pq.add(new Item(next.index, dists[next.index]));
                }
            });
        }

        return dists[N] <= K;
    }

    private int simulate() {
        boolean[] visited = new boolean[N + 1];
        int[] dists = new int[N + 1];

        int left = 0;
        int right = 1_000_000;

        while ( left + 1 < right) {
            int mid = left + (right - left) / 2;
            //System.out.println("left : " + left + ", right : " + right + ", mid : " + mid);
            if ( dajik(mid, visited, dists) ) {
//                System.out.println(" is possible ");
                right = mid;
                answer = Math.min(answer, mid-1);
            } else {
//                System.out.println(" is impossible ");
                left = mid;
            }
        }

        if ( answer == Integer.MAX_VALUE ) {
            return -1;
        }

        return answer;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate());
        System.out.println(sb.toString());
    }
}


