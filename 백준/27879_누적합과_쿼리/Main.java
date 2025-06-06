import java.io.*;
import java.util.*;

class Solution {

    static final class Query {
        int i1,j1,i2,j2;
        String str;

        public Query (int i1, int j1, int i2, int j2, String str) {
            this.i1 = i1;
            this.j1 = j1;
            this.i2 = i2;
            this.j2 = j2;
            this.str = str;
        }
    }

    private final int n, q;

    private final char[][] arr;

    private final Query[] query;

    private final int[] dr = {0, 1};

    private final int[] dc = {1, 0};

    public Solution(BufferedReader reader) throws IOException {
        n = Integer.parseInt(reader.readLine().trim());
        arr = new char[n][n];

        for ( int i = 0 ; i < n ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            for ( int j = 0 ; j < n ; ++j ) {
                arr[i][j] = tokens[j].charAt(0);
            }
        }

        q = Integer.parseInt(reader.readLine().trim());

        query = new Query[q];

        for ( int i = 0 ; i < q ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            int i1 = Integer.parseInt(tokens[0]) - 1;
            int j1 = Integer.parseInt(tokens[1]) - 1;
            int i2 = Integer.parseInt(tokens[2]) - 1;
            int j2 = Integer.parseInt(tokens[3]) - 1;
            String str = tokens[4];
            query[i] = new Query(i1, j1, i2, j2, str);
        }
    }

    int[][][] prefixSum(int k) {
        int[][][] psum = new int[4][n+1][n+1];
        int[][][] origin = new int[4][n][n];

        for ( int i = 0 ; i < n ; ++i ) {
            for (int j = 0; j < n; ++j) {
                int nr = i + dr[k];
                int nc = j + dc[k];

                if (nr < 0 || nr >= n || nc < 0 || nc >= n) {
                    continue;
                }

                if (arr[i][j] == 'a' && arr[nr][nc] == 'a') {
                    origin[0][i][j]++;
                } else if (arr[i][j] == 'a' && arr[nr][nc] == 'b') {
                    origin[1][i][j]++;
                } else if (arr[i][j] == 'b' && arr[nr][nc] == 'a') {
                    origin[2][i][j]++;
                } else if (arr[i][j] == 'b' && arr[nr][nc] == 'b') {
                    origin[3][i][j]++;
                }
            }
        }

        for ( int i = 0 ; i < 4 ; ++i ) {
            for ( int r = 1 ; r <= n ; ++r ) {
                for ( int c = 1 ; c <= n ; ++c ) {
                    psum[i][r][c] = origin[i][r-1][c-1]
                                    + psum[i][r-1][c]
                                    + psum[i][r][c-1]
                                    - psum[i][r-1][c-1];
                }
            }
        }

        return psum;
    }

    private void print(String str, int[][] psum) {
        System.out.println("##########" + str + "#############");
        for (int i = 0; i < psum.length ; ++i) {
            for (int j = 0; j < psum[0].length; ++j) {
                System.out.print(psum[i][j] + " ");
            }
            System.out.println();
        }
    }

    private String simulate() {
        StringBuilder sb = new StringBuilder();
        int[][][] hPsum = prefixSum(0);
        int[][][] vPsum = prefixSum(1);
        Map<String, Integer> strToIndex = new HashMap<>();
        strToIndex.put("aa", 0);
        strToIndex.put("ab", 1);
        strToIndex.put("ba", 2);
        strToIndex.put("bb", 3);

        for (Map.Entry<String, Integer> entry: strToIndex.entrySet()) {
//            print(entry.getKey(), psum[entry.getValue()]);
        }

        for ( Query q : query ) {
            int i1 = q.i1, j1 = q.j1, i2 = q.i2, j2 = q.j2;
            String str = q.str;
            int idx = strToIndex.get(str);
//            sb.append(str).append("\n");
            int count = 0;
            if (q.j1 < q.j2) {
                int r1 = i1, c1 = j1;
                int r2 = i2, c2 = j2 - 1;

                count += hPsum[idx][r2 + 1][c2 + 1]
                        - hPsum[idx][r1][c2 + 1]
                        - hPsum[idx][r2 + 1][c1]
                        + hPsum[idx][r1][c1];
            }

            if ( q.i1 < q.i2) {
                int r1 = i1, c1 = j1;
                int r2 = i2 - 1, c2 = j2;

                count += vPsum[idx][r2 + 1][c2 + 1]
                        - vPsum[idx][r1][c2 + 1]
                        - vPsum[idx][r2 + 1][c1]
                        + vPsum[idx][r1][c1];
            }

            sb.append(count)
              .append("\n");
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
