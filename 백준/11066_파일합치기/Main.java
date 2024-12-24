
class Solution {

    private int nrTestCases;

    private long[] answers;

    protected Solution() {
    }

    public Solution(BufferedReader reader) throws IOException {
        nrTestCases = Integer.parseInt(reader.readLine());
        answers = new long[nrTestCases];

        for(int i = 0 ; i < nrTestCases ; i++) {
            int nrChapters = Integer.parseInt(reader.readLine());
            int[] szChapters = new int[nrChapters+1];
            String[] line = reader.readLine().split(" ");

            for(int j = 0 ; j < nrChapters ; j++) {
                szChapters[j+1] = Integer.parseInt(line[j]);
            }

            solve(i, nrChapters, szChapters);
        }
    }

    private void prefixSum(long[] psum, int[] szChapters) {
        for(int i = 1 ; i < szChapters.length ; i++) {
            psum[i] = psum[i-1] + szChapters[i];
        }
    }

    private void solve(int idx, int nrChapters, int[] szChapters) {
        int totalCost = 0;
        // 제약 조건, 합친 파일들을 구성하는 챕터들은 연속적이어야 한다.
        // dp[i][j] => i번째 챕터부터 j번째 챕터까지 합친 경우 최소 비용
        long[][] dp = new long[nrChapters+1][nrChapters+1];
        for(long[] row : dp){
            Arrays.fill(row, Long.MAX_VALUE);
        }
        for(int i = 0 ; i <= nrChapters ; i++) {
            dp[0][i] = 0;
        }
//
        for(int i = 1 ; i <= nrChapters ; i++) {
            dp[i][i] = szChapters[i];
        }

        long[] psum = new long[nrChapters+1];
        prefixSum(psum, szChapters);
        long minCost = calcCost(1, nrChapters, dp, psum);

        answers[idx] = (long)minCost;
    }

    private long calcCost(int from, int to, long[][] dp, long[] psum) {
        // 우선 하나의 챕터 될 때 까지 분할 되었다면, 현재 챕터 비용을 반환한다.
        if(from == to) {
            return psum[from] - psum[to];
        }

        if(dp[from][to] != Long.MAX_VALUE) {
            return dp[from][to];
        }

        // 그렇지 않다면 파일을 계속 분할하여, 해당 부분 파일들의 비용 합을 구한다.
        for(int i = from ; i < to ; i++) {
            long leftCost = psum[i] - psum[from-1];
            long rightCost= psum[to] - psum[i];

            long cost = leftCost + rightCost + calcCost(from, i, dp, psum) + calcCost(i+1, to, dp, psum);

            if(dp[from][to] > cost) {
                dp[from][to] = cost;
            }
        }

        return dp[from][to];
    }

    public void run() {
        for(int i = 0 ; i < nrTestCases ; i++) {
            System.out.println(answers[i]);
        }
    }

