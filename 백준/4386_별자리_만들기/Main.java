class Solution {

    public static class Vertex {

        float x, y;

        public Vertex(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Edge implements Comparable<Edge> {

        int from;

        int to;

        float weight;

        public Edge(int from, int to, float weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return weight > o.weight ? 1 : (weight == o.weight ? 0 : -1);
        }
    }

    private int N;

    private List<Edge>[] graph;

    private int[] parents;

    private Vertex[] vertex;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        parents = new int[N+1];
        graph = new ArrayList[N+1];
        vertex = new Vertex[N+1];

        for(int i = 1 ; i <= N ; i++) {
            String[] line = reader.readLine().split(" ");
            float x = Float.parseFloat(line[0]);
            float y = Float.parseFloat(line[1]);
            vertex[i] = new Vertex(x, y);
            parents[i] = i;
        }
    }

    private void initGraph() {
        for(int i = 1 ; i <= N ; i++) {
            graph[i] = new ArrayList<>();

            for(int j = 1 ; j<=N ; j++) {
                if(i == j) {
                    continue;
                }

                float weight = (float) Math.sqrt(Math.pow(vertex[i].x - vertex[j].x, 2) + Math.pow(vertex[i].y - vertex[j].y, 2));
                graph[i].add(new Edge(i, j, weight));
            }
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

    private float kruskal() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(int i = 1 ; i <= N ; i++) {
            for(Edge edge : graph[i]) {
                pq.add(edge);
            }
        }

        float answer = 0;
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
        initGraph();
        float answer = kruskal();
        answer = new BigDecimal(answer).setScale(2, BigDecimal.ROUND_HALF_UP)
                .floatValue();
        // 시작 간선을 기준으로
        System.out.println(answer);
    }
}
