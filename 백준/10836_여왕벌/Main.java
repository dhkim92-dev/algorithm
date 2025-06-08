import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private int M, N;

    private int[][] grid;

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
    }

    private void accumulate(int[] accumulated, int[] query) {
        int nrZero = query[0];
        int nrOne = query[1];
        int nrTwo = query[2];
        int cursor = 0;

//        accumulated[cursor] += 0;
        cursor += nrZero;
//        accumulated[cursor] -= 0;
        accumulated[cursor] += 1;
        cursor += nrOne;
        accumulated[cursor] -= 1;
        accumulated[cursor] += 2;
        cursor += nrTwo;
        accumulated[cursor] -= 2;
    }

    private void prefixSum(int[] accumulated) {
        for ( int i = 1 ; i < accumulated.length ; ++i ) {
            accumulated[i] += accumulated[i - 1];
        }
    }

    private void apply(int[][] grid, int[] psum) {
        int limit = M * 2 - 1;
        int r = M - 1;
        int c = 0;

        for ( int i = 0 ; i < limit ; ++i ) {
            grid[r][c] = psum[i];
            if ( r == 0 ) {
                c++;
            } else {
                r--;
            }
        }

        for ( r = 1 ; r < M ; ++r ) {
            for ( c = 1 ; c < M ; ++c ) {
                grid[r][c] = Math.max(grid[r-1][c-1], Math.max(grid[r-1][c], grid[r][c-1]) );
            }
        }

        for ( r = 0 ; r < M ; ++r ) {
            for ( c = 0 ; c < M ; ++c ) {
                grid[r][c]++;
            }
        }

    }

    private String simulate() throws IOException {
        StringBuilder sb = new StringBuilder();
        String[] tokens = reader.readLine().split(" ");
        M = Integer.parseInt(tokens[0]);
        N = Integer.parseInt(tokens[1]);
        grid = new int[M][M];
        int[] accumulated = new int[2 * M];

        //나머지 애벌레들은 자신의 왼쪽(L), 왼쪽 위(D), 위쪽(U)의 애벌레들이 다 자란 다음,
        // 그 날 가장 많이 자란 애벌레가 자란 만큼 자신도 자란다.

        for ( int i = 0 ; i < N ; ++i ) {
            int[] query = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            accumulate(accumulated, query);
        }
        prefixSum(accumulated);
        apply(grid, accumulated);

        for ( int i = 0 ; i < M ; ++i ) {
            for ( int j = 0 ; j < M ; ++j ) {
                sb.append(grid[i][j]);
                if ( j != M - 1 ) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
