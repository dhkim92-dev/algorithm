import java.util.*;

class Solution {
    
    private int height;
    
    private int width;
    
    private Coord getSymetric(Coord p, int dir) {
        
        // dir 0 x축
        // dir 1 y축
        // dir 2 (0, height)에서 x축
        // dir 3 (width, 0) 에서 y축
        Coord symetric = new Coord(p.x, p.y);
        
        int diff = 0;
        switch(dir) {
            case 0:
                symetric.y = -1 * symetric.y;
                break;
            case 1:
                symetric.x = -1 * symetric.x;
                break;
            case 2:
                diff = height - symetric.y;
                symetric.y = height + diff;
                break;
            default :
                diff = width - symetric.x;
                symetric.x = width + diff;
                break;
        }
        
        return symetric;
    }
    
    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
        int[] answer = new int[balls.length];
        this.width = m;
        this.height = n;
        Coord standard = new Coord(startX, startY);
        
        // System.out.println("Standard Coord : " + standard.x + ", " + standard.y);
        for(int i = 0 ; i < balls.length ; i++) {
            int minDist = 999999999;
            Coord curCoord = new Coord(balls[i][0], balls[i][1]);
            // System.out.println("Current Coord " + curCoord.x  + ", " + curCoord.y);
            for(int dir = 0 ; dir < 4 ; dir++) {
                
                if((curCoord.y == standard.y)) {
                    if((curCoord.x < standard.x) && dir == 1) {
                        // 현재 공의 좌표가 치는 공보다 왼쪽이라면
                        // 왼쪽면 대칭 패스
                        continue;
                    }
                    
                    if((curCoord.x >= standard.x) && dir == 3){
                        // (m, 0) 에서 오른쪽면 대칭 패스
                        continue;
                    }
                }
                
                // x축 좌표가 같고
                if(curCoord.x == standard.x) {
                    // 현재공이 치는 공보다 아래 있다면 아랫면 대칭 패스 
                    if((curCoord.y < standard.y) && dir == 0) {
                        continue;
                    }
                    
                    // 현재 공이 치는 공보다 위에 있다면 윗면 대칭 패스
                    if((curCoord.y >= standard.y) && dir == 2) {
                        continue;
                    }
                }
                
                Coord symetric = getSymetric(curCoord, dir);
                // System.out.println("\tSymetric : " + symetric.x + ", " + symetric.y);                
                int dist = symetric.getDistanceSquare(standard);
                if(dist <= minDist) {
                    // System.out.println("\t\tUpdated distance : " + dist);
                    minDist = dist;   
                }
            }
            answer[i] = minDist;
        }
        
        return answer;
    }
    
    class Coord {
        
        public int x;
        
        public int y;
        
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getDistanceSquare(Coord p) {
            return (p.x - x) * (p.x - x) + (p.y - y) * (p.y - y);
        }
    }
}
