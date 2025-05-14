import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class Main {

    private static boolean isDebug = false;
    //private static boolean isDebug = true;

    public static void main(String[] args) throws IOException {
        if ( isDebug ) {
            System.out.println("Debug mode");
            new OfflineSolutionRunner().run();
        } else {
            new OnlineSolutionRunner().run();
        }
    }
}

class Solution {

    private int N, M;
    private List<Road>[] graph;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        graph = new List[N + 1];
        for ( int i = 1 ; i <= N ; ++i ) {
            graph[i] = new ArrayList<>();
        }

        for ( int i = 0 ; i < M ; ++i ) {
            tokens = reader.readLine().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            int cost = Integer.parseInt(tokens[2]);
            graph[u].add(new Road(v, cost));
            graph[v].add(new Road(u, cost));
        }
    }

    private int simulate() {
        // dists[i][0] => i를 짝수번의 도로를 거쳐 도달할 때 최소 거리
        // dists[i][1] => i를 홀수번의 도로를 거쳐 도달할 때 최소 거리
        int[][] dists = new int[N+1][2];
        for ( int[] row : dists ) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<Item> pq = new PriorityQueue<>();
        pq.add(new Item(1, 0, 0));
        dists[1][0] = 0;

        while ( !pq.isEmpty() ) {
            Item cur = pq.poll();

            if ( cur.cost > dists[cur.to][cur.cnt % 2] ) {
                continue;
            }

            for ( Road road : graph[cur.to] ) {
                int nextCost = cur.cost + road.cost;
                int nextCnt = cur.cnt + 1;
                if ( dists[road.to][nextCnt % 2] == 0 || dists[road.to][nextCnt % 2] > nextCost ) {
                    dists[road.to][nextCnt % 2] = nextCost;
                    pq.add(new Item(road.to, nextCost, nextCnt));
                }
            }
        }

        return dists[N][0] == Integer.MAX_VALUE ? -1 : dists[N][0];
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        int answer = simulate();
        sb.append(answer);
        System.out.println(sb.toString());
    }

    static class Road {

        int to, cost;

        public Road(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class Item extends Road implements Comparable<Item> {
        int cnt;

        public Item(int to, int cost, int cnt) {
            super(to, cost);
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Item o) {
            return this.cost - o.cost;
        }
    }
}

class OfflineSolutionRunner extends SolutionRunner {

    @Override
    protected Map<String, BufferedReader> getBufferedReaders() {
        // resource 밑의 모든 txt파일을 읽는다.
        Path base = Paths.get("");
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/";
        //txt
        File dir = null;
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return Map.of();
        }
        File[] files = dir.listFiles();
        if(files == null) {
            return Map.of();
        }
        Map<String, BufferedReader> readers = new HashMap<>();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    readers.put(file.getName(), reader);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return readers;
    }

    @Override
    protected BufferedReader preprocess(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        String contents = sb.toString();

        System.out.println(contents);
        System.out.println("Output : ");
        // String을 다시 reader로
        return new BufferedReader(new StringReader(contents));
    }
}

class OnlineSolutionRunner extends SolutionRunner {

    @Override
    protected Map<String, BufferedReader> getBufferedReaders() {
        return Map.of("stdin", new BufferedReader(new InputStreamReader(System.in)));
    }

    @Override
    protected BufferedReader preprocess(BufferedReader reader) {
        return reader;
    }
}

abstract class SolutionRunner {

    public final void run() throws IOException {
        Map<String, BufferedReader> readers = getBufferedReaders();

        for (String key : readers.keySet()) {
            if ( !key.equals("stdin")) {
                System.out.println("Test case file: " + key);
            }
            BufferedReader reader = readers.get(key);
            BufferedReader br = preprocess(reader);
            new Solution(br).run();
        }
    }

    protected abstract Map<String, BufferedReader> getBufferedReaders();
    protected abstract BufferedReader preprocess(BufferedReader reader) throws IOException;
}

