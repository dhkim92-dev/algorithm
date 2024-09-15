import java.util.*;
import java.util.stream.*;

class Solution {
    
    Map<Integer, Point> pointsMap = new HashMap<>();
    
    Map<Point, Integer> countMap = new HashMap<>();
    
    List<List<Point>> traces = new ArrayList<>();
    
    private void initPointsMap(int[][] points) {
        for(int i = 0 ; i < points.length ; i++) {
            Point newPoint = new Point(points[i][0], points[i][1]);
            pointsMap.put(i+1, newPoint);
        }
    }
    
    private List<Point> traceRoute(int[] routes) {
        List<Point> trace = new ArrayList<>();
        
        for(int i = 1 ; i < routes.length ; i++) {
            Point src = pointsMap.get(routes[i-1]);
            Point dst = pointsMap.get(routes[i]);
            int r = src.r;
            int c = src.c;
            int dr = src.r < dst.r ? 1: -1;
            int dc = src.c < dst.c ? 1: -1;
            
            if(trace.isEmpty()) {
                trace.add(new Point(r,c));
            }
            
            while(r != dst.r) {
                r += dr;
                trace.add( new Point(r, c) );
            }
            
            while(c != dst.c) {
                c += dc;
                trace.add( new Point(r, c) );
            }
        }
        
        return trace;
    }
    
    private int countCrash() {
        int nrRobot = traces.size();
        int answer = 0;
        int maxLen = 0;
        for(int i = 0 ; i < traces.size() ; i++) {
            maxLen = Math.max(maxLen, traces.get(i).size());
        }
        
        for(int i = 0 ; i < maxLen ; i++) {
            countMap.clear();
            for(int j = 0 ; j < nrRobot ; j++) {
                if(i < traces.get(j).size()) {
                    Point p = traces.get(j).get(i);
                    countMap.put(p, countMap.getOrDefault(p, 0)+1 );
                }
            }
            
            for(Map.Entry<Point, Integer> entry : countMap.entrySet()) {
                int cnt = entry.getValue();
                
                if(cnt > 1) {
                    answer++;
                }
            }
        }
        
        
        return answer;
    }
    
    
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        int robotCount = routes.length;
        
        initPointsMap(points);
        
        for(int i = 0 ; i < routes.length ; i++) {
        	List<Point> trace = traceRoute(routes[i]);
            traces.add(trace);
        }
        
        answer += countCrash();
        
        return answer;
    }
    
    public static class Point {
        
        int r, c;
        
        public Point(int r, int c) {
            this.r = r;
            this.c = c;
            
        }
        
        public Point(Point p) {
            this.r = p.r;
            this.c = p.c;
        }
        
        @Override
        public boolean equals(Object o) {
            if(o == null || !(o instanceof Point)) return false;
            if(o == this) return true;
            Point other = (Point)o;
            return this.r == other.r && this.c  == other.c;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
    
}
