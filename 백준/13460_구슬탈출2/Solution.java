import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

enum State {
    BLOCKED,
    EMPTY,
    DOOR
    ;
}

class Pos {
    public int r, c;

    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public Pos add(Pos other) {
        return new Pos(this.r + other.r, this.c + other.c);
    }

    public Pos copy(Pos other) {
        this.r = other.r;
        this.c = other.c;
        return this;
    }

    public boolean same(Pos other) {
        return this.r == other.r && this.c == other.c;
    }
}

class Board {
    private State[][] states;
    private int R, C;
    private static final Pos[] directions = {
            new Pos(0, -1), // left
            new Pos(0, 1),  // right
            new Pos(-1, 0), // up
            new Pos(1, 0)   // down
    };

    public Board(State[][] states, int R, int C) {
        this.states = states;
        this.R = R;
        this.C = C;
    }

    public Board(Board other) {
        this.R = other.R;
        this.C = other.C;
        this.states = new State[R][C];
        for ( int r = 0 ; r < R ; ++r ) {
            System.arraycopy(other.states[r], 0, this.states[r], 0, C);
        }
    }

    public void backup(Board other) {
        this.R = other.R;
        this.C = other.C;
        this.states = other.states;
    }

    public boolean isInRange(Pos p) {
        return isInRange(p.r, p.c);
    }

    public boolean isInRange(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    private void moveBall(Pos ball, Pos otherBall, Pos dir) {
        // System.out.println("moveBall " + ball.r + " " + ball.c + " " + otherBall.r + " " + otherBall.c);
        while ( isInRange(ball.r + dir.r , ball.c + dir.c) ) {
            ball.r += dir.r;
            ball.c += dir.c;
            // System.out.println("    moveBall " + ball.r + " " + ball.c);

            if ( states[ball.r][ball.c] == State.BLOCKED ) {
                ball.r -= dir.r;
                ball.c -= dir.c;
                break;
            }

            if ( ball.same(otherBall) ) {
                if ( states[ball.r][ball.c] == State.DOOR ) {
                    break;
                } else {
                    ball.r -= dir.r;
                    ball.c -= dir.c;
                    break;
                }
            }

            if ( states[ball.r][ball.c] == State.DOOR ) {
                break;
            }
        }
    }

    public void leanLeft(Pos rb, Pos bb) {
        if ( rb.r == bb.r ) {
            if ( rb.c < bb.c ) {
                moveBall(rb, bb, directions[0]);
                moveBall(bb, rb, directions[0]);
            } else {
                moveBall(bb, rb, directions[0]);
                moveBall(rb, bb, directions[0]);
            }
        } else {
            moveBall(rb, bb, directions[0]);
            moveBall(bb, rb, directions[0]);
        }
    }

    public void leanRight(Pos rb, Pos bb) {
        if ( rb.r == bb.r ) {
            if ( rb.c < bb.c ) {
                moveBall(bb, rb, directions[1]);
                moveBall(rb, bb, directions[1]);
            } else {
                moveBall(rb, bb, directions[1]);
                moveBall(bb, rb, directions[1]);
            }
        } else {
            moveBall(rb, bb, directions[1]);
            moveBall(bb, rb, directions[1]);
        }
    }

    public void leanUp(Pos rb, Pos bb) {
        if ( rb.c == bb.c ) {
            if ( rb.r < bb.r ) {
                moveBall(rb, bb, directions[2]);
                moveBall(bb, rb, directions[2]);
            } else {
                moveBall(bb, rb, directions[2]);
                moveBall(rb, bb, directions[2]);
            }
        } else {
            moveBall(rb, bb, directions[2]);
            moveBall(bb, rb, directions[2]);
        }
    }

    public void leanDown(Pos rb, Pos bb) {
        if ( rb.c == bb.c ) {
            if ( rb.r < bb.r ) {
                moveBall(bb, rb, directions[3]);
                moveBall(rb, bb, directions[3]);
            } else {
                moveBall(rb, bb, directions[3]);
                moveBall(bb, rb, directions[3]);
            }
        } else {
            moveBall(rb, bb, directions[3]);
            moveBall(bb, rb, directions[3]);
        }
    }

    public boolean isGoal(Pos rb) {
        return states[rb.r][rb.c] == State.DOOR;
    }
}

class Item {
    public Pos redBall;
    public Pos blueBall;
    public int cnt;

