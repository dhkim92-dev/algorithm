import java.io.*;
import java.util.*;

class Solution {

    private int P, T;

    private List<Edge>[] graph;

    private List<int[]> edges;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        P = Integer.parseInt(tokens[0]);
        T = Integer.parseInt(tokens[1]);
        graph = new ArrayList[P];
        edges = new ArrayList<>();
        for ( int i = 0 ; i < P ; ++i ) {
            graph[i] = new ArrayList<>();
        }

        for ( int i = 0 ; i < T ; ++i ) {
            tokens = reader.readLine().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            int d = Integer.parseInt(tokens[2]);
            Edge e1 = new Edge(u, d);
            Edge e2 = new Edge(v, d);
            graph[u].add(e2);
            graph[v].add(e1);
        }

    }

    private int[] dajik(int from) {
        int[] dists = new int[P];
        Arrays.fill(dists, Integer.MAX_VALUE);
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.dist));
        pq.offer(new Edge(from, 0));
        dists[from] = 0;

        while ( !pq.isEmpty() ) {
            Edge cur = pq.poll();
            int dist = cur.dist;

            for ( Edge nxt : graph[cur.to] ) {
                int v = nxt.to;
                int newDist = dist + nxt.dist;

                if ( newDist < dists[v] ) {
                    dists[v] = newDist;
                    pq.offer(new Edge(v, newDist));
                }
            }
        }

        return dists;
    }

    private int simulate() {
//        int minDist = dajik();
//        System.out.println("최소 거리 : " + minDist);
        int[] distFromStart = dajik(0);
        int[] distFromEnd = dajik(P - 1);
        int total = 0;
        int shortest = distFromStart[P-1];

        for ( int i = 0 ; i < graph.length ; ++i)  {
            for ( Edge e : graph[i] ) {
                int u = i;
                int v = e.to;
                int d = e.dist;

                // Check if the edge is part of the shortest path
                if (distFromStart[u] + d + distFromEnd[v] == shortest) {
                    total += d;
                }
            }
        }

        return total * 2;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }

    static class Edge {

        int to;

        int dist;

        Edge(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
