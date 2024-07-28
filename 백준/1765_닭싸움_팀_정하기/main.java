import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
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
        //testRun(1765);
        run();
    }
}

class Solution {

    int N;

    int M;

    int[] root;

    int[] rank;

    List<Set<Integer>> friends;

    List<Set<Integer>> enemies;

    void union(int a, int b) {
        a = findRoot(a);
        b = findRoot(b);

        if(a == b) return;

        if(rank[a] < rank[b]) {
            root[a] = b;
        } else {
            root[b] = a;

            if(rank[a] == rank[b]) {
                rank[a]++;
            }
        }
    }

    int findRoot(int a) {
        if(root[a] == a) {
            return a;
        }

        return root[a] = findRoot(root[a]);
    }
    
    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        M = Integer.parseInt(reader.readLine());
        friends = new ArrayList<>();
        enemies = new ArrayList<>();
        root = new int[N+1];
        rank = new int[N+1];
        Arrays.fill(rank, 1);
        
        for(int i = 0 ; i <= N ; i++) {
            friends.add( new HashSet<>());
            enemies.add( new HashSet<>());
            root[i] = i;
        }

        for(int i = 0 ; i < M ; i++) {
            String[] line = reader.readLine().split(" ");
            boolean isFriendly = line[0].equals("F");
            int from = Integer.parseInt(line[1]);
            int to = Integer.parseInt(line[2]);

            if(isFriendly) {
                friends.get(from).add(to);
                friends.get(to).add(from);
            } else {
                enemies.get(from).add(to);
                enemies.get(to).add(from);
            }
        }
    }

    public void relationProcess() {

        // 친구의 친구를 모두 구한다.
        for(int i = 1 ; i <= N ; i++) {
            Set<Integer> fg = friends.get(i);

            for(int fi : fg) {
                union(i, fi);
            }       
        }

        for(int i = 1 ; i <= N ; i++) {
            Set<Integer> eg = enemies.get(i);

            for(int ei : eg) {
                Set<Integer> fg = enemies.get(ei); // 적의 적은 친구이다.

                for(int fi : fg) {
                    union(i, fi);           
                }
            }
        }
    }

    public void run() {
        relationProcess();

        Set<Integer> groupIndex = new HashSet();

        for(int i = 1 ; i < root.length ; i++) {
            groupIndex.add(root[i]);
        }

        System.out.println(groupIndex.size());
    }
}


