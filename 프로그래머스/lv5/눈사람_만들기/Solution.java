import java.util.*;
import java.io.*;

class Solution {
    
    private Pos[] dirs = {
        new Pos(-1, 0),
        new Pos(0, 1),
        new Pos(1, 0),
        new Pos(0, -1)
    };
    
    private char[][] board;
    
    private Pos[] balls;
    
    private int aSize, bSize, sharedSize;
    
    private static final char SNOW = '.';
        
    private static final char WALL = '#';
    
    private static final char BALL = 'o';
    
    private static final char EMPTY = ' ';
    
    private int[][] reachable;
    
    private Intersection[] intersections;
    
    private int R, C;
    
    private void init(String[] grid) {
        R = grid.length;
        C = grid[0].length();
        
        board = new char[R][C];
        balls = new Pos[2];
        reachable = new int[R][C];
        
        int offset = 0;
        for ( int r = 0 ; r < R ; ++r ) {
            for ( int c = 0 ; c < C ; ++c ) {
                board[r][c] = grid[r].charAt(c);
                if ( board[r][c] == 'o' ) {
                    balls[offset++] = new Pos(r, c);
                }
            }
        }
        intersections = new Intersection[2];
        intersections[0] = new Intersection(new Pos(-1, -1), Integer.MAX_VALUE);
        intersections[1] = new Intersection(new Pos(-1, -1), Integer.MAX_VALUE);
    }
    
    boolean inBound(Pos p) {
        return inBound(p.r, p.c);
    }
    
    boolean inBound(int r, int c) {
        return (0 <= r && r < R) && (0 <= c && c < C);
    }
    
