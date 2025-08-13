import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int[] dayOffset = {0, 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};

    private static class Flower {
        int from;
        int to;

        Flower(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public boolean isInRange(int day) {
            return this.from <= day && this.to >= day;
        }
    }

    private int N;

    private List<Flower> flowers;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        this.flowers = new ArrayList<>();

        for ( int i = 1 ; i < this.dayOffset.length ; ++i ) {
            this.dayOffset[i] += this.dayOffset[i - 1];
        }

        this.N = Integer.parseInt(reader.readLine().trim());
        int minOffset = toOffset(3, 1);
        int maxOffset = toOffset(11, 30);

        for ( int i = 0 ; i < N ; ++i ) {
            String[] tokens = reader.readLine().trim().split(" ");
            int fromMonth = Integer.parseInt(tokens[0]);
            int fromDay = Integer.parseInt(tokens[1]);
            int toMonth = Integer.parseInt(tokens[2]);
            int toDay = Integer.parseInt(tokens[3]);

            int startOffset = toOffset(fromMonth, fromDay);
            int endOffset = toOffset(toMonth, toDay);

            this.flowers.add(new Flower(
                    startOffset,
                    endOffset - 1
            ));
        }
    }

    private int toOffset(int month, int day) {
        return this.dayOffset[month] + day;
    }

    public void run() throws IOException {

        this.flowers.sort((a,b) -> {
            if (a.from == b.from) {
                return b.to - a.to;
            }
            return a.from - b.from;
        }); // 시작일 기준 오름차순 정렬, 시작일이 동일하면 종료일 기준 내림차순 정렬

        int startDay = dayOffset[3] + 1;
        int endDay = dayOffset[11] + 30;

        // Naive Approach
        // 1. 현재 시작 기준점을 포함하는 영역 중 가장 긴 커버리지를 갖는 꽃을 선택.
        // 2. 종료일이 11월 30일 이후이면 종료, 아니라면 선택된 꽃의 종료일 + 1일을 새로운 시작 기준점으로 설정
        // 3. 1번으로 돌아가기
        // 4. 탐색 종료 후 종료일이 11월 30일 이전이라면 0을 출력

        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder() ); // 최대힙
        int i = 0;
        int currentDay = startDay;

        while ( true ) {
//            System.out.println("Current Day : " + currentDay);
//            if ( currentDay > endDay ) {
//                writer.write("Current day exceeds end day, returning 0\n");
//                writer.write("0\n");
//                return;
//            }
            for ( ; i < this.flowers.size() ; ++i ) {
                Flower flower = this.flowers.get(i);
                if ( !flower.isInRange(currentDay) ) {
                    if ( flower.to < currentDay ) {
                        continue; // 현재 시작일보다 종료일이 이전인 꽃은 무시
                    } else if ( flower.from > currentDay ) {
                        break; // 현재 시작일보다 시작일이 이후인 꽃은 더 이상 볼 필요 없음
                    }
                }
                pq.add(flower.to);
//                System.out.println("Adding flower with end day: " + flower.to + " to PQ");
            }

            if ( pq.isEmpty() ) {
//                writer.write("PQ is empty, currentDay = " + currentDay + "\n");
                writer.write("0\n");
                return;
            }

            answer++;
//            System.out.println("currentDay = " + currentDay + ", pq = " + pq);
            int maxEndDay = pq.poll();
            if ( maxEndDay >= endDay ) {
                break;
            }
            pq.clear();
            currentDay = maxEndDay + 1; // 다음 시작일은 현재 꽃의 종료일 + 1
        }
        writer.write(answer + "\n");

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


