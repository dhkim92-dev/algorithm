import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

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
        //System.out.println("실행됨");
        testRun(2151);
        //run();
    }
}

class Solution {

    public static class Pos implements Comparable<Pos> {

        int r, c;
        int turnCount;
        int dir;

        Pos(int r, int c) {
            this(r, c, 0, 0);
        }

        Pos(int r, int c, int turnCount) {
            this(r, c, turnCount, 0);
        }

        Pos(int r, int c, int turnCount, int dir) {
            this.r = r;
            this.c = c;
            this.turnCount = turnCount;
            this.dir = dir;
        }

        Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }

        @Override
        public int compareTo(Pos o) {
            return this.turnCount - o.turnCount;
        }
    }

    int answer = Integer.MAX_VALUE;
    int N;
    char[][] house;
    Pos[] doors;
    Pos[] dirs = {
        new Pos(-1, 0), // 북
        new Pos(0, 1), // 동
        new Pos(1, 0), // 남
        new Pos(0, -1) // 서
    };
    int[][] visited;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        house = new char[N][N];
        visited = new int[N][N];
        doors = new Pos[2];

        for(int i = 0 ; i < N ; i++) {
            house[i] = reader.readLine().toCharArray();
        }
    }

    private void findDoors() {
        int offset = 0;
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                if(house[i][j] == '#') {
                    doors[offset] = new Pos(i, j);
                    offset++;
                }
            }
        }
    }

    private boolean isMirror(Pos p) {
        return house[p.r][p.c] == '!';
    }

    private boolean isInRange(Pos p) {
        return p.r >= 0 && p.r < N && p.c >= 0 && p.c < N;
    }

    private boolean isWall(Pos p) {
        return house[p.r][p.c] == '*';
    }

    private boolean isDoor(Pos p) {
        return house[p.r][p.c] == '#';
    }

    private int bfs(Pos start, Pos goal) {
        PriorityQueue<Pos> q = new PriorityQueue<>();

        for(int i = 0 ; i < 4 ; i++) {
            q.add(new Pos(start.r, start.c, 0, i));
        }
        visited[start.r][start.c] = (0x01 | 0x02 | 0x04 | 0x08);

        while(!q.isEmpty()) {
            Pos cur = q.poll();
            //System.out.println("cur : (" + cur.r + ", " + cur.c + " ) dir : " + cur.dir);

            if(cur.r == goal.r && cur.c == goal.c) return cur.turnCount;

            // 방향을 바꾸지 않고 진행하는 경우
            Pos nxt = cur.add(dirs[cur.dir]);
            nxt.turnCount = cur.turnCount;
            nxt.dir = cur.dir;

            if(isInRange(nxt) && !isWall(nxt) && (visited[nxt.r][nxt.c] & (1 << (nxt.dir+1))) == 0) {
                visited[nxt.r][nxt.c] |= (1 << (nxt.dir+1));
                q.add(nxt);
            }

            if(isMirror(cur)) {
                // 현재 자리가 거울인 경우 방향 전환 케이스 추가
                for(int i = 0 ; i < 2 ; i++) {
                    int nextDir = i == 0 ? (cur.dir + 1) % 4 : (cur.dir + 3) % 4;
                    nxt = cur.add(dirs[nextDir]);
                    nxt.turnCount = cur.turnCount + 1;
                    nxt.dir = nextDir;
                    if(!isInRange(nxt) || isWall(nxt) || (visited[nxt.r][nxt.c] & (1 << (nxt.dir+1))) != 0) {
                        continue;
                    }
                    visited[nxt.r][nxt.c] |= (1 << (nxt.dir+1));
                    q.add(nxt);
                }
            }
        }
        return 0;
    }

    public void run() {
        findDoors();
        answer = bfs(doors[0], doors[1]);
        System.out.println(answer);
    }
}
