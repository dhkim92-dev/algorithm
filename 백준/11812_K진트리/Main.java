class Solution {
    long N; // 1 <= N <= 10^15
    long K; // 1 <= K <= 100
    long Q; // 1 <= Q <= 100,000

    long[][] pairs;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        N = Long.parseLong(line[0]);
        K = Long.parseLong(line[1]);
        Q = Long.parseLong(line[2]);
        pairs = new long[(int)Q][2];
        for(int i = 0 ; i < Q ; i++) {
            String[] tokens = reader.readLine().split(" ");
            pairs[i][0] = Long.parseLong(tokens[0]);
            pairs[i][1] = Long.parseLong(tokens[1]);
        }
    }

    long getDepth(long a) {
        if(K == 1) return a;

        if(a == 0) return 0;

        long depth = 1;
        long left = 1;
        long right = K;
        while(!(left <= a && a <= right)) {
            left = right + 1;
            right = right * K + K;
            depth++;
        }

        return depth;
    }

    // K진 트리일 때 현재 노드 a의 부모를 반환한다.
    long getParent(long a) {
        // 루트 노드는 0이다.
        if(a == 0) return 0;

        return (a-1)/K;
    }


    long calcDist(long a, long b) {
        long depthA = getDepth(a);
        long depthB = getDepth(b);
        long tmp = 0;

        if(K == 1) {
            return Math.abs(a-b);
        }

        if(depthA > depthB) {
            tmp = depthA;
            depthA = depthB;
            depthB = tmp;
            tmp = a;
            a = b;
            b = tmp;
        }
        int count = 0;
        //깊이를 동일하게 맞춘다.
        while(depthA < depthB) {
            b = getParent(b);
            depthB--;
            count++;
        }

        if(a == b) return count;

        while(a != b) {
            a = getParent(a);
            b = getParent(b);
            count += 2;
        }

        return count;
    }

    public void run() {
        for(long[] pair: pairs) {
            System.out.println(calcDist(pair[0]-1, pair[1]-1));
        }
    }
}
