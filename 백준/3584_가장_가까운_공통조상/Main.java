import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);

        File dir = null;
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return; 
        }
        File[] files = dir.listFiles();

        if(files == null) {
            return;
        }

        for(int i = 0 ; i<files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            reader.lines()
                    .forEach(System.out::println);
            reader.reset();

            System.out.println("answer ");
            new Solution(reader).run();
            reader.close();
        }
    }

    static void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        //testRun(3584);
      run();
    }
}

class Solution {

    public static class Node implements Comparable<Node> {
        int idx;
        int depth;
        Node parent;
        List<Node> childrens;

        public Node(int idx) {
            this.idx = idx;
            this.depth = 0;
            this.parent = null;
            this.childrens = new ArrayList<>();
        }

        public void addChild(Node child) {
            this.childrens.add(child);
        }

        @Override
        public int compareTo(Node o) {
            return this.depth - o.depth;
        }
    }

    private int nrTestCases;

    private List<Node[]> testCases;

    private int[][] targetNodes;

    public Solution(BufferedReader reader) throws IOException {
        nrTestCases = Integer.parseInt(reader.readLine());
        testCases = new ArrayList<>();
        targetNodes = new int[nrTestCases][2];

        for(int i = 0 ; i < nrTestCases ; i++) {
            int nrNodes = Integer.parseInt(reader.readLine());

            Node[] nodes = new Node[nrNodes + 1];

            for(int j = 0 ; j < nrNodes - 1 ; j++) {
                int from, to;
                String[] tokens = reader.readLine().split(" ");
                from = Integer.parseInt(tokens[0]);
                to = Integer.parseInt(tokens[1]);

                if(nodes[from] == null) {
                    nodes[from] = new Node(from);
                }

                if(nodes[to] == null) {
                    nodes[to] = new Node(to);
                }

                nodes[to].parent = nodes[from];
                nodes[from].addChild(nodes[to]);
            }

            testCases.add(nodes);
//
//            for(int k = 1 ; k < nodes.length ; k++) {
//                System.out.printf("TC : %d, Node : %d, Parent : %d\n", i, k, nodes[k].parent == null ? -1 : nodes[k].parent.idx);
//            }

            String[] tokens = reader.readLine().split(" ");
            targetNodes[i][0] = Integer.parseInt(tokens[0]);
            targetNodes[i][1] = Integer.parseInt(tokens[1]);
        }
    }

    private int findDepth(Node[] nodes) {

        Node root = null;

        for(int i = 1 ; i < nodes.length ; i++) {
            if(nodes[i].parent == null && nodes[i].childrens.size() > 0) {
                root = nodes[i];
                break;
            }
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()) {
            Node node = q.poll();

            for(Node child : node.childrens) {
                child.depth = node.depth + 1;
                q.add(child);
            }
        }

        return root.idx;
    }

    private int findCommonRoot(Node[] graph, int[] targets, int root) {
//        System.out.printf("root : %d, targets : %d %d\n", root, targets[0], targets[1]);
        Node node1 = graph[targets[0]];
        Node node2 = graph[targets[1]];
        boolean[] visited = new boolean[graph.length];
        Arrays.fill(visited, false);
        visited[node1.idx] = true;
        visited[node2.idx] = true;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(node1);
        pq.add(node2);

        while(!pq.isEmpty()) {
            Node node = pq.poll();
//            System.out.printf("Current Node : %d, parent : %d\n", node.idx, node.parent == null ? -1 : node.parent.idx);

            if(node.parent != null && !visited[node.parent.idx]) {
                visited[node.parent.idx] = true;
                pq.add(node.parent);
            } else if(node.parent != null) {
                return node.parent.idx;
            }
        }

        return root;
    }

    public void run() {

        for(int i = 0 ; i < nrTestCases ; i++) {
            int root = findDepth(testCases.get(i));
            int commonRoot = findCommonRoot(testCases.get(i), targetNodes[i], root);
            System.out.println(commonRoot);
        }
    }
}
