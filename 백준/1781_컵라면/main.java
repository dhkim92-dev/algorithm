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
//        testRun(1781);
        run();
    }
}

class Task implements Comparable<Task>{
    public int reward;

    public int deadline;

    public Task(int deadline, int reward) {
        this.deadline = deadline;
        this.reward = reward;
    }

    @Override
    public int compareTo(Task o) {

        return deadline - o.deadline;
    }
}

class Solution {

    int N;

    Task[] tasks;

    int maxDeadline = 0;

    public Solution(BufferedReader reader) throws IOException {;
        N = Integer.parseInt(reader.readLine());
        tasks = new Task[N];

        StringTokenizer tokenizer;
        for(int i = 0 ; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int deadline = Integer.parseInt(tokenizer.nextToken());
            int reward = Integer.parseInt(tokenizer.nextToken());
            maxDeadline = Math.max(deadline, reward);
            tasks[i] = new Task(deadline, reward);
        }
    }

    public void run() {
        int answer = 0;
        Arrays.sort(tasks); // 마감일 순으로 우선 정렬.

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int count = 0;


        // 1. 마감일 순으로 오름차순 정렬
        // 2. 우선 순위 큐를 저장한다. 여기엔 과제의 보상 비용만 들어간다.
        // 3. 과제를 하나씩 꺼내 본다.
        // 4. 우선순위 큐에 현재 비용을 넣어준다.
        // 5. 현재 우선 순위 큐의 사이즈가 마감 일보다 크거나 같다면
        // 6. 우선순위 큐를 pop

        for(Task task : tasks) {
            int deadline = task.deadline;
            int reward = task.reward;
            pq.add(reward);

            while(pq.size() > deadline) {
                pq.poll();
            }
        }

        while(!pq.isEmpty()) {
            answer += pq.poll();
        }

        System.out.println(answer);
    }
}

