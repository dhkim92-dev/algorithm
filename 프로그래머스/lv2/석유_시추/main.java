import java.util.*;

class Solution {
    
    class Coord {
        
        public int r;
        
        public int c;
        
        public Coord(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    
    void initVisited(int[][] visited) {
        for(int i = 0 ; i < visited.length ; i++) {
            for(int j = 0 ; j < visited[0].length ; j++) {
                visited[i][j] = 0;
            }
        }
    }
    
    public int bfs(int[][] land, int[][] visited, int areaId, int row, int col) {
        int colSize = land[0].length;
        int rowSize = land.length;
        int count = 0;
        
        int[][] dirs = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1, 0}
        };
        Queue< Coord > q = new LinkedList<>();
        
        q.add(new Coord(row, col));
        visited[row][col] = areaId;
        
        while(!q.isEmpty()) {
            Coord cur = q.poll();
            count++;
            
            for(int i = 0 ; i < 4 ; i++) {
                int nr = cur.r + dirs[i][0];
                int nc = cur.c + dirs[i][1];
                
                if( (nr < 0 || nr >= rowSize) || (nc < 0 || nc >= colSize) ) {
                    continue;
                }
                
                if( visited[nr][nc] > 0 ) continue;
                if( land[nr][nc] == 0 ) continue;
                visited[nr][nc] = areaId;
                q.add(new Coord(nr, nc));
            }
        }
        
        return count;
    }
    
    public int solution(int[][] land) {
        int answer = 0;
        int colSize = land[0].length;
        int rowSize = land.length;
        int[][] visited = new int[rowSize][colSize];
        Map<Integer, Integer> areaMap = new HashMap<>();
        initVisited(visited);
        int areaId = 1;
        
        // bfs 탐색을 통해 석유가 모인 영역을 클러스터링하고, ID와 채산성을 기록한다.
        
        for(int r = 0 ; r < rowSize ; r++) {
            for(int c = 0 ; c < colSize ; c++) {
                if(land[r][c] == 1 && visited[r][c] == 0) {
                    int amount = bfs(land, visited, areaId, r, c);
                    areaMap.put(Integer.valueOf(areaId), Integer.valueOf(amount));
                    areaId++;
                }
            }
        }
        
        for(int c = 0 ; c < colSize ; c++) {
            int amount = 0;
            Set<Integer> visitedAreas = new HashSet<Integer>();
            
            for(int r = 0 ; r < rowSize ; r++) {
                if(visited[r][c] > 0) {
                    visitedAreas.add(Integer.valueOf(visited[r][c]));     
                }
            }
            
            for(int value : visitedAreas) {
                amount += areaMap.get(value);
            }
            
            answer = Math.max(amount, answer);
        }
        
        return answer;
    }
}
