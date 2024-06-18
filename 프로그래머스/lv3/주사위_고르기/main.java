import java.util.*;

class Solution {

    private int[][] dice;
    private int[] comb;
    private int limit;
    private int[] answer;
    private List<int[]> diceCombs = new ArrayList<>();

    private int countWins(int target, List<Integer> scores) {
        int left = 0;
        int right = scores.size() - 1;
        int mid = 0;

        while(left <= right) {
            mid = (left + right) / 2;
            int value = scores.get(mid);

            if(value < target) { // 현재 값이 검색하려는 값보다 작다면
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    private void makeScoreCombination(int[] indices, int start, int limit, int score, List<Integer> output) {
        if(start == limit) {
            output.add(score);
            return;
        }

        for(int i = 0 ; i < 6 ; i++) {
            makeScoreCombination(indices, start + 1, limit, score + dice[indices[start]][i], output);
        }
    }

    private void makeCombination(int start, int cur, int limit) {
        if(cur == limit) {
            diceCombs.add(Arrays.stream(comb).toArray());
            return;
        }

        for(int i = start ; i < dice.length ; i++) {
            comb[cur] = i;
            makeCombination(i + 1, cur + 1, limit);
        }
    }

    public int[] solution(int[][] dice) {
        this.dice = dice;
        limit = dice.length;
        answer = new int[limit / 2];
        comb = new int[limit / 2];
        makeCombination(0, 0, limit/2);

        int maxWin = 0;

        // System.out.println("Score result ");
        for(int i = 0 ; i < diceCombs.size() / 2 ; i++) {

            int[] aDices = diceCombs.get(i);
            int[] bDices = diceCombs.get(diceCombs.size() - i - 1);

            List<Integer> aScore = new ArrayList<>();
            List<Integer> bScore = new ArrayList<>();

            makeScoreCombination(aDices, 0, limit/2, 0, aScore);
            makeScoreCombination(bDices, 0, limit/2, 0, bScore);

            Collections.sort(aScore);
            Collections.sort(bScore);

            int aTotalWin = 0; // 승률은 중요하지 않다. 어차피 모두 같은 시행 회수이므로 승리 수만 합계하여 비교하면 된다.
            int bTotalWin = 0; // 승률은 중요하지 않다. 어차피 모두 같은 시행 회수이므로 승리 수만 합계하여 비교하면 된다.
            
            for(int aValue : aScore) {
                int winCount = countWins(aValue, bScore);
                aTotalWin+=winCount;
            }
            
            for(int bValue : bScore) {
                int winCount = countWins(bValue, aScore);
                bTotalWin += winCount;
            }
            

            if(aTotalWin > maxWin) {
                maxWin = aTotalWin;
                for(int dIdx = 0 ; dIdx < limit/2 ; dIdx++) {
                    answer[dIdx] = aDices[dIdx] + 1;
                }
            }
            
            if(bTotalWin > maxWin) {
                maxWin = bTotalWin;
                for(int dIdx = 0 ; dIdx < limit/2 ; dIdx++) {
                    answer[dIdx] = bDices[dIdx] + 1;
                }
            }
        }

        return answer;
    }
}
