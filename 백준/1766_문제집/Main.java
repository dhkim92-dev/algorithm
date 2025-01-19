
class Solution {

    private int N, M;

    static class Problem implements Comparable<Problem> {

        List<Integer> childs;

        int index;

        int degree;

        public Problem(int index) {
            this.childs = new ArrayList<>();
            this.index = index;
            this.degree = 0;
        }

        @Override
        public int compareTo(Problem o) {
            return this.index - o.index;
        }
    }

    private Problem[] problems;

//    private boolean[] solved;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        problems = new Problem[N + 1];
//        solved = new boolean[N + 1];

        for(int i = 0 ; i <= N ; i++) {
            problems[i] = new Problem(i);
        }

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int before = Integer.parseInt(tokens[0]); // 먼저 풀어야할 문제
            int after = Integer.parseInt(tokens[1]); // 나중에 풀어야할 문제
            problems[before].childs
                    .add(after);
            problems[after].degree++;
        }
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Problem> pq = new PriorityQueue<>();

        for(int i = 1 ; i <= N ; i++) {
            // 차수가 0인 문제들을 우선순위 큐에 넣는다.
            if(problems[i].degree == 0) {
                pq.add(problems[i]);
            }
        }

        while(!pq.isEmpty()) {
            Problem problem = pq.poll();
            sb.append(problem.index)
                    .append(" ");

            for(int child: problem.childs) {
                problems[child].degree--;
                if(problems[child].degree == 0) {
                    pq.add(problems[child]);
                }
            }
        }

        System.out.println(sb.toString());
    }
}
