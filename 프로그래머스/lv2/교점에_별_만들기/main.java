import java.util.*;
import java.util.stream.Collectors;

class Solution {
    
    class Coord {
        
        public int x, y;
  		
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public String toString() {
            return "(" + x + ", " + y + ")"; 
        }
    }
    
    public List<Coord> calcIntersections(int[][] line) {
        int n = line.length;
		List<Coord> intersections = new ArrayList<>();
        
        for(int i = 0 ; i < n ; i++) {
            int a1 = line[i][0];
            int b1 = line[i][1];
            int c1 = line[i][2];
                
            for(int j = i+1 ; j < n ; j++) {
                int a2 = line[j][0];
                int b2 = line[j][1];
                int c2 = line[j][2];
             	long mod = (long)a1*b2 - (long)a2*b1;
                
                if(mod == 0) {
                    continue; // 평행하거나 일치하거나
                }
                
                long x = (long)b1*c2 - (long)c1*b2;
                long y = (long)a2*c1 - (long)a1*c2;
               
                if((x % mod != 0) || (y % mod != 0)) {
                    continue; // 정수가 아님
                }
                
                int r = (int)(y/mod);
                int c = (int)(x/mod);
                
                intersections.add(new Coord(c,r));
            }
        }
        
        return intersections;
    }
    
    
    public String[] solution(int[][] line) {
        String[] answer = {};
        
        // 우선 정수 교점들을 구한다.
        List<Coord> intersections = calcIntersections(line);
        //intersections.forEach(System.out::println);
        
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for(Coord c : intersections) {
            minX = Math.min(minX, (int)c.x);
            minY = Math.min(minY, (int)c.y);
            maxX = Math.max(maxX, (int)c.x);
            maxY = Math.max(maxY, (int)c.y);
        }
        
        //System.out.println("Min X" + minX);
        //System.out.println("Max X" + maxX);
        //System.out.println("Min Y" + minY);
        //System.out.println("Max Y" + maxX);
        
        char[][] board = new char[maxY - minY + 1][maxX - minX + 1];
        //System.out.println("board length : " + board.length + ", " + board[0].length);
        for(int i = 0 ; i < board.length; i++) {
            Arrays.fill(board[i], '.');
        }
        
        answer = new String[board.length];
        
        for(Coord crd : intersections) {
            //System.out.println(crd.toString());
                
            int r = (crd.y - minY); // 0부터 시작하도록 정규화
            int c = crd.x - minX; 
            //System.out.println(" normalized : " + r + ", " + c);
            board[r][c] = '*';
        }
        
        for(int r = 0 ; r < board.length ; r++) {
            answer[r] = new String(board[board.length - r -1]);
        }
        
        
        return answer;
    }
}
