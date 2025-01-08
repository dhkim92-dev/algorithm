
class Solution {

    static class Edge {
        int to, cost;
        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    int n, m;
    List<Edge>[] conn;
    int src, dst;

    public Solution(BufferedReader reader) throws IOException {
        n = Integer.parseInt(reader.readLine());
        m = Integer.parseInt(reader.readLine());
        conn = new ArrayList[n + 1];
        for (int i = 0; i < n + 1; i++) {
            conn[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            String[] line = reader.readLine().split(" ");
            int a = Integer.parseInt(line[0]);
            int b = Integer.parseInt(line[1]);
            int c = Integer.parseInt(line[2]);
            conn[a].add(new Edge(b, c));
            //conn[b].add(new Edge(a, c));
        }
        String[] line = reader.readLine().split(" ");
        src = Integer.parseInt(line[0]);
        dst = Integer.parseInt(line[1]);
    }
    private void daijk() {
        // start에서 다른 노드까지의 최단 거리를 우선 구하고,
        // 스패닝 트리를 구성하는 엣지들을 모두 저장해둔다.

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        int[] routes = new int[n+1];

        pq.add(new Edge(src, 0));
        routes[src] = -1;

        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            
            if(dist[cur.to] < cur.cost) {
                continue;
            }

            for(int i = 0 ; i < conn[cur.to].size() ; i++) {
                Edge next = conn[cur.to].get(i);
                int nCost = dist[cur.to] + next.cost;
                if(dist[next.to] > nCost) {
                    routes[next.to] = cur.to;
                    dist[next.to] = nCost;
                    pq.add(new Edge(next.to, dist[next.to]));
                }
            }
        }
        // 여기까지 왔다면 start로부터 모든 노드까지의 최단 거리는 완료.
        System.out.println(dist[dst]);
        // 이제 start로부터 dst까지의 최단 경로를 구해야한다.

        int V = dst;
        Stack<Integer> path = new Stack();

        while(V > 0) {
            path.push(V);
            V = routes[V];
        }

        System.out.println(path.size());

        while(!path.isEmpty()) {
            System.out.print(path.pop() + " ");
        }
    }

    public void run() {
        daijk();
    }
}
