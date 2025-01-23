import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Solution {

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos p) {
            return new Pos(r + p.r, c + p.c);
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "r=" + r +
                    ", c=" + c +
                    '}';
        }
    }

    static class Line {
        Pos start, end;
        int type;

        public Line(Pos start, Pos end) {
            this.start = new Pos(start.r, start.c);
            this.end = new Pos(end.r, end.c);
            this.type = start.r == end.r ? 0 : 1;
            align();
        }

        private void align() {
            if(start.r > end.r) {
//                Pos temp = start;
//                start = end;
//                end = temp;
                int temp = start.r;
                start.r = end.r;
                end.r = temp;
            }

            if(start.c > end.c) {
//                Pos temp = start;
//                start = end;
//                end = temp;
                int temp = start.c;
                start.c = end.c;
                end.c = temp;
            }
        }

        @Override
        public String toString() {
            return "Line{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    static final Pos[] DIRS = {
        new Pos(-1, 0), // +y
        new Pos(0, 1), // +x
        new Pos(1, 0), // -y
        new Pos(0, -1), // -x
    };

    private int L, N;
    private List<Line> traces;
    private Pos head;
    private int headDir;
    private BufferedReader reader;

    private static final int HORIZONTAL = 0, VERTICAL = 1;
    private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

    public Solution(BufferedReader reader) throws IOException {
        L = Integer.parseInt(reader.readLine());
        N = Integer.parseInt(reader.readLine());
        traces = new ArrayList<>();
        head = new Pos(0, 0);
        headDir = 1; // x축을 바라보고 있다.
        this.reader = reader;
    }

    private void rotateHead(char d) {
        if(d == 'R') {
            headDir = (headDir + 1)%4;
        }else {
            headDir = (headDir + 3)%4;
        }
    }

    private long getIntersectedDist(Pos head, int dir, Line line) {
        long ret = Integer.MAX_VALUE;
        switch(dir) {
            case NORTH: // 현재 머리가 +y축 방향을 향하고 있는 경우
                // 비교 선분이 x축에 평행한 경우
                // 현재 머리와 다음 머리 위치를 잇는 선분이 비교 선분의 y좌표를 포함해야 한다.
                if(line.type == HORIZONTAL
                        && head.c >= line.start.c
                        && head.c <= line.end.c
                        && head.r > line.end.r) {
                    ret = Math.min(ret, Math.abs(head.r - line.end.r));
                }

                if(line.type == VERTICAL
                        && head.c == line.start.c
                        && head.r > line.end.r) {
                    ret = Math.min(ret, Math.abs(head.r - line.end.r));
                }
                break;
            case EAST: // 현재 머리가 +x축 방향을 향하고 있는 경우
                if(line.type == HORIZONTAL
                        && head.r == line.start.r
                        && head.c < line.start.c) {
                    ret = Math.min(ret, Math.abs(head.c - line.start.c));
                }

                if(line.type == VERTICAL
                        && head.r >= line.start.r
                        && head.r <= line.end.r
                        && head.c < line.start.c) {
                    ret = Math.min(ret, Math.abs(head.c - line.start.c));
                }
                break;
            case SOUTH: // 현재 머리가 -y축 방향을 향하고 있는 경우
                if(line.type == HORIZONTAL
                        && head.c >= line.start.c
                        && head.c <= line.end.c
                        && head.r < line.start.r) {
                    ret = Math.min(ret, Math.abs(head.r - line.start.r));
                }
                if(line.type == VERTICAL
                        && head.c == line.start.c
                        && head.r < line.start.r) {
                    ret = Math.min(ret, Math.abs(head.r - line.start.r));
                }
                break;
            case WEST: //a 현재 머리가 -x축 방향을 향하고 있는 경우
                if(line.type == HORIZONTAL
                        && head.r == line.start.r
                        && head.c > line.end.c) {
                    ret = Math.min(ret, Math.abs(head.c - line.end.c));
                }

                if(line.type == VERTICAL
                        && head.r >= line.start.r
                        && head.r <= line.end.r
                        && head.c > line.end.c) {
                    ret = Math.min(ret, Math.abs(head.c - line.end.c));
                }
                break;
            default:
                break;
        }

        return ret;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();

//        String[] tokens;
        // 시작점과 도착점의 좌표만을 저장하여 선분로 저장하고,
        // 새로운 움직임에 의해 새로운 직선이 생기는 경우 이전 직선들과의 교차 여부를 체크한다.
        // 직선은 최대 N-1 개 만큼 생성되기 때문에 1000*1000*8bytes = 8MB 메모리 사용
        // 시간복잡도는 O(N^2)으로 충분함

        long playTime = 0;

        // 경계선을 trace에 포함시켜둔다.
        traces.add(new Line(new Pos(-L-1, -L-1), new Pos(-L-1, L+1))); // 북쪽 경계
        traces.add(new Line(new Pos(L+1, -L-1), new Pos(L+1, L+1))); // 남쪽 경계
        traces.add(new Line(new Pos(-L-1, -L-1), new Pos(L+1, -L-1))); // 서쪽 경계
        traces.add(new Line(new Pos(-L-1, L+1), new Pos(L+1, L+1))); // 동쪽 경계

        int t;
        char d = 'L';

        for(int i = 0 ; i <= N ; i++) {
            if(i < N) {
                String[] tokens = reader.readLine().split(" ");
                t = Integer.parseInt(tokens[0]);
                d = tokens[1].charAt(0);
            } else {
                t = 2 * L + 1;
            }

            Pos stride = new Pos(DIRS[headDir].r * t, DIRS[headDir].c * t);
            Pos nextHead = head.add(stride);

//            System.out.println("head : " + head + " nextHead : " + nextHead + " dir : " + headDir + " playTime : " + playTime + " t : " + t);
            // 경계선을 포함한 기존에 탐색한 모든 선분에 대해 교차 여부를 체크한다.
            long moveTime = Integer.MAX_VALUE;
            for(Line line : traces) {
                long dist = getIntersectedDist(head, headDir, line);
                if(dist != Integer.MAX_VALUE) {
//                    System.out.println(" intersected with line : " + line + " dist : " + dist);
                    moveTime = Math.min(moveTime, dist);
                }
            }
//            System.out.println("        moveTime : " + moveTime);


            if(moveTime <= t) {
                // 주어진 시간보다 실제 움직인 시간이 더 짧은 경우
                // 충돌했다고 볼 수 있다.
                sb.append(playTime + moveTime);
                break;
            }

            // 새로운 선분을 추가한다.
            traces.add(new Line(head, nextHead));
            playTime += t;
            rotateHead(d);
            head = new Pos(nextHead.r, nextHead.c);
        }

        System.out.println(sb);
    }
}
