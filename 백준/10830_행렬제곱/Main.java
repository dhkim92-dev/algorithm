import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
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
        //testRun(10830);
        run();
    }
}

class Solution {

    private int matSize;

    private long B;

    private int[][] A;

    private int[][] tmp;

    public Solution(BufferedReader reader) throws IOException {
        String[] input = reader.readLine()
                .split(" ");

        matSize = Integer.parseInt(input[0]);
        B = Long.parseLong(input[1]);

        A = new int[matSize][matSize];

        for(int i = 0 ; i < matSize ; i++) {
            input = reader.readLine()
                    .split(" ");
            for(int j = 0 ; j < matSize ; j++) {
                A[i][j] = Integer.parseInt(input[j]);
            }
        }

        tmp = new int[matSize][matSize];
    }

    private void clear(int [][] m) {
        for(int[] row : m) {
            Arrays.fill(row, 0);
        }
    }

    private void matmul(int[][] a, int[][] b) {
        clear(tmp);

        for(int k = 0 ; k < matSize ; k++) {
            for(int i = 0 ; i < matSize ; i++) {
                int lFix = a[i][k];
                for(int j = 0 ; j < matSize ; j++) {
                    tmp[i][j] = (tmp[i][j] + lFix * b[k][j]) % 1000;
                }
            }
        }

        copy(a, tmp);
    }

    private void copy(int[][] dst, int[][] src) {
        for(int i = 0 ; i < matSize ; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, matSize);
        }
    }

    private void print(int[][] mat) {
        for(int i = 0 ; i < matSize ; i++) {
            for(int j = 0 ; j < matSize ; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void run() {
        // 125, 000, 000, 000, 000
        // 반씩 분할, log(n) 으로 해결

        int [][] result = new int[matSize][matSize];

        for(int i = 0 ; i < matSize ; i++) {
            result[i][i] = 1;
        }

        while(B>0) {
            if((B%2) == 1) {
                matmul(result, A);
            }
            matmul(A, A);
            B/=2;
        }

        print(result);
    }
}
