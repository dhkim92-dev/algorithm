import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private final int[][] grid;

    private final boolean[][] visited;

    private static final int R = 6;

    private static final int C = 6;

    private static final int[] dr = {-1, 0, 1, 0}; // N E S W

    private static final int[] dc = {0, 1, 0, -1};

    private int r, c;

    /**
     *   5 // 윗면
     *   3
     * 4 0 2  // 0 -> 아랫면
     *   1
     */

    private final boolean[] curDice; // 현재 주사위의 각 면을 나타내는 인덱스

    private boolean[] tmp = new boolean[6];

    private static final int[][] index =  {
            // 주사위를 동서남북으로 돌리는 경우 매핑
            { 3, 0, 2, 5, 4, 1 }, // 북쪽으로 굴리는 경우
            { 2, 1, 5, 3, 0, 4 }, // 동쪽으로 굴리는 경우
            { 1, 5, 2, 0, 4, 3 }, // 남쪽으로 굴리는 경우
            { 4, 1, 0, 3, 5, 2 }  // 서쪽으로 굴리는 경우
    };

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
        this.visited = new boolean[6][6];
        this.grid = new int[R][C];
        this.r = -1;
        this.c = -1;
        this.curDice = new boolean[6];
    }

    private void init() throws IOException {
        Arrays.fill(curDice, false);
        r=-1;
        c=-1;
        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }
        for ( int[] row : grid ) {
            Arrays.fill(row, 0);
        }
        for (int i = 0; i < 6; ++i) {
            String[] line = reader.readLine().split(" ");
            for ( int j = 0 ; j < 6 ; ++j ) {
                grid[i][j] = Integer.parseInt(line[j]);
                if ( r == -1 && grid[i][j] == 1 ) {
                    r = i;
                    c = j;
                }
            }
        }
    }

    private void printDice() {
        System.out.print("\t" + Arrays.toString(curDice));
        System.out.println();
    }

    private void rotate(int dir) {
//        System.out.println("\t\trotate dir : " + dir);
        tmp[0] = curDice[index[dir][0]];
        tmp[1] = curDice[index[dir][1]];
        tmp[2] = curDice[index[dir][2]];
        tmp[3] = curDice[index[dir][3]];
        tmp[4] = curDice[index[dir][4]];
        tmp[5] = curDice[index[dir][5]];

        for ( int i = 0 ; i<6 ; ++i ) {
            curDice[i] = tmp[i];
        }
    }

    private boolean folding(int r, int c, int depth) {
//        System.out.println("cur r : " + r + ", c : " + c + ", depth : " + depth);
//        printDice();
        if ( curDice[0] == true ) {
            return false;
        }

//        System.out.println("\t after marking");
        curDice[0] = true;
//        printDice();

        if( depth == 6 ) {
            for ( int i = 0 ; i < 6 ; ++i ) {
                if ( !curDice[i] ) {
                    return false; // 모든 면이 사용되지 않았으면 false
                }
            }
            return true;
        }




        for ( int i = 0 ; i < 4 ; ++i) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if ( nr < 0 || nr >= R
                || nc < 0 || nc >= C
                || visited[nr][nc]
            ) {
                continue; // 범위를 벗어나거나 이미 방문한 곳은 무시
            }
            if ( grid[nr][nc] == 0 ) {
                continue;
            }

//            System.out.println("\t\tnext r : " + nr + ", c : " + nc + ", dir : " + i);
            rotate(i); // 주사위를 회전
            visited[nr][nc] = true; // 다음 위치 방문 표시
            boolean result = folding(nr, nc, depth + 1);
//            visited[nr][nc] = false; // 백트래킹: 방문 표시 해제
//            System.out.println("\t\tbacktrack r : " + nr + ", c : " + nc + ", dir : " + i);
            rotate((i + 2) % 4 ); // 주사위 원래 상태로 되돌리기
//            printDice(); // 현재 주사위 상태 출력

            if ( !result ) {
                return false;
            }
        }

        return true;
    }

    private boolean matching() {
        visited[r][c] = true;
//        System.out.println("start r : " + r + ", c : " + c);

//        for ( int i = 0 ; i < 6 ; ++i ) {
//            for(int j = 0 ; j < 6 ; ++j ) {
//                System.out.print(grid[i][j] + " ");
//            }
//            System.out.println();
//        }

        return folding(r, c, 1);
    }

    private String simulate() throws IOException {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0 ; i < 3 ; ++i ){
            init();
            sb.append(matching() ? "yes" : "no")
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
