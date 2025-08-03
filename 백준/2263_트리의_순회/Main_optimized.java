import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private List<Integer> inOrder;

    private List<Integer> postOrder;

    private List<Integer> preOrder;

    private int[] LUT;

    private int nrNode = 0;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        nrNode = Integer.parseInt(reader.readLine());
        inOrder = new ArrayList<>();
        postOrder = new ArrayList<>();
        LUT = new int[nrNode + 1];

        inOrder = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        for ( int i = 0 ; i < nrNode ; ++i ) {
            int node = inOrder.get(i);
            LUT[node] = i; // inOrder 에서의 위치를 저장한다.
        }

        postOrder = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    private void printPreOrder(List<Integer> inOrder,
                               int iStart, int iEnd,
                               List<Integer> postOrder,
                               int pStart, int pEnd
    ) throws IOException {

        if ( iStart > iEnd || pStart > pEnd ) {
            return;
        }

        int root = postOrder.get(pEnd);
        writer.write(String.valueOf(root) + " ");
        int inOrderRootIndex = LUT[root]; // 루트 노드의 위치를 inOrder 에서 찾는다.
        int leftSubTreeSize = inOrderRootIndex - iStart;
        int rightSubTreeSize = iEnd - inOrderRootIndex;
        printPreOrder( inOrder,
                iStart, inOrderRootIndex - 1,
                postOrder,
                pStart, pStart + leftSubTreeSize - 1
        );
        printPreOrder(inOrder,
                inOrderRootIndex + 1, iEnd,
                postOrder,
                pStart + leftSubTreeSize, pEnd - 1
        );
    }

    public void run() throws IOException {
        preOrder = new ArrayList<>();

        // 다음과 같은 방식을 반복한다.
        // 1. 루트노드의 위치를 postOrder 에서 찾는다.
        // 2. 해당 루트 노드의 offset 을 inOrder 에서 찾는다.
        // 3. postOrder 의 해당 offset 좌측은 루트 노드의 좌측에 존재하는 서브트리이며,
        //    우측의 노드들은 우측의 서브 트리를 구성한다.
        // 4. 좌측의 서브트리의 루트를 먼저 구하고, 출력을 한다.
        // 5.      2, 3 과정을 모든 좌측 서브트리에 대해 수행한다.
        // 6. 그 후 우측 서브 트리의 루트를 구해서 출력한다.
        printPreOrder(inOrder,
                0, nrNode - 1,
                postOrder,
                0, nrNode - 1);

        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}


