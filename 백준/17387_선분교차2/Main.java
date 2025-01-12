
class Solution {

    public static class Point {

        long x, y;

        Point(int x, int y) {
            this.x = (long) x;
            this.y = (long) y;
        }
    }

    Point[] points;

    public Solution(BufferedReader reader) throws IOException {
        points = new Point[4];

        for(int i = 0 ; i < 2 ; i++) {
            String[] tokens = reader.readLine().split(" ");
            points[i*2] = new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            points[i*2+1] = new Point(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        }
    }

    private static int ccw(Point p1, Point p2, Point p3) {
        long result = (p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) - (p1.y * p2.x + p2.y * p3.x + p3.y * p1.x);
        return result > 0 ? 1 : result < 0 ? -1 : 0;
    }

    public void run() {
        int result1 = ccw(points[0], points[1], points[2]) * ccw(points[0], points[1], points[3]);
        int result2 = ccw(points[2], points[3], points[0]) * ccw(points[2], points[3], points[1]);

        long mnX1 = Math.min(points[0].x, points[1].x);
        long mnY1 = Math.min(points[0].y, points[1].y);
        long mxX1 = Math.max(points[0].x, points[1].x);
        long mxY1 = Math.max(points[0].y, points[1].y);

        long mnX2 = Math.min(points[2].x, points[3].x);
        long mnY2 = Math.min(points[2].y, points[3].y);
        long mxX2 = Math.max(points[2].x, points[3].x);
        long mxY2 = Math.max(points[2].y, points[3].y);

        int res = 0;

        if(result1 == 0 && result2 == 0) {
            // 평행 시 교차 여부 체크
            if( mnX1 <= mxX2 && mnX2 <= mxX1 && mnY1 <= mxY2 && mnY2 <= mxY1) {
                res = 1;
            }
        } else {
            if(result1 <= 0 && result2 <= 0) {
                res = 1;
            }
        }

        System.out.println(res);
    }
}
