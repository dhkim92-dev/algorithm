import java.io.*;
import java.util.*;

class Solution {

    private final Pos[] dirs = {
        new Pos(-1, 0), // Up
        new Pos(0, 1),  // Right
        new Pos(1, 0),  // Down
        new Pos(0, -1)  // Left
    };

    private int N;

    Pos[] cows;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine().trim());
        cows = new Pos[N];

        String[] tokens;

        for ( int i = 0 ; i < N ; ++i ) {
            tokens = reader.readLine().trim().split(" ");
            int r = Integer.parseInt(tokens[0]) - 1;
            int c = Integer.parseInt(tokens[1]) - 1;
            cows[i] = new Pos(r, c);
        }
    }

    private int[][] prefixSum2D(int[][] input) {
        int[][] psum = new int[input.length+1][input[0].length+1];

        for (int r = 1; r <= input.length; r++) {
            for (int c = 1; c <= input[0].length; c++) {
                psum[r][c] = input[r-1][c-1] + psum[r-1][c] + psum[r][c-1] - psum[r-1][c-1];
            }
        }

        return psum;
    }

    private void compress(Pos[] targets, Set<Integer> coords, boolean isColumn) {
        List<Integer> sortedCoords = new ArrayList<>(coords);
        Collections.sort(sortedCoords);
        Map<Integer, Integer> coordMap = new HashMap<>();

        for (int i = 0; i < sortedCoords.size(); i++) {
            coordMap.put(sortedCoords.get(i), i);
        }

        for (Pos target : targets) {
            if (isColumn) {
                target.c = coordMap.get(target.c);
            } else {
                target.r = coordMap.get(target.r);
            }
        }
    }

    private void compress() {
        // r 기준 좌표 정렬
        Set<Integer> rCoords = new HashSet<>();
        Set<Integer> cCoords = new HashSet<>();

        for ( Pos cow : cows ) {
            rCoords.add(cow.r);
        }

        compress(cows, rCoords, false);

        for ( Pos cow : cows ) {
            cCoords.add(cow.c);
        }
        compress(cows, cCoords, true);
    }

    private int split(int r, int c, int[][] psum) {
        int leftTop = psum[r][c] - psum[0][c] - psum[r][0] + psum[0][0];
        int rightTop = psum[r][psum[0].length - 1] - psum[r][c] - psum[0][psum[0].length - 1] + psum[0][c];
        int leftBottom = psum[psum.length - 1][c] - psum[r][c] - psum[psum.length - 1][0] + psum[r][0];
        int rightBottom = psum[psum.length - 1][psum[0].length - 1] - psum[psum.length - 1][c] - psum[r][psum[0].length - 1] + psum[r][c];
        int maxArea = Math.max(Math.max(leftTop, rightTop), Math.max(leftBottom, rightBottom));
        return maxArea;
    }

    private void print(int[][] grid) {
        for (int[] row : grid) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    private int simulate() {
        compress();
        int maxR = Arrays.stream(cows).mapToInt(c -> c.r).max().orElse(0);
        int maxC = Arrays.stream(cows).mapToInt(c -> c.c).max().orElse(0);
        int[][] grid = new int[maxR+1][maxC+1];
        for ( Pos cow : cows ) {
            grid[cow.r][cow.c] = 1;
        }

        int[][] psum = prefixSum2D(grid);
        int answer = Integer.MAX_VALUE;

        for ( int r = 0 ; r <= maxR  ; ++r ) {
            for ( int c = 0 ; c <= maxC ; ++c ) {
                answer = Math.min(answer, split(r, c, psum));
            }
        }

        return answer;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
                .append("\n");

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pos)) return false;
            Pos pos = (Pos) o;
            return r == pos.r && c == pos.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
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
