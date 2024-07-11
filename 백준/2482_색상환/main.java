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
        testRun(2482);
        // run();
    }
}

class Solution {

    int N, K;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        K = Integer.parseInt(reader.readLine());
    }

    void initMemoization(int[][] dp) {
        for(int i = 0 ; i <= N ; i++) {
            dp[i][0] = 1;
            dp[i][1] = i;
        }
    }

    public void run() {
        int answer = 0;
        int mod = 1000000003;

        if((N/2) < K) {
            System.out.println(0);
            return;
        }     

        if(K == 1) {
            System.out.println(N);
            return;
        }

        int[][] dp = new int[N+1][K+1];
        initMemoization(dp);

        for(int i = 2 ; i <= N ; i++) {
            for(int j = 1 ; j <= K ; j++) {
                dp[i][j] = (dp[i-2][j-1] + dp[i-1][j])%mod;
            }
        }

        // 첫 색상을 포함시키는 경우 N번째 색상은 포함시킬 수 없다.
        System.out.println((dp[N-3][K-1] + dp[N-1][K]) % mod);
    }
}
