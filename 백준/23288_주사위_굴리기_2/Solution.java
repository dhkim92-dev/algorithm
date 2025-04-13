import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, M, K;
    private int[][] board;
    private int[][] count;
    private boolean[][] visited;
    private int diceDir = 0;
    private int[][] dice = {
        {0, 2, 0},
        {4, 1, 3},
        {0, 5, 0},
        {0, 6, 0}
    };
    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {1, 0, -1, 0};
    private int r, c;

    public static class Element {
        int r, c;

        Element(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) return true;
            if ( !(obj instanceof Element) ) return false;
            Element e = (Element)obj;
            return r == e.r && c == e.c;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);

        board = new int[N][M];
        count = new int[N][M];
        visited = new boolean[N][M];

        for ( int i = 0 ; i < N ; i++ ) {
            tokens = reader.readLine().split(" ");
            for ( int j = 0 ; j < M ; j++ ) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        r = 0;
        c = 0;
    }

    private boolean isInRange(int r, int c) {
        return (r >= 0 && r < N && c >= 0 && c < M);
    }

    private void bfs(int r, int c) {
        Queue<Element> q = new LinkedList<>();
        Set<Element> sameNumbers = new HashSet<>();
        visited[r][c] = true;
        q.add(new Element(r, c));
        int curNum = board[r][c];

        while(!q.isEmpty()) {
            Element cur = q.poll();
            sameNumbers.add(cur);

            for ( int i = 0 ; i < 4 ; i++ ) {
                int nr = cur.r + dx[i];
                int nc = cur.c + dy[i];

                if ( !isInRange(nr, nc) ) continue;
                if ( visited[nr][nc] ) continue;
                if ( board[nr][nc] != curNum ) continue;

                Element nxt = new Element(nr, nc);
                sameNumbers.add(nxt);
                visited[nr][nc] = true;
                q.add(nxt);
            }
        }

        int cnt = sameNumbers.size();

        for ( Element e : sameNumbers ) {
            count[e.r][e.c] = cnt;
        }
    }

    private void markSameNumbers() {

        for ( int i = 0 ; i < N ; i++ ) {
            for ( int j = 0 ; j < M ; j++ ) {
                if ( visited[i][j] ) continue;
                bfs(i, j);
            }
        }
    }

    private void moveNorth() {
        // {0, 2, 0},
        // {4, 1, 3},
        // {0, 5, 0},
        // {0, 6, 0}
        //
        // { 0, 1, 0 },
        // { 4, 5, 3 },
        // { 0, 6, 0 },
        // { 0, 2, 0 }
        int tmp = dice[0][1];
        dice[0][1] = dice[1][1];
        dice[1][1] = dice[2][1];
        dice[2][1] = dice[3][1];
        dice[3][1] = tmp;
    }
    
    private void moveSouth() {
        // {0, 2, 0},
        // {4, 1, 3},
        // {0, 5, 0},
        // {0, 6, 0}
        //
        // {0, 6, 0},
        // {4, 2, 3},
        // {0, 1, 0},
        // {0, 5, 0}
        int tmp = dice[3][1];
        dice[3][1] = dice[2][1];
        dice[2][1] = dice[1][1];
        dice[1][1] = dice[0][1];
        dice[0][1] = tmp;
    }

    private void moveWest() {
        // {0, 2, 0},
        // {4, 1, 3},
        // {0, 5, 0},
        // {0, 6, 0}
        //
        // {0, 2, 0},
        // {1, 3, 6},
        // {0, 5, 0},
        // {0, 4, 0}
        int tmp = dice[1][0];
        dice[1][0] = dice[1][1];
        dice[1][1] = dice[1][2];
        dice[1][2] = dice[3][1];
        dice[3][1] = tmp;
    }

    private void moveEast() {
        // {0, 2, 0},
        // {4, 1, 3},
        // {0, 5, 0},
        // {0, 6, 0}
        //
        // {0, 2, 0},
        // {6, 4, 1},
        // {0, 5, 0},
        // {0, 3, 0}
        int tmp = dice[1][2];
        dice[1][2] = dice[1][1];
        dice[1][1] = dice[1][0];
        dice[1][0] = dice[3][1];
        dice[3][1] = tmp;
    }

    private void moveDice() {
        int nr = r + dx[diceDir];
        int nc = c + dy[diceDir];

        if ( !isInRange(nr, nc) ) {
            diceDir = (diceDir + 2) % 4;
            nr = r + dx[diceDir];
            nc = c + dy[diceDir];
        }

        r = nr;
        c = nc;

        if ( diceDir == 0 ) moveEast();
        else if ( diceDir == 1 ) moveSouth();
        else if ( diceDir == 2 ) moveWest();
        else if ( diceDir == 3 ) moveNorth();
    }


    private void rotateDice() {
        int bottomNum = dice[3][1];
        int boardNum = board[r][c];

        if ( bottomNum > boardNum ) {
            diceDir = (diceDir + 1) % 4;
        } else if ( bottomNum < boardNum ) {
            diceDir = (diceDir + 3) % 4;
        }
    }

    public void run () {
        long answer = 0;

        markSameNumbers();

        for ( int i = 0 ; i < K ; i++ ) {
            moveDice();
            rotateDice();
            long score = (long)count[r][c] * (long)board[r][c];
            answer += score;
        }

        System.out.println(answer);
    }
}

