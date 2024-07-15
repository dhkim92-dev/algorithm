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
        testRun(1955);
        run();
    }
}

class Solution {
    
    int n;

    public Solution(BufferedReader reader) throws IOException {
        n = Integer.parseInt(reader.readLine());
    }

    public void run() {
        int answer = 0;
        int dp[] = IntStream.rangeClosed(0, n)
                            .toArray();

        // dp[i] = 수식 표현으로 i를 표현하기 위해 사용되는 1의 최소 개수

        int factIndex=2;
        int fact = 2;
        int nFact = 6;

        int[] factorial = {1, 1, 2, 6, 24, 120, 720, 5040, 40320};

        for(int i = 2 ; i <= n ; i++) {
            int sqrt = (int)Math.sqrt(i);

            for(int j = 1 ; j <= i / 2 ; j++) {
                dp[i] = Math.min(dp[i], dp[j] + dp[i-j]);
            }

            for(int j = 2 ; j<= sqrt ; j++) {
                if(i%j != 0) continue;
                dp[i] = Math.min(dp[i], dp[j] + dp[i/j]);
            }

            if(i == fact) {
                dp[i] = Math.min(dp[i], dp[factIndex]);
            }

            if(i + 1== nFact) {
                factIndex++;
                fact = nFact;
                nFact = fact * (factIndex+1);
            }
        }

        answer = dp[n];
        System.out.println(answer);
    }
}


