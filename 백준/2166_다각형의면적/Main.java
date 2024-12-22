class Solution {

    public static class Vector {
        long x, y;

        public Vector(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    protected Solution() {

    }

    private int nrPoints = 0;

    private Vector[] points;

    public Solution(BufferedReader reader) throws IOException {
        nrPoints = Integer.parseInt(reader.readLine());
        points = new Vector[nrPoints+1];

        for(int i = 0 ; i < nrPoints ; i++) {
            String[] line = reader.readLine().split(" ");
            points[i] = new Vector(Long.parseLong(line[0]), Long.parseLong(line[1]));
        }
        points[nrPoints] = new Vector(points[0].x, points[0].y);
    }

    public void run() {
        double area = getArea();
        //area = round(area, 2);
        System.out.printf("%.1f\n", area);
    }

    private double round(double value, int place) {
        long factor = (long) Math.pow(10, place);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private double getArea() {
        long x = 0;
        long y = 0;

        for(int i = 0 ; i < nrPoints ; i++) {
            x += points[i].x * points[i+1].y;
            y += points[i].y * points[i+1].x;
        }

        return Math.abs(x-y)/2.0;
    }
}

