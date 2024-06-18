
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
        for(int i = 0 ; i < diceCombs.size() ; i++) {

            int[] aDices = diceCombs.get(i);
            int[] bDices = diceCombs.get(diceCombs.size() - i - 1);

            List<Integer> aScore = new ArrayList<>();
            List<Integer> bScore = new ArrayList<>();

            makeScoreCombination(aDices, 0, limit/2, 0, aScore);
            makeScoreCombination(bDices, 0, limit/2, 0, bScore);

