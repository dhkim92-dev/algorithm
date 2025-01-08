
class Solution {

    class Vertex {
        double r, c;

        Vertex(double r, double c) {
            this.r = r;
            this.c = c;
        }
    }

    class Edge implements Comparable<Edge> {
        int from;
        int to;
        double weight;

        Edge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) {
                return false;
            }

            if(obj == this) {
                return true;
            }

            if(obj instanceof Edge) {
                Edge edge = (Edge) obj;
                return this.from == edge.from && this.to == edge.to;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(31 * from) ^ Integer.hashCode(to);
        }
    }

    int N, M;
    Vertex[] vertex;
    Edge[] knownEdges;
    int[] parents;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        vertex = new Vertex[N];

        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            vertex[i] = new Vertex(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
        }
        knownEdges = new Edge[M];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]) - 1;
            int to = Integer.parseInt(tokens[1]) - 1;
            knownEdges[i] = new Edge(from, to, 0);
        }

        parents = new int[N];
        for(int i = 0 ; i < N ; i++) {
            parents[i] = i;
        }
    }

    private int findParent(int x) {
        if(x == parents[x]) {
            return x;
        }

        return parents[x] = findParent(parents[x]);
    }

    private void unionParent(int a, int b) {
        a = findParent(a);
        b = findParent(b);

        if(a < b) {
            parents[b] = a;
        } else {
            parents[a] = b;
        }
    }

    private List<Edge> initGraph() {
        List<Edge> edges = new ArrayList<>();

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                if(i == j) {
                    continue;
                }
                Vertex from = vertex[i];
                Vertex to = vertex[j];
                double weight = (double) Math.sqrt(Math.pow(from.r - to.r, 2) + Math.pow(from.c - to.c, 2));
                edges.add(new Edge(i, j, weight));
            }
        }

        for(int i = 0 ; i < M ; i++) {
            Edge edge = knownEdges[i];
            edges.add(edge);
        }

        return edges;
    }

    public void run() {
        double answer = 0;
        // 이미 연결이 수립된 네트워크는 어떻게 처리해야 하는가?
        // 출력은 새로 추가된 네트워크의 길이이다.
        // 이미 연결된 네트워크를 대상으로 유니온 파인드를 우선 적용해둔다.
        // 그리고 이 네트워크의 비용들은 최소로 설정하여 무조건 새로 생성하는 네트워크에 포함시킨다.
        // 그리고 이후에 새로 생성되는 네트워크의 비용을 계산한다.

        PriorityQueue<Edge> pq = new PriorityQueue<>();

//        for(int i = 0 ; i < M ; i++) {
//            Edge edge = knownEdges[i];
//            unionParent(edge.from, edge.to);
//        }

        List<Edge> edges = initGraph();
        Collections.sort(edges);
        pq.addAll(edges);

        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            if(findParent(edge.from) != findParent(edge.to)) {
                unionParent(edge.from, edge.to);
                answer += edge.weight;
            }
        }

        answer = new BigDecimal(answer).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println(answer);
    }
}
