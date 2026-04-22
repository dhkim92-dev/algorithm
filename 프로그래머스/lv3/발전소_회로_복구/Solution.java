import java.util.*;

class Solution {
    
    private int R, C, N;
    
    private final static char ELEVATOR = '@';
    
    private final static char OBSTACLE = '#';
    
    private final static char EMPTY = '.';
    
    private final int[][] dirs = {
        {-1, 0},
        {0, 1},
        {1, 0},
        {0, -1}
    };
    
    void printDist(int[][] dist) {
        System.out.println("######### dist ########");
        for ( int i = 0 ; i <= N ; ++i ) {
            for ( int j = 0 ; j <= N ; ++j) {
                System.out.printf("%2d ", dist[i][j] );
            }
            System.out.println();
        }
    }
    
    public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
        // 소문제 1, 각 엘리베이터에서 패널까지의 거리
        // 소문제 2. 작업간의 의존 순서를 만족하는 작업 순서
        
        // p(x) => x번째 패널 위치
        // f(x) => x가 존재하는 층 
        // a(f(x)) => f(x)층의 엘리베이터
        
        // 관찰 1. f(x) != f(y) 인 두 패널 x,y 에 대해
        //        x to y 의 거리는 dist( a(f(x), p(x) ) + |f(x) - f(y) | 초가 걸림
        // 관찰 2. Topology Sort는 가능한 경로중 하나만 출력하기 때문에 최단 시간을 구할 수 없음. 
        //        최단 거리 출력이니 다익스트라  
        
        R = grid.length;
        C = grid[0].length();
        N = panels.length;
        char[][][] cubeMap = createCubeMap(grid, panels, h);
        int[][] dist = getDist(cubeMap, panels);
        int[] prequisitions = new int[N]; // 
        for ( int[] seq : seqs ) {
            int before = seq[0]-1;
            int job = seq[1]-1;
            prequisitions[job] |= 0x01 << before;
        }
        
        int answer = dajik(dist, prequisitions);
        
