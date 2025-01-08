
class Solution {
    int N, K;

    static class Cost {
        int to;
        int cost;

        Cost(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        K = Integer.parseInt(line[1]);
    }

    boolean isInRange(int idx) {
        return 0 <= idx && idx <= 100000;
    }

    void bfs() {
        PriorityQueue<Cost> q = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        q.add(new Cost(N, 0));
        int[] dist = new int[100001];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[N] = 0;
        int[] trace = new int[100001];
        trace[N] = -1;

        while(!q.isEmpty()) {
            Cost cur = q.poll();
            // 현재 좌표에서 갈 수 있는 세곳
            if(cur.cost > dist[cur.to]) {
                continue;
            }
            int Xm = cur.to - 1;
            int Xp = cur.to + 1;
            int X2M = cur.to * 2;

            if(isInRange(Xm) && (dist[Xm] > cur.cost + 1)) {
                q.add(new Cost(Xm, cur.cost + 1));
                trace[Xm] = cur.to;
                dist[Xm] = cur.cost + 1;
            }

            if(isInRange(Xp) && (dist[Xp] > cur.cost + 1)) {
                q.add(new Cost(Xp, cur.cost + 1));
                trace[Xp] = cur.to;
                dist[Xp] = cur.cost + 1;
            }

            if(isInRange(X2M) && (dist[X2M] > cur.cost + 1)) {
                q.add(new Cost(X2M, cur.cost + 1));
                trace[X2M] = cur.to;
                dist[X2M] = cur.cost + 1;
            }
        }

        System.out.println(dist[K]);
        int t = K;
        Stack<Integer> history = new Stack<>();
        history.push(K);
        while(t != -1) {
            if(trace[t] != - 1) {
                history.push(trace[t]);
            }
            t = trace[t];
        }

        while(!history.isEmpty()) {
            System.out.print(history.pop() + " ");
        }
    }

    public void run() {
        
        bfs();
    }
}
