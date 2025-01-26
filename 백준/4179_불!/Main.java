package algorithm;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int R,C;
    private char[][] board;
    private boolean[][] visited;
    private boolean[][] fired;

    static class Pos {
        int r, c;
        int t;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
            this.t = 0;
        }

        public Pos(int r, int c, int t) {
            this.r = r;
            this.c = c;
            this.t = t;
        }

        public Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c, t + 1);
        }
    }

    private Pos[] dirs = {
        new Pos(-1, 0),
        new Pos(1, 0),
        new Pos(0, -1),
        new Pos(0, 1)
    };

    private Pos player;
    private List<Pos> fires;
    private int lastTick;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        board = new char[R][C];
        visited = new boolean[R][C];
        fired = new boolean[R][C];
        fires = new ArrayList<>();

        for(int i = 0 ; i < R ; i++) {
            String line = reader.readLine();
            for(int j = 0 ; j < C ; j++) {
                board[i][j] = line.charAt(j);
                if(board[i][j] == 'J') {
                    player = new Pos(i, j);
                } else if(board[i][j] == 'F') {
                    fires.add(new Pos(i, j));
                }
            }
        }
    }

    private boolean isInRange(Pos pos) {
        return pos.r >= 0 && pos.r < R && pos.c >= 0 && pos.c < C;
    }

    private boolean isEscapable(Pos pos) {

        for(int i = 0 ; i < C ; i++) {
            if(pos.r == 0 || pos.r == R - 1) {
                if(board[pos.r][i] == '.' || board[pos.r][i] == 'J') {
                    return true;
                }
            }

            if(pos.c == 0 || pos.c == C - 1) {
                if(board[i][pos.c] == '.' || board[i][pos.c] == 'J') {
                    return true;
                }
            }
        }

        return false;
    }

    private List<Pos> spreadFires(Queue<Pos> q, int tick) {
        List<Pos> nextFires = new ArrayList<>();

        if(lastTick <= tick) {
            while (!q.isEmpty()) {
                Pos cur = q.poll();

                for (int i = 0; i < 4; i++) {
                    Pos next = cur.add(dirs[i]);

                    if (!isInRange(next)) {
                        continue;
                    }

                    if (board[next.r][next.c] == '#') {
                        continue;
                    }

                    if (fired[next.r][next.c]) {
                        continue;
                    }
                    fired[next.r][next.c] = true;
                    nextFires.add(next);
                }
            }
            lastTick = tick + 1;
        }

        return nextFires;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();

        Queue<Pos> q = new LinkedList<>();
        Queue<Pos> fireQ = new LinkedList<>(fires);
        q.offer(player);
        visited[player.r][player.c] = true;
        for(Pos fire : fires) {
            fired[fire.r][fire.c] = true;
        }
        boolean escaped = false;
        int minTime = Integer.MAX_VALUE;

        while(!q.isEmpty()) {
            Pos cur = q.poll();

            if(isEscapable(cur)) {
                minTime = Math.min(minTime, cur.t);
            }

            // 우선 불을 확산시킨다.
            List<Pos> nextFires = spreadFires(fireQ, cur.t);

            // 플레이어를 이동시켜본다.
            for(int i = 0 ; i < 4 ; i++) {
                Pos next = cur.add(dirs[i]);

                if(!isInRange(next)) {
                    escaped = true;
                    break;
                }

                if(board[next.r][next.c] == '#') {
                    continue;
                }

                if(fired[next.r][next.c]) {
                    continue;
                }

                if(visited[next.r][next.c]) {
                    continue;
                }

                visited[next.r][next.c] = true;
                q.offer(next);
            }

            fireQ.addAll(nextFires);
        }

        if(minTime == Integer.MAX_VALUE) {
            sb.append("IMPOSSIBLE");
        } else {
            sb.append(minTime+1);
        }

        System.out.println(sb);
    }
}
