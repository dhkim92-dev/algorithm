import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private int T, n, m;

    private int[] A,B;

    private Map<Integer, Long> aCount, bCount;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        T = Integer.parseInt(reader.readLine());
        n = Integer.parseInt(reader.readLine());
        A = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        m = Integer.parseInt(reader.readLine());
        B = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        aCount = new HashMap<>();
        bCount = new HashMap<>();
    }

    private int[] prefixSum(int[] arr) {
        int sz = arr.length;
        int[] ps = new int[sz + 1];
        for (int i = 1; i <= sz; i++) {
            ps[i] = ps[i - 1] + arr[i - 1];
        }

        return ps;
    }

    private void countAllPartialSums(int[] ps, Map<Integer, Long> countMap) {
        int sz = ps.length;
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                int partialSum = ps[j] - ps[i];
                countMap.put(partialSum, countMap.getOrDefault(partialSum, 0L) + 1L);
            }
        }
    }

    public void run() throws IOException {
        long answer = 0;
        int[] pa = prefixSum(A);
        int[] pb = prefixSum(B);

        // a에서 나올 수 있는 모든 부분합 구하기
        countAllPartialSums(pa, aCount);
        countAllPartialSums(pb, bCount);
        
        // a의 모든 부분합에 대하여 T - partialSum(a)에 해당하는 b의 부분합 개수를 더한다.

        for ( Map.Entry<Integer, Long> entry: aCount.entrySet() ) {
            int value = entry.getKey();
            long cnt = entry.getValue();
            int complement = T - value;

            if ( bCount.containsKey(complement) ) {
                answer += cnt * bCount.get(complement);
            }
        }

        writer.write(String.valueOf(answer));
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
