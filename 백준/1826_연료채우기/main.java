import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");
        System.out.println("base path : " + base.toAbsolutePath());
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);
        File dir = new File(testFileDirName);
        File[] files = dir.listFiles();

        if(files == null) {
            return;
        }

        for(int i = 0 ; i<files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            reader.lines()
                    .forEach(System.out::println);
            reader.reset();

            System.out.println("answer ");
            new Solution(reader).run();
            reader.close();
        }
    }

    static void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
//        testRun(1826);
        run();
    }
}

class Solution {

    int N; // 주유소의 수

    Integer[][] gasStations; // gasStations[i][0] = i번째 주유소의 좌표, gasStations[i][1] = i번째 주유소의 주요 제한량

    int dist; // 도착지까지의 거리

    int fuel; // 시작 시 트럭의 연료량

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        gasStations = new Integer[N+1][2];

        for(int i = 0 ; i < N ; i++) {
            String[] line = reader.readLine().split(" ");
            gasStations[i][0] = Integer.parseInt(line[0]);
            gasStations[i][1] = Integer.parseInt(line[1]);
        }

        String[] line = reader.readLine().split(" ");
        dist = Integer.parseInt(line[0]);
        fuel = Integer.parseInt(line[1]);
        gasStations[N][0] = dist;
        gasStations[N][1] = 0;
    }

    public void run() {
        int answer = 0;

        // 주유소를 위치가 가까운 순, 주유 량이 많은 순으로 정렬한다.
        Arrays.sort(gasStations, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int truckPos = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder()); // 최대 힙
        for(int i = 0 ; i <= N ; i++) {
            Integer[] station = gasStations[i];
            if(station[0] <= fuel) {
                pq.add(station[1]);
            } else {
                // 다음 거리까지 갈 연료를 확보해야함
                while(!pq.isEmpty()) {
                    fuel += pq.poll();
                    answer++;
                    if(fuel >= station[0]) {
                        pq.add(station[1]);
                        break;
                    }
                }

                if(fuel < station[0]) {
                    break;
                }
            }
        }

        if(fuel < dist) {
            answer = -1;
        }


        System.out.println(answer);
    }
}

