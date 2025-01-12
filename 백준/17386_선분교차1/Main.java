
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

        if(result1 < 0 && result2 < 0) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}
