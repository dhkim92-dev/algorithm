import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");
        System.out.println("base path : " + base.toAbsolutePath());
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);
        File dir = new File(testFileDirName);
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
        testRun(2176);
        // run();
    }
}

class Solution {

    public static class Edge {
        int to;

        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    int nrVertex;

    int nrEdge;

    List<List<Edge>> adj = new ArrayList<>();
        
    public Solution(BufferedReader reader) throws IOException {
        String[] line =reader.readLine().split(" ");
        nrVertex = Integer.parseInt(line[0]);
        nrEdge = Integer.parseInt(line[1]);

        adj = new ArrayList<>(nrVertex+1);
        for(int i = 0 ; i <= nrVertex ; i++){
            adj.add(new ArrayList<>());
        }

        int from, to, weight;

        for(int i = 0 ; i < nrEdge ; i++) {
            line = reader.readLine().split(" ");
            from = Integer.parseInt(line[0]);
            to = Integer.parseInt(line[1]);
            weight = Integer.parseInt(line[2]);
            adj.get(from).add(new Edge(to, weight));
            adj.get(to).add(new Edge(from, weight));
        }
    }

    public int[] dijik(int from) {
        int[] dists = new int[nrVertex + 1]; // from에서 [i]로 가는 최소 거리
        boolean[] visited = new boolean[nrVertex+1];

        Arrays.fill(dists, Integer.MAX_VALUE);
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight-b.weight);

        pq.add(new Edge(from, 0));
        dists[from] = 0;

        while(!pq.isEmpty()) {
            Edge cur = pq.poll();

            if(cur.weight > dists[cur.to]) continue;
            if(visited[cur.to]) continue;
            visited[cur.to] = true;
            
            List<Edge> edges = adj.get(cur.to);

            for(int i = 0 ; i < edges.size() ; i++) {
                Edge nxt = edges.get(i);
                int nxtDist = dists[cur.to] + nxt.weight;
                
                if(nxtDist > dists[nxt.to]) continue;
                dists[nxt.to] = nxtDist;
                pq.add(new Edge(nxt.to, nxtDist));
            }
        }

        return dists;
    }

    public void run() {
        int answer = 0;

        // 끝점에서 모든 점으로 가는 거리를 구한다.
        int[] minDists = dijik(2);
        Integer[] indices = IntStream.rangeClosed(0, nrVertex)
                            .boxed()
                            .toArray(Integer[]::new);

        Arrays.sort(indices, (a, b)->minDists[a]-minDists[b]);
        
        int[] memo = new int[nrVertex+1];
        memo[2] = 1;
        // 거리가 가장 짧은 노드부터 탐색한다.

        for(int i = 0 ; i < nrVertex ; i++) {
            if(indices[i] == 2 || indices[i] == 0) continue;
            int cur = indices[i];
            List<Edge> edges = adj.get(indices[i]);

            for(Edge edge : edges) {
                if(minDists[edge.to] >= minDists[cur]) continue;
                memo[cur] += memo[edge.to];
            }
        }


        System.out.println(memo[1]);
    }
}
