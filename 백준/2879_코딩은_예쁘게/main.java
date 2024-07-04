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
//        testRun(2879);
        run();
    }
}

class Solution {

    int n;

    int[] wrong;

    int[] correct;

    public Solution(BufferedReader reader) throws IOException {;
        n = Integer.parseInt(reader.readLine());
        wrong = new int[n];
        correct = new int[n];

        StringTokenizer tokenizer1 = new StringTokenizer(reader.readLine());
        StringTokenizer tokenizer2 = new StringTokenizer(reader.readLine());
        for(int i = 0 ; i < n ; i++) {
            wrong[i] = Integer.parseInt(tokenizer1.nextToken());
            correct[i] = Integer.parseInt(tokenizer2.nextToken());
        }
    }

    void updateScalar(int[] scalar, int[] delta, int from, int to, int unit) {
        for(; from <= to ; from++) {
            scalar[from]-=unit;
            delta[from] = scalar[from] == 0 ? 0 : delta[from];
        }
    }

    public void run() {
        int answer = 0;
        int[] delta = new int[n];
        int[] scalar = new int[n];

        for(int i = 0 ; i < n ; i++) {
            int diff = wrong[i] - correct[i];
            delta[i] = diff > 0 ? 1 : (diff == 0) ?  0 : -1;
            scalar[i] = Math.abs(diff);
        }

        // 1. 0부터 탐색한다. 델타가 같은 방향인 것 끼리 최대한 묶는다. // 0은 별도이므로 패스
        // 2. 묶였으면 델타의 절대값의 최소값을 구한다.
        // 3. 해당 최소 코스트의 값을 답에 더한다.
        // 4. 스칼라를 업데이트 해준다.

        for(int i = 0 ; i < n ; i++) {
            if(scalar[i] == 0) continue;
            int lastIdx = i;
            int opCount = scalar[i];

            for(int j = i + 1; j < n ; j++) {
                if(delta[i] != delta[j]) break;
                opCount = Math.min(opCount, scalar[j]);
                lastIdx = j;
            }

//            System.out.printf("line %d to %d update. %d operation executed.\n", i, lastIdx, opCount);
            updateScalar(scalar, delta, i, lastIdx, opCount);

            if(scalar[i] != 0) {
                i--;
            }
            answer += opCount;
        }

        System.out.println(answer);
    }
}

