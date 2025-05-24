import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static StringTokenizer st;
    private static StringBuilder sb = new StringBuilder();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static int N, M, I;
    private static int[] age;
    private static boolean[][] connMap;

    public static void main(String[] args) throws IOException {
        setting();
        for (int i = 0; i < I; i++) {
            st = new StringTokenizer(br.readLine());
            if (st.nextToken().equals("T")) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                change(a, b);
            } else {
                int a = Integer.parseInt(st.nextToken());
                int youngestParent = getYoungestParent(a);
                sb.append(youngestParent == Integer.MAX_VALUE ? "*" : youngestParent);
                if (i != I - 1) sb.append(("\n"));
            }
        }
        System.out.println(sb.toString());
//        printMap();
    }

    private static int getYoungestParent(int a) {
        boolean[] visited = new boolean[N + 1];
        visited[a] = true;
        return bfs(a);
    }

    private static int bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        boolean[] visited = new boolean[N + 1];
        visited[start] = true;
        int minAge = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int i = 1; i < N + 1; i++) {
                if (connMap[node][i]) {
                    int manager = i;
                    if (!visited[manager]) {
                        visited[manager] = true;
                        minAge = Math.min(minAge, age[manager]);
                        queue.add(manager);
                    }
                }

            }
        }
        return minAge;
    }

    private static void change(int a, int b) {
        if (a == b) return;
        for (int i = 1; i < N + 1; i++) {
            if (i == a || i == b) continue;
            boolean tmp1 = connMap[i][b];
            connMap[i][b] = connMap[i][a];
            connMap[i][a] = tmp1;
            boolean tmp2 = connMap[b][i];
            connMap[b][i] = connMap[a][i];
            connMap[a][i] = tmp2;
        }
        //반례 발생으로 서로 같은 경우가 되었을때에는 나눠서 정리해준다
        //Ex. 2의 부모가 3이되는 경우는 위에 코드를 실행하면 connMap[2][3]=connMap[2][2]가 되기때문에 a,b가 서로 직접적인 연결일때에는 나눠서 처리한다.
        boolean tmp = connMap[a][b];
        connMap[a][b] = connMap[b][a];
        connMap[b][a] = tmp;
    }

    private static void setting() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        I = Integer.parseInt(st.nextToken());

        age = new int[N + 1];
        connMap = new boolean[N + 1][N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < N + 1; i++) {
            age[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connMap[b][a] = true;
        }
//        printMap();
    }

    private static void printMap() {
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                System.out.print(connMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}
