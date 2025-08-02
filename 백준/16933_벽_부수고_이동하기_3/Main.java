

import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M, K;

    private char[][] board;

    private int[][] visited;

    private static final char EMPTY = '0';

    private static final char WALL = '1';

    private static final Pos[] dirs = {
        new Pos(0, 1),   // 우
        new Pos(-1, 0),
        new Pos(0, -1), // 좌
        new Pos(1, 0),  // 하
    };

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        this.N = Integer.parseInt(tokens[0]);
        this.M = Integer.parseInt(tokens[1]);
        this.K = Integer.parseInt(tokens[2]);
        this.board = new char[N][M];
        this.visited = new int[N][M];// 0: 낮, 1: 밤

        for ( int i = 0 ; i < N ; ++i ) {
            board[i] = reader.readLine().toCharArray();
        }

    }

    boolean isBounded(Pos p) {
        return p.r >= 0 && p.r < N && p.c >= 0 && p.c < M;
    }

    int bfs() {
        Queue<Trace> q = new LinkedList<>();
        q.add( new Trace(new Pos(0, 0), 1, 0));

        for(int[] row : visited) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        visited[0][0] = 0;

        while ( !q.isEmpty() ) {
            Trace cur = q.poll();
//            System.out.println("Current Position: " + cur.pos.r + ", " + cur.pos.c + " | Distance: " + cur.dist + " | Break Count: " + cur.breakCount);

            if ( cur.pos.r == N - 1 && cur.pos.c == M - 1 ) {
                return cur.dist;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                Pos nxt = cur.pos.add(dirs[i]);
//                System.out.println("    Next Position: " + nxt.r + ", " + nxt.c);
                if ( !isBounded(nxt) ) continue;
                if ( board[nxt.r][nxt.c] == WALL ) {
                    if ( cur.breakCount >= K ) continue;
                    if ( cur.dist % 2 == 0 ) {
//                        System.out.println(" 밤 ");
                        // 밤, 하루 대기해야 하므로 현재 상태에서 이동 거리 1 추가
                        q.add(new Trace(cur.pos, cur.dist+1, cur.breakCount));
//                        System.out.println("    Wall encountered at night, waiting for day: " + cur.dist + 1);
                    } else {
//                        System.out.println(" 낮 ");
                        if ( visited[nxt.r][nxt.c] <= cur.breakCount + 1)  continue;
                            // 낮, 벽을 부수고 이동
                        visited[nxt.r][nxt.c] = cur.breakCount + 1;
                        q.add(new Trace(nxt, cur.dist + 1, cur.breakCount + 1));
//                            System.out.println("    Wall encountered at day, breaking wall: " + (cur.breakCount + 1));
                    }
                } else {
                    if ( visited[nxt.r][nxt.c] <= cur.breakCount ) continue;
                    visited[nxt.r][nxt.c] = cur.breakCount;
                    q.add(new Trace(nxt, cur.dist + 1, cur.breakCount));
//                    System.out.println("    Empty space, moving to: " + nxt.r + ", " + nxt.c + " | Distance: " + (cur.dist + 1) + " | Break Count: " + cur.breakCount);
                }
            }
        }
        return -1;
    }

    public void run() throws IOException {
        // N = 1000, M = 1000
        // N * M = 1,000,000 x 4 BFS도 충분히 고려할만한 수치
        // 메모리 제한 512, 각 BFS 큐 엘리먼트 당 데이터 크기 = 20바이트, 20 * 4 1MB = 80MB 추정

        int minDist = bfs();
        writer.write(String.valueOf(minDist));
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

    private static class Trace {

        Pos pos;

        int dist;

        int breakCount;

        Trace(Pos pos, int dist, int breakCount) {
            this.pos = pos;
            this.dist = dist;
            this.breakCount = breakCount;
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

