import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos pos) {
            return new Pos(r + pos.r, c + pos.c);
        }
    }

    private static final Pos[] dirs = {
        new Pos(0, 1),
        new Pos(-1, 0),
        new Pos(0, -1),
        new Pos(1, 0)
    };
    private int N;
    private boolean[][] board;
    private int[][] curves;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        board = new boolean[102][102];
        curves = new int[N][4];

        for(int i = 0 ; i < N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int c = Integer.parseInt(tokens[0]);
            int r = Integer.parseInt(tokens[1]);
            int d = Integer.parseInt(tokens[2]);
            int g = Integer.parseInt(tokens[3]);

            curves[i][0] = c;
            curves[i][1] = r;
            curves[i][2] = d;
            curves[i][3] = g;
        }
    }

    private Pos rotate(Pos target, Pos pivot) {
        int dr = target.r - pivot.r;
        int dc = target.c - pivot.c;
        int nc = pivot.c - dr;
        int nr = pivot.r + dc;

        return new Pos(nr, nc);
    }

    private void makeDragonCurve(int idx) {
        List<Pos> points = new ArrayList<>();

        Pos start = new Pos(curves[idx][1], curves[idx][0]);
        Pos end = start.add(dirs[curves[idx][2]]);
        int gen = curves[idx][3];

        points.add(start);
        points.add(end);

        for(int i = 0 ; i < gen ;i++) {
            Pos pivot = points.get(points.size() - 1);
            List<Pos> newPoints = new ArrayList<>();

            for(int j = points.size() - 2; j >= 0 ; j--) {
                Pos newPoint = rotate(points.get(j), pivot);
                newPoints.add(newPoint);
            }

            points.addAll(newPoints);
        }

        for(Pos point : points) {
            board[point.r][point.c] = true;
        }
    }

    private int countSquares() {
        int count = 0;
        for(int i = 0 ; i < 100 ; i++) {
            for(int j = 0;  j < 100 ; j++) {
                if(board[i][j] && board[i][j + 1] && board[i + 1][j] && board[i + 1][j + 1]) {
                    count++;
                }
            }
        }

        return count;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < N ; i++) {
            makeDragonCurve(i);
        }
        sb.append(countSquares());

        System.out.println(sb.toString());
    }
}

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

        for(int i = 0 ; i < files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            List<String> lines = reader.lines()
                    .collect(Collectors.toList());

            boolean found = true;
            for(String line : lines) {
                if(line.contains("answer")) {
                    found = true;
                }

                if(found) {
                    System.out.println(line);
                }
            }
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
        testRun(15685);
        //run();
    }
}


