import java.io.*;
import java.util.*;

class Solution {

    static class Node {
        int r, c;
        List<Node> childs;

        Node(int r, int c) {
            this.r = r;
            this.c = c;
            childs = new ArrayList<>();
        }

        void addChild(Node child) {
            childs.add(child);
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            Node node = (Node) o;
            return r == node.r && c == node.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private final BufferedReader reader;

    private final int[] dr = {-1, 0, 1, 0};

    private final int[] dc = {0, 1, 0, -1};

    private final char[][] board;

    private final boolean[][] visited;

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
        board = new char[1000][1000];
        visited = new boolean[1000][1000];
    }

    private boolean isInRange(int r, int c, int[] size) {
        return r >= 0 && r < size[0] && c >= 0 && c < size[1];
    }

    private int[] bfs(int[] start, boolean[][] visited, int[] size, int[] goal) {
        Queue<int[]> q = new ArrayDeque<>();
        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }
        q.offer(new int[]{start[0], start[1], 0});
        visited[start[0]][start[1]] = true;

        int[] farthest = new int[]{start[0], start[1], 0}; // r, c, distance

        while ( !q.isEmpty() ) {
            int[] current = q.poll();
            int r = current[0];
            int c = current[1];
            int distance = current[2];

            if ( goal != null && r == goal[0] && c == goal[1] ) {
                return new int[]{r, c, distance};
            }

            if ( distance > farthest[2] ) {
                farthest[0] = r;
                farthest[1] = c;
                farthest[2] = distance;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if ( !isInRange(nr, nc, size) ||
                     visited[nr][nc] ||
                    board[nr][nc] == '#' ) {
                    continue;
                }
                visited[nr][nc] = true;
                q.offer(new int[]{nr, nc, distance + 1});
            }
        }

        return farthest;
    }

    private int getMaxRope() throws IOException {
        int[] size = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int tmp = size[0];
        size[0] = size[1];
        size[1] = tmp;
        int[] start = null;

        for ( int i = 0 ; i < size[0] ; ++i ) {
            String line = reader.readLine();
            for ( int j = 0 ; j < size[1] ; ++j ) {
                char ch = line.charAt(j);
                board[i][j] = ch;
                if ( start == null && ch == '.' ) {
                    start = new int[]{i, j};
                }
            }
        }

        int[] farFromRoot = bfs(start, visited, size, null);
        int[] farFromFar = bfs(farFromRoot, visited, size, null);
//        return Math.abs(farFromRoot[2] - farFromFar[2]);

        return bfs(farFromRoot, visited, size, farFromFar)[2];
    }

    private String simulate() throws IOException {
        StringBuilder sb = new StringBuilder();
        int nrTestCase = Integer.parseInt(reader.readLine());
        for ( int i = 0 ; i < nrTestCase ; ++i ) {
            int maxRope = getMaxRope();
            sb.append("Maximum rope length is ")
              .append(maxRope)
              .append(".\n");
        }
        return sb.toString();
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }

    static class Pair<T, U> {
        public T first;
        public U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
