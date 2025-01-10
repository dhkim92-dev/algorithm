
class Solution {

    private int N, R, Q;

    private int[] subTreeSize;

    private boolean[] visited;

    private List<Integer>[] graph;

    private int[] query;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        R = Integer.parseInt(tokens[1]);
        Q = Integer.parseInt(tokens[2]);

        graph = new List[N + 1];
        for(int i = 0 ; i < N + 1 ; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i = 0 ; i < N - 1 ; i++) {
            tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            graph[from].add(to);
            graph[to].add(from);
        }

        subTreeSize = new int[N + 1];
        visited = new boolean[N + 1];
        query = new int[Q];

        for(int i = 0 ; i < Q ; i++) {
            query[i] = Integer.parseInt(reader.readLine());
        }
    }

    private int dfs(int root) {
        if(subTreeSize[root] != 0) {
            return subTreeSize[root];
        }
        visited[root] = true;
        int count = 1;

        for(int next: graph[root]) {
            if(visited[next]) {
                continue;
            }
            count += dfs(next);
        }
        subTreeSize[root] = count;

        return subTreeSize[root];
    }

    public void run() {
        dfs(R);
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < Q ; i++) {
            sb.append(subTreeSize[query[i]]).append("\n");
        }
        System.out.println(sb.toString());
    }
}
