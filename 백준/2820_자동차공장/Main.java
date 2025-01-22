import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Solution {

    private int N, M, offset=0;
    private int[] arr;
    private int[] rangeStart;
    private int[] rangeEnd;
    private long[] tree;
    private long[] lazy;
    private List<Integer>[] subordinates;
    private char[] commands;
    private int[][] args;

    public Solution(BufferedReader reader) throws IOException {
        String[] input = reader.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        arr = new int[N + 1];
        rangeStart = new int[N + 1];
        rangeEnd = new int[N + 1];
        subordinates = new List[N + 1];
        commands = new char[M];
        args = new int[M][2];

        for(int i = 1 ; i <= N ; i++) {
            subordinates[i] = new ArrayList<>();
        }

        arr[1] = Integer.parseInt(reader.readLine());

        for(int i = 2 ; i <= N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            arr[i] = Integer.parseInt(tokens[0]);
            subordinates[Integer.parseInt(tokens[1])].add(i);
        }

        for(int i = 0 ; i < M ; i++) {
            String[] tokens = reader.readLine().split(" ");
            commands[i] = tokens[0].charAt(0);
            args[i][0] = Integer.parseInt(tokens[1]);
            if(commands[i] == 'p') {
                args[i][1] = Integer.parseInt(tokens[2]);
            }
        }
    }

    private void dfs(int node) {
        rangeStart[node] = ++offset;

        for(int i = 0 ; i < subordinates[node].size() ; i++) {
            dfs(subordinates[node].get(i));
        }

        rangeEnd[node] = offset;
    }

    private void update(int node, int start, int end, int left, int right, int delta) {
        propagate(node, start, end);
        if(start > right || end < left) {
            return;
        }
        if(left <= start && end <= right) {
            tree[node] += delta;
            if (start != end) {
                lazy[node * 2] += delta;
                lazy[node * 2 + 1] += delta;
            }
            return;
        }

        int mid = start + (end - start) / 2;
        update(node * 2, start, mid, left, right, delta);
        update(node * 2 + 1, mid + 1, end, left, right, delta);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    private long query(int node, int start, int end, int left, int right) {
        propagate(node, start, end);
        if(start > right || end < left) {
            return 0;
        }
        if(left <= start && end <= right) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);
    }

    private void propagate(int node, int start, int end) {
        if(lazy[node] != 0) {
            tree[node] += lazy[node];
            if(start != end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }
            lazy[node] = 0;
        }
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        int height = (int)Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = (1 << (height + 1));
        tree = new long[treeSize];
        lazy = new long[treeSize];

        dfs(1); // 직원 트리에서 각 직원의 부하(자신 포함) range 설정

        for(int i = 1 ; i <= N ; i++) {
            // 리프노드를 월급으로 초기화
            update(1, 1, N, rangeStart[i], rangeStart[i], arr[i]);
        }

        for(int i = 0 ; i < M ;i++) {
            char cmd = commands[i];
            int node = args[i][0];

            if(cmd == 'p') {
                int delta = args[i][1];
                update(1, 1, N, rangeStart[node]+1, rangeEnd[node], delta);
            } else {
                sb.append(query(1, 1, N, rangeStart[node], rangeStart[node])).append("\n");
            }
        }

        System.out.println(sb);
    }
}


