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
        testRun(9177);
//        run();
    }
}

class Solution {

    private int N;

    List<String[]> targets;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        targets = new ArrayList<>();
        for(int i = 0 ; i < N ; i++) {
            targets.add(reader.readLine().split(" "));
        }
    }

    private boolean bfs(String f, String s, String goal) {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[f.length()+1][s.length()+1];
        q.add(new int[]{0, 0, 0});
        visited[0][0] = true;

        while(!q.isEmpty()) {
            int[] indices = q.poll();

            if(indices[2] >= goal.length()) {
                return true;
            }

            if(indices[0] < f.length() && f.charAt(indices[0]) == goal.charAt(indices[2]) && !visited[indices[0]+1][indices[1]]) {
                q.add(new int[]{indices[0]+1, indices[1], indices[2]+1});
                visited[indices[0]+1][indices[1]] = true;
            }

            if(indices[1] < s.length() && s.charAt(indices[1]) == goal.charAt(indices[2]) && !visited[indices[0]][indices[1]+1]) {
                q.add(new int[]{indices[0], indices[1]+1, indices[2]+1});
                visited[indices[0]][indices[1]+1] = true;
            }
        }

        return false;
    }

    private void solve(int idx, String[] words) {
        String first = words[0];
        String second = words[1];
        String third = words[2];

        boolean result = bfs(first, second, third);

        if(result) {
            System.out.println("Data set " + idx + ": yes");
        } else {
            System.out.println("Data set " + idx + ": no");
        }
    }

    public void run() {
        for(int i = 0 ; i < targets.size() ; i++) {
            solve(i+1, targets.get(i));
        }
    }
}
