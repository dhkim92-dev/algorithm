class Solution {

    private int N;

    private List<Integer>[] conn;

    private int[][] dp;

    private boolean[] visited;

    public Solution(BufferedReader reader) throws IOException {
        String[] input = reader.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        conn = new ArrayList[N+1];

        for(int i = 0 ; i < N+1 ; i++) {
            conn[i] = new ArrayList<>();
        }

        for(int i = 0 ; i < N-1 ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            conn[from].add(to);
            conn[to].add(from);
        }
    }

    private void dfs(int start) {
        visited[start] = true;
        dp[start][0] = 0;
        dp[start][1] = 1;

        for(int next : conn[start]) {
            if(visited[next]) {
                continue;
            }
            dfs(next);
            dp[start][0] += dp[next][1];
            dp[start][1] += Math.min(dp[next][0], dp[next][1]);
        }
    }

    public void run() {
        // dp[i][j] i번째 노드가 j = 0 : 얼리어답터가 아닌 경우, 1 : 얼리어답터인 경우 현태 노드를 루트로 하는 서브트리에서 필요한 최소 얼리어답터 수
        dp = new int[N+1][2];
        visited = new boolean[N+1]; // 트리에서 해당 노드 방문 여부
        dfs(1);
        System.out.println(Math.min(dp[1][0], dp[1][1]));
    }
}
