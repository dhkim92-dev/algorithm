
class Solution {

    private int N;
    private int[][] board;
    private int[][] tmp;
    private boolean[][] merged;
    private Deque<Element> dq = new ArrayDeque<>();
    private Stack<Element> st = new Stack<>();

    static class Element {
        int value;
        boolean merged;

        Element(int value, boolean merged) {
            this.value = value;
            this.merged = merged;
        }
    }

    private void clearArray(int[][] target) {
        for(int[] row : target) {
            Arrays.fill(row, 0);
        }
    }

    private void copyArray(int[][] dst, int[][] src) {
        for(int i = 0 ; i < N ; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, N);
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        board = new int[N][N];
        tmp = new int[N][N];
        merged = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            board[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }

    private void up(int[][] current) {
        int[][] tmp = new int[N][N];

        for(int c = 0 ; c < N ; c++) {
            dq.clear();
            st.clear();

            for(int r = 0 ; r < N ; r++) {
                if(current[r][c] == 0) { continue; }
                dq.addLast(new Element(current[r][c], false));
            }

            if(dq.isEmpty()) {
                continue;
            }

            st.add(dq.pollFirst());

            while(true) {
                Element top = st.peek();

                if(dq.isEmpty()) {
                    break;
                }

                Element next = dq.pollFirst();
                if(next.value == top.value && !top.merged) {
                    st.pop();
                    st.add(new Element(top.value * 2, true));
                } else {
                    st.add(next);
                }
            }

            Stack<Integer> reverse = new Stack<>();
            while(!st.isEmpty()) {
                reverse.add(st.pop().value);
            }

            for(int r = 0 ; r < N ; r++) {
                if(reverse.isEmpty()) {
                    break;
                }
                tmp[r][c] = reverse.pop();
            }
        }

        copyArray(current, tmp);
    }

    private void down(int[][] current) {
        int[][] tmp = new int[N][N];
        for(int c = 0 ; c < N ; c++) {
            dq.clear();
            st.clear();

            for(int r = N - 1 ; r >= 0 ; r--) {
                if(current[r][c] == 0) { continue; }
                dq.addFirst(new Element(current[r][c], false));
            }

            if(dq.isEmpty()) {
                continue;
            }

            st.add(dq.pollLast());

            while(true) {
                Element top = st.peek();

                if(dq.isEmpty()) {
                    break;
                }

                Element next = dq.pollLast();
                if(next.value == top.value && !top.merged) {
                    st.pop();
                    st.add(new Element(top.value * 2, true));
                } else {
                    st.add(next);
                }
            }

            Stack<Integer> reverse = new Stack<>();
            while(!st.isEmpty()) {
                reverse.add(st.pop().value);
            }

            for(int r = 0 ; r < N ; r++) {
                if(reverse.isEmpty()) {
                    break;
                }
                tmp[N - 1 - r][c] = reverse.pop();
            }
        }

        copyArray(current, tmp);
    }

    private void left(int[][] current) {
        int[][] tmp = new int[N][N];
        for(int r = 0 ; r < N ; r++) {
            st.clear();
            dq.clear();

            for(int c = 0 ; c < N ; c++) {
                if(current[r][c] == 0) { continue; }
                dq.addFirst(new Element(current[r][c], false));
            }

            if(dq.isEmpty()) {
                continue;
            }

            st.push(dq.pollLast());

            while(true) {
                Element top = st.peek();

                if(dq.isEmpty()) {
                    break;
                }

                Element next = dq.pollLast();
                if(next.value == top.value && !top.merged) {
                    st.pop();
                    st.push(new Element(top.value * 2, true));
                } else {
                    st.push(next);
                }
            }

            Stack<Integer> reverse = new Stack<>();
            while(!st.isEmpty()) {
                reverse.add(st.pop().value);
            }

            for(int c = 0 ; c < N ; c++) {
                if(reverse.isEmpty()) {
                    break;
                }
                tmp[r][c] = reverse.pop();
            }
        }

        copyArray(current, tmp);
    }

    private void right(int[][] current) {
        int[][] tmp = new int[N][N];
        for(int r = 0 ; r < N ; r++) {
            st.clear();
            dq.clear();

            for(int c = N - 1 ; c >= 0 ; c--) {
                if(current[r][c] == 0) { continue; }
                dq.addLast(new Element(current[r][c], false));
            }

            if(dq.isEmpty()) {
                continue;
            }

            st.push(dq.pollFirst());

            while(true) {
                Element top = st.peek();

                if(dq.isEmpty()) {
                    break;
                }

                Element next = dq.pollFirst();
                if(next.value == top.value && !top.merged) {
                    st.pop();
                    st.push(new Element(top.value * 2, true));
                } else {
                    st.push(next);
                }
            }

            Stack<Integer> reverse = new Stack<>();
            while(!st.isEmpty()) {
                reverse.add(st.pop().value);
            }

            for(int c = 0 ; c < N ; c++) {
                if(reverse.isEmpty()) {
                    break;
                }
                tmp[r][N - 1 - c] = reverse.pop();
            }
        }

        copyArray(current, tmp);
    }

    private void printBoard(int[][] board) {
        System.out.println("Board status");
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int getBiggest(int[][] board) {
        int max = 0;
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                max = Math.max(max, board[i][j]);
            }
        }
        return max;
    }

    private int dfs(int[][] current, int depth) {
        if(depth == 5) {
            return getBiggest(current);
        }

        int max = 0;
        int[][] copy = new int[N][N];

        copyArray(copy, current);
        up(copy);
        max = Math.max(max, dfs(copy, depth + 1));

        copyArray(copy, current);
        down(copy);
        max = Math.max(max, dfs(copy, depth + 1));

        copyArray(copy, current);
        left(copy);
        max = Math.max(max, dfs(copy, depth + 1));

        copyArray(copy, current);
        right(copy);
        max = Math.max(max, dfs(copy, depth + 1));

        return max;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        int answer = 0;
        answer = dfs(board, 0);
        sb.append(answer);
        System.out.println(sb);
    }
}
