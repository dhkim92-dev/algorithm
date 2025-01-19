
class Solution {

    private int N, C;

    private Pos[] fields;

    private int[] parents;

    static class Pos {

        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        static int dist(Pos a, Pos b) {
            return (int)Math.pow(b.r-a.r, 2) + (int)Math.pow(b.c-a.c, 2);
        }
    }

    static class Node {
        int from, to, cost;

        Node(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);

        fields = new Pos[N];

        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            fields[i] = new Pos(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        }
    }

    private int findParent(int x) {
        if(parents[x] == x) {
            return x;
        }

        return parents[x] = findParent(parents[x]);
    }

    private void unionParent(int x, int y) {
        x = findParent(x);
        y = findParent(y);

        if(x < y) {
            parents[y] = x;
        } else {
            parents[x] = y;
        }
    }

    private boolean isSameParent(int x, int y) {
        x = findParent(x);
        y = findParent(y);

        return x == y;
    }

    public void run() {
        // N개의 좌표가 존재한다.
        // 각 좌표에서 다른 좌표로 가는 코스트 배열을 구한다.
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        parents = new int[N+1];

        for(int i = 0 ; i <= N ; i++) {
            parents[i] = i;
        }

        for(int i = 0 ; i < N ; i++) {
            for(int j = i + 1 ; j < N ; j++) {
                int cost = Pos.dist(fields[i], fields[j]);

                if(cost >= C) {
                    pq.add(new Node(i, j, cost));
                }
            }
        }

        int count = 0;
        int cost = 0;

        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            if(!isSameParent(cur.from, cur.to)) {
                cost += cur.cost;
                unionParent(cur.from, cur.to);
                count++;
            }

            if(count == N-1) {
                break;
            }
        }

        if(count != N-1) {
            System.out.println(-1);
        } else {
            System.out.println(cost);
        }
    }
}
