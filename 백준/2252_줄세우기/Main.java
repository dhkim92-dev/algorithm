
class Solution {

    private int N, M;

    private List<Integer>[] graph;
    private int[] count;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        graph = new ArrayList[N+1];
        for(int i = 0 ; i <= N ; i++) {
            graph[i] = new ArrayList<>();
        }

        count = new int[N+1];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int a = Integer.parseInt(tokens[0]);
            int b = Integer.parseInt(tokens[1]);
            graph[a].add(b);
            count[b]++;
        }
    }

    public void run() {
        Queue<Integer> q = new LinkedList<>();
        List<Integer> sorted = new ArrayList<>();

        for(int i = 1 ; i <= N ; i++) {
            if(count[i] == 0) {
                q.add(i);
            }
        }

        while(!q.isEmpty()) {
            int cur = q.poll();
            sorted.add(cur);

            for(int next : graph[cur]) {
                count[next]--;
                if(count[next] == 0) {
                    q.add(next);
                }
            }
        }
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < sorted.size() ; i++) {
            sb.append(sorted.get(i)).append(" ");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }
}
