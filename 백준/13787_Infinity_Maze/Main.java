import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private static final char[] dirs = {'N', 'E', 'S', 'W'};

    private static final int[] dr = {-1, 0, 1, 0}; // North, East, South, West

    private static final int[] dc = {0, 1, 0, -1}; // North, East, South, West

    private Robot robot;

    private long[][][] dists;

    private boolean[][][] visited;

    private static int R = -1, C = -1;

    private long fuel;

    private Map<Character, Integer> directionMap = new HashMap<>();
    {
        directionMap.put('N', 0);
        directionMap.put('E', 1);
        directionMap.put('S', 2);
        directionMap.put('W', 3);
    }

    static class Robot {
        int r, c, dir;

        public Robot(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }

        public boolean move(char[][] board) {
            int nr = r + dr[dir];
            int nc = c + dc[dir];

            if ( isInRange(nr, nc) && board[nr][nc] == '.' ) {
                r = nr;
                c = nc;
                return true; // 이동 성공
            } else {
                rotate(); // 그 외의 경우엔 회전
                return false; // 이동 실패
            }
        }

        private void rotate() {
            dir = (dir + 1) & 3; // 시계방향으로 회전
        }

        private boolean isInRange(int r, int c) {
            return r >= 0 && r < R && c >= 0 && c < C;
        }
    }


    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
        dists = new long[100][100][4];
        visited = new boolean[100][100][4];
    }

    private void setRobot(char[][] board) {
        robot = null;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if ( board[i][j] == '.' || board[i][j] == '#' ) {
                    continue;
                }
                int dir = directionMap.get(board[i][j]);
                robot = new Robot(i, j, dir);
                board[i][j] = '.';
            }
        }
    }

    private void search(char[][] board) {
        long dist = 0;
        dists[robot.r][robot.c][robot.dir] = 0;
        visited[robot.r][robot.c][robot.dir] = true;

        while ( true ) {
            if ( robot.move(board) ) dist++;

            if ( visited[robot.r][robot.c][robot.dir] ) break;

            visited[robot.r][robot.c][robot.dir] = true;
            dists[robot.r][robot.c][robot.dir] = dist;
            if ( dist == fuel ) break;
        }

        if ( dist != fuel ) {
            // 이 경우 사이클이 발생한 것이다.
            // 로봇의 시작점에서부터 사이클 발생 시작점까지의 거리
            long nonCycleDist =  dists[robot.r][robot.c][robot.dir];
            // 사이클 거리
            long cycleDist = dist - nonCycleDist;
            // 모듈러 연산을 통해 거리를 줄인다.
            fuel = (fuel - nonCycleDist) % cycleDist;

            for ( int step = 0 ; step < fuel ; ) {
                if( robot.move(board) ) {
                    //dist++;
                    step++;
                }
            }
        }

        while ( !robot.move(board) );
    }

    void reset() {
        for ( boolean[][] plane : visited ) {
            for ( boolean[] row : plane ) {
                Arrays.fill(row, false);
            }
        }

        for ( long[][] plane : dists) {
            for ( long[] row : plane ) {
                Arrays.fill(row, 0);
            }
        }
    }

    private String runTestCase(int H, int W, long L, char[][] board) {
        R = H;
        C = W;
        fuel = L;
        //System.out.println("Test Case : " + R + " " + C + " " + fuel);
        reset();
        setRobot(board);
        search(board);
        StringBuilder sb = new StringBuilder();
        sb.append(robot.r + 1).append(" ") // 1-based index
          .append(robot.c + 1).append(" ") // 1-based index
          .append(dirs[robot.dir]); // 방향 (0: North, 1: East, 2: South, 3: West)

        return sb.toString();
    }

    public void run () throws IOException {
        StringBuilder sb = new StringBuilder();

        char[][] board = new char[100][100];
        while ( true ) {
            String[] tokens = reader.readLine().split(" ");
            int H = Integer.parseInt(tokens[0]);
            int W = Integer.parseInt(tokens[1]);
            long L = Long.parseLong(tokens[2]) - 1;

            if ( H + W + L <= 0 ) {
                break;
            }
//
            for ( int i = 0 ; i < H ; i++ ) {
                board[i] = reader.readLine().toCharArray();
            }
//
            sb.append(runTestCase(H, W, L, board))
                    .append("\n");
        }

        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution(new BufferedReader(new InputStreamReader(System.in))).run();
    }
}
