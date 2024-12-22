class Solution {

    private static final int COUNTER_CLOCKWISE = 1;
    private static final int CLOCKWISE = -1;
    private static final int LINE = 0;

    public static class Vector {
        int x, y;

        public Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static int cross(Vector u, Vector v) {
            return u.x * v.y - u.y * v.x;
        }
    }

    protected Solution() {

    }

    private Vector[] points = new Vector[3];

    public Solution(BufferedReader reader) throws IOException {

        for(int i = 0 ; i < 3 ; i++) {
            String[] input = reader.readLine().split(" ");
            points[i] = new Vector(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        }
    }

    public void run() {
        System.out.println(ccw(points[0], points[1], points[2]));
    }

    private int ccw(Vector a, Vector b, Vector c) {
        Vector ab = new Vector(b.x - a.x, b.y - a.y);
        Vector bc = new Vector(c.x - b.x, c.y - b.y);
        int result = Vector.cross(ab, bc);

        if(result > 0) {
            return COUNTER_CLOCKWISE;
        } else if(result < 0) {
            return CLOCKWISE;
        } else {
            return LINE;
        }
    }
}

