class Solution {

    private int N;
    private int[] inputs;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        inputs = new int[N];

        for(int i = 0 ; i < N ; i++) {
            inputs[i] = Integer.parseInt(reader.readLine());
        }
    }

    private void dfs(int depth, int num, int sum, int op, String exp, StringBuilder sb) {
        if(depth == N) {
            sum += num*op;
            if(sum == 0) {
                sb.append(exp).append("\n");
            }
            return;
        }

        dfs(depth + 1, num * 10 + (depth + 1), sum, op, exp + " " + (depth + 1), sb);
        dfs(depth + 1, depth + 1, sum + num * op, 1, exp + "+" + (depth + 1), sb);
        dfs(depth + 1, depth + 1, sum + num * op, -1, exp + "-" + (depth + 1), sb);
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < inputs.length ; i++) {
            List<String> exps = new ArrayList<>();
            dfs(1, 1, 0, 1, "1", sb);
        }

        System.out.println(sb);
    }
}
