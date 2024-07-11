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
        testRun(1915);
        // run();
    }
}

class Solution {
    
    int n, m;

    int[][] board;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        m = Integer.parseInt(line[1]);
        board = new int[n][m];

        for(int i = 0 ; i < n ; i++) {
            String row = reader.readLine();

            for(int j = 0 ; j < row.length() ; j++) {
                board[i][j] = row.charAt(j) - '0';
            }
        }
    }

    public void run() {
        int[][] dp = new int[n+1][m+1];
        int answer = 0;
        for(int i = 1 ; i <= n ; i++) {
            for(int j = 1 ; j <= m ; j++) {
                if(board[i-1][j-1] == 0) {
                    dp[i][j] = 0;
                    continue;
                }
                dp[i][j] = Math.min(Math.min(dp[i][j-1], dp[i-1][j]), dp[i-1][j-1]) + board[i-1][j-1];
                answer = Math.max(dp[i][j], answer);
            }
        }
        System.out.println(answer*answer);
    }
}