    public Item(Pos redBall, Pos blueBall, int cnt) {
        this.redBall = new Pos(redBall.r, redBall.c);
        this.blueBall = new Pos(blueBall.r, blueBall.c);
        this.cnt = cnt;
    }
}

class Solution {

    private int answer = Integer.MAX_VALUE;
    private Board board;
    private Pos redBall, blueBall;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        int N = Integer.parseInt(tokens[0]);
        int M = Integer.parseInt(tokens[1]);
        State[][] _board = new State[N][M];

        for ( int r = 0 ; r < N ; ++r ) {
            String line = reader.readLine();
            for ( int c = 0 ; c < M ; ++c ) {
                if ( line.charAt(c) == '#' ) {
                    _board[r][c] = State.BLOCKED;
                } else if ( line.charAt(c) == '.' ) {
                    _board[r][c] = State.EMPTY;
                } else if ( line.charAt(c) == 'R' ) {
                    _board[r][c] = State.EMPTY;
                    redBall = new Pos(r, c);
                } else if ( line.charAt(c) == 'B' ) {
                    _board[r][c] = State.EMPTY;
                    blueBall = new Pos(r, c);
                } else if ( line.charAt(c) == 'O' ) {
                    _board[r][c] = State.DOOR;
                }
            }
        }

        board = new Board(_board, N, M);
    }

    private int makeCacheKey(Pos r, Pos b) {
        int key = 0xEF000000;
        key |= (r.r << 12 | r.c << 8);
        key |= (b.r << 4 | b.c );
        return key;
    }

    private void cacheAndPush(Queue<Item> q, Map<Integer, Integer> cached, Pos r, Pos b, int cnt) {
        int key = makeCacheKey(r, b);
        if ( !cached.containsKey(key) ) {
            cached.put(key, cnt + 1);
            q.add(new Item(r, b, cnt + 1));
        } else if ( cached.get(key) > (cnt + 1) ) {
            q.add(new Item(r, b, cnt + 1));
            cached.put(key, cnt + 1);
        }
    }

    public int bfs(Pos rb, Pos bb) {
        Queue<Item> q = new LinkedList<>();
        Map<Integer, Integer> cached = new HashMap<>();
        q.add(new Item(rb, bb, 0));
        int minCnt = Integer.MAX_VALUE;

        while ( !q.isEmpty() ) {
            Item cur = q.poll();
            Pos curR = cur.redBall;
            Pos curB = cur.blueBall;

            if ( cur.cnt > minCnt ) continue;
            if ( board.isGoal(curR) && board.isGoal(curB) ) continue;
            if ( board.isGoal(curB) ) continue;
            if ( board.isGoal(curR) ) {
                minCnt = Math.min( minCnt, cur.cnt );
                continue;
            }

            Pos backupR = new Pos(curR.r, curR.c);
            Pos backupB = new Pos(curB.r, curB.c);

            // 네방향 탐색 
            board.leanUp(curR, curB);
            cacheAndPush(q, cached, curR, curB, cur.cnt);
            curR.copy(backupR);
            curB.copy(backupB);

            board.leanRight(curR, curB);
            cacheAndPush(q, cached, curR, curB, cur.cnt);
            curR.copy(backupR);
            curB.copy(backupB);

            board.leanDown(curR, curB);
            cacheAndPush(q, cached, curR, curB, cur.cnt);
            curR.copy(backupR);
            curB.copy(backupB);

            board.leanLeft(curR, curB);
            cacheAndPush(q, cached, curR, curB, cur.cnt);
            curR.copy(backupR);
            curB.copy(backupB);
        }

        return minCnt;
    }

    private int simulate() {
        answer = bfs(redBall, blueBall);
        return answer > 10 ? -1 : answer;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate());
        System.out.println(sb.toString());
    }
}
