class Solution {

    private int N, M, K;

    private int[][] board;

    static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }
    }

    static class Turn implements Comparable<Turn> {
        Pos pos;
        int breakCount;
        int moveCount;

        public Turn(Pos pos, int breakCount, int moveCount) {
            this.pos = pos;
            this.breakCount = breakCount;
            this.moveCount = moveCount;
        }

        @Override
        public int compareTo(Turn o) {
            return this.moveCount - o.moveCount;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);
        board = new int[N][M];

        for(int i = 0 ; i < N ; i++) {
            char[] line = reader.readLine().toCharArray();
            for(int j = 0 ; j < M ; j++) {
                board[i][j] = (int)(line[j] - '0');
            }
        }
    }

    private boolean isInRange(Pos pos) {
        return pos.r >= 0 && pos.r < N && pos.c >= 0 && pos.c < M;
    }

    private int bfs() {
        Pos start = new Pos(0, 0);
        Pos goal = new Pos(N - 1, M - 1);
        Pos[] directions = new Pos[] {new Pos(0, 1), new Pos(0, -1), new Pos(1, 0), new Pos(-1, 0)};
        Queue<Turn> q = new LinkedList<>();
        boolean[][][] visited = new boolean[N][M][K + 1];
        q.offer(new Turn(start, 0, 1));

        while(!q.isEmpty()) {
            Turn turn = q.poll();
            Pos pos = turn.pos;
            int breakCount = turn.breakCount;
            int moveCount = turn.moveCount;

            if(pos.r == goal.r && pos.c == goal.c) {
                return moveCount;
            }

            for(Pos direction : directions) {
                Pos nextPos = pos.add(direction);

                if(!isInRange(nextPos)) {
                    continue;
                }

                if( board[nextPos.r][nextPos.c] == 0 && !visited[nextPos.r][nextPos.c][breakCount]) {
                    visited[nextPos.r][nextPos.c][breakCount] = true;
                    q.offer(new Turn(nextPos, breakCount, moveCount + 1));
                }

                if( board[nextPos.r][nextPos.c] == 1 && breakCount < K && !visited[nextPos.r][nextPos.c][breakCount + 1]) {
                    visited[nextPos.r][nextPos.c][breakCount + 1] = true;
                    q.offer(new Turn(nextPos, breakCount + 1, moveCount + 1));
                }
            }
        }

        return -1;
    }

    public void run() {
        int answer = bfs();
        System.out.println(answer);
    }
}
