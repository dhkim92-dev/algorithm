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
        //testRun(2011);
        run();
    }
}

class Solution {

    int[] cipher;
    
    public Solution(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        // System.out.println("line length : " + line.length());
        cipher = new int[line.length()+1];

        for(int i = 0 ; i < line.length() ; i++) {
            cipher[i+1] = (line.charAt(i) - '0');
        }
    }

    public void run() {
        int[] memo = new int[cipher.length+1]; // dp[i] = i번째 암호문자까지 포함하여 만들 수 있는 복호문의 수
        int mod = 1000000;

        if(cipher.length == 1) {
            System.out.println(1);
            return;
        }
        memo[0] = 1;
        memo[1] = 1;
        
        int limit = cipher.length;

        if(cipher[1] == 0) {
            System.out.println(0); 
            return;
        }


        for(int i = 2 ; i < limit ; i++) {
            if(cipher[i] == 0 && cipher[i-1]*10 + cipher[i] > 26)  {
                System.out.println(0);
                return;
            }

            if(cipher[i-1] == 0 && cipher[i] == 0) {
                System.out.println(0);
                return;
            }

            if(cipher[i-1] == 0) {
                memo[i] = memo[i-1];
            } else if(cipher[i] != 0 && cipher[i-1]*10 + cipher[i] <= 26) {
                memo[i] = (memo[i-1] + memo[i-2])%mod;
            } else if(cipher[i] == 0) {
                memo[i] = memo[i-2];
            } else {
                memo[i] = memo[i-1];
            }
        }

        System.out.println(memo[cipher.length-1]);
    }
}
