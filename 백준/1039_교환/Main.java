class Solution {

    private int N, K;

    private char[] NArray;

    public static class Case {

        String current;

        int k;

        public Case(String current, int k) {
            this.current = current;
            this.k = k;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        K = Integer.parseInt(tokens[1]);
    }

    private int bfs() {
        Queue<Case> q = new LinkedList<>();
        //Set<Integer> visited = new HashSet<>();
        q.add(new Case(String.valueOf(N), 0));

        int lenLimit = String.valueOf(N).length();
        boolean isPossible = false;
        boolean[][] visited = new boolean[1000001][11];
        Set<Integer> set = new HashSet<>();

        while(!q.isEmpty()) {
            Case c = q.poll();

            if(visited[Integer.parseInt(c.current)][c.k]) {
                continue; // 이미 처리를 한 케이스의 경우 그냥 지나간다.
            }

            if(c.k == K) {
                set.add(Integer.parseInt(c.current));
                continue;
            }

            visited[Integer.parseInt(c.current)][c.k] = true;

            for(int i = 0 ; i < lenLimit ; i++) {
                for(int j = i + 1 ; j < lenLimit ; j++) {
                    if(i == 0 && c.current.charAt(j) == '0') {
                        // 앞자리가 0이 될 순 없다.
                        continue;
                    }

                    char[] next = c.current.toCharArray();
                    char temp = next[i];
                    next[i] = next[j];
                    next[j] = temp;
                    String nextStr = new String(next);
                    if(!visited[Integer.parseInt(nextStr)][c.k + 1]) {
                        q.add(new Case(nextStr, c.k + 1));
                        isPossible = true;
                    }
                }
            }
        }

        if(!isPossible) {
            return -1;
        }

        return set.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);
    }

    public void run() {
        // 숫자 N 의 i번째 자리와 j번째 자리를 바꾸는 연산을 K번 수행하여
        // 만들 수 있는 최대 값을 출력
        // 단, i, j 번째 수를 자리를 바꾼 결과로 만들어진 수는 0으로 시작할 수 없다.
        // 1 <= N <= 10^6
        // 1 <= K <= 10
        StringBuilder sb = new StringBuilder();
        sb.append(bfs());
        System.out.println(sb);
    }
}
