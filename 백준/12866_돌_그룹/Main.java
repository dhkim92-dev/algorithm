

class Solution {

    private static final int NR_STONES = 3;

    private int[] groupStones = new int[NR_STONES];

    protected Solution() {
    }

    public Solution(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        String[] tokens = line.split(" ");

        for (int i = 0; i < NR_STONES; i++) {
            groupStones[i] = Integer.parseInt(tokens[i]);
        }

        Arrays.sort(groupStones);
//        Arrays.stream(groupStones).forEach(System.out::println);
    }

    public void run() {
        boolean answer = simulate(groupStones);
        System.out.println(answer ? 1 : 0);
    }

    private boolean simulate(int[] stones) {
        Queue<ArrayList<Integer>> q = new LinkedList<>();
        int total = Arrays.stream(stones).sum();
        boolean[][] visited = new boolean[total+1][total+1];
        q.add(new ArrayList<>(Arrays.asList(stones[0], stones[1])));
        visited[stones[0]][stones[1]] = true;

        while(!q.isEmpty()) {
            ArrayList<Integer> cur = q.poll();
            int x = cur.get(0);
            int y = cur.get(1);
            int z = total - x - y;

            if(x==y && y==z) {
                return true;
            }

            for(int[] n : new int[][]{{x, y}, {x, z}, {y,z}}) {

                if(n[0] < n[1]) {
                    n[1] -= n[0];
                    n[0] += n[0];
                } else if(n[0] > n[1]) {
                    n[0] -= n[1];
                    n[0] += n[1];
                } else {
                    continue;
                }

                int nc = total - n[0] - n[1];
                int mn = Math.min(n[0], Math.min(n[1], nc));
                int mx = Math.max(n[0], Math.max(n[1], nc));

                if(visited[mn][mx]) {
                    continue;
                }
                q.add(new ArrayList<>(Arrays.asList(mn, mx)));
                visited[mn][mx] = true;
            }
        }
        return false;
    }
}
