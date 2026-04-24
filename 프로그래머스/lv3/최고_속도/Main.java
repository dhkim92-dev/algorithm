import java.util.*;

class Solution {
    
    int nodeIdx = 0;

    private static class Pos {
        public int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos o) {
            return new Pos(this.r + o.r, this.c + o.c);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return r == pos.r && c == pos.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private final Pos[] dirs = {
        new Pos(-1, 0),
        new Pos(0, 1),
        new Pos(1, 0),
        new Pos(0, -1)
    };

    private static class Edge implements Comparable<Edge> {
        public Node to;
        public int limit;

        public Edge(Node to, int limit) {
            this.to = to;
            this.limit = limit;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.limit, o.limit);
        }
    }

    private static class Node {
        public int id;
        public Pos pos;
        public List<Edge>[] adj = new List[4];
        public int speedLimit = Integer.MAX_VALUE;

        public Node(int id, Pos pos) {
            this(id, pos.r, pos.c);
        }
        
        public Node(int id, int r, int c) {
            this.id = id;
            this.pos = new Pos(r, c);
            for (int i = 0; i < 4; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        @Override 
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(pos, node.pos);
        }

        @Override public int hashCode() {
            return Objects.hash(pos);
        }

        @Override
        public String toString() {
            return String.format("Node(%d, %d)", pos.r, pos.c);
        }
    };

    public int[] solution(int[][] city, int[][] road) {
        int[] answer = {};

        Map<Pos, Node> nodes = new HashMap<>();
        buildGraph(nodes, city, road);
        Map<Integer, Pos> cityToPos = new HashMap<>();

        // for (int [] c : city) {
        // }

        answer = new int[nodes.size() - 1]; // 2 ~ N번 도시까지 도달 시 최대거리 반환 
        answer = dajik(nodes, city);

        return answer;
    }

    private int[] dajik(Map<Pos, Node> nodes, int[][] city) {
        int[] answer = new int[city.length - 1];

        int n = nodes.size();
        int[] best = new int[n]; // best bottleneck value to each node
        Arrays.fill(best, 0);

        // build id -> node array for fast access
        Node[] nodesById = new Node[n];
        for ( Map.Entry<Pos, Node> entry : nodes.entrySet() ) {
            Node node = entry.getValue();
            nodesById[node.id] = node;
        }

        Pos startPos = new Pos(city[0][0], city[0][1]);
        Node start = nodes.get(startPos);
        if ( start == null ) return answer;

        best[start.id] = Integer.MAX_VALUE;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(b[0], a[0])); // {limit, nodeId}
        pq.offer(new int[]{best[start.id], start.id});

        while ( !pq.isEmpty() ) {
            int[] cur = pq.poll();
            int curLimit = cur[0];
            int curId = cur[1];

            //System.out.println(String.format("curId = %d, curLimit = %d", curId, curLimit));

            if ( curLimit < best[curId] ) continue; // stale entry

            Node curNode = nodesById[curId];

            for ( int d = 0 ; d < 4 ; ++d ) {
                for ( Edge e : curNode.adj[d] ) {
                    int cand = Math.min(curLimit, e.limit);
                    if ( cand > best[e.to.id] ) {
                        best[e.to.id] = cand;
                        pq.offer(new int[]{cand, e.to.id});
                        //System.out.println(String.format("  update best[%d] = %d (from node %d)", e.to.id, cand, curId));
                    }
                }
            }
        }

        for ( int i = 1 ; i < city.length ; ++i ) {
            // Pos p = new Pos(city[i][0], city[i][1]);
            // Node cityNode = nodes.get(p);
            // answer[i-1] = (cityNode == null) ? 0 : best[cityNode.id];
            answer[i-1] = best[nodes.get(new Pos(city[i][0], city[i][1])).id];
            if (answer[i-1] == Integer.MAX_VALUE) {
                answer[i-1] = 0; // unreachable
            }
        }

        return answer;
    }

