import java.io.*;
import java.util.*;

class Solution {

    private int N;
    private int[][] board;
    private boolean[][] visited;
    private static final Pos[] dirs = {
            new Pos(-1, 0),
            new Pos(1, 0),
            new Pos(0, -1),
            new Pos(0, 1)
    };
    private List<PCL> pcls = new ArrayList<>();
    private int[] colorCount = new int[26];

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        board = new int[N][N];
        visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            String line = reader.readLine();
            for (int j = 0; j < N; j++) {
                board[i][j] = (int)(line.charAt(j) - 'A');
            }
        }
    }

    private void traverse(int r, int c, int color, Pos leftTop, Pos rightBottom) {
        visited[r][c] = true;

        for (Pos dir : dirs) {
            int nr = r + dir.r;
            int nc = c + dir.c;
            if (nr >= leftTop.r && nr <= rightBottom.r &&
                nc >= leftTop.c && nc <= rightBottom.c &&
                !visited[nr][nc] &&
                board[nr][nc] == color) {
                traverse(nr, nc, color, leftTop, rightBottom);
            }
        }
    }

    private boolean isConsistedByTwoColors(Pos leftTop, Pos rightBottom) {
        int numColors = 0;
        Arrays.fill(colorCount, 0);

        for (int i = leftTop.r; i <= rightBottom.r; i++) {
            for (int j = leftTop.c; j <= rightBottom.c; j++) {
                if (visited[i][j]) continue;
                int color = board[i][j];
                if (colorCount[color] == 0) {
                    numColors++;
                }
                colorCount[color]++;
                traverse(i, j, color, leftTop, rightBottom);
            }
        }
        return numColors == 2;
    }

    private boolean isPcl(Pos leftTop, Pos rightBottom) {
        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }
        // 1. 두가지 색으로만 구성되어야 한다.
        boolean twoColors = isConsistedByTwoColors(leftTop, rightBottom);
        if ( !twoColors ) {
            return false;
        }

        boolean connectedCluster = false;
        boolean separatedCluster = false;

//        System.out.print("colorCount: ");
        for ( int i = 0 ; i < 26 ; ++i ) {
//            System.out.print(colorCount[i] + " ");
            if ( colorCount[i] == 1 ) connectedCluster = true;
            if ( colorCount[i] > 1 ) separatedCluster = true;
        }

//        System.out.println("{ " + leftTop.r + ", " + leftTop.c + " }, { " + rightBottom.r + ", " + rightBottom.c + "} is " + (separatedCluster && connectedCluster ? "pcl" : "not pcl"));

        return connectedCluster && separatedCluster;
    }

    private boolean isBiggestPcl(int index) {
        PCL pcl = pcls.get(index);

        for ( int i = 0 ; i < pcls.size() ; ++i ) {
            if ( i == index ) continue;
            if ( pcls.get(i).contains(pcl.leftTop, pcl.rightBottom) ) {
//                System.out.println("pcl: " + pcl.leftTop.r + ", " + pcl.leftTop.c + ", " + pcl.rightBottom.r + ", " + pcl.rightBottom.c +
//                        " contained by: " + pcls.get(i).leftTop.r + ", " + pcls.get(i).leftTop.c + ", " +
//                        pcls.get(i).rightBottom.r + ", " + pcls.get(i).rightBottom.c);
                return false;
            }
        }
        return true;
    }

    private int simulate() {
        for( int r0 = 0 ; r0 < N ; r0++ ) {
            for( int c0 = 0 ; c0 < N ; c0++ ) {
                for ( int r1 = r0 ; r1 < N ; r1++ ) {
                    for ( int c1 = c0 ; c1 < N ; c1++ ) {
                        Pos leftTop = new Pos(r0, c0);
                        Pos rightBottom = new Pos(r1, c1);
                        if ( isPcl(leftTop, rightBottom) ) {
                            pcls.add(new PCL(leftTop, rightBottom));
                        }
                    }
                }
            }
        }
        int answer = 0;
        for ( int i = 0 ; i < pcls.size() ; ++i ) {
            if( isBiggestPcl(i) ) {
                answer++;
            }
        }

        return answer;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        int answer = simulate();
        sb.append(answer);
        System.out.println(sb.toString());
    }

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos other) {
            return new Pos(this.r + other.r, this.c + other.c);
        }
    }

    static class PCL {
        Pos leftTop, rightBottom;

        PCL(Pos leftTop, Pos rightBottom) {
            this.leftTop = leftTop;
            this.rightBottom = rightBottom;
        }

        boolean contains(Pos lt, Pos rb) {
            return lt.r >= leftTop.r &&
                    lt.c >= leftTop.c &&
                    rb.r <= rightBottom.r &&
                    rb.c <= rightBottom.c;
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution(new BufferedReader(new InputStreamReader(System.in))).run();
    }
}
