
class Solution {

    static class Pos {

        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }
    }

    static class Element {

        Pos p;

        List<Integer> history;

        public Element(Pos p, List<Integer> history) {
            this.p = p;
            this.history = history;
        }
    }


    private int N;

    private int[][] domino; // 도미노 숫자들

    private int[][] tiles; // 타일 번호

    private boolean[][] visited;

    static final Pos[] DIRECTIONS = {
        new Pos(-1, 0),
        new Pos(0, 1),
        new Pos(1, 0),
        new Pos(0, -1),
    };

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        domino = new int[N][2*N];
        tiles = new int[N][2*N];
        visited = new boolean[N][2*N];

        int tileNo = 1;

        for(int i = 0 ; i < N ; i++) {
            boolean even = i % 2 == 0;
            int limit = even ? 2 * N  : 2*N - 1;

            for(int j = even ? 0 : 1 ; j < limit ; j+=2) {
                String[] tokens = reader.readLine().split(" ");
                domino[i][j] = Integer.parseInt(tokens[0]);
                domino[i][j+1] = Integer.parseInt(tokens[1]);
                tiles[i][j] = tiles[i][j+1] = tileNo++;
            }
        }
    }

    private boolean isInRange(Pos p) {
        return p.r >= 0 && p.r < N && p.c >= 0 && p.c < 2*N;
    }

    private List<Integer> search() {
        Queue<Element> q = new LinkedList<>();
        List<Integer> history = new ArrayList<>();
        boolean[][] visited = new boolean[N][N*2];

        q.offer(new Element(new Pos(0, 0), history));
        q.offer(new Element(new Pos(0, 1), history));
        visited[0][0] = visited[0][1] = true;

        int maxTileNum = Integer.MIN_VALUE;
        Element answer = null;
        history.add(1);

        while(!q.isEmpty()) {
            Element e = q.poll();
            Pos p = e.p;
            List<Integer> h = e.history;

            if(tiles[p.r][p.c] > maxTileNum) {
                maxTileNum = tiles[p.r][p.c];
                answer = e;
            }

            for(int i = 0 ; i < 4 ; i++) {
                Pos next = p.add(DIRECTIONS[i]);

                if(!isInRange(next) || visited[next.r][next.c]) {
                    continue;
                }

                if(domino[next.r][next.c] == domino[p.r][p.c]) { // 이동하려는 위치와 현재 위치의 숫자가 같다면 이동 가능
                    visited[next.r][next.c] = true;
                    List<Integer> nextHistory = new ArrayList<>(h);
                    nextHistory.add(tiles[next.r][next.c]);
                    q.offer(new Element(new Pos(next.r, next.c), nextHistory));

                    if(tiles[next.r][next.c] == tiles[next.r][next.c+1]) {
                        visited[next.r][next.c+1] = true;
                        q.offer(new Element(new Pos(next.r, next.c+1), nextHistory));
                        continue;
                    }

                    if(tiles[next.r][next.c] == tiles[next.r][next.c-1]) {
                        visited[next.r][next.c-1] = true;
                        q.offer(new Element(new Pos(next.r, next.c-1), nextHistory));
                    }
                }
            }
        }

        return answer.history;
    }

    private void print(int[][] arr) {
        for(int i = 0 ; i < arr.length ; i++) {
            for(int j = 0 ; j < arr[i].length ; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void run() {
        List<Integer> history = search();
        StringBuilder sb = new StringBuilder();
        sb.append(history.size())
          .append('\n');
        for(int i = 0 ; i < history.size() ; i++) {
            sb.append(history.get(i))
              .append(" ");
        }
        sb.append('\n');
        System.out.println(sb.toString());
    }
}
