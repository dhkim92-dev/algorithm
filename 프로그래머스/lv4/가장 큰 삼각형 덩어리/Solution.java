import java.util.*;

class Pos {
    public int r, c;
    
    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

class Solution {
    
    private int[][] grid;
    private int[][][] visited;
    private int R,C;
    private static final Pos[] dirs = {
        new Pos(-1, 0),
        new Pos(0, 1),
        new Pos(1, 0),
        new Pos(0, -1)
    };
    
    /** type 0  type 1
    *   -----   ------
    *   |\  |   |   /|
    *   |  \|   | /  |
    *   -----   ------
    * nextDirs[사각형타입][아랫면/윗면][이동인덱스]
    */
    private static final int[][][] nextDirs = {
        {
            {2, 3},
            {0, 1}
        },
        {
            {1, 2},
            {0, 3}
        }
    };
    
    private boolean isInRange(int r, int c) {
        return 0 <= r && r < R && 0 <= c && c < C;
    }
    
    private int getNextSide(int r, int c, int s, int nr, int nc) {
        int curType = this.grid[r][c];
        int nxtType = this.grid[nr][nc];
       
        // 북쪽이나 남쪽으로 이동하는 경우 윗면 아랫면 여부는 무조건 변경된다.
        int dr = Math.abs(nr - r);
        int dc = Math.abs(nc - c);
        if ( dr == 1 ) {
            return 1 - s;
        }
        
        // 좌우로 이동하는 경우 같은 타입이면 윗면 아랫면이 반대로 변경된다.
        
        return (curType == nxtType) ? 1 - s : s;
    }
    
    private int bfs(int areaNo, int r, int c, int side) {
        
        Queue<int[]> q = new LinkedList<>();
        visited[r][c][side] = areaNo;
        q.add( new int[]{r, c, side} );
        
        //System.out.println("areaNo : " + areaNo + " Search Result");
        int cnt = 0;
        while ( !q.isEmpty() ) {
            int[] cur = q.poll();
            int cr = cur[0];
            int cc = cur[1];
            int cs = cur[2];
            //System.out.printf("%d %d %d -> ", cr, cc, cs);
            cnt++; 
            for ( int i = 0 ; i < 2 ; ++i ) {
                int type = grid[cr][cc];
                Pos nxt = dirs[nextDirs[type][cs][i]];
                int nr = cur[0] + nxt.r;
                int nc = cur[1] + nxt.c;
                
                if ( !isInRange(nr, nc) ) continue;
                int ns = getNextSide(cr, cc, cs, nr, nc);
                if ( visited[nr][nc][ns] == areaNo || visited[nr][nc][1-ns] == areaNo ) continue;
                
                visited[nr][nc][ns] = areaNo;
                q.offer( new int[]{nr, nc, ns} );
            }
        }
        //System.out.println("");
        
        return cnt;
    }
    
    public int solution(int[][] grid) {
        int answer = 0;
        this.grid = grid;
        this.R = this.grid.length;
        this.C = this.grid[0].length;
        for ( int r = 0 ; r < R ; ++r ) {
            for ( int c = 0 ; c < C ; ++c ) {
                if ( this.grid[r][c] == -1 ) {
                    this.grid[r][c] = 0;
                } 
            }
        }
        visited = new int[R][C][2];
        
        //Map<Integer, Integer> areas = new HashMap<>();
        int areaOffset = 1;
        for ( int r = 0 ; r < this.R ; ++r ) {
            for ( int c = 0 ; c < this.C ; ++c ) {
                for ( int side = 0 ; side < 2 ; ++side ) {
                    if ( visited[r][c][side] != 0 ) continue;
                    answer = Math.max(answer, bfs(areaOffset, r, c, side));
                    areaOffset++;
                }
            }
        }
        
        //answer = Collections.max(areas.values());
        return answer;
    }
}
