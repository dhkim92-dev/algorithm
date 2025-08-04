import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private static final String impossible = "IMPOSSIBLE\n";

    private int nrTest, nrNode, nrSwap;

    private int[] ranks;

    private int[] inDegree;

    boolean[][] connected;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        nrTest = Integer.parseInt(reader.readLine().trim());
    }

    private void init() throws IOException {
        nrNode = Integer.parseInt(reader.readLine().trim());
        String[] tokens = reader.readLine().trim().split(" ");
        ranks = new int[nrNode];
        connected = new boolean[nrNode+1][nrNode+1];
        inDegree = new int[nrNode+1];

        for (int i = 0; i < nrNode; ++i) {
            ranks[i] = Integer.parseInt(tokens[i]);
            inDegree[ranks[i]] = i;

            for ( int j = 1 ; j <= nrNode ; ++j) {
                if ( j != ranks[i] && !connected[j][ranks[i]] ) {
                    connected[ranks[i]][j] = true;
                }
            }
        }

        nrSwap = Integer.parseInt(reader.readLine().trim());
        for (int i = 0; i < nrSwap; ++i) {
            tokens = reader.readLine().trim().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);

            if ( !connected[u][v] ) {
                connected[u][v] = true;
                connected[v][u] = false; // u -> v
                inDegree[u]--;
                inDegree[v]++;
            } else {
                connected[u][v] = false;
                connected[v][u] = true; // v -> u
                inDegree[u]++;
                inDegree[v]--;
            }
        }
    }

    private void sort() throws IOException {

        Queue<Integer> q = new LinkedList<>();
        for ( int i = 1 ; i <= nrNode ; ++i ) {
            if ( inDegree[i] == 0 ) {
                q.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();

        for ( int i = 1 ; i<= nrNode ; ++i ) {
            if ( q.isEmpty() ) {
                writer.write(impossible);
                return;
            }

            if ( q.size() > 1  ) {
                writer.write("?\n");
                return;
            }

            int current = q.poll();
            sb.append(current + " ");
            for ( int j = 1 ; j <= nrNode ; ++j ) {
                if ( connected[current][j] ) {
                    connected[current][j] = false; // Mark as processed
                    inDegree[j]--;
                    if ( inDegree[j] == 0 ) {
                        q.add(j);
                    }
                }
            }
        }

        writer.write(sb.toString());
        writer.write("\n");
    }

    private void solve() throws IOException {
        init();
        sort();
    }

    public void run() throws IOException {

        for ( int i = 0 ; i < nrTest ; ++i ) {
            solve();
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


