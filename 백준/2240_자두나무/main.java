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
        testRun(2240);
        // run();
    }
}

class Solution {

    private int T, W;

    private Integer[] falls;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        T = Integer.parseInt(line[0]);
        W = Integer.parseInt(line[1]);
        falls = new Integer[T+1];

        for(int i =  1 ; i <= T ; i++) {
            falls[i] = Integer.parseInt(reader.readLine()) - 1;
        }
    }

    public void run() {
        int answer = 0;
        int[][] dp = new int[T+1][W+1]; // 시점 T까지 W번 움직여서 얻을 수 있는 최대 자두 개수

        for(int[] row : dp) {
            Arrays.fill(row, 0);
        }

        dp[1][0] = falls[1] == 0 ? 1 : 0;
        dp[1][1] = falls[1] == 1 ? 1 : 0;

        // 한번도 이동하지 않았을 때 얻을 수 있는 최대 값을 구한다.
        for(int i = 1 ; i <= T ; i++) {
            dp[i][0] = dp[i-1][0] + (falls[i] == 0 ? 1 : 0); 
            // System.out.println("dp[" + i + "][0]" + dp[i][0]);
        }

        for(int i = 1 ; i <= T ; i++) {
            for(int j = 1 ; j <= W ; j++) {
                // System.out.printf("i : %d j : %d\n",i ,j);
                // System.out.printf(" dp[%d][%d] = %d\n", i-1, j, dp[i-1][j]);
                // System.out.printf(" dp[%d][%d] = %d\n", i-1, j-1, dp[i-1][j-1]);
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-1]) + (j%2==falls[i] ? 1 : 0);
                // System.out.printf(" dp[%d][%d] = %d\n", i, j, dp[i][j]);
            }
        }

        for(int i = 0 ; i <= W ; i++) {
            answer = Math.max(dp[T][i], answer);
        }

        System.out.println(answer);
    }
}
