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
        //testRun(1508);
        run();
    }
}

class Solution {

    int trackLength;

    int nrReferee;

    int nrRefereePos;

    Integer[] pos;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        trackLength = Integer.parseInt(line[0]);
        nrReferee = Integer.parseInt(line[1]);
        nrRefereePos = Integer.parseInt(line[2]);

        line = reader.readLine().split(" ");
        pos = new Integer[nrRefereePos];

        for(int i = 0 ; i < nrRefereePos ; i++) {
            pos[i] = Integer.parseInt(line[i]);
        }
    }

    boolean check(int value) {
        int used = 0;
        int cur = -1;

        for(int i = 0 ; i < nrRefereePos ; i++) {
            if(cur <= pos[i]) {
                used++;
                cur = pos[i] + value; // value만큼 진행 시켜본다.
            }
        }
        // 조건 상에서 선택할 수 있는 위치 수가
        // 심판 위치 수보다 사용된 개수가 많다면 배치가 가능한 최소 거리이다.
        return used >= nrReferee;
    }

    public void run() {
        String answer;
        int maxMinDist = 0;

        int left = 0;
        int right = pos[pos.length - 1];

        // 1. 이분 탐색을 이용하여 최소구간의 최대값을 구한다.
        // 2. 구한 최대 최소 거리를 기준으로 문자열을 구성한다.

        // [left, right] 개구간 탐색
        while(left <= right) {
            int mid = left + (right - left) / 2;

            // mid를 최소 간격으로 해본다.
            if(check(mid)) {
                maxMinDist = mid;
                left = mid+1;
            } else {
                right = mid-1;
            }
        }

        StringBuilder sb = new StringBuilder();

        int cur = 0;
        int selected = 0;

        for(int i = 0 ; i < nrRefereePos ; i++) {
            if( cur <= pos[i] && selected < nrReferee ) {
                selected++;
                cur = pos[i] + maxMinDist;
                sb.append(1);
            } else {
                sb.append(0);
            }
        }

        answer = sb.toString();

        System.out.println(answer);
    }
}
