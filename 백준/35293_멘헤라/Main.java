import java.io.*;
import java.util.*;

/**
 * N이 주어진다, N은 10^16 + 1 이하의 실수이며, 소수점 첫자리 까지 주어진다. 
 * 취하는 정도는 9.0, 7.0 , 4.5, -2.0 이다.
 * N을 달성하기 위한 최소한의 섭취 횟수를 구해야한다. 
 * 부동소수점 계산 오차를 줄이기 위해 정수로 변환하여 계산한다. 모두 10을 곱한다. 
 */
class Solution {

    private final BufferedReader reader;
    private final BufferedWriter writer;

    long[] al = { 90L, 70L, 45L, -20L }; // -> 18,14,9,-4

    private long N;

    private long GCD = 5L;

    private long MOD = 4L;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String s = reader.readLine();
        s = s.replace(".", "");
        N = Long.parseLong(s);

        for (int i = 0 ; i < al.length ; ++i ) {
            al[i] /= GCD;
        }
    }

    private void solve() throws IOException {

        // 5로 나누어 떨어지지 않으면 애초에 도달 불가능
        if (N % GCD != 0) {
            writer.write("-1");
            return;
        }

        long target = N / GCD;

        int LIMIT = 500;

        long[] dist = new long[LIMIT + 1];
        Arrays.fill(dist, -1);

        Queue<Integer> q = new ArrayDeque<>();

        q.add(0);
        dist[0] = 0;

        while (!q.isEmpty()) {

            int v = q.poll();

            for (long mv : al) {

                long nv = v + mv;

                if (nv < 0 || nv > LIMIT) continue;
                if (dist[(int) nv] != -1) continue;

                dist[(int) nv] = dist[v] + 1;
                q.add((int) nv);
            }
        }

        long ans = Long.MAX_VALUE;

        for (int v = 0; v <= LIMIT; v++) {

            if (dist[v] == -1) continue;
            if (v > target) continue;

            long diff = target - v;

            if (diff % 18 != 0) continue;

            ans = Math.min(ans, dist[v] + diff / 18);
        }

        if (ans == Long.MAX_VALUE) writer.write("-1");
        else writer.write(String.valueOf(ans));
    }

    public void run() throws IOException {
        solve();
        writer.newLine();
        writer.flush();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}
