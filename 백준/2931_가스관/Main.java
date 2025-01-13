
class Solution {

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj == null) return false;
            if(!(obj instanceof Pos)) return false;
            return r == ((Pos)obj).r && c == ((Pos)obj).c;
        }

        @Override
        public int hashCode() {
            return 31 * r + c;
        }
    }

    int R, C;
    int[][] board;
    List<Pos>[] dirs;
    // BLOCKS
    // 1 -> VERTICAL
    // 2 -> HORIZONTAL
    // 3 -> CROSS
    // 4 -> LEFT_TOP_CORNER
    // 5 -> LEFT_BOTTOM_CORNER
    // 6 -> RIGHT_BOTTOM_CORNER
    // 7 -> RIGHT_TOP_CORNER

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        board = new int[R][C];
        dirs = new ArrayList[8];
        for(int i = 0 ; i < 8 ; i++) {
            dirs[i] = new ArrayList<>();
        }

        dirs[1].addAll(Arrays.asList(new Pos(-1, 0), new Pos(1, 0))); // VERTICAL
        dirs[2].addAll(Arrays.asList(new Pos(0, -1), new Pos(0, 1))); // HORIZONTAL
        dirs[3].addAll(Arrays.asList(new Pos(-1, 0), new Pos(1, 0), new Pos(0, -1), new Pos(0, 1))); // CROSS
        dirs[4].addAll(Arrays.asList(new Pos(0, 1), new Pos(1, 0))); // LEFT_TOP_CORNER
        dirs[5].addAll(Arrays.asList(new Pos(0, 1), new Pos(-1, 0))); // LEFT_BOTTOM_CORNER
        dirs[6].addAll(Arrays.asList(new Pos(0, -1), new Pos(-1, 0))); // RIGHT_BOTTOM_CORNER
        dirs[7].addAll(Arrays.asList(new Pos(0, -1), new Pos(1, 0))); // RIGHT_TOP_CORNER

        for(int i = 0 ; i < R ; i++) {
            char[] line = reader.readLine().toCharArray();
            for(int j = 0 ; j < C ; j++) {
                switch(line[j]) {
                    case '|':
                        board[i][j] = 1;
                        break;
                    case '-':
                        board[i][j] = 2;
                        break;
                    case '+':
                        board[i][j] = 3;
                        break;
                    case '1':
                        board[i][j] = 4;
                        break;
                    case '2':
                        board[i][j] = 5;
                        break;
                    case '3':
                        board[i][j] = 6;
                        break;
                    case '4':
                        board[i][j] = 7;
                        break;
                    case 'M':
                        board[i][j] = 99;
                        break;
                    case 'Z':
                        board[i][j] = 100;
                        break;
                    default:
                        board[i][j] = 0;
                        break;
                }
            }
        }
    }

    private Pos searchEmptyPos(Pos start, Pos goal) {
        Queue<Pos> q = new LinkedList<>();
        boolean visited[][] = new boolean[R][C];
        visited[start.r][start.c] = true;
        Pos emptyPos = null;
        Pos[] allDir = new Pos[] {new Pos(-1, 0), new Pos(1, 0), new Pos(0, -1), new Pos(0, 1)};

        // 보드에서 시작지나 출발지, 비어있는 칸이 아닌 아무 파이프에 해당하는 지점을 하나 찾는다
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if(board[i][j] != 0 && board[i][j] != 99 && board[i][j] != 100) {
                    q.offer(new Pos(i, j));
                    break;
                }
            }
        }

        while(!q.isEmpty()) {
            Pos cur = q.poll();

            for(Pos dir: dirs[board[cur.r][cur.c]]) {
                Pos next = cur.add(dir);

                if(next.r < 0 || next.r >= R || next.c < 0 || next.c >= C) {
                    continue;
                }

                if(visited[next.r][next.c]) {
                    continue;
                }

                if(next.equals(goal)) {
                    continue;
                }

                if(board[next.r][next.c] == 0) {
                    emptyPos = next;
                    break;
                }

                visited[next.r][next.c] = true;
                q.offer(next);
            }
        }

        return emptyPos;
    }

    private int searchNeedBlock(Pos emptyPos) {
        int block = 0;
        List<Pos> requiredDirs = new ArrayList<>();
        Pos[] allDir = new Pos[] {
                new Pos(-1, 0),
                new Pos(0, 1),
                new Pos(1, 0),
                new Pos(0, -1)
        };
        // 비어있는 자리에서 4방향으로 인접한 칸들을 검사한다.
        // 검사한 칸에 파이프가 존재하고, 그 파이프가 비어진 자리로 들어오는 방향이 존재하는지 확인한다.
        // 존재한다면 해당 디렉션을 true로 설정한다.
        // 이 과정이 끝나고 나면 연결이 필요한 방향의 리스트에 해당하는 파이프를 찾아 반환한다.

        for(int i = 0 ; i < 4 ; i++) {
            Pos adj = emptyPos.add(allDir[i]);

            if(adj.r < 0 || adj.r >= R || adj.c < 0 || adj.c >= C) {
                continue;
            }

            if(board[adj.r][adj.c] == 0) {
                continue;
            }
            
            if(board[adj.r][adj.c] >= 99) {
                continue;
            }

            for(Pos dir: dirs[board[adj.r][adj.c]]) {
                Pos to = adj.add(dir);

                if(to.equals(emptyPos)) {
                    requiredDirs.add(new Pos(-dir.r, -dir.c));
                }
            }
        }

        if(requiredDirs.size() == 4) {
            return 3;
        }

        for(int i = 1 ; i <= 7 ; i++) {
            if(i==3) { continue; }
            if(dirs[i].size() == requiredDirs.size() && dirs[i].containsAll(requiredDirs)) {
                block = i;
                break;
            }
        }

        return block;
    }

    public void run() {
        Pos start = null, goal = null;
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if(board[i][j] == 99) {
                    start = new Pos(i, j);
                } else if(board[i][j] == 100) {
                    goal = new Pos(i, j);
                }
            }
        }

        Pos empty = searchEmptyPos(start, goal);
        int block = searchNeedBlock(empty);
        StringBuilder sb = new StringBuilder();
        sb.append(empty.r + 1).append(" ").append(empty.c + 1).append(" ");
        switch(block) {
            case 1:
                sb.append("|");
                break;
            case 2:
                sb.append("-");
                break;
            case 3:
                sb.append("+");
                break;
            case 4:
                sb.append("1");
                break;
            case 5:
                sb.append("2");
                break;
            case 6:
                sb.append("3");
                break;
            case 7:
                sb.append("4");
                break;
            default:
                break;
        }
        sb.append("\n");

        System.out.println(sb);
    }
}