    private int _markReachable(int idx, int r, int c, boolean[][] visited) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c, 0});
        visited[r][c] = true;
        reachable[r][c] |= (0x01 << idx);
        int minDist = Integer.MAX_VALUE;
        
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int nrRoad = 0;
            for ( int i = 0 ; i < 4 ; ++i ) {
                int[] nxt = new int[3];
                nxt[0] = cur[0] + dirs[i].r;
                nxt[1] = cur[1] + dirs[i].c;
                nxt[2] = cur[2] + 1;
                
                if ( !inBound(nxt[0], nxt[1]) ) continue;
                if ( board[nxt[0]][nxt[1]] == WALL ) continue;
                nrRoad++;
                if ( visited[nxt[0]][nxt[1]] ) continue;
                if ( board[nxt[0]][nxt[1]] == BALL ) {
                    minDist =  Math.min(minDist, nxt[2]);
                    continue;
                }
                
                visited[nxt[0]][nxt[1]] = true;
                reachable[nxt[0]][nxt[1]] |= (0x01 << idx);
                q.offer(nxt);
            }
            
            if ( 3 <= nrRoad ) {
                if ( intersections[idx].dist > cur[2] ) {
                    intersections[idx].pos.r = cur[0];
                    intersections[idx].pos.c = cur[1];
                    intersections[idx].dist = cur[2];
                }
            }
        }
        
        return minDist;
    } 
    
    private int markReachable() {
        boolean[][] visited = new boolean[R][C];
        for ( boolean[] row : visited) {
            Arrays.fill(row, false);
        }
        int minDist = _markReachable(0, balls[0].r, balls[0].c, visited);
        for ( boolean[] row : visited) {
            Arrays.fill(row, false);
        }
        minDist = Math.min(minDist,_markReachable(1, balls[1].r, balls[1].c, visited));
        
        return minDist;
    }
    
    void countAreaSize() {
        for ( int i = 0 ; i < R ; ++i ) {
            for ( int j = 0 ; j < C ; ++j ) {
                //System.out.printf("reachable[%d][%d] = %d\n", i, j, board[i][j]);
                if ( reachable[i][j] == 3 ) {
                    sharedSize++;
                } else if ( reachable[i][j] == 1 ) {
                    aSize++;
                } else if ( reachable[i][j] == 2  ) {
                    bSize++;
                }
            }
        }
    }
    
    public long solution(String[] grid) {
        long answer = 0;
        init(grid);
        int dist = markReachable();
        countAreaSize();
        
        System.out.printf(
        "A : %d, B : %d, C : %d gap : %d D[0] : %d D[1] : %d\n", aSize, bSize, sharedSize, dist, intersections[0].dist, intersections[1].dist);
        if ( intersections[0].dist == Integer.MAX_VALUE
            && intersections[1].dist == Integer.MAX_VALUE ) {
            // 직선 위상인 경우
            System.out.println("직선 위상");
            answer = calc(0, Math.min(aSize,bSize), Math.max(aSize,bSize), sharedSize, dist, Integer.MAX_VALUE);
        } else if ( intersections[0].dist != Integer.MAX_VALUE 
                   && intersections[1].dist != Integer.MAX_VALUE ) {
            // A,B 모두 교차점이 존재하거나, A-B 사이 구간에 교차점이 존재하는 경우
            System.out.println("양쪽 모두 접근 가능한 교차점 존재");
            answer = calc(2, aSize, bSize, sharedSize, dist, Integer.MAX_VALUE);
        } else if ( intersections[0].dist != Integer.MAX_VALUE 
                   && intersections[1].dist == Integer.MAX_VALUE ) {
            System.out.println("A 한쪽에만 교차점 존재");
            // A 또는 B에만 교차점이 존재하는 경우
            answer = calc(1, aSize, bSize, sharedSize, dist, intersections[0].dist);
        } else if ( intersections[0].dist == Integer.MAX_VALUE 
                   && intersections[1].dist != Integer.MAX_VALUE ) {
            System.out.println("B 한쪽에만 교차점 존재");
            answer = calc(1, bSize, aSize, sharedSize, dist, intersections[1].dist);
        }
        
        return answer;
    }
    
    long calc( int type, int a, int b, int c, int ballDist, int intersectionDist ) {
        // n을 눈사람을 구성하는데 필요한 눈덩이의 양이라고 정의하고,
        // a = 2, c = 2, b = 9 인 직선 위상을 가정하자.
        // n = 13 라면, 조합의 수는 기본적으로 n/2가 된다.
        // (1, 12), (2, 11), (3, 10), (4, 9), (5, 8), (6, 7) 쌍에서 (5, 8), (6, 7) 불가(최소 머리크기 조건 위배)
        // (1, 12) 불가 (최대 몸통 크기 조건 위배)
        // n = 12
        // (1, 11), (2, 10), (3, 9), (4, 8), (5, 7), (6, 6) 
        // (5, 7), (6, 6) 불가 (최소 머리 크기 조건 위배)
        
        
        long ret = 0;
        long sum = a+b+c;
        
        if ( type == 0 ) {
            for P1+r4D73=1B5D35323B25703125733B257032257307\( int n = ballDist + 1 ; n <= sum ; ++n ) {
                ret += Math.min( n/2, a+c );
                    //System.out.printf("n : %d a: %d b : %d c: %d  gap : %d toInt : %d add : %d minus : %d\n", n, a,b,c, ballDist, intersectionDist, Math.min(n/2, a+c), n-b-c-1);
                // 머리 크기가 a+c 이하인 경우의 수를 추린다.
                if ( n > b + c + 1) {
                    // 불가능한 머리크기 사이즈라면 
                    //System.out.println("    불가능한 머리 사이즈 존재");
                    ret -= (n-b-c-1);
                }
            }
        }
        
        // A, B양끝에 교차점이 있거나, A, B 사이에 교차점이 존재하는 경우
        // A-B 사이에 교차점이 존재하는 경우 
        // n으로 만들 수 있는 모든 경우에 대해 가능하다.
        // 가령 C에 교차점이 있고 A와 B에서 모두 접근 가능하다고 가정하자. 
        // A영역 + A와 B사이의 모든 눈을 A 눈덩이로 소모하고, 교차점의 틈으로 이동 후 B를 이동시킨다면
        // B의 영역까지 A가 모두 이용가능하다(B자체 눈덩이 크기 1 제외)
        // B도 마찬가지이다. 
        // 이는 A와 B의 영역에 모두 교차점이 존재한다고 가정하자.
        // 이 경우에도 동일하다, 한쪽이 교차점을 A가 A의 영역을 모두 소비하고 B를 A의 영역으로 전개하면, B의 크기는 1이 유지되고,
        // B의 영역도 모두 소비할 수 있으므로 모든 경우의 수가 가능하다.
        if ( type == 2 ) {
            for ( int n = ballDist + 1 ; n <= sum ; ++n ) {
                ret += n/2;
            }
        }
        
        // 한쪽만 교차로가 존재하는 경우 문제가 된다.
        if ( type == 1 ) {
            // A는 교차로에서 가까운 눈덩이
            // B는 교차로가 없는 눈덩이
            
            
            for ( int n = ballDist + 1 ; n <= sum ; ++n ) {
                if ( n > b + c + intersectionDist + 2) {
                    c++;
                }
                //System.out.printf("n : %d a: %d b : %d c: %d  gap : %d toInt : %d add : %d\n", n, a,b,c, ballDist, intersectionDist, Math.min(n/2, a+c));
                
                ret += Math.min(n/2, b+c);
            }
        }
        
        return ret;
    }
    
    private static class Intersection {
        Pos pos;
        int dist;
    
        public Intersection(Pos pos, int dist) {
            this.pos = pos;
            this.dist = dist;
        }
    }
    
    private static class Pos {
        int r,c;
        
        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
        
        Pos add(Pos o) {
            return new Pos(r + o.r , c + o.c);
        }
    }
}
