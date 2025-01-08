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
    }

    private char[][] board;
    private String target;
    private int R, C, K;
    private int[][][] dp;

    Pos[] dirs = {
            new Pos(0, 1), // 동
            new Pos(1, 0), // 남
            new Pos(0, -1), // 서
            new Pos(-1, 0) // 북
    };

    public Solution(BufferedReader reader) throws IOException {
        String[] token = reader.readLine().split(" ");
        R = Integer.parseInt(token[0]);
        C = Integer.parseInt(token[1]);
        K = Integer.parseInt(token[2]);

        board = new char[R][C];
        for(int i = 0 ; i < R ; i++) {
            String line = reader.readLine();
            for(int j = 0 ; j < C ; j++) {
                board[i][j] = line.charAt(j);
            }
        }
        target = reader.readLine();
    }

    private boolean isInRange(Pos p) {
        return (p.r>=0 && p.r < R) && (p.c >=0 && p.c < C);
    }

    private int dfs(Pos p, int index) {
        if(dp[p.r][p.c][index] != -1) {
            // 이미 계산된 결과가 있는 경우
            return dp[p.r][p.c][index];
        }

        if(index == target.length() - 1) {
            // target의 마지막 문자에 도달한 경우
            return dp[p.r][p.c][index] = 1;
        }

        dp[p.r][p.c][index] = 0;

        for(int j = 0 ; j < 4 ; j++) {
            Pos stride = dirs[j];
            for(int i = 1 ; i <= K ; i++) {
            // 현재 위치에서 상하좌우 최대 K칸까지 탐색
                Pos next = p.add(new Pos(stride.r * i, stride.c * i));

                if(!isInRange(next)) continue;
                if(board[next.r][next.c] != target.charAt(index + 1)) continue;

                dp[p.r][p.c][index] += dfs(next, index + 1);
            }
        }

        return dp[p.r][p.c][index];
    }

    public void run() {
        int answer = 0;
        dp = new int[R][C][target.length()]; // dp[i][j][k] 보드 상 (i,j)에서 target의 인덱스 k부터의 문자열을 만들 수 있는 경우의 수, -1이면 아직 미방문, 0은 불가능, 1 이상은 가능한 경우

        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if(board[i][j] == target.charAt(0)) {
                    answer += dfs(new Pos(i, j), 0);
                }
            }
        }

        System.out.println(answer);
    }
}
