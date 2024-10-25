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
        //testRun(1956);
        run();
    }
}

class Solution {

    int[][] graph;

    int V, E;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        V = Integer.parseInt(tokens[0]);
        E = Integer.parseInt(tokens[1]);

        graph = new int[V][V];

        for(int[] row : graph) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        for(int i = 0 ; i < E ; i++) {
            tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]) - 1;
            int to = Integer.parseInt(tokens[1]) - 1;
            int weight = Integer.parseInt(tokens[2]);

            graph[from][to] = weight;
        }
    }

    private void floydWarshall() {
        for(int k = 0 ; k < V ; k++) {
            for(int i = 0 ; i < V ; i++) {
                for(int j = 0 ; j < V ; j++) {
                    if(graph[i][k] != Integer.MAX_VALUE && graph[k][j] != Integer.MAX_VALUE) {
                        graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                    }
                }
            }
        }
    }

    void printMat() {
        System.out.println("#########################################");
        for(int i = 0 ; i < V ; i++) {
            for(int j = 0 ; j < V ; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("#########################################");
    }

    public void run() {
        floydWarshall();
        //printMat();
        int min = Integer.MAX_VALUE;
        for(int i = 0 ; i < V ; i++) {
            min = Math.min(min, graph[i][i]);
        }
        
        if(min == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(min);
        }
    }
}
