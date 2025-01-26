import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, S;
    private int[] arr;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        S = Integer.parseInt(tokens[1]);
        arr = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

    }

    private int[] prefixSum() {
        int[] prefixSum = new int[N + 1];
        for(int i = 1 ; i <= N ; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i - 1];
        }

        return prefixSum;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        int[] prefixSum = prefixSum();

        int cnt = 0;
        int minLength =Integer.MAX_VALUE;

        int left = 0, right = 0;

        while(left <= right && right <= N) {
            int sum = prefixSum[right] - prefixSum[left];
            if(sum >= S) {
                cnt++;
                minLength = Math.min(minLength, right - left);
                left++;
            } else {
                right++;
            }
        }

        if(cnt == 0) {
            sb.append(0);
        } else {
            sb.append(minLength);
        }

        System.out.println(sb);
    }
}
