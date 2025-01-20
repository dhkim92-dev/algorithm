
class Solution {

    private int N, K;

    static class Pos {
        int time;
        int x;

        Pos(int time, int x) {
            this.time = time;
            this.x = x;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        K = Integer.parseInt(tokens[1]);
    }

    public void run() {
        // N개의 좌표가 존재한다.
        // 각 좌표에서 다른 좌표로 가는 코스트 배열을 구한다.

        int[] minTime = new int[100001];
        Arrays.fill(minTime, Integer.MAX_VALUE);
        boolean visited[] = new boolean[100001];
        visited[N] = true;

        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(0, N));
        int answer = Integer.MAX_VALUE;

        while(!q.isEmpty()) {
            Pos cur = q.poll();
            visited[cur.x] = true;

            if(cur.x == K) {
                answer = Math.min(answer, cur.time);
            }

            // 2배 이동
            if(cur.x * 2 <= 100000 && !visited[cur.x * 2]) {
                q.add(new Pos(cur.time, cur.x * 2));
            }

            if(cur.x + 1 <= 100000 && !visited[cur.x + 1]) {
                q.add(new Pos(cur.time + 1, cur.x + 1));
            }

            if(cur.x - 1 >= 0 && !visited[cur.x - 1]) {
                q.add(new Pos(cur.time + 1, cur.x - 1));
            }
        }
        
        System.out.println(answer);
    }
}
