class Solution {

    private int N, M, H;

    private boolean[][] visited;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        H = Integer.parseInt(tokens[2]);

        visited = new boolean[N+2][H+1];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int a = Integer.parseInt(tokens[0]);
            int b = Integer.parseInt(tokens[1]);
            visited[b][a] = true;
        }
    }

    private boolean playGame() {
        boolean result = true;

        for(int i = 1 ; i <= N ; i++) {
            int cur = i;

            for(int j = 1 ; j <= H ; j++) {
                if(visited[cur][j]) {
                    cur++;
                } else if(visited[cur-1][j]) {
                    cur--;
                }
            }

            if(cur != i) {
                return false;
            }
        }

        return result;
    }

    private boolean combination(int depth, int from, int limit) {
        if(depth == limit) {
             return playGame();
        }

        boolean result = false;
        for(int i = from ; i < N ; i++) {
            for(int j = 1 ; j <= H ; j++) {
                if(visited[i][j] || visited[i-1][j] || visited[i+1][j]) {
                    continue;
                }

                visited[i][j] = true;
                result |= combination(depth + 1, i, limit);
                visited[i][j] = false;
                if(result) {
                    break;
                }
            }
        }

        return result;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        int minValue = Integer.MAX_VALUE;

        for(int tryCount = 0 ; tryCount <= 3 ; tryCount++) {
            if(combination(0, 1, tryCount)) {
                minValue = tryCount;
                break;
            }
        }


        sb.append(minValue == Integer.MAX_VALUE ? -1 : minValue);
        System.out.println(sb.toString());
    }
}
