
import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private int N, M;

    private List<int[]>[] graph;

    private int from, to;


    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        graph = new List[N+1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokens = reader.readLine().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            int w = Integer.parseInt(tokens[2]);
            graph[u].add(new int[]{v, w});
            graph[v].add(new int[]{u, w}); // Assuming undirected graph
        }

        tokens = reader.readLine().split(" ");
        from = Integer.parseInt(tokens[0]);
        to = Integer.parseInt(tokens[1]);
    }

    private boolean canReach(int weight) {
        boolean[] visited = new boolean[N + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(from);
        visited[from] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            if (current == to) {
                return true;
            }

            for ( int[] edge : graph[current] ) {
                int neighbor = edge[0];
                int edgeWeight = edge[1];

                if (!visited[neighbor] && edgeWeight >= weight) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }

    private void simulate() throws IOException { 

        int lo = 0;
        int hi = 1_000_000_001; 

        while ( lo + 1 < hi ) {
            int mid = (lo + hi) / 2;
            if ( canReach(mid) ) {
                lo = mid ; 
            } else {
                hi = mid; 
            }
        }

        writer.write(String.valueOf(lo) + "\n");
    }

    public void run() throws IOException {
        simulate();
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


