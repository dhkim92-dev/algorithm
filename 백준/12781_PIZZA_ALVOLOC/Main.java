
class Solution {

    public static class Point implements Comparable<Point> {

        long x, y;

        Point(int x, int y) {
            this.x = (long)x;
            this.y = (long)y;
        }

        float crossProduct(Point p) {
            return x * p.y - y * p.x;
        }

        float dotProduct(Point p) {
            return x * p.x + y * p.y;
        }

        @Override
        public int compareTo(Point o) {
            if(x == o.x) {
                return Long.compare(y, o.y);
            }
            return Long.compare(x, o.x);
        }
    }

    Point[] points;

    private static int ccw(Point p1, Point p2, Point p3) {
        long result = (p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) - (p1.y * p2.x + p2.y * p3.x + p3.y * p1.x);
        return result > 0 ? 1 : result < 0 ? -1 : 0;
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        points = new Point[4];

        for(int i = 0 ; i < 8 ; i+=2) {
            int x = Integer.parseInt(tokens[i]);
            int y = Integer.parseInt(tokens[i+1]);
            points[i/2] = new Point(x, y);
        }
    }

    public void run() {
        long ccw1 = ccw(points[0], points[1], points[2]);
        long ccw2 = ccw(points[0], points[1], points[3]);
        long ccw3 = ccw(points[2], points[3], points[0]);
        long ccw4 = ccw(points[2], points[3], points[1]);

        long result1 = ccw1 * ccw2;
        long result2 = ccw3 * ccw4;

        if(result1 < 0 && result2 < 0) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}
