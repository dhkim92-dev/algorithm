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
        // testRun(5582);
        run();
    }
}

class Solution {
    
    String A, B;
    
    public Solution(BufferedReader reader) throws IOException {
        A = " "+reader.readLine();
        B = " "+reader.readLine();
    }
    
    void copy(int[] dst, int[] src) {
        for(int i = 0 ; i < dst.length ; i++) {
            dst[i] = src[i];
        }
    }

    public void run() {
        int answer = 0;       
        char ch = A.charAt(0);
        
        int[] memo = new int[B.length()];
        int[] tmp = new int[B.length()];
       
        for(int i = 1 ; i < A.length() ; i++) {
            ch = A.charAt(i);
            Arrays.fill(tmp, 0);

            for(int j = 1 ; j < B.length() ; j++) {
                boolean match = ch == B.charAt(j);
                tmp[j] = match ? memo[j-1]+1 : 0;
                answer = Math.max(answer, tmp[j]);
            }
            copy(memo, tmp);
        }


        System.out.println(answer);
    }
}
