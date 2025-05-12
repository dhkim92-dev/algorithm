import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class Main {

    // private static boolean isDebug = false;
    private static boolean isDebug = true;

    public static void main(String[] args) throws IOException {
        if ( isDebug ) {
            System.out.println("Debug mode");
            new OfflineSolutionRunner().run();
        } else {
            new OnlineSolutionRunner().run();
        }
    }
}

class Solution {

    static class Item {
        Pos pos;
        int cnt;
        int gravity;

        public Item(Pos pos, int cnt, int gravity) {
            this.pos = pos;
            this.cnt = cnt;
            this.gravity = gravity;
        }
    }

    static class Pos {
        int r;
        int c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos other) {
            return new Pos(this.r + other.r, this.c + other.c);
        }
    }

    private Pos captain;
    private char[][] board;
    private static final Pos[] dirs = {
            new Pos(0, 1), // right
            new Pos(0, -1) // left
    };
    private Pos dst;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        int R = Integer.parseInt(tokens[0]);
        int C = Integer.parseInt(tokens[1]);
        board = new char[R][C];

        for (int i = 0; i < R; i++) {
            String line = reader.readLine();
            for (int j = 0; j < C; j++) {
                board[i][j] = line.charAt(j);

                if (board[i][j] == 'C') {
                    captain = new Pos(i, j);
                    board[i][j] = '.';
                } else if (board[i][j] == 'D') {
                    dst = new Pos(i, j);
                    board[i][j] = '.';
                }
            }
        }
    }

    private int simulate() {
        int answer = Integer.MAX_VALUE;
        PriorityQueue<Item> q = new PriorityQueue<>(Comparator.comparing(item -> item.cnt));
        int[][][] dists = new int[board.length][board[0].length][2]; // 중력 방향 상관없이 최소 도착 거리
        for (int i = 0; i < dists.length; i++) {
            for (int j = 0; j < dists[i].length; j++) {
                Arrays.fill(dists[i][j], Integer.MAX_VALUE);
            }
        }
        // 초기 위치에서 중력을 적용해서 위치를 보정한다.
        int rowNum = doGravity(captain, 0);
        if ( rowNum == -1 ) return -1;
        // System.out.println("보정된 위치 : " + rowNum + ", " + captain.c);
        captain.r = rowNum;
        q.add(new Item(captain, 0, 0));
        dists[captain.r][captain.c][0] = 0;

        while ( !q.isEmpty() ) {
            Item item = q.poll();
            // System.out.println("item = " + item.pos.r + ", " + item.pos.c + ", cnt = " + item.cnt);
            Pos pos = item.pos;
            int cnt = item.cnt;
            int gravity = item.gravity;
            if ( dists[pos.r][pos.c][gravity] < cnt ) continue;
            if ( item.pos.r == dst.r && item.pos.c == dst.c ) {
                answer = Math.min(answer, cnt);
                continue;
            }

            // 반대 방향 중력 진행
            int reverseGravity = (gravity + 1) % 2;
            rowNum = doGravity(pos, reverseGravity);
           // System.out.println("rowNum = " + rowNum);

            if ( rowNum != -1 && dists[rowNum][pos.c][reverseGravity] > cnt + 1) {
                // System.out.println("Gravity toggled at " + pos.r + ", " + pos.c + " with : dists[" + rowNum + "][" + pos.c + "][" + reverseGravity + "] = " + (cnt + 1));
                dists[rowNum][pos.c][reverseGravity] = cnt + 1;
                q.add(new Item(new Pos(rowNum, pos.c), cnt + 1, reverseGravity));
            }

            

            // 좌우 이동
            for ( int i = 0 ; i < 2 ; ++i ) {
                Pos nxt = pos.add(dirs[i]);
                if ( !isInRange(nxt) || board[nxt.r][nxt.c] == '#' ) continue;
                if ( nxt.r == dst.r && nxt.c == dst.c ) {
                    q.add(new Item(nxt, cnt, gravity));
                    continue;
                }
                // 현재 중력 방향으로 이동
                if ( nxt.r == dst.r && nxt.c == dst.c ) {
                    answer = Math.min(answer, cnt + 1);
                    continue;
                }
                rowNum = doGravity(nxt, gravity);
                if ( rowNum == -1 ) continue;
                nxt.r = rowNum;
                if ( !isInRange(nxt) || board[nxt.r][nxt.c] == '#' || dists[nxt.r][nxt.c][gravity] <= cnt) continue;
                dists[nxt.r][nxt.c][gravity] = cnt ;
                q.add(new Item(nxt, cnt, gravity));
            }
        }

        return answer;
    }

    private boolean isBoundary(int rowNum) {
        return rowNum == 0 || rowNum == board.length - 1;
    }

    private int doGravity(Pos cur, int gravity) {
        // r == 0 || r == board.length - 1 에 해당하는 위치에 도달 가능하면 -1을 반환해야한다.
//        System.out.println("Do gravity at " + cur.r + ", " + cur.c);
        Pos delta = new Pos(gravity == 0 ? 1 : -1, 0);
        Pos pos = new Pos(cur.r, cur.c);
        do {
            Pos next = pos.add(delta);
            if ( !isInRange(next) ) return -1;
            if ( board[next.r][next.c] != '#' ) {
                if ( next.r == dst.r && next.c == dst.c ) {
                    return next.r;
                }
            } else {
                // 블록인 경우 return previous position row
                return pos.r;
            }

            pos = next;
        } while ( true );
    }

    private boolean isInRange(Pos pos) {
        return (pos.r >= 0 && pos.r < board.length) && (pos.c >= 0 && pos.c < board[0].length);
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        int answer = simulate();

        if ( answer == Integer.MAX_VALUE ) {
            sb.append(-1);
        } else {
            sb.append(answer);
        }
        System.out.println(sb.toString());
    }
}

class OfflineSolutionRunner extends SolutionRunner {

    @Override
    protected List<BufferedReader> getBufferedReaders() {
        // resource 밑의 모든 txt파일을 읽는다.
        Path base = Paths.get("");
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/";
        //txt
        File dir = null;
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return List.of();
        }
        File[] files = dir.listFiles();
        if(files == null) {
            return List.of();
        }
        List<BufferedReader> readers = new ArrayList<>();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    readers.add(reader);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return readers;
    }

    @Override
    protected BufferedReader preprocess(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        String contents = sb.toString();

        System.out.println(contents);
        System.out.println("Output : ");
        // String을 다시 reader로
        return new BufferedReader(new StringReader(contents));
    }
}

class OnlineSolutionRunner extends SolutionRunner {

    @Override
    protected List<BufferedReader> getBufferedReaders() {
        return List.of(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Override
    protected BufferedReader preprocess(BufferedReader reader) {
        return reader;
    }
}

abstract class SolutionRunner {

    public final void run() throws IOException {
        List<BufferedReader> readers = getBufferedReaders();

        for (BufferedReader reader : readers) {
            BufferedReader br = preprocess(reader);
            new Solution(br).run();
        }
    }

    protected abstract List<BufferedReader> getBufferedReaders();
    protected abstract BufferedReader preprocess(BufferedReader reader) throws IOException;
}

