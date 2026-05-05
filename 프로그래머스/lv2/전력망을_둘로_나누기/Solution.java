import java.util.*;

class Solution {
    
    private List<Integer>[] conn;
    
    private int maxDiff;
    
    public int solution(int n, int[][] wires) {
        int answer = -1;
        
        conn = new List[n];
        for ( int i = 0 ; i < n ; ++i ) {
            conn[i] = new ArrayList<>();
        }
        
        // 0-based
        for ( int i = 0 ; i < wires.length ; ++i ) {
            int u = wires[i][0] - 1;
            int v = wires[i][1] - 1;
            
            conn[u].add(v);
            conn[v].add(u);
        }
        
        boolean[] visited = new boolean[n];
        
        answer = Integer.MAX_VALUE;
        for ( int i = 0 ; i < wires.length ; ++i ) {
            int u = wires[i][0]-1;
            int v = wires[i][1]-1;
            // tried[u][v] = true;
            // tried[v][u] = true;
            answer = Math.min( calc(u, v, visited), answer );
        }
        
        return answer;
    }
    
    int calc( int u, int v, boolean[] visited ) {
        Arrays.fill(visited, false);
        visited[v] = true;
        visited[u] = true;
        
        int vSize = bfs(v, visited);
        int uSize = bfs(u, visited);
        if ( vSize + uSize != visited.length ) {
            return Integer.MAX_VALUE;
        }
        //
       //System.out.printf("u: %d v : %d ( %d - %d )\n", u+1, v+1, uSize, vSize);
        
        
        return Math.abs(vSize - uSize);
    }
    
    int bfs( int x, boolean[] visited ) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(x);
        visited[x] = true;
        int cnt = 0;
        while ( !q.isEmpty() ) {
            int cur = q.poll();
            cnt++;
            
            for ( int nxt : conn[cur] ) {
                if ( visited[nxt] ) continue;
                visited[nxt] = true;
                q.offer(nxt);
            }
        }
        
        return cnt;
    }
}
