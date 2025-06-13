import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private double[] points;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
    }

    private double calcIntersectionHeight(double width) {
        double x = points[0];
        double y = points[1];
        double h1 = Math.sqrt(Math.pow(x, 2) - Math.pow(width, 2));
        double h2 = Math.sqrt(Math.pow(y, 2) - Math.pow(width, 2));
//        System.out.println( "h1: " + h1 + ", h2: " + h2 + ", width: " + width );
        return ( h1 * h2 ) / (h1 + h2);
    }

    private void simulate() throws IOException {
        points = new double[3];
        StringTokenizer st = new StringTokenizer(reader.readLine());
        for (int i = 0; i < 3; i++) {
            points[i] = Double.parseDouble(st.nextToken());
        }

        double lo = 0;
        double hi = Math.min(points[0], points[1]);

        while ( lo + 0.001 < hi ) {
            double mid = (lo + hi) / 2;
            double height = calcIntersectionHeight(mid);
            // width가 작아지면 height 커진다.
            // width가 커지면 height 작아진다.
            // 따라서 lo, hi를 반대로 조정
            if ( height < points[2] ) {
                hi = mid;
            } else {
                lo = mid;
            }
        }

        writer.write(String.format("%.3f", hi));
    }

    public void run() throws IOException {
        simulate();
        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}


