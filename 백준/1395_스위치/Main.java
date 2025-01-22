class Solution {

    private int N, M;

    private int[][] queries;

    private int[] tree;

    private int[] lazy;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        queries = new int[M][3];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            queries[i][0] = Integer.parseInt(tokens[0]);
            queries[i][1] = Integer.parseInt(tokens[1]) - 1;
            queries[i][2] = Integer.parseInt(tokens[2]) - 1;
        }
    }

    private void updateRange(int node, int start, int end, int left, int right) {
        updateLazy(node, start, end);

        if(right < start || end < left) {
            return;
        }

        if(left <= start && end <= right) {
            tree[node] = (end - start + 1) - tree[node];
            if(start != end) {
                lazy[node * 2] ^= 1;
                lazy[node * 2 + 1] ^= 1;
            }
            return;
        }

        int mid = (start + end) / 2;
        updateRange(node * 2, start, mid, left, right);
        updateRange(node * 2 + 1, mid + 1, end, left, right);

        tree[node] = tree[node * 2] + tree[node * 2 + 1]; // 켜져있는 자식 스위치의 수
    }

    private void updateLazy(int node, int start, int end) {
        if(lazy[node] != 0) {
            tree[node] = (end - start + 1) - tree[node];
            if(start != end) {
                lazy[node * 2] ^= 1;
                lazy[node * 2 + 1] ^= 1;
            }
            lazy[node] = 0;
        }
    }

    private int query(int node, int start, int end, int left, int right) {
        updateLazy(node, start, end);

        if(right < start || end < left) {
            return 0;
        }

        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        int leftValue = query(node * 2, start, mid, left, right);
        int rightValue = query(node * 2 + 1, mid + 1, end, left, right);

        return leftValue + rightValue;
    }

    public void run() {
        int height = (int)Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = (1 << (height + 1));
        tree = new int[treeSize];
        lazy = new int[treeSize];

        StringBuilder sb = new StringBuilder();

        for(int[] query : queries) {
            int cmd = query[0];
            int a = query[1];
            int b = query[2];

            if(cmd == 0) {
                updateRange(1, 0, N-1, a, b);
            } else {
                sb.append(query(1, 0, N-1, a, b)).append('\n');
            }
        }

        System.out.println(sb);
    }
}
