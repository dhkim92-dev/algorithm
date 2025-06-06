
import java.io.*;
import java.util.*;

class Solution {

    private int N;

    private BufferedReader reader;

    private final int[] dr = { -1, 1, 0, 0 };
    private final int[] dc = { 0, 0, 1, -1 };

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
    }

    private boolean isInRange(int r, int c, int n, int m) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }

    private boolean isStable(int[][] frame, int r, int c) {
        int n = frame.length;
        int m = frame[0].length;

        // 자신 좌 우에 하나라도 없고 그리고
        // 자신 상 하에 하나라도 없으면 불안정
        boolean hStable = true;
        boolean vStable = true;

        for ( int i = 0 ; i < 2 ; ++ i) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if ( isInRange(nr, nc, n, m) && frame[nr][nc] == 0 ) {
                hStable = false;
                break;
            }
        }

        for ( int i = 2 ; i < 4 ; ++ i) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if ( isInRange(nr, nc, n, m) && frame[nr][nc] == 0 ) {
                vStable = false;
                break;
            }
        }

        return hStable || vStable;
    }

    private int knock(int[][] frame, int r, int c) {
        int n = frame.length;
        int m = frame[0].length;

        if ( frame[r][c] == 0 ) {
//            System.out.println("Already knocked at (" + (r) + ", " + (c) + ")");
            return 0;
        }

//        System.out.println("Knocking at (" + (r) + ", " + (c) + ")");
        int count = 1;
        frame[r][c] = 0;

        for ( int i = 0 ; i < 4 ; ++i ) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (!isInRange(nr, nc, n, m) ) continue;
            if ( isStable(frame, nr, nc) ) continue;
            if ( frame[nr][nc] == 0 ) continue;
//            System.out.println("    {" + nr + ", " + nc + "} is unstable, knocking it down");
            count = count + knock(frame, nr, nc);
        }

//        System.out.println("knock at (" + (r) + ", " + (c) + ") finished, count = " + count);
        return count;
    }

    private String simulate(int n, int m, int q) throws IOException {
        StringBuilder sb = new StringBuilder();
        int[][] frame = new int[n][m];
        for ( int[] row : frame ) {
            Arrays.fill(row, 1);
        }

        for ( int i = 0 ; i < q ; ++i ) {
            String[] tokens = reader.readLine().split(" ");
            int r = Integer.parseInt(tokens[0]) - 1;
            int c = Integer.parseInt(tokens[1]) - 1;
            int dropCount = knock(frame, r, c);
            sb.append(dropCount)
              .append("\n");
        }

        return sb.toString();
    }

    private String simulate() throws IOException {
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(reader.readLine());
        int n, m, q;

        for ( int i = 0 ; i < N ; ++i ) {
            String[] tokens = reader.readLine().split(" ");
            n = Integer.parseInt(tokens[0]);
            m = Integer.parseInt(tokens[1]);
            q = Integer.parseInt(tokens[2]);
            sb.append( simulate(n, m, q) );
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
