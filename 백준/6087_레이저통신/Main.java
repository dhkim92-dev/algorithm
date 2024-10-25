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
        //testRun(6087);
        run();
    }
}

class Solution {

    private char [][] board;

    Pos[] pos = new Pos[2];

    private int W, H;

    private Pos[] dirs = {
        new Pos(-1, 0, 0, 0),
        new Pos(0, 1, 1, 0),
        new Pos(1, 0, 2, 0),
        new Pos(0, -1, 3, 0)
    };

    public static class Pos implements Comparable<Pos> {
        int r, c;
        int dir = 0;
        int reflexCnt = 0;

        Pos(int r, int c, int dir, int reflexCnt) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.reflexCnt = reflexCnt;
        }

        @Override
        public int compareTo(Pos o2) {
            return Integer.compare(reflexCnt, o2.reflexCnt);
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        String [] tokens = line.split(" ");
        W = Integer.parseInt(tokens[0]);
        H = Integer.parseInt(tokens[1]);

        board = new char[H][W];

        int cnt = 0;
        for(int r = 0 ; r < H ; r++) {
            line = reader.readLine();
            board[r] = line.toCharArray();
            for(int c = 0 ; c < W ; c++) {
                if(board[r][c] == 'C') {
                    pos[cnt++] = new Pos(r, c, 0, 0);
                }
            }
        }
    }

    private boolean isInRange(Pos p) {
        return (0 <= p.r && p.r < H) && (0 <= p.c && p.c < W);
    }

    private int bfs(Pos start, Pos end) {
        int answer = Integer.MAX_VALUE;
        Queue<Pos> q = new LinkedList<>();
        int[][][] reflexCnt = new int [4][H][W];

        for(int i = 0 ; i < 4 ; i++) {
            for(int r = 0 ; r < H ; r++) {
                Arrays.fill(reflexCnt[i][r], Integer.MAX_VALUE);
            }
        }

        for(int i = 0 ; i < 4 ; i++) {
            q.add(new Pos(start.r, start.c, i, 0));
        }

        while(!q.isEmpty()) {
            Pos cur = q.poll();

            if(cur.r == end.r && cur.c == end.c) {
                answer = Math.min(answer, cur.reflexCnt);
                continue;
            }

            for(int i = 0 ; i < 4 ; i++) {
                Pos next = new Pos(cur.r + dirs[i].r, cur.c + dirs[i].c, i, cur.reflexCnt);

                if(!isInRange(next) || board[next.r][next.c] == '*' || Math.abs(i - cur.dir) == 2) {
                    continue;
                }

                if(cur.dir != i) {
                    next.reflexCnt++;
                }

                if(reflexCnt[next.dir][next.r][next.c] <= next.reflexCnt) {
                    continue;
                }

                reflexCnt[next.dir][next.r][next.c] = next.reflexCnt;
                q.add(next);
            }
        }

        return answer;
    }

    public void run() {

        int answer = bfs(pos[0], pos[1]);
        System.out.println(answer);
    }
}
