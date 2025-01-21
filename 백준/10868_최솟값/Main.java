class Solution {

    private int N, M;

    private int[] arr;

    private int[][] queries;

    private int[] tree;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        arr = new int[N];
        queries = new int[M][2];

        for(int i = 0 ; i < N ; ++i) {
            arr[i] = Integer.parseInt(reader.readLine());
        }

        for(int i = 0 ; i < M ; ++i) {
            tokens = reader.readLine().split(" ");
            queries[i][0] = Integer.parseInt(tokens[0]);
            queries[i][1] = Integer.parseInt(tokens[1]);
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
        tree[node] = Math.min(tree[node * 2], tree[node * 2 + 1]);
    }

    private int query(int node, int start, int end, int left, int right) {
        if(right < start || end < left) {
            return Integer.MAX_VALUE;
        }
        if(left <= start && end <= right) {
            return tree[node];
        }
        int mid = start + (end - start) / 2;
        return Math.min(query(node * 2, start, mid, left, right), query(node * 2 + 1, mid + 1, end, left, right));
    }

    public void run() {
        int height = (int)(Math.ceil(Math.log(N)/Math.log(2)));
        int treeSize = (1 << (height + 1));
        tree = new int[treeSize];
        StringBuilder sb = new StringBuilder();
        initTree(1, 0, N - 1);

        for(int[] query : queries) {
            sb.append(query(1, 0, N - 1, query[0] - 1, query[1] - 1)).append("\n");
        }

        System.out.println(sb);
    }
}
