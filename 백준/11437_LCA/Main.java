import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private List<Integer>[] graph;

    private int[][] parent;

    private int[] depth;

    private int maxDepth;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        N = Integer.parseInt(reader.readLine().trim());
        graph = new ArrayList[N+1];
        depth = new int[N + 1];
        Arrays.fill(depth, -1);
        for ( int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for ( int i = 0 ; i < N - 1 ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            graph[u].add(v);
            graph[v].add(u);
        }

        M = Integer.parseInt(reader.readLine().trim());
        maxDepth = (int) Math.ceil(Math.log(N) / Math.log(2));
        parent = new int[N + 1][maxDepth];
        depth[1] = 0;
    }

    /**
     * 최소 공통 조상을 구하기 위한 DFS 탐색
     */
    private void dfs(int cur){//}, int level) {
//        this.depth[cur] = level;
        for (int next : graph[cur]) {
            if (depth[next] == -1) { // 방문하지 않은 노드
                parent[next][0] = cur;
                depth[next] = depth[cur] + 1;
                dfs(next);
            }
        }
    }

    private int getLCA(int u, int v) {
        if ( depth[u] < depth[v] ) {
            int tmp = u;
            u = v;
            v = tmp;
        }

        int diff = depth[u] - depth[v];

        for ( int i = 0 ; diff != 0 ; ++i) {
            if ( (diff & 0x01) == 0x01) {
                u = parent[u][i];
            }
            diff>>=1;
        }

        if ( u != v ) {
            for (int i = maxDepth - 1; i >= 0; --i) {
                if ((parent[u][i] != 0) && (parent[u][i] != parent[v][i])) {
                    u = parent[u][i];
                    v = parent[v][i];
                }
            }
            u = parent[u][0];
        }
        return u;
    }

    public void run() throws IOException {
//        dfs(1, 0);
        dfs(1);

        for ( int i = 1 ; i < maxDepth ; ++i ) {
            for ( int j = 1 ; j <= N ; ++j ) {
                if ( parent[j][i - 1] != 0 ) {
                    parent[j][i] = parent[parent[j][i - 1]][i - 1];
                }
            }
        }

        for ( int i = 0 ; i < M ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            int lca = getLCA(u, v);
            writer.write(String.valueOf(lca));
            if ( i < M - 1 ) {
                writer.newLine();
            }
        }
        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}

