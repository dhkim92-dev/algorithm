
class Solution {

    private long N;
    private final int MOD = 1000000;
    private final int PERIOD = MOD / 10 * 15;
    public Solution(BufferedReader reader) throws IOException {
        N = Long.parseLong(reader.readLine());
    }

    public void run() {
        int[] cache = new int[PERIOD];
        cache[0] = 0;
        cache[1] = 1;
        StringBuilder sb = new StringBuilder();

        for(int i = 2 ; i < PERIOD ; i++) {
            cache[i] = (cache[i-1] + cache[i-2]) % MOD;
        }
        sb.append(cache[(int)(N % PERIOD)]);
        sb.append('\n');

        System.out.println(sb);
    }
}
