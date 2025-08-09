import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private List<Integer>[] graph;

    private int[][] parents;

    private int[] depth;

    private int maxDepth = 0;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        N = Integer.parseInt(reader.readLine().trim());
        graph = new List[N+1];
        for (int i = 0 ; i <= N ; ++i ) {
            graph[i] = new ArrayList<>();
        }

        for ( int i = 0 ; i < N-1 ; ++i ) {
            String[] line = reader.readLine().trim().split(" ");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            graph[u].add(v);
            graph[v].add(u);
        }

        M = Integer.parseInt(reader.readLine().trim());
        maxDepth = (int) Math.ceil(Math.log(N) / Math.log(2));
        parents = new int[N + 1][maxDepth];
        depth = new int[N + 1];

//        for ( int[] row: parents ) {
//            Arrays.fill(row, 0);
//        }
        Arrays.fill(depth, -1);
        depth[1] = 0;
    }

    private void dfs(int node) {
        for ( int child : graph[node] ) {
            if ( this.depth[child] == -1 ) {
                parents[child][0] = node;
                this.depth[child] = this.depth[node] + 1;
                dfs(child);
            }
        }
    }

    /**
     * 각 노드에서 2^n 스트라이드로 부모 노드를 탐색하여 지정해둔다.
     * parents[i][j] = i의 2^j번째 부모 노드
     * parents[i][j] = parents[parents[i][j-1]][j-1]
     * 이렇게 하면 O(logN) 시간에 부모 노드를 찾을 수 있다.
     */
    private void connection() {
        for ( int i = 1 ; i < maxDepth ; ++i ) {
            for (int j = 1 ; j <= N ; ++j) {
                if ( parents[j][i-1] != 0 ) {
                    parents[j][i] = parents[parents[j][i-1]][i-1];
                }
            }
        }
    }

    private int getLCA(int u, int v) {
        if ( depth[u] < depth[v] ) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for ( int i = 0 ; diff != 0 ; ++i ) {
            if ( (diff & 0x01) == 0x01 ) {
                u = parents[u][i];
            }
            diff >>= 1;
        }

        if ( u == v ) return u;
        for ( int i = maxDepth - 1 ; i >= 0 ; --i ) {
            if ( parents[u][i] != 0 && parents[u][i] != parents[v][i] ) {
                u = parents[u][i];
                v = parents[v][i];
            }
        }
        return parents[u][0];
    }

    public void run() throws IOException {
        dfs(1);
        connection();

        for ( int i = 1 ; i <= M ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            int lca = getLCA(u, v);
            writer.write(lca + "\n");
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


