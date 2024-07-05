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
        testRun(1202);
//        run();
    }
}

class Solution {

    int N; // 보석의 수

    int K; // 가방의 수

    Integer[][] jewels; // 보석의 무게와 가격

    Integer[] bags; // 가방의 무게 제한

    public Solution(BufferedReader reader) throws IOException {;
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        K = Integer.parseInt(tokens[1]);

        jewels = new Integer[N][2];
        bags = new Integer[K];

        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            jewels[i][0] = Integer.parseInt(tokens[0]); // 무게 M
            jewels[i][1] = Integer.parseInt(tokens[1]); // 가격 V
        }

        for(int i = 0 ; i < K ; i++) {
            bags[i] = Integer.parseInt(reader.readLine());
        }
    }

    public void run() {
        long answer = 0;

        // 가방 1개에는 최대 한개의 보석을 담을 수 있다.
        // 보석을 골라 비용을 최대화 해야함.

        // 가방의 무게 제한이 낮은 순ㅇ로 정렬한다.
        Arrays.sort(bags);
        // 보석을 무게가 가벼운 순, 가격이 비싼 순으로 정렬한다.
        Arrays.sort(jewels, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

//        System.out.println("보석 정렬 결과");
//        Arrays.stream(jewels)
//                .forEach(it -> System.out.printf("%d %d\n",it[0], it[1]));

        int jewelCursor = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for(int bag : bags) {
//            System.out.println("가방 무게 : " + bag);
            // 현재 가방의 무게 제한과 동일한 무게까지의 모든 보석을 우선 순위 큐에 담는다.

            for(; jewelCursor < N && jewels[jewelCursor][0] <= bag ; jewelCursor++) {
//                System.out.println("  보석 우선순위 큐에 입력 : " + jewels[jewelCursor][0] + ", " + jewels[jewelCursor][1]);
                pq.add(jewels[jewelCursor][1]);
            }

            // 다음 것 중 최대 비용을 갖는 보석을 가져온다
            if(!pq.isEmpty()) {
                int cost = pq.poll();
//                System.out.printf(" Bag weight : %d   take jewel cost : %d\n", bag, cost);
                answer += (long)cost;
            }
        }


        System.out.println(answer);
    }
}

