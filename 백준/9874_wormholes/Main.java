import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class Main {

    // private static boolean isDebug = false;
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

    static class Wormhole implements Comparable<Wormhole> {
        int r,c;
        int pairedIndex = -1;

        Wormhole(int r, int c) {
            this.r = r;
            this.c = c;
        }

        void pairing(int index) {
            this.pairedIndex = index;
        }

        @Override
        public int compareTo(Wormhole o) {
            return Integer.compare(this.r, o.r);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Wormhole wormhole = (Wormhole) obj;
            return r == wormhole.r && c == wormhole.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private int wormholeCount;
    private Wormhole[] wormHoles;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        wormholeCount = Integer.parseInt(tokens[0]);

        wormHoles = new Wormhole[wormholeCount];

        for (int i = 0; i < wormholeCount; i++) {
            tokens = reader.readLine().split(" ");
            int r = Integer.parseInt(tokens[0]);
            int c = Integer.parseInt(tokens[1]);
            wormHoles[i] = new Wormhole(r, c);
        }
        Arrays.sort(wormHoles);
    }

    private Wormhole proceed(Wormhole hole) {
        for ( int i = 0 ; i < wormHoles.length ; ++i ) {
            if ( wormHoles[i].c == hole.c && wormHoles[i].r > hole.r ) {
                return wormHoles[i];
            }
        }
        return null;
    }

    private boolean checkLoop(int from) {
        // System.out.println("checkLoop from r : " + wormHoles[from].r + " c : " + wormHoles[from].c);
        int visited = 0;
        // boolean[] visited = new boolean[wormholeCount];
        Wormhole hole = wormHoles[from];

        while (true) {
            if ( (visited & (0x01 << hole.pairedIndex)) != 0) {
            // if ( visited[hole.pairedIndex] ) {
                return true;
            }
            visited |= ( 0x01 << hole.pairedIndex );
            // visited[hole.pairedIndex] = true;
            hole = wormHoles[hole.pairedIndex];
            hole = proceed(hole);
            if ( hole == null ) {
                return false;
            }
        }
    }

    private int getPossiblePairs(int count, int index, int visited) {
        if ( count == wormholeCount ) {
            for ( int i = 0 ; i < wormholeCount ; ++i ) {
                if ( checkLoop(i) ) return 1;
            }
            // System.out.println("no loop");
            return 0;
        }

        int loopCaseCount = 0;

        for (int i = index; i < wormholeCount ; ++i ) {
            if ( (visited & (0x01 << i)) != 0 ) continue;
            visited |= (0x01 << i);
            // if ( visited[i] ) continue;
            // visited[i] = true;

            for ( int j = i + 1 ; j < wormholeCount ; ++j ) {
                if ( (visited & (0x01 << j)) != 0 ) continue;
                visited |= (0x01 << j);
                // if ( visited[j] ) continue;
                // visited[j] = true;
                wormHoles[i].pairing(j);
                wormHoles[j].pairing(i);
                loopCaseCount += getPossiblePairs(count + 2, i + 1, visited);
                // wormHoles[i].pairing(-1);
                // wormHoles[j].pairing(-1);
                // visited[j] = false;
                visited &= ~(0x01 << j);
            }
            visited &= ~(0x01 << i);
            // visited[i] = false;
        }

        return loopCaseCount;
    }

    private int simulate() {
        // Bessie Cow는 항상 delta (1, 0) 방향으로만 이동한다.
        return getPossiblePairs(0, 0, 0);
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
        reader.close();
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

        for (Map.Entry<String, BufferedReader> entry : readers.entrySet()) {
            if ( entry.getKey() != "stdin" ) {
                System.out.println("Input : " + entry.getKey());
            }

            BufferedReader br = preprocess(entry.getValue());
            new Solution(br).run();
            br.close();
        }
    }

    protected abstract Map<String, BufferedReader> getBufferedReaders();
    protected abstract BufferedReader preprocess(BufferedReader reader) throws IOException;
}

