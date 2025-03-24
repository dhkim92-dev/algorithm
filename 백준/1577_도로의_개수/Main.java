package algorithm;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, M, K;

    static class Pos {
        public int r, c;

        public Pos(int r, int c){
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) return true;
            if(!(o instanceof Pos)) return false;
            Pos p = (Pos) o;
            return r == p.r && c == p.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    Pos[] dirs = {new Pos(0, 1), new Pos(1, 0), new Pos(0, -1), new Pos(-1, 0)};
    Map<Pos, List<Pos>> roads;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(reader.readLine());

        roads = new HashMap<>();

        for(int i  = 0 ; i < K ; i++) {
            tokens = reader.readLine().split(" ");
            Pos from = new Pos(Integer.parseInt(tokens[0])+1, Integer.parseInt(tokens[1])+1);
            Pos to = new Pos(Integer.parseInt(tokens[2])+1, Integer.parseInt(tokens[3])+1);
            roads.putIfAbsent(from, new ArrayList<>());
            roads.putIfAbsent(to, new ArrayList<>());
            roads.get(from).add(to);
            roads.get(to).add(from);
        }
    }

    private long bottomUp() {
        long[][] dp = new long[N+2][M+2];
        dp[1][1] = 1;

        for(int r = 1 ; r <= N+1 ; r++) {
            for(int c = 1 ; c <= M+1 ; c++) {
                if(r == 1 && c == 1) continue;
                Pos cur = new Pos(r, c);
                Pos left = new Pos(r, c-1);
                Pos up = new Pos(r-1, c);

                int upPossible = (!roads.containsKey(up) || !roads.get(up).contains(cur)) ? 1 : 0;
                int leftPossible = (!roads.containsKey(left) || !roads.get(left).contains(cur)) ? 1 : 0;

                dp[r][c] = dp[r-1][c] * upPossible + dp[r][c-1] * leftPossible;
            }
        }

        return dp[N+1][M+1];
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(bottomUp());
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        testRun(1577);
//        run();
    }

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

        for(int i = 0 ; i < files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            List<String> lines = reader.lines()
                    .collect(Collectors.toList());

            boolean found = true;
            for(String line : lines) {
                if(line.contains("answer")) {
                    found = true;
                }

                if(found) {
                    System.out.println(line);
                }
            }
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
}
