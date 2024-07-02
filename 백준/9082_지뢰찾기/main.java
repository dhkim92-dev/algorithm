import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {

    int T;

    public Solution() {

    }

    boolean isInRange(int idx, int limit) {
        return (0<=idx && idx < limit);
    }

    private int solve(int[] first) {
        int answer = 0;
        int N = first.length;

        for(int c = 0 ; c < N ; c++) {
            // 숫자 행에서 자신과 인접한 좌우의 값이 모두 0보다 크다면
            // 최소 아래 행에서 동일 열의 칸 중 하나에는 지뢰가 1개 이상 존재해야 한다.
            // 해당 인덱스의 숫자행들을 1씩 감소시킨다.
            // 이 과정을 반복하면 답을 구할 수 있다.
            // 예)
            // 1 1 0
            // # # #
            // 예로 보자면
            // 1행 0열, 1열의 중 한개가 지뢰이기 때문에
            // 0 0 0
            // # # # 이 되면서 지뢰가 한 개 존재하게 된다.
            if(c == 0) {
                if((first[0] > 0) && (first[1] > 0)) {
                    first[0]--;
                    first[1]--;
                    answer++;
                }
            } else if(c == N-1) {
                if((first[N-2] > 0) && (first[N-1] > 0)) {
                    first[N-2]--;
                    first[N-1]--;
                    answer++;
                }
            } else {
                if((first[c-1] > 0) && (first[c] > 0) && (first[c+1] > 0)) {
                    first[c-1]--;
                    first[c]--;
                    first[c+1]--;
                    answer++;
                }
            }
        }

        return answer;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // T개의 테스트 케이스
        T = Integer.parseInt(scanner.nextLine());

        for(int i = 0 ; i < T ; i++) {
            int N = Integer.parseInt(scanner.nextLine());
            int[] first = new int[N];
            char[] line = scanner.nextLine()
                    .toCharArray();

            for(int j = 0 ; j < N ; j++) {
                first[j] = line[j] - '0';
            }

            char[] second = scanner.nextLine()
                    .toCharArray();

            System.out.println(solve(first));
        }


        // 배열의 크기는 2 x N이다.
        scanner.close();
    }
}
