import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int R, C;

    private char[][] board;

    private int Q;

    private int[] queries;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] tokens = reader.readLine().split(" ");
        this.R = Integer.parseInt(tokens[0]);
        this.C = Integer.parseInt(tokens[1]);

        board = new char[R][C];
        for (int i = 0; i < R; i++) {
            String line = reader.readLine();
            board[i] = line.toCharArray();
        }

        this.Q = Integer.parseInt(reader.readLine());
        queries = new int[Q];
        tokens = reader.readLine().split(" ");
        for (int i = 0; i < Q; i++) {
            queries[i] = Integer.parseInt(tokens[i]);
        }
    }

    private void removeMineral(int row, boolean isLeft) {

        if ( isLeft ) {
            for ( int c = 0; c < C; c++) {
                if (board[row][c] == 'x') {
                    board[row][c] = '.';
                    return;
                }
            }
        } else {
            for ( int c = C - 1; c >= 0; c--) {
                if (board[row][c] == 'x') {
                    board[row][c] = '.';
                    return;
                }
            }
        }
    }

    private List<List<Pos>> findClusters() {
        boolean[][] visited = new boolean[R][C];
        List<List<Pos>> clusters = new ArrayList<>();

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (board[r][c] == 'x' && !visited[r][c]) {
                    List<Pos> cluster = new ArrayList<>();
                    Queue<Pos> queue = new LinkedList<>();
                    queue.add(new Pos(r, c));
                    visited[r][c] = true;

                    while (!queue.isEmpty()) {
                        Pos pos = queue.poll();
                        cluster.add(pos);

                        for (Pos dir : Arrays.asList(new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1))) {
                            Pos next = pos.add(dir);
                            if (next.r >= 0 && next.r < R && next.c >= 0 && next.c < C &&
                                board[next.r][next.c] == 'x' && !visited[next.r][next.c]) {
                                visited[next.r][next.c] = true;
                                queue.add(next);
                            }
                        }
                    }

                    clusters.add(cluster);
                }
            }
        }

        return clusters;
    }

    void doGravity(List<List<Pos>> clusters) {
        for (List<Pos> cluster : clusters) {
            int maxRow = -1;
            for (Pos pos : cluster) {
                maxRow = Math.max(maxRow, pos.r);
            }

            if (maxRow == R - 1) {
                continue; // Already at the bottom, no gravity needed
            }

            for ( Pos p : cluster ) {
                board[p.r][p.c] = '.'; // Clear the cluster from its current position
            }

            int fallDistance = 0;
            while (true) {
                boolean canFall = true;
                for (Pos pos : cluster) {
                    if (pos.r + fallDistance + 1 >= R || board[pos.r + fallDistance + 1][pos.c] == 'x') {
                        canFall = false;
                        break;
                    }
                }

                if (!canFall) {
                    break;
                }

                fallDistance++;
            }

            for (Pos pos : cluster) {
                pos.r += fallDistance; // Move the cluster down
            }
        }
    }

    public void run() throws IOException {
        boolean isLeft = true;

        for (int query : queries) {
            removeMineral(R - query, isLeft);
            isLeft = !isLeft; // Alternate between left and right
            List<List<Pos>> clusters = findClusters();
            doGravity(clusters);

            for ( char[] row : board ) {;
                Arrays.fill(row, '.'); // Clear the board before applying gravity
            }
            for ( List<Pos> cluster : clusters ) {
                for ( Pos p : cluster ) {
                    board[p.r][p.c] = 'x'; // Restore the cluster after gravity
                }
            }
        }

        for (int i = 0; i < R; i++) {
            writer.write(board[i]);
            writer.newLine();
        }

        writer.flush();
    }

    private static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos other) {
            return new Pos(this.r + other.r, this.c + other.c);
        }
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


