
class Solution {

    private int N, M, K;

    private final int MOD = 1000000007;

    private long[] arr;

    private long[] tree;

    private long[][] commands;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);

        arr = new long[N];

        for(int i = 0 ; i < N ; i++) {
            arr[i] = Long.parseLong(reader.readLine());
        }

        commands = new long[M + K][3];

        int qOffset = 0;
        int mOffset = 0;

        for(int i = 0 ; i < K + M ; i++) {
            tokens = reader.readLine().split(" ");
            long a = Long.parseLong(tokens[0]);
            long b = Long.parseLong(tokens[1]);
            long c = Long.parseLong(tokens[2]);

            commands[i][0] = a;
            commands[i][1] = b;
            commands[i][2] = c;
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
        tree[node] = (tree[node * 2]*tree[node * 2 + 1]) % MOD;
    }

    private long query(int node, int start, int end, int left, int right) {
        if(left > end || right < start) {
            return 1;
        }

        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        long leftValue = query(node * 2, start, mid, left, right);
        long rightValue = query(node * 2 + 1, mid + 1, end, left, right);

        return (leftValue * rightValue) % MOD;
    }

    private void update(int node, int start, int end, int index, int value) {
        if(index < start || index > end) {
            return;
        }

        if(start == end) {
//            arr[index] = value;
            tree[node] = value;
            return;
        }

        int mid = start + (end - start) / 2;
        update(node * 2, start, mid, index, value);
        update(node * 2 + 1, mid + 1, end, index, value);
        tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % MOD;
    }

    public void run() {
        int height = (int)Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = (1 << (height + 1));
//        System.out.println("Node size : " + N + " treeHeight " + height + " treeSize : " + treeSize);
        tree = new long[treeSize];
        initTree(1, 0, N-1);
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < M + K ; i++) {
            long a = commands[i][0];
            long b = commands[i][1];
            long c = commands[i][2];

            if(a == 1) {
                update(1, 0, N-1, (int)(b-1), (int)c);
                arr[(int)(b-1)] = c;
            }else {
                sb.append(query(1, 0, N-1, (int)(b-1), (int)(c-1)))
                        .append("\n");
            }
        }

        System.out.println(sb.toString());
    }
}
