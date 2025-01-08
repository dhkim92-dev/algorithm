
class Solution {

    char[][] board = new char[8][8];
    char[][][] boardSnapshots = new char[9][8][8]; // 보드는 어차피 8번의 시도 이후엔

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos p) {
            return new Pos(r + p.r, c + p.c);
        }
    }

    static class Turn {
        Pos player;
        int turn;

        Turn(Pos player, int turn) {
            this.player = player;
            this.turn = turn;
        }
    }

    Pos[] dirs = {
        new Pos(0, 0), // 현재 위치
        new Pos(0, 1), // 동
        new Pos(1, 0), // 남
        new Pos(0, -1), // 서
        new Pos(-1, 0), // 북,
        new Pos(1, 1), // 남동
        new Pos(1, -1), // 남서
        new Pos(-1, -1), // 북서
        new Pos(-1, 1) // 북동
    };

    Pos player = new Pos(7, 0); // 좌측 하단이 항상 시작점

    public Solution(BufferedReader reader) throws IOException {
        for(int i = 0 ; i < 8 ; i++) {
            board[i] = reader.readLine().toCharArray();
        }
    }

    private boolean isInRange(Pos p) {
        return (p.r >= 0 && p.r < 8) && (p.c >= 0 && p.c < 8);
    }

    private boolean isWall(Pos p, int turn) {
        return boardSnapshots[turn > 8 ? 8: turn][p.r][p.c] == '#';
    }

    private void printAll() {
        for(int i = 0 ; i <= 8 ; i++) {
            //System.out.println("Turn : " + i);
            for(int r = 0 ; r < 8 ; r++) {
                for(int c = 0 ; c < 8 ; c++) {
                    System.out.print(boardSnapshots[i][r][c]);
                }
                System.out.println();
            }
        }
    }

    private int bfs() {
        Queue<Turn> q = new LinkedList<>();
        q.add(new Turn(player, 0));

        while(!q.isEmpty()) {
            Turn cur = q.poll();
            //System.out.println("r : " + cur.player.r + " c : " + cur.player.c + " turn : " + cur.turn);

            if(cur.player.r == 0) {
                return 1;
            }

            for(Pos dir: dirs) {
                Pos nxt = cur.player.add(dir);

                if(!isInRange(nxt)) {
                    continue;
                }

                // 현재 벽인 곳과, 다음턴에 벽인곳은 갈 수 없다
                if(isWall(nxt, cur.turn) || isWall(nxt, cur.turn+1)) {
                    continue;
                }

                q.add(new Turn(nxt, cur.turn + 1));
            }
        }

        return 0;
    }

    private void copy(char[][] src, char[][] dest) {
        for(int i = 0 ; i < 8 ; i++) {
            for(int j = 0 ; j < 8 ; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }

    private void simulateBoard() {
        copy(board, boardSnapshots[0]);

        for(int i = 1 ; i <= 8 ; i++) {
            copy(boardSnapshots[i-1], boardSnapshots[i]); // 이전 상태를 복사한다.

            for(int r = 7 ; r >= 1 ; r--) {
                for(int c = 0 ; c < 8 ; c++) {
                    // 벽을 한줄씩 내린다.
                    boardSnapshots[i][r][c] = boardSnapshots[i][r-1][c];
                }
            }
            //가장 윗줄을 비운다.
            for(int c = 0 ; c < 8 ; c++) {
                boardSnapshots[i][0][c] = '.';
            }
        }
    }

    public void run() {
        simulateBoard(); // 보드가 각 턴에 어떻게 변하는지 미리 구해둔다.
        //printAll();
        int answer = bfs();
        System.out.println(answer);
    }
}
