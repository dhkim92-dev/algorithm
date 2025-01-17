
class Solution {

    private int nrTestCases;

    private int[] F;

    static class Relation {
        String from;
        String to;

        public Relation(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }

    List<Relation>[] relations;

    public Solution(BufferedReader reader) throws IOException {
        this.nrTestCases = Integer.parseInt(reader.readLine());
        F = new int[nrTestCases];
        relations = new List[nrTestCases];

        for(int i = 0 ; i < relations.length ; i++) {
            relations[i] = new ArrayList<>();
        }

        for(int i = 0 ; i < nrTestCases ; i++) {
            String[] line = reader.readLine().split(" ");
            F[i] = Integer.parseInt(line[0]);

            for(int j = 0 ; j < F[i] ; j++) {
                String[] friend = reader.readLine().split(" ");
                relations[i].add(new Relation(friend[0], friend[1]));
            }
        }
    }

    private String find(String x, Map<String, String> parents) {
        if(!parents.get(x).equals(x)) {
            parents.put(x, find(parents.get(x), parents));
        }
        return parents.get(x);
    }

    private void union(String x, String y, Map<String, String> parents) {

        String xParent = find(x, parents);
        String yParent = find(y, parents);

        if(!xParent.equals(yParent)) {
            parents.put(xParent, yParent);
        }
    }

    private void solve(int testNo, StringBuilder sb) {
        Map<String, String> parents = new HashMap<>();
        Map<String, Integer> cnt = new HashMap<>();

        for(Relation relation : relations[testNo]) {
            String from = relation.from;
            String to = relation.to;
            parents.putIfAbsent(from, from);
            parents.putIfAbsent(to, to);
            cnt.put(from, 1);
            cnt.put(to, 1);
        }

        for(Relation relation : relations[testNo]) {
            String from = relation.from;
            String to = relation.to;

            String fromParent = find(from, parents);
            String toParent = find(to, parents);

            union(from, to, parents);
            if(fromParent != find(fromParent, parents)) {
                // 이 경우 to로 합쳐진 경우이다.
                cnt.put(toParent, cnt.get(toParent) + cnt.get(fromParent));
            }

            sb.append(cnt.get(toParent)).append("\n");
        }

        parents.clear();
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < nrTestCases ; i++) {
            solve(i, sb);
        }
        System.out.println(sb);
    }
}
