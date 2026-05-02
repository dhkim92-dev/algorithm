import java.util.*;
import java.lang.*;

class Solution {
    
    private int n, infection, k;
    
    private int[][] edges;
    
    private static final int A = 1;
    
    private static final int B = 2;
    
    private static final int C = 3;
    
    private List<int[]>[] adj;
    
    private Queue<Integer> q;

    private boolean[] visited;
    
    /** 
    * @param n:int -> n개의 배양체
    * @param infection: int -> 감염된 배양체 번호
    * @param edges: int[][] -> 연결 정보, node1, node2, pipeType
    * @param k: int -> 최대 시도 가능한 Operation 수
    * @return answer: int -> 감염된 최대 배양체 개수
    **/ 
    public int solution(int n, int infection, int[][] edges, int k) {
        int answer = 0;
        this.n = n;
        this.k = k;
        this.infection = infection-1;
        this.edges = edges;
        this.q = new LinkedList<>();
        this.visited = new boolean[n];
        
        boolean[] infected = new boolean[n];
        adj = new List[n];
        for ( int i = 0 ; i < n ; ++i ) {
            adj[i] = new ArrayList<>();
        }
       
        for( int[] edge : edges ) {
            // System.out.printf("edge: %d, %d, %d\n", edge[0], edge[1], edge[2]);
            int n1 = edge[0]-1;
            int n2 = edge[1]-1;
            int type = edge[2];
            
            adj[n1].add(new int[]{n2, type});
            adj[n2].add(new int[]{n1, type});
        }
        
        // n <= 100
        // DFS와 BFS를 혼합하여 사용하면 해결 가능한지?
        // 1. 파이프 타입 별로 큐를 하나씩 가지고 있는다.
        // 2. DFS 탐색(k, type), 각 DFS 스텝에서 3개 타입에 대해 재귀 분기
        // 3. 한 탐색마다 BFS를 이용하여 감염을 진행한다.
        
        for ( int type = A ; type <= C ; ++type ) {
            Arrays.fill(infected, false);
            infected[this.infection] = true;
            answer = Math.max( answer, dfs( k, type, infected) );
        }
        
        return answer;
    }
    
    private int dfs( 
        int left, 
        int type, 
        boolean[] infected
    ) {
        if ( left <= 0 ) {
            int cnt = 0 ; 
            for ( int i = 0 ; i < infected.length ; ++i ) {
                cnt += (infected[i]) ? 1 : 0;
            }
            return cnt;
        }
        
        bfs(type, infected);
        // 감염 진행
        boolean[] infectedBackup = infected.clone();
        int maxInfected = 0;
        
        maxInfected = Math.max(maxInfected, dfs(left - 1, A, infected));
        infected = Arrays.copyOf(infectedBackup, infected.length);
        maxInfected = Math.max(maxInfected, dfs(left - 1, B, infected));
        infected = Arrays.copyOf(infectedBackup, infected.length);
        maxInfected = Math.max(maxInfected, dfs(left - 1, C, infected));
        infected = Arrays.copyOf(infectedBackup, infected.length);
        
        return maxInfected;
    }
    
    private void bfs(int type, boolean[] infected) {
        // 감염된 모든 노드를 큐에 넣고 BFS 탐색을 진행한다.
        q.clear();
        for ( int i = 0 ; i < infected.length ; ++i ) {
            if ( infected[i] ) {
                q.offer(i);
            }
        }

        while ( !q.isEmpty() ) {
            int curr = q.poll();
            for ( int[] next : adj[curr] ) {
                int nextNode = next[0];
                int pipeType = next[1];
                if ( pipeType == type && !infected[nextNode] ) {
                    infected[nextNode] = true;
                    q.offer(nextNode);
                }
            }
        }
    }
}

class Main {

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] n = {10, 7};
        int[] infection = {1, 6};
        int[][][] edges = {
            {{1, 2, 1}, {1, 3, 1}, {1, 4, 3}, {1, 5, 2}, {5, 6, 1}, {5, 7, 1}, {2, 8, 3}, {2, 9, 2}, {9, 10, 1}},
            {{1, 2, 3}, {1, 4, 3}, {4, 5, 1}, {5, 6, 1}, {3, 6, 2}, {3, 7, 2}}
        };
        int[] k = {2, 3};
        int[] answers = {6, 7};

        for (int i = 0; i < n.length; ++i) {
            int ret = sol.solution(n[i], infection[i], edges[i], k[i]);
            System.out.println("Answer: " + ret);
            System.out.println("Expected: " + answers[i]);
            System.out.println(ret == answers[i] ? "PASS" : "FAIL");
            System.out.println();
        }
    }
}