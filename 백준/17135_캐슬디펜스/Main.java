
class Solution {

    private int N, M, D;

    private int answer;

    static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Pos) {
                Pos pos = (Pos)obj;
                return r == pos.r && c == pos.c;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 31 * r + c;
        }
    }

    static class Board {
        int[][] board;
        int R, C;

        public Board(int R, int C) {
            this.R = R;
            this.C = C;
            board = new int[R][C];
        }

        static Board copy(Board src) {
            Board newBoard = new Board(src.R, src.C);

            for(int i = 0 ; i < src.R ; i++) {
                for(int j = 0 ; j < src.C ; j++) {
                    newBoard.board[i][j] = src.board[i][j];
                }
            }
            return newBoard;
        }
    }

    private Board initialBoard;

    public Solution(BufferedReader reader) throws IOException {
        StringTokenizer st = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        initialBoard = new Board(N+1, M);

        for(int i = 0 ; i < N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            for(int j = 0 ; j < M ; j++) {
                initialBoard.board[i][j] = Integer.parseInt(tokens[j]);
            }
        }
    }

    private Set<Pos> setArchers(boolean[] selected, Board board) {
        Set<Pos> archers = new HashSet<>();

        for(int i = 0 ; i < M ; i++) {
            if(selected[i]) {
                board.board[N][i] = -1;
                archers.add(new Pos(N, i));
            }
        }

        return archers;
    }

    private int getDist(Pos archer, int r, int c) {
        return Math.abs(archer.r - r) + Math.abs(archer.c - c);
    }

    private int shoot(Board board, Set<Pos> archers) {
        Set<Pos> attacked = new HashSet<>();

        for(Pos archer : archers) {
            // 궁수의 사격 반경 내 가까운 순으로 적을 나열하되, 거리가 같다면 좌측에 있는 적을 우선 공격한다.
            PriorityQueue<Pos> pq = new PriorityQueue<>((a, b) -> {
                int distA = getDist(archer, a.r, a.c);
                int distB = getDist(archer, b.r, b.c);
                if(distA == distB) {
                    return a.c - b.c;
                }
                return distA - distB;
            });

            for(int i = N - 1 ; i >= 0 ; i--) {
                for(int j = 0 ; j < M ; j++) {
                    if(board.board[i][j] == 0) continue; // 적이 없음
                    if(getDist(archer, i, j) > D) continue; // 사거리 밖
                    pq.add(new Pos(i, j));
                }
            }

            if(!pq.isEmpty()) {
                attacked.add(pq.poll());
            }
        }

        for(Pos pos : attacked) {
            board.board[pos.r][pos.c] = 0;
        }

        return attacked.size();
    }

    private void moveEnemy(Board board) {
        for(int i = N - 1 ; i > 0 ; i--) {
            for(int j = 0 ; j < M ; j++) {
                board.board[i][j] = board.board[i-1][j];
            }
        }
        Arrays.fill(board.board[0], 0);
    }

    private void play(boolean[] selected) {
        Board board = Board.copy(initialBoard);
        Set<Pos> archers = setArchers(selected, board);
        int killCount=0;

        for(int i = 0 ; i < N ; i++) {
            int kill = shoot(board, archers);
            moveEnemy(board);
            killCount += kill;
        }

//        if(answer < killCount) {
//            System.out.println("killCount : " + killCount);
//             archer Pos 출력
//            System.out.println("archer Pos");
//            for(Pos pos : archers) {
//                System.out.println(pos.r + " " + pos.c);
//            }
//        }

        answer = Math.max(answer, killCount);
    }

    private void dfs(int start, int depth, boolean[] selected) {
        if(depth == 2) {
            play(selected);
        }

        for(int i = start + 1 ; i < M ; i++) {
            selected[i] = true;
            dfs(i, depth + 1, selected);
            selected[i] = false;
        }
    }

    public void run() {
        boolean[] selected = new boolean[M];
        answer = Integer.MIN_VALUE;

        for(int i = 0 ; i < M ; i++) {
            Arrays.fill(selected, false);
            selected[i] = true;
            dfs(i, 0, selected);
        }

        System.out.println(answer);
    }
}
