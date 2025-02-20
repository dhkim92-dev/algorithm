import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final Map<String, Integer> cardMap = new HashMap() {
        {
            put("6", 1);
            put("7", 2);
            put("8", 3);
            put("9", 4);
            put("T", 5);
            put("J", 6);
            put("Q", 7);
            put("K", 8);
            put("A", 9);
        }
    };
    private int[][] card;

    private Map<Integer, Double> cache = new HashMap<>();

    public Solution(BufferedReader reader) throws IOException {
        card = new int[10][5];

        for(int i = 1 ; i <= 9 ; i++) {
            String[] tokens = reader.readLine().split(" ");
            for(int j = 1 ; j <= 4 ; j++) {
                card[i][j] = cardMap.get(tokens[j-1].substring(0, 1));
                //System.out.print(card[i][j] + " ");
            }
            //System.out.println();
        }
    }

    private List<int[]> getPossiblePairs(int[] status) {
        List<int[]> pairs = new ArrayList<>();

        for(int i = 1 ; i <= 8 ; i++) {
            for(int j = i+1 ; j <= 9 ; j++) {
                int iStatus = status[i];
                int jStatus = status[j];
                if( iStatus > 0  && jStatus > 0 && card[i][iStatus] == card[j][jStatus]) {
                    pairs.add(new int[]{i, j});
                }
            }
        }

        return pairs;
    }

    private int makeCacheKey(int[] status) {
//         9개의 그룹은 최대 4개의 카드를 가지므로
//         444444444 가 최대값이다.
//         그리고 0이 최소값이다.
        int key = 0;
        int pow = (int)Math.pow(10, 8);
        for(int i = 1 ; i < status.length ; i++) {
            int value = status[i];
            value *= pow;
            pow/=10;
            key+=value;
        }

        return key;

//        StringBuilder sb = new StringBuilder();
//        for(int i = 1 ; i < status.length ; i++) {
//            sb.append(status[i]);
//        }
//        return sb.toString();
    }

    private void printStatus(int[] status) {
        System.out.println("printStatus by list");
        for(int i = 1 ; i < status.length ; i++) {
            System.out.print(status[i] + " ");
        }
        System.out.println();
    }

    private void printStatus(int status) {
        System.out.println("printStatus by value");
        for(int i = 8 ; i >= 0 ; i--) {
            int value = status / (int)Math.pow(10, i);
            status = status % (int)Math.pow(10, i);
            System.out.print(value + " ");
        }
        System.out.println();
    }

    private boolean isFinished(int[] status) {
        for (int i = 1 ; i < status.length ; i++) {
            if (status[i] != 0) {
                return false;
            }
        }

        return true;
    }

    private double solve(int[] status) {
        // 각 그룹의 제일 위의 카드만을 선택할 수 있다.
        // 탈출 조건: 모든 그룹의 카드가 다 소진되면 완료된 것이다.
//        printStatus(status);
        if(isFinished(status)) {
            return 1.0;
        }

        int cacheKey = makeCacheKey(status);
//        printStatus(cacheKey);
        // 이미 계산된 결과가 있다면 반환
        if(cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        // 현재 만들 수 있는 카드 쌍을 반환한다.
        List<int[]> pairs = getPossiblePairs(status);

        // 만들 수 있는 카드 쌍이 없다면 0.0을 반환한다.
        if(pairs.isEmpty()) {
            cache.put(cacheKey, 0.0);
            return 0.0;
        }

        int pairsSize = pairs.size();
        cache.put(cacheKey, 0.0);
        // 페어 당 확률 1 / pairSize
        // cur = next * (1 / pairSize);
        for(int[] pair : pairs) {
            int[] newStatus = new int[10];
            for(int i = 0 ; i < status.length ; i++) {
                newStatus[i] = status[i];
            }
            newStatus[pair[0]]--;
            newStatus[pair[1]]--;
            cache.put(cacheKey, cache.get(cacheKey) + solve(newStatus) / (double)pairsSize);
        }


        return cache.get(cacheKey);
    }

    public void run () {
        double answer = 0.0f;
        Map<Integer, Double> c = new HashMap<>();
        int[] status = new int[10];
        Arrays.fill(status, 4);
        answer = solve(status);
        System.out.printf("%.6f\n", answer);
    }
}


