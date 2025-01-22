
class Solution {

    private int N;

    private int[] arr;

    private int[] tree;

    private int[][] queries;

    private int[] lazy;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        arr = new int[N];

        String[] tokens = reader.readLine().split(" ");
        for(int i = 0 ; i < N ; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }

        int M = Integer.parseInt(reader.readLine());
        queries = new int[M][4];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            queries[i][0] = Integer.parseInt(tokens[0]);
            queries[i][1] = Integer.parseInt(tokens[1]);
            queries[i][2] = Integer.parseInt(tokens[2]);

            if(queries[i][0] == 1) {
                queries[i][3] = Integer.parseInt(tokens[3]);
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
        tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
    }

    private void updateRange(int node, int start, int end, int left, int right, int value) {
        updateLazy(node, start, end);

        if(left > end || right < start) {
            return;
        }

        if(left <= start && end <= right) {
            tree[node] ^= value * ((end-start+1) %2);

            if(start != end) {
                lazy[node * 2] ^= value;
                lazy[node * 2 + 1] ^= value;
            }

            return;
        }

        int mid = start + (end - start) / 2;
        updateRange(node * 2, start, mid, left, right, value);
        updateRange(node * 2 + 1, mid + 1, end, left, right, value);
        tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
    }

    private void updateLazy(int node, int start, int end) {
        if(lazy[node] != 0) {
            if(start != end) {
                lazy[node * 2] ^= lazy[node];
                lazy[node * 2 + 1] ^= lazy[node];
            }

            if((end - start + 1) % 2 == 1) {
                tree[node] ^= lazy[node];
            }

            lazy[node] = 0;
        }
    }

    private int query(int node, int start, int end, int left, int right) {
        updateLazy(node, start, end);

        if(left > end || right < start) {
            return 0;
        }

        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        int leftvalue = query(node * 2, start, mid, left, right);
        int rightvalue = query(node * 2 + 1, mid + 1, end, left, right);
        return leftvalue ^ rightvalue;
    }

    public void run() {
        // XOR 연산은 분배법칙이 적용된다.

        StringBuilder sb = new StringBuilder();
        int height = (int)Math.ceil( Math.log(N)/Math.log(2));
        int treeSize = (1 << (height + 1));
        tree = new int[treeSize];
        lazy = new int[treeSize];

        initTree(1, 0, N-1);

        for(int[] query : queries) {
            if(query[0] == 1) {
                // 영역에 속한 모든 노드에 query[3] 를 xor한다.
                updateRange(1, 0, N-1, query[1], query[2], query[3]);
            }else {
                sb.append(query(1, 0, N-1, query[1], query[2]))
                  .append("\n");
            }
        }

        System.out.println(sb);
    }
}
