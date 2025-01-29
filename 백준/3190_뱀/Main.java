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

        public Pos(Pos pos) {
            this.r = pos.r;
            this.c = pos.c;
        }

        public Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) {
                return true;
            }

            if(o == null) {
                return false;
            }

            if(o.getClass() != this.getClass()) {
                return false;
            }

            o = (Pos)o;

            return r == ((Pos) o).r && c == ((Pos) o).c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private int N, K, L;

    private int[][] board;

    private Map<Integer, Character> commands;

    private Pos[] dirs = {
        new Pos(0, 1), // Right
        new Pos(1, 0), // Down
        new Pos(0, -1), // Left
        new Pos(-1, 0) // Up
    };

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        K = Integer.parseInt(reader.readLine());

        board = new int[N][N];
        commands = new HashMap<>();

        for(int i = 0 ; i < K ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int r = Integer.parseInt(tokens[0]) - 1;
            int c = Integer.parseInt(tokens[1]) - 1;
            board[r][c] = 1;
        }
        L = Integer.parseInt(reader.readLine());

        for(int i = 0 ; i < L ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int time = Integer.parseInt(tokens[0]);
            char command = tokens[1].charAt(0);
            commands.put(time, command);
        }
    }

    private boolean isInRange(Pos p) {
        return p.r >= 0 && p.r < N && p.c >= 0 && p.c < N;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        Deque<Pos> snake = new ArrayDeque<>();
        Set<Pos> snakeBodies = new HashSet<>();

        int time = 0;
        int dir = 0;
        snake.addFirst(new Pos(0, 0));

        while(true) {
            time++;
            Pos head = new Pos(snake.peekLast());
//            System.out.println("head : " + head.r + " " + head.c);
            Pos next = head.add(dirs[dir]);

            if(!isInRange(next)) {
                break;
            }

            if(snakeBodies.contains(next)) {
                break;
            }

            snake.addLast(next);
            snakeBodies.add(next);

            if(board[next.r][next.c] == 0) {
                Pos tail = snake.pollFirst();
                snakeBodies.remove(tail);
            }else {
                board[next.r][next.c] = 0;
            }

            // 방향을 바꿔야하는 경우
            if(commands.containsKey(time)) {
                char command = commands.get(time);
                if(command == 'L') {
                    dir = (dir + 3) % 4;
                }else {
                    dir = (dir + 1) % 4;
                }
            }
        }

        sb.append(time);
        System.out.println(sb.toString());
    }
}

