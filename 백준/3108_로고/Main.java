class Solution {

    private int N;

    private Square[] squares;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        squares = new Square[N+1];
        squares[0] = new Square(0, new Pos(0, 0), new Pos(0, 0));
        int parent = 1;
        for(int i = 1 ; i <= N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            Pos p1 = new Pos(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            Pos p2 = new Pos(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
            squares[i] = new Square(parent++, p1, p2);
        }
    }

    private int find(int x) {
        if(squares[x].parent == x) {
            return x;
        }
        return squares[x].parent = find(squares[x].parent);
    }

    private void union(int a, int b) {
        int x = find(a);
        int y = find(b);

        if(x < y) {
            squares[y].parent = x;
        }else {
            squares[x].parent = y;
        }
    }

    private boolean isIntersected(Square a, Square b) {
        boolean result = true;

        if(b.bl.c > a.bl.c && b.tr.c < a.tr.c && b.bl.r > a.bl.r && b.tr.r < a.tr.r) {
            // A 안에 B가 있는 경우
            result = false;
        }else if(a.bl.c > b.bl.c && a.tr.c < b.tr.c && a.bl.r > b.bl.r && a.tr.r < b.tr.r) {
            // B 안에 A가 있는 경우
            result = false;
        }else if(b.tr.c < a.bl.c || // A의 왼쪽에 B가 있는 경우
            b.bl.c > a.tr.c || // A의 오른쪽에 B가 있는 경우
            a.bl.r > b.tr.r || // B가 A의 아래에 있는 경우
            a.tr.r < b.bl.r //B가 A의 위에 있는 경우
        ) {
            // A의 영역이 B와 겹치지 않는 경우
            result = false;
        }

        return result;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < N ; i++) {
            for(int j = i + 1 ; j <= N ; j++) {
                if(isIntersected(squares[i], squares[j])) {
                    union(i, j);
                }
            }
        }

        Set<Integer> set = new HashSet<>();
        for(int i = 0 ; i <= N ; i++) {
            set.add(find(i));
        }
        sb.append(set.size() - 1);
        sb.append("\n");
        System.out.println(sb);
    }

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

    static class Square {

        int parent;

        Pos bl, tr;

        public Square(int parent, Pos p1, Pos p2) {
            this.parent = parent;
            this.bl = new Pos(Math.min(p1.r, p2.r), Math.min(p1.c, p2.c));
            this.tr = new Pos(Math.max(p1.r, p2.r), Math.max(p1.c, p2.c));
        }
    }
}
