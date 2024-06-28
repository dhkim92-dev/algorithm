import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static class Solution {

        int nrNode;

        Map<Integer, List<Integer>> graph = new HashMap<>();

        int[][] dp;

        int H;

        private void init() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nrNode = Integer.parseInt(reader.readLine());

            for(int i = 0 ; i < nrNode-1 ; i++) {
                String[] nums = reader.readLine().split(" ");
                int from = Integer.parseInt(nums[0]);
                int to = Integer.parseInt(nums[1]);

                List<Integer> connection = graph.getOrDefault(from, new ArrayList<>());
                connection.add(to);
                graph.put(from, connection);
            }

            H = Integer.parseInt(reader.readLine());

            dp = new int[101][101];
            for(int[] row : dp) {
                Arrays.fill(row, -1);
            }
        }

        private int cost(int index, int height) {
            if(dp[index][height] != -1) {
                return dp[index][height];
            }

            List<Integer> childrens = graph.getOrDefault(index, new ArrayList<>());

            int ret = 0;

            for(int child : childrens) {
                if(height == 0) {
                    ret += cost(child, height+1);
                } else if(height == H) {
                    // 현재 노드에 대해 연산한 값을 1 더해주고, 자식 노드로 탐색을 한다.
                    // 이 떄 자식 노드는 레벨이 1 낮아지므로 height 를 그대로 넘겨준다.
                    ret += cost(child, height) + 1;
                } else {
                    // 현재 노드에 대해 연산한 경우의 비용과
                    // 현재 노드에 대해 연산을 하지 않은 경우 자식 노드의 서브트리에서 발생하는 비용을 합한 값의 최소값을 취한다.
                    ret += Math.min(cost(child, height) + 1, cost(child, height+1));
                }
            }

            return dp[index][height] = ret;
        }

        public int run() throws IOException {
            init();

            return cost(0, 0);
        }

    }
    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}
