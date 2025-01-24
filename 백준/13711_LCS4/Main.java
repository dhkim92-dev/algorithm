import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// LIS 문제
// 두 수열 중 한 수열을 인덱스로 활용하여
// 이진탐색을 이용해 풀이


class Solution {

    private int N;

    private int[] A, B;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        A = new int[N];
        B = new int[N];
        A = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        B = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private int lowerBound(int[] arr, int size, int target) {
        int start = -1;
        int end = size;

        while(start + 1 < end) {
            int mid = start + (end - start) / 2;

            if(!(arr[mid] > target)) {
                start = mid ;
            } else {
                end = mid;
            }
        }

        return end;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        int[] index = new int[N]; // A를 구성하는 원소들의 등장 위치를 저장하는 배열
        // 예를들어 A = [1, 3 ,2, 0] 이고, B = [2, 3, 1, 0] 이라고 하면
        // index[0] = [ 3, 0, 2, 1 ] 이다.
        // 이 인덱스 순서를 기준으로 B를 정렬하면
        // B[i] = index[B[i] = [ 0, 2, 1, 3 ] 이 된다.
        int[] lis = new int[N];
        Arrays.fill(lis, Integer.MAX_VALUE);

        for(int i = 0 ; i < N ; i++) {
            A[i]--;
            B[i]--;
        }

        for(int i = 0 ; i < N ; i++) {
            index[A[i]] = i;
        }

        for(int i = 0 ; i < N ; i++) {
            B[i] = index[B[i]];
        }

//        lis[0] = B[0];
        for(int i = 0 ; i < N ; i++) {
            int idx = lowerBound(lis, N, B[i]);
            lis[idx] = Math.min(lis[idx], B[i]);
        }

        int answer = 0;
        while(answer < N && lis[answer] != Integer.MAX_VALUE) {
//            System.out.println("lis[" + answer + "] = " + lis[answer]);
            answer++;
        }

        sb.append(answer)
                .append("\n");
        System.out.println(sb);
    }
}


