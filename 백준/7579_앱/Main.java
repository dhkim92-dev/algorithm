class Solution {

    int N, M;
    int[] memory;
    int[] costs;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);

        memory = new int[N+1];
        costs = new int[N+1];

        line = reader.readLine().split(" ");

        for(int i = 1 ; i <= N ; i++) {
            memory[i] = Integer.parseInt(line[i-1]);
        }

        line = reader.readLine().split(" ");
        for(int i = 1 ; i <= N ; i++) {
            costs[i] = Integer.parseInt(line[i-1]);
        }
    }

    public void run() {
        // M 의 용량을 확보하기 위해 N개의 어플리케이션 중 임의의 어플리케이션을 삭제할 때 필요한 최소 비용을 구하라.
        // dp[i][j] => i 번째 앱까지 탐색했을 때, j 비용을 이용해 확보할 수 있는 최대 메모리 량
        int answer = 0;
        int[][] dp = new int[N+1][10001];
        int sum = Arrays.stream(costs).sum();

        for(int i = 1 ; i <= N ; i++) {
            int mem = memory[i];
            int cost = costs[i];
            for(int j = 0 ; j <= sum ; j++) {
                if(j - cost >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - cost] + mem);
                }
                dp[i][j] = Math.max(dp[i][j], dp[i-1][j]);
            }
        }

        for(int i = 0 ; i<= sum ; i++) {
            if(dp[N][i] >= M) {
                answer = i;
                break;
            }
        }

        System.out.println(answer);
    }
}
