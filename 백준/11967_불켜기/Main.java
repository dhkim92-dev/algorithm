import java.io.*;
import java.util.*;

class Solution {

    private int N, M;

    private List<Integer>[] switches;

    private static final int[] dr = {-1, 0, 1, 0};

    private static final int[] dc = {0, 1, 0, -1};

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        switches = new List[N*N];
        for ( int i = 0 ; i < N * N ; ++i ) {
            switches[i] = new ArrayList<>();
        }

        for ( int i = 0 ; i < M ; ++i ) {
            tokens = reader.readLine().split(" ");
            int x = Integer.parseInt(tokens[0]) - 1;
            int y = Integer.parseInt(tokens[1]) - 1;
            int z = Integer.parseInt(tokens[2]) - 1;
            int w = Integer.parseInt(tokens[3]) - 1;

            switches[x * N + y].add(z * N + w);
        }
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private boolean hasVisitedNeighbor(boolean[] visited, int r, int c) {
        for ( int i = 0 ; i < 4 ; ++i ) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if ( isInRange(nr, nc) && visited[nr * N + nc] ) return true;
        }
        return false;
    }

    private int bfs() {
        boolean[] visited = new boolean[(N * N)];
        boolean[] turnedOn = new boolean[(N * N)];

        Queue<Node> q = new LinkedList<>();
        visited[0] = true;
        turnedOn[0] = true;
        q.offer(new Node(0, 0));

        while ( !q.isEmpty() ) {
            Node cur = q.poll();
            int r = cur.r;
            int c = cur.c;
            int offset = r * N + c;

            for ( int next : switches[offset] ) {
                if ( !turnedOn[next] ) {
                    turnedOn[next] = true;
                    if ( hasVisitedNeighbor(visited, next / N, next % N) ) {
                        q.offer(new Node(next / N, next % N));
                        visited[next] = true;
                    }
                }
            }

            for ( int i = 0; i < 4; ++i ) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                int nextOffset = nr * N + nc;
                if ( !isInRange(nr, nc) ||
                     visited[nextOffset] ||
                     !turnedOn[nextOffset]) continue;
                visited[nextOffset] = true;
                q.offer(new Node(nr, nc));
            }
        }

        int cnt = 0;
        for ( boolean isOn : turnedOn ) {
            if ( isOn ) cnt++;
        }

        return cnt;
    }

    public void run () throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(bfs())
          .append('\n');
        System.out.println(sb.toString());
    }

    private static class Node {
        public int r, c;

        Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
