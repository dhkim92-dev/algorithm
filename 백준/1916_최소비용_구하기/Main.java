
class Solution {

    private int N, M, start, dest;

    private List<Edge>[] graph;

    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        graph = new ArrayList[N + 1];
        for(int i = 1 ; i <= N ; i++) {
            graph[i] = new ArrayList<>();
        }
        M = Integer.parseInt(reader.readLine());

        for(int i = 0 ; i < M ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int cost = Integer.parseInt(tokens[2]);
            graph[from].add(new Edge(to, cost));
        }
        String[] tokens = reader.readLine().split(" ");
        start = Integer.parseInt(tokens[0]);
        dest = Integer.parseInt(tokens[1]);
    }

    private int[] daijk() {
        int[] dists = new int[N + 1];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[start] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        pq.add(new Edge(start, 0));

        while(!pq.isEmpty()) {
            Edge edge = pq.poll();

            if(dists[edge.to] < edge.cost) {
                continue;
            }

            for(Edge next : graph[edge.to]) {
                int nextDist = edge.cost + next.cost;
                if(nextDist < dists[next.to]) {
                    dists[next.to] = nextDist;
                    pq.add(new Edge(next.to, nextDist));
                }
            }
        }

        return dists;
    }

    public void run() {
        int[] dists = daijk();
        System.out.println(dists[dest]);
    }
}
