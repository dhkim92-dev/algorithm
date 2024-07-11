import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
        testRun(14002);
        // run();
    }
}

class Solution {

    int N;

    Integer[] arr ;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        arr = new Integer[N];
        String[] line = reader.readLine().split(" ");
        arr[0] = 0;
        for(int i = 0 ; i < N ; i++) {
            arr[i] = Integer.parseInt(line[i]);
        }
    }


    public void run() {
        Stack<Integer> trace = new Stack<>();
        int answer = 0;
        int[] dp = new int[N];
        Arrays.fill(dp, 0);
        dp[0] = 1;

        for(int i = 0 ; i < N ; i++) {
            dp[i] = 1;
            for(int j = 0 ; j < i ; j++) {
                if(arr[i] > arr[j]) {
                    if(dp[i] <= dp[j]) {
                        dp[i] = dp[j] + 1;
                    }
                }else if(arr[i].equals(arr[j])) {
                    dp[i] = dp[j];
                }
            }
            answer = Math.max(answer, dp[i]);
        }

        System.out.println(answer);

        for(int i = N-1 ; i >= 0 ; i--) {
            if(dp[i] == answer) {
                trace.push(arr[i]);
                answer--;
            }
        }

        StringBuilder sb = new StringBuilder();

        while(!trace.isEmpty()) {
            sb.append(trace.pop() + " ");
        }
        System.out.println(sb.toString());
    }
}
