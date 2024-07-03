import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {
    int N = 0;

    List<String> numbers = new ArrayList<>();

    Long[] score = new Long[10]; // a,b,c,d,e,f,g,h,i,j 에 해당하는 스코어 입력
    boolean[] first = new boolean[10]; // 문자열의 첫 문자로 사용됐는지 여부

    int maxLength = 0;

    public Solution() {
        Arrays.fill(first, false);
        Arrays.fill(score, 0L);
        Scanner scanner = new Scanner(System.in);
        N = Integer.parseInt(scanner.nextLine());

        for(int i = 0 ; i < N ; i++) {
            String s = scanner.nextLine();
            //numbers.add(s);
            int ch = s.charAt(0)-'A';
            first[ch] = true;
            long k = 1;
            for(int j = s.length()-1 ; j >= 0 ; j--) {
                ch = s.charAt(j)-'A';
                score[ch] += k;
                k*=10L;
            }
        }
        scanner.close();
    }

    public void run() {
        // 주어진 문자열을 정수로 변환하여 합을 구했을 때 최대 값을 구하라.
//        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        // 0을 할당할 문자가 갖는 점수를 구한다.
        long answer = 0L;


        boolean isZeroUsed = false;
        for(int i = 0 ; i < 10 ; i++) {
            isZeroUsed |= (score[i] > 0);
        }

        // 점수를 내림차순으로 정렬한다.

        if(isZeroUsed) {
            // 0이 사용되어야 하는 상황이라면 첫번째 문자로 사용된 적이 없는 문자중
            // 점수가 가장 낮은 문자에 0을 할당한다.
//            System.out.println("zero used.");
            long min = Long.MAX_VALUE;
            int idx = -1;

            for(int i = 0 ; i < 10 ; i++) {
                if(first[i]) continue;
                if(score[i] < min) {
                    min = score[i];
                    idx = i;
                }
            }
//            System.out.println((char)('A' + idx) + " should be 0");
            score[idx] = 0L;
        }
        Arrays.sort(score, (a, b) -> (a < b) ? 1 : (a==b)? 0 : -1);

        for (int i = 0, curNum = 9; i < 10;  i++, curNum--) {
            answer += (long)curNum * score[i];
        }

        System.out.println(answer);
    }
}
