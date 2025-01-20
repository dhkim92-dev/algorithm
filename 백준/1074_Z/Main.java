class Solution {

    private int N,r,c;
    private int answer = 0;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        r = Integer.parseInt(tokens[1]);
        c = Integer.parseInt(tokens[2]);
    }

    private void find(int size, int r, int c) {
        if(size == 1) {
            return;
        }

        if(r < size / 2 && c < size / 2) {
            // 1사분면
            find(size / 2, r, c);
        } else if( r < size / 2 && c >= size / 2 ) {
            // 2사분면
            find(size / 2, r, c - size / 2);
            answer += size * size / 4;
        } else if( r >= size / 2 && c < size / 2 ) {
            // 3사분면
            find(size/2, r - size / 2, c);
            answer += size * size / 2;
        } else {
            // 4사분면
            find(size / 2, r - size / 2, c - size / 2);
            answer += size * size / 4 * 3;
        }
    }

    public void run() {
        int size = (int)Math.pow(2, N);
        find(size, r, c);
        System.out.println(answer);
    }
}
