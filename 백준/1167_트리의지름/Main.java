
class Solution {

    private int V;

    private Set<Edge>[] graph;

    private long answer;

    boolean[] visited;

    static class Edge {
        public int to;
        public int dist;

        public Edge(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Edge) {
                Edge e = (Edge)obj;
                return e.to == this.to;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 31 * this.to + this.dist;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        V = Integer.parseInt(reader.readLine());
        graph = new HashSet[V + 1];

        for(int i = 1 ; i <= V ; i++) {
            graph[i] = new HashSet<>();
        }

        for(int i = 0 ; i < V-1 ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]);

            for(int j = 1 ; j < tokens.length ; j+=2) {
                int to = Integer.parseInt(tokens[j]);
                if(to == -1) {
                    continue;
                }
                int dist = Integer.parseInt(tokens[j+1]);
                graph[from].add(new Edge(to, dist));
                graph[to].add(new Edge(from, dist));
            }
        }
    }

    private Edge dfs(int root, int cost) {
        visited[root] = true;
        if(graph[root].isEmpty()) {
            return new Edge(root, cost);
        }

        Edge farthest = new Edge(root, cost);

        for(Edge e : graph[root]) {
            if(visited[e.to]) continue;
            Edge next = dfs(e.to, cost + e.dist);
            if(farthest.dist < next.dist) {
                farthest = next;
            }
        }

        return farthest;
    }

    private int getDist(int from, int to) {
        Queue<Edge> q = new LinkedList<>();
        q.add(new Edge(from, 0));

        int dist = Integer.MIN_VALUE;

        while(!q.isEmpty()) {
            Edge e = q.poll();
            if(e.to == to) {
                dist = Math.max(dist, e.dist);
                break;
            }

            for(Edge next : graph[e.to]) {
                if(visited[next.to]) continue;
                visited[next.to] = true;
                q.add(new Edge(next.to, e.dist + next.dist));
            }
        }

        return dist;
    }

    public void run() {
        visited = new boolean[V + 1];
        Edge farthest = dfs(1, 0);
        Arrays.fill(visited, false);
        Edge farthest2 = dfs(farthest.to, 0);
        Arrays.fill(visited, false);
        System.out.println(getDist(farthest.to, farthest2.to));
    }
}
