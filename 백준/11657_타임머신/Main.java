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
        testRun(11657);
//        run();
    }
}

class Solution {

    public static class Edge {
        int from;
        int to;
        long weight;

        public Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    Edge[] edges;

    int N, M;

    long[] dists;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        edges = new Edge[M];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]) - 1;
            int to = Integer.parseInt(tokens[1]) - 1;
            long weight = Long.parseLong(tokens[2]);
            edges[i] = new Edge(from, to, weight);
        }

        dists = new long[N];
        Arrays.fill(dists, Integer.MAX_VALUE);
    }

    private boolean bellmanFord(int start) {

        dists[start] = 0;

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                Edge e = edges[j];
                int src = e.from;
                int dst = e.to;
                long dist = e.weight;

                if(dists[src] == Integer.MAX_VALUE) {
                    continue;
                }

                if(dists[dst] > dists[src] + dist) {
                    dists[dst] = dists[src] + dist;

                    if (i == N - 1) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void run() {
        boolean negativeCycle = bellmanFord(0);

        if(negativeCycle) {
            System.out.println(-1);
            return;
        }

        for(int i = 1 ; i < dists.length ; i++) {
            if(dists[i] == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(dists[i]);
            }
        }
    }
}
