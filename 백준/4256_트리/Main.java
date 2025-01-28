import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int T;
    private int[] N;
    private List<Integer>[] preorders;
    private List<Integer>[] inorders;

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        T = Integer.parseInt(reader.readLine());
        N = new int[T];
        preorders = new ArrayList[T];
        inorders = new ArrayList[T];
        for(int i = 0 ; i < T ; i++) {
            N[i] = Integer.parseInt(reader.readLine());
            preorders[i] = Arrays.stream(reader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            inorders[i] = Arrays.stream(reader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }

    private Node recoverTree(List<Integer> preorder,
                             List<Integer> inorder) {
        if(preorder.size() == 0) {
            return null;
        }
        Node root = new Node(preorder.get(0));
//        System.out.println("root : " + root.value);
        int rootIndex = inorder.indexOf(root.value);

        List<Integer> leftInorder = inorder.subList(0, rootIndex);
        List<Integer> rightInorder = inorder.subList(rootIndex + 1, inorder.size());
        List<Integer> leftPreorder = preorder.subList(1, rootIndex + 1);
        List<Integer> rightPreorder = preorder.subList(rootIndex + 1, preorder.size());


        root.left = recoverTree(leftPreorder, leftInorder);
        root.right = recoverTree(rightPreorder, rightInorder);

        return root;
    }

    private void postOrder(Node root, StringBuilder sb) {
        if(root == null) {
            return;
        }
        postOrder(root.left, sb);
        postOrder(root.right, sb);
        sb.append(root.value).append(" ");
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        // 전위 순회 결과와 후위 순위 결과가 주어졌을 때, 원본 트리를 복구
        for(int i = 0 ; i < inorders.length ; i++) {
            Node tree = recoverTree(preorders[i], inorders[i]);
            postOrder(tree, sb);
            sb.append("\n");
        }
        System.out.println(sb);
    }
}


