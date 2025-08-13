import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N;

    static class Point {
        int x,y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    List<Point> points;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        this.N = Integer.parseInt(reader.readLine().trim());
        this.points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String[] line = reader.readLine().trim().split(" ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            points.add(new Point(x, y));
        }
    }

    public void run() throws IOException {
        points.sort((a, b) -> {
            if ( a.x == b.x) {
                return b.y - a.y; // 길이가 긴 것부터
            }
            return a.x - b.x; // x좌표가 작은 것부터
        });


        int answer = 0;
        Point range = new Point(points.get(0).x, points.get(0).y);
        int count = 1;

        for ( int i = 1 ; i < N ; ++i ) {
            Point nxt = points.get(i);
//            System.out.printf("next: (%d, %d), last: (%d, %d), range: (%d, %d)\n", nxt.x, nxt.y, last.x, last.y, range.x, range.y);
//            System.out.printf("range : (%d, %d), next: (%d, %d)\n", range.x, range.y, nxt.x, nxt.y);

            if ( nxt.x == range.x ) {
                continue;
            }

            if ( overlapped(range, nxt) ) {
                if (i == N - 1) {
                    range.y = Math.max(range.y, nxt.y);
                    answer += range.y - range.x;
                    count = 0;
//                    System.out.println("overlapped");
                } else {
                    range.y = Math.max(range.y, nxt.y);
                    count++;
//                    System.out.println("overlapped");
                }
            } else {
                // 겹치는 영역이 존재하지 않는 경우
                answer += range.y - range.x;
                count = 1;
                range.x = nxt.x;
                range.y = nxt.y;
//                System.out.println("not overlapped");
            }
//            System.out.printf(" range : (%d, %d), next: (%d, %d)\n", range.x, range.y, nxt.x, nxt.y);
        }

        if ( count == 1) {
            answer += range.y - range.x;
        }
        writer.write(String.valueOf(answer));
        writer.flush();
    }

    private boolean overlapped(Point last, Point nxt) {
        return ( nxt.x <= last.x && last.x <= nxt.y  && nxt.y <= last.y ) ||
                ( last.x <= nxt.x && nxt.x <= last.y && nxt.y <= last.y ) ||
                ( last.x <= nxt.x && nxt.x <= last.y && last.y <= nxt.y );

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


