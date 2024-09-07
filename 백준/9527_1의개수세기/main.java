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
        testRun(9527);
//        run();
    }
}

class Solution {

    private long a, b;

    private long[] psum = new long[64];

    public Solution(BufferedReader reader) throws IOException {
        String[] input = reader.readLine().split(" ");
        a = Long.parseLong(input[0]);
        b = Long.parseLong(input[1]);
    }

    private long countOneBits(long x) {
        long cnt = x & 1L;
        int i = 55;
        // x까지의 1비트 개수
        // x를 구성하는 이진수 최상위 비트가 1인 자리 i를 찾고
        // psum[i-1]개는 기본적으로 포함됨.
        // 예 13 => 1101
        // 0000
        // ~
        // 0111 까지는 psum[2] 에 포함됨


        // 1 101
        // 1 100
        // 1 011
        // 1 010
        // 1 001
        // 1 000


        for(; i > 0 ; i--) {
            long highest = 1L<<i;
            if((x & (highest)) != 0) {
                cnt += psum[i-1] + (x - highest + 1);
                x -= highest;
            }
        }

        return cnt;
    }

    public void run() {
        int answer = 0;

        psum[0] = 1;
        for(int i = 1 ; i < 55 ; i++) {
            // 1 비트 개수 누적합
            // i번째 비트가 1인 경우, 그리고 그 하위 비트들의 모든 경우의 수가 구성하는 1비트의 개수
            // 일단 최상위 비트가 1인 경우의 수만 세면 2^i 가 됨.
            // 하위 비트들의 1의 경우의 수는 (i-1)번째 비트의 1의 수의 누적합과 동일하다.
            // 따라서 p[i] = p[i-1]*2 + 2^i 가 됨
            psum[i] = psum[i-1]*2 + (1L << i);
        }

        System.out.println( countOneBits(b) - countOneBits(a-1) );
    }
}
