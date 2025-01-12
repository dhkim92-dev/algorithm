
class Solution {

    static class Node implements Comparable<Node> {

        String index;

        Set<Node> childs;

        public Node(String word) {
            this.index = word;
            this.childs = new LinkedHashSet<>();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj == null) return false;
            if(obj.getClass() != this.getClass()) return false;

            return this.index == ((Node)obj).index;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(index);
        }

        @Override
        public int compareTo(Node o) {
            return this.index.compareTo(o.index);
        }
    }

    private int N;

    private Set<Node> roots;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        roots = new LinkedHashSet<>();

        for(int i = 0 ; i < N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int K = Integer.parseInt(tokens[0]);
            String[] treeValues = Arrays.copyOfRange(tokens, 1, tokens.length);
            dfs(K, roots, 0, treeValues);
        }
    }

    private void dfs(int K, Set<Node> root, int depth, String[] tokens) {
        if(K == depth) return;
        Node newNode = root.stream().filter(node -> node.index.equals(tokens[depth]))
                .findFirst()
                .orElse(new Node(tokens[depth]));
        root.add(newNode);
        dfs(K, newNode.childs, depth+1, tokens);
    }

    private void search(Set<Node> root, int depth, StringBuilder sb) {
        List<Node> searchRoot = root.stream()
                .sorted()
                .collect(Collectors.toList());
        for(Node node : searchRoot) {
            for(int i = 0 ; i < depth ; i++) {
                sb.append("--");
            }
            sb.append(node.index);
            sb.append("\n");
            search(node.childs, depth+1, sb);
        }
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        search(roots, 0, sb);
        System.out.println(sb);
    }
}
