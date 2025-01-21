class Solution {

    private int N, M, K;

    private long[] arr;

    private long[][] queries;

    private long[] lazy;

    private long[] tree;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);

        arr = new long[N];

        for(int i = 0 ; i < N ; i++) {
            arr[i] = Long.parseLong(reader.readLine());
        }

        queries = new long[M + K][4];

        for(int i = 0 ; i < M + K ; i++) {
            tokens = reader.readLine().split(" ");
            queries[i][0] = Long.parseLong(tokens[0]);
            queries[i][1] = Long.parseLong(tokens[1]);
            queries[i][2] = Long.parseLong(tokens[2]);

            if(queries[i][0] == 1) {
                queries[i][3] = Long.parseLong(tokens[3]);
            }
        }
    }

    private void initTree(int node, int start, int end) {
        if(start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = start + (end - start) / 2;

        initTree(node * 2, start, mid);
        initTree(node * 2 + 1, mid + 1, end);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    private void updateRange(int node, int start, int end, int left, int right, long diff) {
        updateLazy(node, start, end);
        if(left > end || right < start) {
            return;
        }

        if(left <= start && end <= right) {
            tree[node] += (end - start + 1) * diff;

            if(start != end) {
                lazy[node * 2] += diff;
                lazy[node * 2 + 1] += diff;
            }

            return;
        }

        int mid = start + (end - start) / 2;
        updateRange(node * 2, start, mid, left, right, diff);
        updateRange(node * 2 + 1, mid + 1, end, left, right, diff);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    private void updateLazy(int node, int start, int end){
        if(lazy[node] != 0) {
            tree[node] += (end - start + 1) * lazy[node];

            if(start != end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }

            lazy[node] = 0;
        }
    }

    private long query(int node, int start, int end, int left, int right) {
        updateLazy(node, start, end);

        if(left > end || right < start) {
            return 0;
        }

        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);
    }

    public void run() {
        int height = (int)Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = (int)(1 << (height + 1));

        tree = new long[treeSize];
        lazy = new long[treeSize];

        initTree(1, 0, N-1);
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < M + K ; i++) {
            long command = queries[i][0];

            if(command == 1L) {
                updateRange(1, 0, N-1, (int)queries[i][1] - 1, (int)queries[i][2] - 1, queries[i][3]);
            } else {
                sb.append(query(1, 0, N-1, (int)queries[i][1] - 1, (int)queries[i][2] - 1)).append("\n");
            }
        }

        System.out.println(sb);
    }
}
