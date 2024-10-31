import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);

        File dir = null;
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return; 
        }
        File[] files = dir.listFiles();

        if(files == null) {
            return;
        }

        for(int i = 0 ; i<files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            reader.lines()
                    .forEach(System.out::println);
            reader.reset();

            System.out.println("answer ");
            new Solution(reader).run();
            reader.close();
        }
    }

    static void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        testRun(17404);
//        run();
    }
}

class Solution {

    private int N;

    private int[][] costs;

    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(!(obj instanceof Pair)) return false;
            Pair p = (Pair)obj;
            return x == p.x && y == p.y;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        String[] line;

        costs = new int[N][3];

        for(int i = 0 ; i < N ; i++) {
            line = reader.readLine().split(" ");

            for(int j = 0 ; j < 3 ; j++) {
                costs[i][j] = Integer.parseInt(line[j]);
            }
        }
    }

    public void run() {
        Set<Pair> combs = new HashSet<>();
        combs.add(new Pair(0, 1));
        combs.add(new Pair(1, 0));
        combs.add(new Pair(1, 2));
        combs.add(new Pair(2, 1));
        combs.add(new Pair(0, 2));
        combs.add(new Pair(2, 0));
        int answer = Integer.MAX_VALUE;

        for(Pair p : combs) {
            int[][] dp = new int[N][3]; // i번째에 칠한 색이 j인 경우 최소 비용

            for(int[] row : dp) {
                Arrays.fill(row, 100_000_000);
            }

            dp[0][p.x] = costs[0][p.x];

            for(int i = 1 ; i < N - 1; i++) {
                dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i][0];
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i][1];
                dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i][2];
            }
            dp[N - 1][p.y] = Math.min(dp[N - 2][p.x], dp[N - 2][3 - p.x - p.y]) + costs[N - 1][p.y];
            answer = Math.min(answer, dp[N-1][p.y]);
        }

        System.out.println(answer);
    }
}
