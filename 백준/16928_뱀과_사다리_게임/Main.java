class Solution {

    private int N, M;
    private int answer = Integer.MAX_VALUE - 2;
    private Map<Integer, Integer> ladders;
    private Map<Integer, Integer> snakes;
    private long dp[] = new long[101];

    protected Solution() {

    }

    public Solution(BufferedReader reader) throws IOException {
        ladders = new HashMap<>();
        snakes = new HashMap<>();
        String[] nm = reader.readLine().split(" ");
        N = Integer.parseInt(nm[0]);
        M = Integer.parseInt(nm[1]);

        Arrays.fill(dp, Integer.MAX_VALUE);

        for(int i = 0 ; i < N ; i++) {
            String[] ladder = reader.readLine().split(" ");
            ladders.put(Integer.parseInt(ladder[0]), Integer.parseInt(ladder[1]));
        }

        for(int i = 0 ; i < M ; i++) {
            String[] snake = reader.readLine().split(" ");
            snakes.put(Integer.parseInt(snake[0]), Integer.parseInt(snake[1]));
        }
    }

    public void run() {
        // goal은 10x10 보드에서 100번째 칸
        // ladder는 번호가 증가하는 방향
        // snake는 번호가 감소하는 방향
        // 주사위는 1~6까지의 눈을 가짐
        dp[0] = 0;
        simulate(0, 1);
        System.out.println(dp[100]);
    }

    private void simulate(int turn, int current) {
        //System.out.println("turn : " + turn + " current : " + current);
        if(ladders.containsKey(current)) {
            int dest = ladders.get(current);
            if(dp[dest] > turn) {
                dp[dest] = turn;
                simulate(turn, dest);
            }
        } else if(snakes.containsKey(current)) {
            int dest = snakes.get(current);
            if(dp[dest] > turn) {
                dp[dest] = turn;
                simulate(turn, dest);
            }
        } else {
            for (int i = 1; i <= 6; i++) {
                if (current + i > 100) {
                    break;
                }
                if (dp[current + i] > turn + 1) {
                    dp[current + i] = turn + 1;
                    simulate(turn + 1, current + i);
                }
            }
        }
    }
}
