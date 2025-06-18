package algorithm;

import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private int N, M;

    private char[][] grid;

    private int[] dr = {0, 1, 0, -1};

    private int[] dc = {1, 0, -1, 0};

    private static final char EMPTY = '.';

    private static final char TREE = '+';

    private static final char SHELTER = 'J';

    private static final char WOLF = 'V';

    private int[] wolfPos;

    private int[][] distFromTree;

    private boolean[][] visited;

    private List<int[]> trees;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        grid = new char[N][M];
        wolfPos = new int[2];
        trees = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = reader.readLine();
            for (int j = 0; j < M; j++) {
                grid[i][j] = line.charAt(j);

                if ( grid[i][j] == WOLF ) {
                    wolfPos[0] = i; // row
                    wolfPos[1] = j; // column
                } else if ( grid[i][j] == TREE ) {
                    trees.add(new int[]{i, j});
                }
            }
        }

        visited = new boolean[N][M];
        distFromTree = new int[N][M];
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }

    private void calculateDistanceFromTrees() {
        Queue<int[]> q = new ArrayDeque();

        for (int[] tree : trees) {
            q.add(new int[]{tree[0], tree[1], 0});
            visited[tree[0]][tree[1]] = true;
        }

        while ( !q.isEmpty() ) {
            int[] cur = q.poll();

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if ( isInRange(nr, nc) && !visited[nr][nc] ) {
                    visited[nr][nc] = true;
                    distFromTree[nr][nc] = cur[2] + 1;
                    q.add(new int[]{nr, nc, cur[2] + 1});
                }
            }
        }
    }

    private void printDistanceFromTrees() {
        System.out.println("Distance from trees:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(distFromTree[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void simulate() throws IOException { 
        calculateDistanceFromTrees();

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[2] - a[2]);

        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }

        // printDistanceFromTrees();
        pq.add(new int[]{wolfPos[0], wolfPos[1], distFromTree[wolfPos[0]][wolfPos[1]]});
        visited[wolfPos[0]][wolfPos[1]] = true;
        int answer = Integer.MAX_VALUE;

        while ( !pq.isEmpty() ) {
            int[] cur = pq.poll();
            // System.out.println("Current position: " + cur[0] + ", " + cur[1] + ", Distance: " + cur[2]);

            answer = Math.min(answer, cur[2]);

            if ( grid[cur[0]][cur[1]] == SHELTER ) {
                break;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if ( isInRange(nr, nc) && !visited[nr][nc] ) {
                    visited[nr][nc] = true;
                    // System.out.println("Adding to PQ: " + nr + ", " + nc + ", " + distFromTree[nr][nc]);
                    pq.add(new int[]{nr, nc, distFromTree[nr][nc]});
                }
            }
        }
        writer.write(String.valueOf(answer) + "\n");
    }

    public void run() throws IOException {
        simulate();
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
