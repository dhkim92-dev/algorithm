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
        //testRun(3109);
        run();
    }
}

class Solution {

    int R, C;

    int[][] board;

    int[][] dirs = {
            {-1, 1},
            {0, 1},
            {1, 1}
    };

    boolean found = false;

    boolean isInRange(int r, int c) {
        return (0<=r && r<R) && (0<=c && c<C);
    }

    void print(int r, int c) {
        System.out.println("#################Board status#####################");
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if(i==r && j == c) {
                    System.out.println("@");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int pathFind(int r, int c) {
        // 현재 지점부터 탐색한다.
        if(board[r][c] == 1) return 0;
        board[r][c] = 1;

        if(c == C-1) {
            found = true;
            return 1; // 첫줄에 도달할 수 있으면 파이프를 정상적으로 놓은 경우이다.
        }

        int ret = 0;
        int nxtR=-1, nxtC=-1;
        for(int i = 0 ; i < 3 ; i++) {
            nxtR = r + dirs[i][0];
            nxtC = c + dirs[i][1];

            if(!isInRange(nxtR, nxtC)) continue;
            if(board[nxtR][nxtC] == 1) continue;
            ret = pathFind(nxtR, nxtC);
            if(found) {
                return 1;
            }
        }

        return ret;
    }

    public Solution(BufferedReader reader) throws IOException {;
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());
        board = new int[R][C];
        for(int[] row : board) {
            Arrays.fill(row,0);
        }

        for(int i = 0 ; i < R ; i++) {
            String s = reader.readLine();

            for(int j = 0 ; j < C ; j++) {
                if(s.charAt(j) == 'x') {
                    board[i][j] = 1;
                }
            }
        }
    }

    public void run() {
        int limit = Integer.MAX_VALUE;
        int answer = 0;

        for(int i = 0 ; i < C ; i++) {
            int emptyCount = 0;
            for(int j = 0 ; j < R ; j++) {
                if(board[j][i] == 0) continue;
                emptyCount++;
            }
            limit = Math.min(emptyCount, limit);
        }

        for(int i = 0 ; i < R ; i++) {
            found=false;
            answer += pathFind(i, 0);
        }

        System.out.println(answer);
    }
}
