import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private int N, M;

    private int[] arr;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        arr = new int[N];

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(reader.readLine());
        }
    }

    public void run() throws IOException {
        /**
         * 1. 입력 배열을 정렬한다.
         * 2. 투 포인터를 이용하여 차이가 M 이상이 되는 두 수를 찾는다.
         * 3. 차이가 M 이상이 되는 경우, 두 수의 차를 최소값과 비교하여 갱신한다.
         * 4. 차이가 M 미만인 경우, 오른쪽 포인터를 증가시킨다.
         * 5. 모든 경우를 탐색한 후, 최소값을 출력한다.
         */

        int lo = 0, hi = 0;
        int minDiff = Integer.MAX_VALUE;
        Arrays.sort(arr);

        while ( lo < N && hi < N ) {
            int diff = arr[hi] - arr[lo];

            if ( diff >= M ) {
                // System.out.println(arr[hi] + " - " + arr[lo] + " = " + diff);
                minDiff = Math.min(minDiff, diff);
                lo++;
            } else {
                hi++;
            }
        }

        writer.write(String.valueOf(minDiff));
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
