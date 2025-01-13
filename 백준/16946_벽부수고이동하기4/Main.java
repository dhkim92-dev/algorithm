
class Solution {

    private int R, C;

    private int[][] board;

    private int[][] groupMap;

    private int[][] countMap;

    private boolean[][] visited;

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

    Pos[] dirs = {
        new Pos(-1, 0),
        new Pos(0, 1),
        new Pos(1, 0),
        new Pos(0, -1)
    };

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        board = new int[R][C];
        visited = new boolean[R][C];
        groupMap = new int[R][C];
        countMap = new int[R][C];

        for(int i = 0 ; i < R ; i++) {
            char[] line = reader.readLine().toCharArray();

            for(int j = 0 ; j < C ; j++) {
                board[i][j] = line[j] - '0';
            }
        }
    }

    private int search(Pos p) {
        int possible = 0;
        Set<Integer> searchedGroup = new HashSet<>();

        for(int i = 0 ; i < 4 ; i++) {
            Pos next = p.add(dirs[i]);

            if(!isInRange(next)) {
                continue;
            }

            int group = groupMap[next.r][next.c];
            if(searchedGroup.contains(group)) {
                continue;
            }

            searchedGroup.add(group);
            possible += countMap[next.r][next.c];
        }

        return possible+1;
    }

    private boolean isInRange(Pos p) {
        return p.r >= 0 && p.r < R && p.c >= 0 && p.c < C;
    }

    private void bfs(Pos p, int offset) {
        // 0으로 채워진 연결된 영역을 미리 모두 구한다.
        Queue<Pos> q = new LinkedList<>();
        q.add(p);
        visited[p.r][p.c] = true;
        int count = 0;
        List<Pos> history = new LinkedList<>();

        while(!q.isEmpty()) {
            Pos cur = q.poll();
            history.add(cur);
            groupMap[cur.r][cur.c] = offset;
            count++;

            for(int i = 0 ; i < 4 ; i++) {
                Pos next = cur.add(dirs[i]);

                if(!isInRange(next)) {
                    continue;
                }

                if(visited[next.r][next.c] || board[next.r][next.c] != 0) {
                    continue;
                }

                visited[next.r][next.c] = true;
                q.add(next);
            }
        }

        for(Pos pos : history) {
            countMap[pos.r][pos.c] = count;
        }
    }

    public void run() {
        int groupOffset = 1;
        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                if(board[r][c] == 0 && !visited[r][c]) {
                    bfs(new Pos(r, c), groupOffset++);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for(int r = 0 ; r < R ; r++) {
            for(int c = 0 ; c < C ; c++) {
                if(board[r][c] == 1) {
                    sb.append(search(new Pos(r, c)) % 10);
                } else {
                    sb.append(0);
                }
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
