class Solution {

    private int R, C;

    private int[][] inputs;

    static class Element {
        int r, c;

        Element(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "r=" + r +
                    ", c=" + c +
                    '}';
        }
    }

    Element[] dirs = {
        new Element(-1, 0),
        new Element(1, 0),
        new Element(0, -1),
        new Element(0, 1)
    };

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        inputs = new int[R][C];
        for(int i = 0 ; i < R ; i++) {
            String line = reader.readLine();
            for(int j = 0 ; j < C ; j++) {
                inputs[i][j] = line.charAt(j) - 'A';
            }
        }
    }

    private boolean isInRange(Element e) {
        return e.r >= 0 && e.r < R && e.c >= 0 && e.c < C;
    }

    private int dfs(int r, int c, int bits, int cnt) {
        if(r == R - 1 && c == C - 1) {
            return cnt;
        }
        
        if(cnt == 26) {
            return cnt;
        }

        int max = cnt;

        // 4방향 순차 탐색

        for(Element dir : dirs) {
            Element next = new Element(r + dir.r, c + dir.c);

            if(isInRange(next)) {
                int nextBits = 1 << inputs[next.r][next.c];
                if((bits & nextBits) == 0) {
                    max = Math.max(max, dfs(next.r, next.c, bits | nextBits, cnt + 1));
                }
            }
        }

        return max;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(dfs(0, 0, 1 << inputs[0][0], 1));
        System.out.println(sb);
    }
}
