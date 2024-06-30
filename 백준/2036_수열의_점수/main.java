import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}

class Solution {
    int N = 0;

    long answer = 0;

    List<Long> positive = new ArrayList<>();

    List<Long> negative = new ArrayList<>();

    long one = 0;

    long zero = 0;

    public Solution() {
        Scanner scan = new Scanner(System.in);
        N = Integer.parseInt(scan.next());

        for(int i = 0 ; i < N ; i++) {
            long value = Long.parseLong(scan.next());
            if(value == 0) {
                zero++;
            }else if(value == 1) {
                one++;
            }else if(value > 0) {
                positive.add(value);
            }else {
                negative.add(value);
            }
        }

        positive.sort(Comparator.reverseOrder());
        Collections.sort(negative);

        scan.close();
    }

    public long run() {

        // 양수를 먼저 큰 수를 순차로 뽑아서 곱을 구한다.
        boolean positiveIsOdd = (positive.size() & 0x01) == 0x01;

        for(int i = 0 ; i < positive.size() - 1 ; i+=2) {
            long first = positive.get(i);
            long second = positive.get(i+1);
            answer += first * second;
        }

        answer += one;

        if(positiveIsOdd) {
            //System.out.println("Positive odd : " + positive.get(positive.size() - 1));
            answer += positive.get(positive.size() - 1); // positive.size() == 1 인 경우도 홀수 이므로, 무조건 더해진다.
        }

        boolean negativeIsOdd = ((negative.size() & 0x01) == 0x01);
//        System.out.println("is negative odd ? " + negativeIsOdd + " answer : " + answer);


        // 음수에 대해서도 가장 작은 값부터 두 수를 곱해서 스코어에 더하고
        // 2로 나누어 떨어지지 않는다면 유지한다.
        for(int i = 0 ; i < negative.size() - 1 ; i+=2) {
            long first = negative.get(i);
            long second = negative.get(i+1);
            answer += first * second;
        }
//        System.out.println("is negative odd ? " + negativeIsOdd + " answer : " + answer);

        if(zero == 0 && negativeIsOdd) {
            answer += negative.get(negative.size() - 1);
        }

        return answer;
    }
}