    private void buildGraph(Map<Pos, Node> nodes, int[][] city, int[][] road) {
        // === Phase 1: 모든 노드 생성 & speedLimit 세팅 ===

        // 도시 노드 생성
        for (int[] c : city) {
            nodes.put(new Pos(c[0], c[1]), new Node(nodeIdx++, c[0], c[1]));
        }

        // 도로 끝점, 중간점(카메라) 노드 생성 및 speedLimit 세팅
        for (int[] r : road) {
            int x0 = r[0], y0 = r[1], x1 = r[2], y1 = r[3], speedLimit = r[4];
            nodes.computeIfAbsent(new Pos(x0, y0), p -> new Node(nodeIdx++, p));
            nodes.computeIfAbsent(new Pos(x1, y1), p -> new Node(nodeIdx++, p));
            int midX = (x0 + x1) / 2, midY = (y0 + y1) / 2;
            Node cam = nodes.computeIfAbsent(new Pos(midX, midY), p -> new Node(nodeIdx++, p));
            cam.speedLimit = Math.min(cam.speedLimit, speedLimit);
        }

        // 교차점 노드 생성 (카메라 위치일 경우 위에서 이미 speedLimit 세팅됨)
        for (int i = 0; i < road.length; i++) {
            int x0 = road[i][0], y0 = road[i][1], x1 = road[i][2], y1 = road[i][3];
            boolean v1 = (x0 == x1);
            for (int j = 0; j < road.length; j++) {
                if (i == j) continue;
                int x2 = road[j][0], y2 = road[j][1], x3 = road[j][2], y3 = road[j][3];
                boolean v2 = (x2 == x3);
                if (v1 == v2) continue;
                if (!isIntersect(x0, y0, x1, y1, x2, y2, x3, y3)) continue;
                Pos p = getIntersect(x0, y0, x1, y1, x2, y2, x3, y3);
                nodes.computeIfAbsent(p, pos -> new Node(nodeIdx++, pos));
            }
        }

        // === Phase 2: 모든 speedLimit이 확정된 후 엣지 생성 ===
        for (int i = 0; i < road.length; i++) {
            int x0 = road[i][0], y0 = road[i][1], x1 = road[i][2], y1 = road[i][3];
            boolean isVertical = (x0 == x1);
            int midX = (x0 + x1) / 2, midY = (y0 + y1) / 2;

            Node start = nodes.get(new Pos(x0, y0));
            Node end = nodes.get(new Pos(x1, y1));
            Node speedLimitNode = nodes.get(new Pos(midX, midY));

            Set<Pos> addedPos = new HashSet<>();
            List<Node> curRoadNodes = new ArrayList<>();
            addedPos.add(start.pos);
            addedPos.add(speedLimitNode.pos);
            addedPos.add(end.pos);
            curRoadNodes.add(start);
            curRoadNodes.add(speedLimitNode);
            curRoadNodes.add(end);

            // 교차점 추가
            for (int j = 0; j < road.length; j++) {
                if (i == j) continue;
                int x2 = road[j][0], y2 = road[j][1], x3 = road[j][2], y3 = road[j][3];
                boolean v2 = (x2 == x3);
                if (isVertical == v2) continue;
                if (!isIntersect(x0, y0, x1, y1, x2, y2, x3, y3)) continue;
                Pos p = getIntersect(x0, y0, x1, y1, x2, y2, x3, y3);
                if (addedPos.contains(p)) continue;
                curRoadNodes.add(nodes.get(p));
                addedPos.add(p);
            }

            // 도로 위에 있는 도시 노드 추가 (내부 점)
            for (int[] c : city) {
                Pos cp = new Pos(c[0], c[1]);
                if (addedPos.contains(cp)) continue;
                boolean onRoad;
                if (isVertical) {
                    onRoad = c[0] == x0 && Math.min(y0, y1) <= c[1] && c[1] <= Math.max(y0, y1);
                } else {
                    onRoad = c[1] == y0 && Math.min(x0, x1) <= c[0] && c[0] <= Math.max(x0, x1);
                }
                if (!onRoad) continue;
                Node cityNode = nodes.get(cp);
                if (cityNode != null) {
                    curRoadNodes.add(cityNode);
                    addedPos.add(cp);
                }
            }

            Collections.sort(curRoadNodes, (a, b) -> isVertical
                ? Integer.compare(a.pos.c, b.pos.c)   // 수직 도로: y 기준 정렬
                : Integer.compare(a.pos.r, b.pos.r));  // 수평 도로: x 기준 정렬

            for (int j = 0; j < curRoadNodes.size() - 1; j++) {
                Node from = curRoadNodes.get(j);
                Node to = curRoadNodes.get(j + 1);
                int limit = Math.min(from.speedLimit, to.speedLimit);
                if (isVertical) {
                    from.adj[2].add(new Edge(to, limit));
                    to.adj[0].add(new Edge(from, limit));
                } else {
                    from.adj[1].add(new Edge(to, limit));
                    to.adj[3].add(new Edge(from, limit));
                }
            }
        }
    }

    private boolean isIntersect(
        int x0, int y0, int x1, int y1, 
        int x2, int y2, int x3, int y3
    ) {
        // Handle perpendicular roads: one vertical, one horizontal.
        // First case: first road is vertical (x0==x1), second is horizontal (y2==y3)
        if (x0 == x1 && y2 == y3) {
            int vx = x0;
            int vYmin = Math.min(y0, y1);
            int vYmax = Math.max(y0, y1);
            int hXmin = Math.min(x2, x3);
            int hXmax = Math.max(x2, x3);
            int hy = y2; // same as y3
            return (hXmin <= vx && vx <= hXmax) && (vYmin <= hy && hy <= vYmax);
        }

        // Second case: first road is horizontal (y0==y1), second is vertical (x2==x3)
        if (y0 == y1 && x2 == x3) {
            int hy = y0;
            int hXmin = Math.min(x0, x1);
            int hXmax = Math.max(x0, x1);
            int vx = x2; // same as x3
            int vYmin = Math.min(y2, y3);
            int vYmax = Math.max(y2, y3);
            return (hXmin <= vx && vx <= hXmax) && (vYmin <= hy && hy <= vYmax);
        }

        return false;
    }

    private Pos getIntersect(
        int x0, int y0, int x1, int y1,
        int x2, int y2, int x3, int y3
    ) {
        if (x0 == x1 && y2 == y3) {
            return new Pos(x0, y2);
        }
        if (y0 == y1 && x2 == x3) {
            return new Pos(x2, y0);
        }
        return null;
    }

    private boolean isInRange(int a, int b, int o) {
        // o 가 a~b 범위 안에 있는지 여부 (끝점 포함)
        return Math.min(a, b) <= o && o <= Math.max(a, b);
    }
}

public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] result = sol.solution(
            new int[][] {
                {-1, 3}, {7, 3}, {1, -1}, {-2, 6}
            },
            new int[][] {
            {-1, 7, 7, 7, 80}, {-3, 3, 9, 3, 45}, {-2, -4, -2, 6, 60}, {1, -4, 1, 8, 50}, {5, 1, 5, 7, 70}
            }
        );

        System.out.println("result = " + Arrays.toString(result));
    }
}