        return answer;
    }
    
    int[][] getDist( char[][][] cm, int[][] panels ) {
        int[][] dist = new int[N+1][N+1];
        
        // dist[i][j] => i번 패널에서 j번 패널까지 최소 거리
        // N = 엘리베이터
        Queue<int[]> q = new ArrayDeque<>();
        dist[0][0] = 0;
        
        int[][] rcDist = new int[R][C];
        
        for ( int[] row : rcDist ) {
            Arrays.fill(row, -1);
        }
        
        // 1. 엘리베이터에서 각 위치까지 거리를 우선 구한다.
        int er = -1;
        int ec = -1;
        for ( int r = 0 ; r < R ; ++r ) {
            for ( int c = 0 ; c < C ; ++c ) {
                if ( cm[0][r][c] == ELEVATOR ) {
                    er = r;
                    ec = c;
                    break;
                }
            }
            if (er != -1) break;
        }

        q.offer(new int[] { er, ec });
        rcDist[er][ec] = 0;
         
        
        while ( !q.isEmpty() ) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            int d = rcDist[r][c];
            //System.out.printf("q cur : %d %d %d\n",r, c, d);
            
            for ( int i = 0 ; i < 4 ; ++i ) {
                int nr = r + dirs[i][0];
                int nc = c + dirs[i][1];
                int nd = d + 1;
                //System.out.printf("  nr: %d nc : %d : nd :%d \n", nr, nc, nd);
                
                if ( !isBounded(nr, nc) ) continue;
                if ( rcDist[nr][nc] != -1 ) continue;
                if ( cm[0][nr][nc] == OBSTACLE ) continue;
                rcDist[nr][nc] = nd;
                q.offer(new int[]{nr, nc});
            }
        }
        
        /*
         for ( int r = 0 ;r < R ; ++r) {
            for ( int c = 0 ; c < C ; ++c) {
                System.out.printf("%c ", cm[0][r][c]);
            }
            System.out.println();
        }
        
        for ( int r = 0 ;r < R ; ++r) {
            for ( int c = 0 ; c < C ; ++c) {
                System.out.printf("%2d ", rcDist[r][c]);
            }
            System.out.println();
        }
        
        System.out.println("엘리베이터에서 거리");
        for ( int i = 0 ; i < N ; ++i) {
            System.out.printf("From eleavator to %d is %d\n", i, rcDist[panels[i][1]-1][panels[i][2]-1]);
        }
        */
        
        for ( int i = 0 ; i < panels.length ; i++ ) {
            int[] panel = panels[i];
            int r = panel[1] - 1;
            int c = panel[2] - 1;
            
            dist[N][i] = rcDist[r][c];
            dist[i][N] = rcDist[r][c];
        }
        
        // 각 패널별로 bfs 수행 후 각 그리드까지 거리 확인  
        
        for ( int i = 0 ; i < panels.length ; i++ ) {
            int[] panel = panels[i];
            int f = panel[0] - 1;
            int pr = panel[1] - 1;
            int pc = panel[2] - 1;
            
            q.clear();
            for(int [] row : rcDist ) {
                Arrays.fill(row, -1);
            }
            
            q.offer(new int[]{pr, pc});
            rcDist[pr][pc] = 0;
            
            while ( !q.isEmpty() ) {
                int[] cur = q.poll();
                int r = cur[0];
                int c = cur[1];
                int d = rcDist[r][c];
                
                for ( int dir = 0 ; dir < 4 ; ++dir ) {
                    int nr = r + dirs[dir][0];
                    int nc = c + dirs[dir][1];
                    int nd = d + 1;
                    
                    if ( !isBounded(nr, nc) 
                        || rcDist[nr][nc] != -1
                        || cm[f][nr][nc] == OBSTACLE ) continue;
                    
                    rcDist[nr][nc] = nd;
                    q.offer(new int[]{nr, nc});
                }
            }
            
            for ( int j = i ; j < panels.length ; j++ ) {
                if (i == j) {
                    dist[i][j] = 0;
                    continue;
                }
                int floor = panels[j][0] - 1;
                int opr = panels[j][1] - 1;
                int opc = panels[j][2] - 1;
                
                if ( floor != f ) {
                    dist[i][j] = dist[N][i] + dist[N][j] + Math.abs( f - floor );
                    //System.out.printf(" node %d to %d need elevator(%d floor to %d floor) dist : %d\n", i, j, f, floor, dist[i][j]);
                } else {
                    dist[i][j] = rcDist[opr][opc];
                    //System.out.printf(" node %d to %d on same floor dist : %d \n", i, j, dist[i][j]);
                }
                dist[j][i] = dist[i][j];
            }
        }
        
        return dist;
    }
    
    char[][][] createCubeMap(String[] grid, int[][] panels, int height) {
        char[][][] cubeMap = new char[height][R][C];
        
        for ( int i = 0 ; i < height ; ++i ) {
            for ( int r = 0 ; r < R ; ++r ) {
                for ( int c = 0 ; c < C ; ++c ) {
                    cubeMap[i][r][c] = grid[r].charAt(c);
                }
            }
        }
        
        for ( int id = 0 ; id < panels.length ; ++id ) {
            int f = panels[id][0] - 1;
            int r = panels[id][1] - 1;
            int c = panels[id][2] - 1;
            cubeMap[f][r][c] = (char)('0' + id);
        }
        
        return cubeMap;
    }
    
    int dajik(int[][] dist, int[] prequisitions) {
        int totalStates = 1 << N;
        int completeMask = 0;
        for (int i = 0; i < N; ++i) {
            completeMask |= (1 << i);
        }

        int startState = (prequisitions[0] == 0) ? (1 << 0) : 0;

        int[][] best = new int[totalStates][N];
        for (int[] row : best) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1])
        );

        best[startState][0] = 0;
        pq.offer(new int[]{0, 0, startState});

        int answer = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {

            int[] cur = pq.poll();
            int node = cur[0];
            int d = cur[1];
            int state = cur[2];

            if (d != best[state][node]) continue;

            if (state == completeMask) {
                answer = d;
                break;
            }

            for (int nxt = 0; nxt < N; ++nxt) {

                int mask = 1 << nxt;

                if ((state & mask) != 0) continue;
                if ((state & prequisitions[nxt]) != prequisitions[nxt]) continue;

                int nState = state | mask;
                int nd = d + dist[node][nxt];

                if (nd >= best[nState][nxt]) continue;

                best[nState][nxt] = nd;
                pq.offer(new int[]{nxt, nd, nState});
            }
        }

        return answer;
    }

    private boolean isBounded(int r, int c) {
        return (0 <= r && r < R) && (0 <= c && c < C);
    }
}