
class Solution {

    private int N, M;

    private int[] arr;

    private int[][] queries;

    private Node[] tree;

    static class Node {
        int min;
        int max;

        public Node(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }


    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        arr = new int[N];
        queries = new int[M][2];

        for(int i = 0 ; i < N ; i++) {
            arr[i] = Integer.parseInt(reader.readLine());
        }

        for(int i = 0 ; i < M ; i++) {
            queries[i] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
    }

    private void initTree(int node, int start, int end) {
        if(start == end) {
            tree[node] = new Node(arr[start], arr[start]);
            return;
        }

        int mid = start + (end - start) / 2;
        initTree(node * 2, start, mid);
        initTree(node * 2 + 1, mid + 1, end);
        tree[node] = new Node(Math.min(tree[node * 2].min, tree[node * 2 + 1].min), Math.max(tree[node * 2].max, tree[node * 2 + 1].max));
    }

    private Node query(int node, int start, int end, int left, int right) {
        if(left > end || right < start) {
            return new Node(Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        Node leftNode = query(node * 2, start, mid, left, right);
        Node rightNode = query(node * 2 + 1, mid + 1, end, left, right);

        return new Node(Math.min(leftNode.min, rightNode.min), Math.max(leftNode.max, rightNode.max));
    }

    public void run() {
        int treeHeight = (int)Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = 1<<(treeHeight + 1);
        tree = new Node[treeSize];
        initTree(1, 0, N - 1);

        StringBuilder sb = new StringBuilder();

        for(int[] q : queries) {
            Node node = query(1, 0, N - 1, q[0] - 1, q[1] - 1);
            sb.append(node.min).append(" ").append(node.max).append("\n");
        }

        System.out.println(sb.toString());
    }
}
