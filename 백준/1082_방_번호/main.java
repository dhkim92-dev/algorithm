import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {

    int N, M;

    List<Integer> prices = new ArrayList<>();

    public Solution() {
        Scanner scanner = new Scanner(System.in);
        N = Integer.parseInt(scanner.nextLine());
        String costs = scanner.nextLine();

        for(String cost : costs.split(" ")) {
            prices.add(Integer.parseInt(cost));
        }
        M = Integer.parseInt(scanner.nextLine());
        scanner.close();
    }

    private int[] getMinimumLengthArray(int minIndex, int cost) {
        int zeroCost = prices.get(0);

        int zeroLength = 0;
        int length = 0;
//        System.out.println("min cost : " + cost);

        if(zeroCost <= cost) {
            // 우선 0이 아닌 수를 하나 쓰고
            length++;
            M-=cost;
            // 나머지는 0으로 채워본다.
            while(true) {
                if(M - zeroCost >= 0) {
                    M-=zeroCost;
                    zeroLength++;
                    continue;
                }
                break;
            }
        } else {
            // 0은 고려할 필요가 없으므로
            while(true) {
                if(M-cost >= 0) {
                    M-=cost;
                    length++;
                    continue;
                }
                break;
            }
        }

//        System.out.println("Non zero length : " + length + " zero length : " + zeroLength);

        int[] ret = new int[length + zeroLength];

        for(int i = 0 ; i < length ; i++) {
            ret[i] = minIndex;
        }
        for(int i = 0 ; i < zeroLength ; i++) {
            ret[1 + i] = 0;
        }

        return ret;
    }

    // 0이 아닌 숫자 중 최소 비용을 구한다.
    private int getMinimumCost() {
        int minValue = Integer.MAX_VALUE;
        for(int i = 1 ; i < prices.size() ; i++) {
            minValue = Math.min(minValue, prices.get(i));
        }
        return minValue;
    }

    private int getMinimumCostNumber(int cost) {
        int idx = N-1;
        for( ; idx >= 1 ; idx--) {
            if(cost != prices.get(idx)) continue;
            break;
        }

        return idx;
    }

    private int getZeroCount() {
        int zeroPrice = prices.get(0);
        int count = 0;

        while(M > 0) {
            if(M - zeroPrice >= 0) {
                M-=zeroPrice;
                count++;
            }else{
                break;
            }
        }

        return count;
    }

    public void run() {
        // 기저 조건 처리
        if(N == 1) {
            System.out.println(0);
            return;
        }

        int minCost = getMinimumCost();
        if(minCost > M) {
            // 0이 아닌 수중 최소 비용이 현재 값보다 크다면
            // 0을 출력
            System.out.println(0);
            return;
        }

        int minIndex = getMinimumCostNumber(minCost);
        int[] answer = getMinimumLengthArray(minIndex, minCost);
//        System.out.println("answer length :" + answer.length);
//        for(int i : answer) {
//            System.out.print(i);
//        }
//        System.out.println();
        int minLength = answer.length;

        for(int i = 0 ; i < minLength ; i++) {
            int curNum = answer[i];
            int curCost = prices.get(curNum);
//System.out.printf("answer[%d] : %d remain : %d curCost : %d\n", i, curNum, M, curCost);

            for(int j = prices.size()-1 ; j > curNum ; j--) {
                // 현재 수보다 큰 수로 교체가 가능한지 확인한다.
                int diff = M + curCost - prices.get(j); // 잔여 비용 + 현재 수의 비용 - 바꿀 수의 비용
//                System.out.printf("target num : %d diff = %d\n", j, diff);

                if(diff >= 0) {
                    // 이 경우 변경이 가능하다.
                    answer[i] = j;
                    M = diff;
                    break;
                }
            }

        }

        for(int i : answer) {
            System.out.print(i);
        }
        System.out.println();

    }
}
