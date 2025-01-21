
class Solution {

    private int N;

    private int M;

    private int[] arr;

    private int[][] queries;

    static class Node {
        int value;
        int index;

        public Node(int value, int index) {
            this.value = value;
            this.index = index;
        }

        public String toString() {
            return "{ value : " + value + " index : " + index + "}";
        }
    }

    private Node[] tree;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        arr = new int[N];
        String[] tokens = reader.readLine().split(" ");
        for(int i = 0 ; i < N ; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }

        M = Integer.parseInt(reader.readLine());
        queries = new int[M][3];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            queries[i][0] = Integer.parseInt(tokens[0]);
            queries[i][1] = Integer.parseInt(tokens[1]);
            queries[i][2] = Integer.parseInt(tokens[2]);
        }
    }

    private void initTree(int node, int start, int end) {
        if(start == end) {
//            System.out.println("tree[" + node + "] = " + arr[start]);
            tree[node] = new Node(arr[start], start);
            return;
        }

        int mid = start + (end - start) / 2;
        initTree(node*2, start, mid);
        initTree(node*2+1, mid+1, end);
        int minValue = Math.min(tree[node*2].value, tree[node*2+1].value);
        int index = (tree[node*2].value == minValue) ? tree[node*2].index : tree[node*2+1].index;

        tree[node] = new Node(minValue, index);
    }

    private void update(int node, int start, int end, int index, int value) {
        if(start > index || end < index) {
            return ;
        }

        if(start == end) {
            tree[node] = new Node(value, index);
            return;
        }

        int mid = start + (end - start) / 2;

        update(node*2, start, mid, index, value);
        update(node*2+1, mid+1, end, index, value);

        int minValue = Math.min(tree[node*2].value, tree[node*2+1].value);
        int minIndex = (tree[node*2].value == minValue) ? tree[node*2].index : tree[node*2+1].index;

        tree[node] = new Node(
            minValue,
            minIndex
        );
    }

    private Node query(int node, int start, int end, int left, int right) {
//        System.out.println("    node : " + node + " start : " + start + " end : " + end + " left : " + left + " right : " + right);
        if(start > right || end < left) {
            return null;
        }

        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        Node leftNode = query(node*2, start, mid, left, right);
        Node rightNode = query(node*2+1, mid+1, end, left, right);

        if(leftNode == null) {
//            System.out.println("    leftNode is null");
            return rightNode;
        }else if(rightNode == null) {
//            System.out.println("    rightNode is null");
            return leftNode;
        }

        if( leftNode.value < rightNode.value) {
            return leftNode;
        } else if(leftNode.value > rightNode.value) {
            return rightNode;
        } else {
            return leftNode.index < rightNode.index ? leftNode : rightNode;
        }
    }

    public void run() {
        int height = (int)(Math.ceil(Math.log(N)/Math.log(2)));
        int treeSize = (1 << (height + 1));
        tree = new Node[treeSize];
        for(int i = 0 ; i < treeSize ; i++) {
            tree[i] = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        initTree(1, 0, N-1);
        StringBuilder sb = new StringBuilder();

//        System.out.print("arr : ");
//        for(int i =  0 ; i < N ; i++) {
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println();

        for(int i = 0 ; i < M ; i++) {
            int command = queries[i][0];
            int b = queries[i][1];
            int c = queries[i][2];

            if(command == 1) {
                update(1, 0, N-1, b-1, c);
            }else {
//                System.out.println("query command : " + command + " b : " + (b - 1) + " c : " + (c - 1));
                Node node = query(1, 0, N-1, b-1, c-1);
//                System.out.println("query result: " + node);
                sb.append(node.index+1).append("\n");
            }
        }

        System.out.println(sb);
    }
}

