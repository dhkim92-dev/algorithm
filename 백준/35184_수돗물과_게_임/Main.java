import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;
    private final BufferedWriter writer;

    private int N, M;
    private long T;

    private char[][] board;

    static final char EMTPY = '.';
    static final char FRESH = 'T';
    static final char SALTED = 'S';

    private int cr, cc, cd;

    int[] dr = {-1, 0, 1, 0};
    int[] dc = {0, -1, 0, 1};

    boolean[][][] visited;
    boolean[][][] resolving;
    long[][][] resolved;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        T = Long.parseLong(tokens[2]);

        board = new char[N][M];
        visited = new boolean[N][M][4];
        resolving = new boolean[N][M][4];
        resolved = new long[N][M][4];

        for ( int r = 0 ; r < N ; ++r )
            for ( int c = 0 ; c < M ; ++c )
                for ( int d = 0 ; d < 4 ; ++d )
                    resolved[r][c][d] = -2L;

        for ( int r = 0 ; r < N ; ++r ) {
            String line = reader.readLine();
            for ( int c = 0 ; c < M ; ++c ) {
                board[r][c] = line.charAt(c);
                if ( board[r][c] >= '0' && board[r][c] <= '3' ) {
                    cr = r;
                    cc = c;
                    cd = board[r][c] - '0';
                    board[r][c] = EMTPY;
                }
            }
        }
    }

    private boolean isBound(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }

    private boolean isFresh(int r, int c) {
        return board[r][c] == FRESH;
    }

    private long encode(int r, int c, int d) {
        return (long)r * M * 4 + (long)c * 4 + d;
    }

    private long resolveFresh(int r, int c, int d) {
        if ( resolved[r][c][d] != -2L ) return resolved[r][c][d];
        if ( resolving[r][c][d] ) return resolved[r][c][d] = -1L;

        resolving[r][c][d] = true;

        int nd = (d + 1) % 4;   // CCW
        int nr = r + dr[nd];
        int nc = c + dc[nd];

        if ( !isBound(nr, nc) ) {
            resolved[r][c][d] = -1L;
        } else if ( isFresh(nr, nc) ) {
            resolved[r][c][d] = resolveFresh(nr, nc, nd);
        } else {
            resolved[r][c][d] = encode(nr, nc, nd);
        }

        resolving[r][c][d] = false;
        return resolved[r][c][d];
    }

    public void run() throws IOException {
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[1]));
        pq.offer(new long[]{encode(cr, cc, cd), 0});

        while ( !pq.isEmpty() ) {
            long[] cur = pq.poll();
            long state = cur[0];
            long cost = cur[1];

            int r = (int)(state / (M * 4));
            int c = (int)((state / 4) % M);
            int d = (int)(state % 4);

            if ( board[r][c] == SALTED ) {
                writer.write(String.valueOf(cost));
                writer.flush();
                return;
            }

            if ( visited[r][c][d] ) continue;
            visited[r][c][d] = true;

            int nd = (d + 1) % 4;
            if ( !visited[r][c][nd] )
                pq.offer(new long[]{encode(r, c, nd), cost + T});

            for ( int side : new int[]{1, 3} ) {
                int sd = (d + side) % 4;
                int nr = r + dr[sd];
                int nc = c + dc[sd];

                if ( !isBound(nr, nc) ) continue;

                long next;
                if ( isFresh(nr, nc) ) {
                    next = resolveFresh(nr, nc, d);
                    if ( next == -1L ) continue;
                } else {
                    next = encode(nr, nc, d);
                }

                int rr = (int)(next / (M * 4));
                int cc = (int)((next / 4) % M);
                int dd = (int)(next % 4);

                if ( !visited[rr][cc][dd] )
                    pq.offer(new long[]{next, cost + 1});
            }
        }

        writer.write("-1");
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

