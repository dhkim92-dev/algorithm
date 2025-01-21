
class Solution {

    private int N, treeHeight, treeSize;

    private Node[] arr;

    private int[] tree;

    static class Node {
        int value;
        int index;

        public Node(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());

        String[] tokens = reader.readLine().split(" ");
        arr = new Node[N];

        for(int i = 0 ; i < N ; i++) {
            arr[i] = new Node(Integer.parseInt(tokens[i]), i);
        }

    }

    private long query(int node, int start, int end, int left, int right) {
        if(left > end || right < start) { // 겹치지 않는 경우
            return 0;
        }

        if(left <= start && end <= right) { // left - right가 start, end를 포함하는 경우(현재 노드가 포함되는 경우)
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        long leftSum = query(node * 2, start, mid, left, right);
        long rightSum = query(node * 2 + 1, mid + 1, end, left, right);

        return leftSum + rightSum;
    }

    private void update(int node, int start, int end, int index) {
        if(start == end) {
            tree[node] = 1;
            return;
        }
        int mid = start + (end - start) / 2;
        if(index <= mid) update(node*2, start, mid, index);
        else update(node*2 + 1, mid + 1, end, index);

        tree[node] = tree[node*2] + tree[node*2 + 1];
    }

    private void printArr() {
        System.out.println("printArr  :  ");
        for(int i = 0 ; i < N ; i++) {
            System.out.println(arr[i].value + " " + arr[i].index);
        }
    }

    public void run() {
        Arrays.sort(arr, (a, b) -> a.value - b.value);

        int treeHeight = (int)Math.ceil(Math.log(N)/Math.log(2));
        int treeSize = (1 << (treeHeight + 1));

//        System.out.println("입력 크기 : " + arr.length);
//        System.out.println("트리 높이 : " + treeHeight);
//        System.out.println("트리 크기 : " + treeSize);
//        printArr();
        long answer = 0;
        tree = new int[treeSize];

        for(int i = 0 ; i < N ; i++) {
            int nodeIndex = arr[i].index;
            answer += query(1, 0, N - 1, nodeIndex, N - 1);
            update(1, 0, N - 1, nodeIndex);
        }

        System.out.println(answer);
    }
}
