
class Solution {

    int n, m;

    int[] parents;

    StringBuilder answer;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        n = Integer.parseInt(tokens[0]);
        m = Integer.parseInt(tokens[1]);
        parents = new int[n + 1];

        for(int i = 1; i <= n; i++) {
            parents[i] = i;
        }

        answer = new StringBuilder();

        for(int i = 0 ; i < m ; i++) {
            tokens = reader.readLine().split(" ");
            int a = Integer.parseInt(tokens[0]);
            int b = Integer.parseInt(tokens[1]);
            int c = Integer.parseInt(tokens[2]);

            if(a == 0) {
                unionParent(b, c);
            } else {
                if(findParent(b) == findParent(c)) {
                    answer.append("YES\n");
                } else {
                    answer.append("NO\n");
                }
            }
        }
    }

    private int findParent(int x) {
        if(parents[x] != x) {
            parents[x] = findParent(parents[x]);
        }
        return parents[x];
    }

    private void unionParent(int a, int b) {
        a = findParent(a);
        b = findParent(b);

        if(a < b) {
            parents[b] = a;
        } else {
            parents[a] = b;
        }
    }

    public void run() {
        System.out.println(answer);
    }
}
