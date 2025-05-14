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

    private int N, M;
    private List<Integer>[] graph;
    private int[] colorCount = new int[]{0, 0};
    private int[] colors;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        graph = new ArrayList[N + 1];
        colors = new int[N + 1];
        Arrays.fill(colors, -1);

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            tokens = reader.readLine().split(" ");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            graph[u].add(v);
            graph[v].add(u);
        }
    }

    public boolean dfs(int nodeIdx, int color) {
        if ( colors[nodeIdx] != -1 ) {
            return colors[nodeIdx] == color;
        }
        colors[nodeIdx] = color;
        colorCount[color]++;

        for ( int nextNode : graph[nodeIdx] ) {
            if ( !dfs(nextNode, 1 - color) ) {
                return false;
            }
        }

        return true;
    }

    private int simulate() {
        int answer = 0;

        for ( int i = 1 ; i <= N ; ++i ) {
            if ( colors[i] != -1 ) continue;
            colorCount[0] = 0;
            colorCount[1] = 0;
            if ( !dfs(i, 0) ) {
                answer = -1;
                break;
            }
            answer += Math.max(colorCount[0], colorCount[1]);
        }

        return answer;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        int answer = simulate();
        sb.append(answer);
        System.out.println(sb.toString());
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
        Map<String, BufferedReader> readers = new HashMap<>();
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return readers;
        }
        File[] files = dir.listFiles();
        if(files == null) {
            return readers;
        }

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

        //System.out.println(contents);
        System.out.println("Output : ");
        // String을 다시 reader로
        return new BufferedReader(new StringReader(contents));
    }
}

class OnlineSolutionRunner extends SolutionRunner {

    @Override
    protected Map<String, BufferedReader> getBufferedReaders() {
        Map<String, BufferedReader> readers = new HashMap<>();
        readers.put("stdin", new BufferedReader(new InputStreamReader(System.in)));
        return readers;
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

