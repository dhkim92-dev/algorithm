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
        //testRun(2410);
        run();
    }
}

class Solution {
    
    int n = 0;
    
    public Solution(BufferedReader reader) throws IOException {
        n = Integer.parseInt(reader.readLine());    
    }

    public void run() {
        int answer = 0;
        int[] dp = new int[n+10];
        int mod = 1000000000;
        // n 을 멱수의 합으로 나타내는 방법의 집합 F[n]
        dp[0] = 1;
        dp[1] = 1; // 2^0
        // dp[2] = 2; // 2^0 + 2^0 , 2^1
        // dp[3] = 2; // [[...x, 2^0] for x in F[2]]
        //dp[4] = 3; // [[...x, 2^0] for x in F[3] U [...x, 2^1] for x in F[2]] U [2^2]
        //dp[5] = 3; // 홀수일 땐 이전 집합 원소에 2^0 을 추가.
        //dp[6] = 4; // dp[5] + dp[4]에 2^1 추가
        //...

        for(int i = 2 ; i <= n ; i++) {
            if( (i % 2) == 1 ) {
                dp[i] = dp[i-1];
            } else {
                dp[i] = (dp[i-1] + dp[i/2])%mod;
            }
        }

        answer = dp[n];


        System.out.println(answer);
    }
}
