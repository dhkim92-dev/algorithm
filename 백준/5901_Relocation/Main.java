import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class Main {

    //private static boolean isDebug = false;
    private static boolean isDebug = true;

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

    private int N, M, K;
    private int[] markets;
    private List<Road>[] roads;
    private int answer = Integer.MAX_VALUE;
    private int[][] dists;
    PriorityQueue<Road> pq;
    Set<Integer> marketSet;

    public Solution(BufferedReader reader) throws IOException {
        // N = 도시 수
        // M = 도로 수
        // K = 시장 수
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);
        dists = new int[K+1][N + 1];
        pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.cost));

        markets = new int[K];
        for ( int i = 0 ; i < K ; ++i ) {
            markets[i] = Integer.parseInt(reader.readLine());
        }
        marketSet = new HashSet<>();
        Arrays.stream(markets).forEach(market -> marketSet.add(market));

        roads = new List[N + 1];
        for ( int i = 0 ; i <= N ; ++i ) {
            roads[i] = new ArrayList<>();
        }

        // 도로 정보
        for ( int i = 0 ; i < M ; ++i ) {
            String[] roadInfo = reader.readLine().split(" ");
            int from = Integer.parseInt(roadInfo[0]);
            int to = Integer.parseInt(roadInfo[1]);
            int cost = Integer.parseInt(roadInfo[2]);
            roads[from].add(new Road(to, cost));
            roads[to].add(new Road(from, cost));
        }
    }

    private void dajikstra(int index) {
//        System.out.println("Dajikstra " + index);
        pq.clear();
        pq.add(new Road(markets[index], 0));
        dists[index][markets[index]] = 0;

        while ( !pq.isEmpty() ) {
            Road cur = pq.poll();
//            System.out.println("   " + cur.to + " with cost " + cur.cost);
            int to = cur.to;
            int cost = cur.cost;

            if ( dists[index][to] < cost ) continue;

            for ( Road road : roads[to] ) {
                int next = road.to;
                int nextCost = cost + road.cost;
                if ( dists[index][next] < nextCost ) continue;
                dists[index][next] = nextCost;
                pq.add(new Road(next, nextCost));
            }
        }
    }

    private int calculateDistance(int[] orders) {
        // 시작점은 0으로 설정함.
        int dist = Integer.MAX_VALUE;

        // 시장이 아닌 각 도시에서 시장의 진입점과 탈출점까지의 거리 중 최소값 구하기
        for ( int i = 1 ; i <= N ; ++i ) {
            if ( marketSet.contains(i) ) continue;
            dist = Math.min( dist, dists[orders[0]][i] + dists[orders[K-1]][i] );
        }

        // 시장 순회 거리 구하기
        for ( int i = 0 ; i < K-1 ; ++i ) {
            dist += dists[orders[i]][markets[orders[i+1]]];
        }

        return dist;
    }

    /**
     * 시장을 방문하는 모든 순서를 탐색함.
     * K = 5 이므로 최대 5! = 120 가지의 경우의 수가 존재함.
     */
    private void exploreMarkets(int depth, int[] orders, int visited) {
        if ( depth == K ) {
//            Arrays.stream(orders).forEach(it -> System.out.print(it + " "));
//            System.out.println();
            answer = Math.min(answer, calculateDistance(orders));
            return;
        }

        for ( int i = 0 ; i < K ; ++i ) {
            int marketIdx = 1 << i;
            if ( (visited & marketIdx) != 0 ) continue; // 이미 방문한 시장은 제외함.
            orders[depth] = i;
            visited |= marketIdx;
            exploreMarkets(depth + 1, orders, visited);
            visited &= ~marketIdx; // 방문한 시장을 다시 방문할 수 있도록 설정함.
        }
    }

    private int simulate() {
        for ( int marketIndex = 0 ; marketIndex < markets.length ; marketIndex++ ) {
            for ( int i = 1 ; i <= N ; ++i ) {
                dists[marketIndex][i] = Integer.MAX_VALUE;
            }
            dajikstra(marketIndex);
        }

        int[] orders = new int[K];
        exploreMarkets(0, orders, 0);

        return answer;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        int answer = simulate();
        sb.append(answer);
        System.out.println(sb.toString());
    }

    static class Road implements Comparable<Road> {
        int to, cost;

        Road(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Road o) {
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

//        System.out.println(contents);
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

