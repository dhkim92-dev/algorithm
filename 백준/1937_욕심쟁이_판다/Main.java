import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N; 

    private int[][] board;

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos other) {
            return new Pos(this.r + other.r, this.c + other.c);
        }
    };

    static Pos[] dirs = {
        new Pos(-1, 0), // up
        new Pos(0, 1),   // right
        new Pos(1, 0),  // down
        new Pos(0, -1), // left
    };

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        N = Integer.parseInt(reader.readLine());
        board = new int[N][N];

        for ( int i = 0 ; i < N ; ++i ) {
            String[] tokens = reader.readLine().split(" ");
            for ( int j = 0 ; j < N ; ++j ) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private int explore(int r, int c, int[][] dp) {
        if ( dp[r][c] != 0 ) return dp[r][c];
        if ( dp[r][c] == 0 ) dp[r][c] = 1;

        for ( Pos dir : dirs ) {
            Pos nxt = new Pos(r, c).add(dir);
            if ( isInRange(nxt.r, nxt.c) && board[nxt.r][nxt.c] > board[r][c] ) {
                dp[r][c] = Math.max(dp[r][c], explore(nxt.r, nxt.c, dp) + 1);
            }
        }

        return dp[r][c];
    }

    public void run() throws IOException {
        int answer = 0;

        int[][] dp = new int[N][N];
        for ( int[] row : dp ) {
            Arrays.fill(row, 0);
        }

        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0; c < N ; ++c ) {
                explore(r, c, dp);
            }
        }

        for ( int i = 0 ; i < N ; ++i ) {
            for ( int j = 0 ; j < N ; ++j ) {
                answer = Math.max(answer, dp[i][j]);
            }
        }

        writer.write(String.valueOf(answer));
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

