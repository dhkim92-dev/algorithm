import java.io.*;
import java.util.*;

class Solution {

    private static class Pos {
        int idx, r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos other) {
            return new Pos(this.r + other.r, this.c + other.c);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pos)) return false;
            Pos pos = (Pos) o;
            return r == pos.r && c == pos.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private static class Item {
        Pos pos;
        int mask;
        int dist;

        Item(Pos pos, int mask, int dist) {
            this.pos = pos;
            this.mask = mask;
            this.dist = dist;
        }
    }

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private char[][] board;

    private List<Pos> dirties;

    private int N, M;

    Pos robot;

    private static final char DIRTY = '*';

    private static final char CLEAN = '.';

    private static final char WALL = 'x';

    private static final Pos[] DIRECTIONS = {
        new Pos(-1, 0), // Up
        new Pos(0, 1),  // Right
        new Pos(1, 0),  // Down
        new Pos(0, -1), // Left
    };

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
    }

    void init(int R, int C) throws IOException {
        N = R;
        M = C;
        board = new char[N][M];
        dirties = new ArrayList<>();

        for ( int i = 0 ; i < N ; ++i ) {
            board[i] = reader.readLine().toCharArray();
        }

        List<Pos> tmp = new ArrayList<>();
        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < M ; ++c ) {
                if (DIRTY == board[r][c]) {
                    tmp.add(new Pos(r, c));
                } else if ('o' == board[r][c]) {
                    board[r][c] = DIRTY;
                    robot = new Pos(r, c);
                }
            }
        }
//        dirties.add(robot);
        dirties.addAll(tmp);
    }

    boolean isBounded(Pos pos) {
        return pos.r >= 0 && pos.r < N && pos.c >= 0 && pos.c < M;
    }

    private int getDist() {
        boolean[][][] visited = new boolean[N][M][0x01<<10];
        Queue<Item> q = new LinkedList<>();
        q.offer(new Item(robot, 0, 0));
        visited[robot.r][robot.c][0] = true;

        while ( !q.isEmpty() ) {
            Item item = q.poll();
            Pos pos = item.pos;
            int mask = item.mask;
            int dist = item.dist;

            if ( board[pos.r][pos.c] == DIRTY ) {
                for ( int i = 0 ; i < dirties.size() ; ++i ) {
                    if ( dirties.get(i).equals(pos) ) {
                        mask |= (1 << i);
                        break;
                    }
                }
            }

            if ( mask == (1 << dirties.size()) - 1 ) {
                return dist;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                Pos nxt = pos.add(DIRECTIONS[i]);
                if ( !isBounded(nxt) || WALL == board[nxt.r][nxt.c] ) continue;
                if ( visited[nxt.r][nxt.c][mask] ) continue;
                visited[nxt.r][nxt.c][mask] = true;
                q.offer(new Item(nxt, mask, dist + 1));
            }
        }
        return -1;
    }

    public void run() throws IOException {

        while ( true ) {
            int R, C;
            String[] tokens = reader.readLine().split(" ");
            C = Integer.parseInt(tokens[0]);
            R = Integer.parseInt(tokens[1]);

            if ( R == 0 && C == 0 ) {
                break;
            }

            init(R, C);
            int minDist = getDist();
            writer.write(String.format("%d\n", minDist));
        }

        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}


