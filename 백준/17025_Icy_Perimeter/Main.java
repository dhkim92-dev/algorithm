import java.io.*;
import java.util.*;

class Solution {

    private int N;

    private char[][] board;

    private int[][] visited;

    private static final int[] dr = {-1, 0, 1, 0};

    private static final int[] dc = {0, 1, 0, -1};

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        board = new char[N][];

        for (int r = 0; r < N; ++r) {
            board[r] = reader.readLine()
                    .toCharArray();
        }
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private int[] search(int sr, int sc, int index) {
        Queue<int[] > q = new LinkedList();
        q.offer(new int[]{sr, sc, index});
        visited[sr][sc] = index;
        int areaSize = 0;
        int perimeter = 0;

        while ( !q.isEmpty() ) {
            int[] cur = q.poll();
            int curR = cur[0];
            int curC = cur[1];
            int curIndex = cur[2];

            areaSize++;

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nextR = curR + dr[i];
                int nextC = curC + dc[i];

                if ( !isInRange(nextR, nextC) || board[nextR][nextC] == '.' ) continue;
                if ( board[nextR][nextC] == '#' && visited[nextR][nextC] != 0 ) continue;

                visited[nextR][nextC] = curIndex;
                q.offer(new int[]{nextR, nextC, curIndex});
            }
        }

        q.clear();

        q.add(new int[]{sr, sc, -index});
        visited[sr][sc] = -index; // 방문 표시

//        System.out.println("Start BFS for perimeter from (" + sr + ", " + sc + ") with index " + index);
        while ( !q.isEmpty() ) {
            int[] cur = q.poll();
            int curR = cur[0];
            int curC = cur[1];

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nextR = curR + dr[i];
                int nextC = curC + dc[i];
                boolean valid = isInRange(nextR, nextC);
                if ( valid && board[nextR][nextC] == '.' ) {
//                    System.out.println("from (" + curR + ", " + curC + ") to (" + nextR + ", " + nextC + ")");
                    perimeter++;
                } else if ( !valid ) {
//                    System.out.println("from (" + curR + ", " + curC + ") to (" + nextR + ", " + nextC + ")");
                    perimeter++;
                }
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nextR = curR + dr[i];
                int nextC = curC + dc[i];

                if ( !isInRange(nextR, nextC) || board[nextR][nextC] == '.' ) continue;
                if ( visited[nextR][nextC] == -index ) continue;

                q.offer(new int[]{nextR, nextC, -index});
                visited[nextR][nextC] = -index; // 방문 표시
            }
        }

        return new int[] { index, areaSize, perimeter };
    }

    private List<int[]> findBlocks() {
        visited = new int[N][N];
        List<int[]> blocks = new ArrayList<>();

        int index = 1;
        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                if ( board[r][c] == '#' && visited[r][c] == 0 ) {
                    int[] result = search(r, c, index);
                    blocks.add(result);
                    index++;
                }
            }
        }

        return blocks;
    }

    private void printBlocks() {

        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                System.out.print(visited[r][c] + " ");
            }
            System.out.println();
        }
    }

    private String simulate() {
        int area = 0;
        int perimeter = 0;
        List<int[]> blocks = findBlocks();

        Collections.sort(blocks, (a, b) -> {
            if( a[1] == b[1] ) {
                return a[2] - b[2]; // 둘레가 작은순
            }
            return b[1] - a[1]; // 부피가 큰순
        }); // 내림차순 정렬
        area = blocks.get(0)[1]; // 가장 큰 블록의 면적
        perimeter = blocks.get(0)[2]; // 가장 큰 블록의 둘레
        return area + " " + perimeter;
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
