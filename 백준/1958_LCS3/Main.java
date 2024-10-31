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
        testRun(1958);
//        run();
    }
}

class Solution {

    private char[] A,B,C;

    public Solution(BufferedReader reader) throws IOException {
        A = reader.readLine().toCharArray();
        B = reader.readLine().toCharArray();
        C = reader.readLine().toCharArray();
    }

    public void run() {
        int[][][] dp = new int[A.length + 1][B.length + 1][C.length + 1];
        int al = A.length;
        int bl = B.length;
        int cl = C.length;

        for(int i = 0 ; i < al ; i++) {
            for(int j = 0 ; j < bl ; j++) {
                for(int k = 0 ; k < cl ; k++) {
                    if(A[i] == B[j] && B[j] == C[k]) {
                        dp[i + 1][j + 1][k + 1] = dp[i][j][k] + 1;
                    }else {
                        dp[i + 1][j + 1][k + 1] = Math.max(dp[i][j + 1][k + 1], Math.max(dp[i + 1][j][k + 1], dp[i + 1][j + 1][k]));
                    }
                }
            }
        }

        System.out.println(dp[al][bl][cl]);
    }
}
