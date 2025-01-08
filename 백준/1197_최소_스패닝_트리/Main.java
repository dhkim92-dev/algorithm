import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

class Solution {

    public static class Edge implements Comparable<Edge> {

        int from;

        int to;

        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    private int V, E;

    private List<Edge>[] graph;

    private int[] parents;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        V = Integer.parseInt(line[0]);
        E = Integer.parseInt(line[1]);
        parents = new int[V + 1];
        graph = new ArrayList[V+1];

        for(int i = 0 ; i < parents.length ; i++) {
            parents[i] = i;
        }

        for(int i = 0 ; i <= V ; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i = 0 ; i < E ; i++) {
            line = reader.readLine().split(" ");
            int from = Integer.parseInt(line[0]);
            int to = Integer.parseInt(line[1]);
            int weight = Integer.parseInt(line[2]);
            graph[from].add(new Edge(from, to, weight));
            graph[to].add(new Edge(from, to, weight));
        }
    }

    private int unionParent(int a, int b) {
        a = getParent(a);
        b = getParent(b);

        if(a < b) {
            parents[b] = a;
            return a;
        } else {
            parents[a] = b;
            return b;
        }
    }

    private int getParent(int x) {
        if(parents[x] == x) {
            return x;
        }

        return parents[x] = getParent(parents[x]);
    }

    private int kruskal() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(int i = 1 ; i <= V ; i++) {
            for(Edge edge : graph[i]) {
                pq.add(edge);
            }
        }

        int answer = 0;
        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            if(getParent(edge.from) != getParent(edge.to)) {
                answer += edge.weight;
                unionParent(edge.from, edge.to);
            }
        }

        return answer;
    }

    public void run() {
        int answer = kruskal();
        // 시작 간선을 기준으로
        System.out.println(answer);
    }
}
