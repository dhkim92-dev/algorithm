import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");

        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);

        File dir = null;
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return; 
        }
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
        testRun(1423);
        //run();
    }
}

class Solution {
    
    int levelLimit;

    int days;
    
    long[] nrCharacters;

    long[] strength;

    public Solution(BufferedReader reader) throws IOException {
        levelLimit = Integer.parseInt(reader.readLine());
        nrCharacters = new long[levelLimit+1];
        strength = new long[levelLimit+1];
        String[] chars = reader.readLine().split(" ");
        String[] strs = reader.readLine().split(" ");

        for(int i = 1 ; i <= levelLimit ; i++) {
            nrCharacters[i] = Long.parseLong(chars[i-1]);
            strength[i] = Long.parseLong(strs[i-1]);
        }

        days = Integer.parseInt(reader.readLine());
    }

    public void run() {
        long answer = 0;
        long[] dp = new long[days+1];
        long initialSum = 0;

        for(int i = 1 ; i <= levelLimit ; i++) {
            initialSum += (nrCharacters[i] * strength[i]);
            nrCharacters[i] = Math.min(nrCharacters[i], days); // 주어진 days 내에 레벨업 할 수 있는 최대 캐릭터 수는 days개.
        }
        
        //
        // dp[i] = i일째 얻을 수 있는 최대 추가 힘 수치
        // 1. 레벨 1의 캐릭터들부터 levelLimit - 1의 캐릭터들까지 순차적으로 레벨 업을 시켜본다.
        // 2. dp[i]의 업데이트를 최대 일수부터 진행한다.
        // 3. 현재 업데이트를 시도하는 일자 day에 대하여(day in range(days, -1, -1))
        // 4. 각 목표 레벨(nextLv in range(startLv + 1, levelLimit + 1)) 에 대해 필요한 플레이 일 수를 구한다.(requiredDays)
        // 5. 현재 일(day) + 플레이 일 수(requiredDays) > 최대 일수(days) 라면 반복문을 종료한다. // 목표 레벨에 도달할 수 없다.
        // 6. 목표 레벨에 도달했을 때 얻을 수 있는 추가 힘의 값을 구한다.(dStr = strength[nextDay] - strength[startDay]);
        // 7. dp[day + requiredDays] = max(dp[day+requiredDays], dp[day] + dStr)
        
        for(int startLv = 1 ; startLv < levelLimit ; startLv++) {
            while(nrCharacters[startLv] > 0) {
                for(int day = days ; day >=0 ; day--) {
                    for(int nextLv = startLv+1 ; nextLv <= levelLimit ; nextLv++) {
                        int requiredDays = nextLv - startLv;
                        if(day + requiredDays > days) {
                            break;
                        }

                        long dStr = strength[nextLv] - strength[startLv];
                        dp[day+requiredDays] = Math.max(dp[day+requiredDays], dp[day] + dStr);
                    }
                }
                nrCharacters[startLv]--;
            }
        }

        answer = initialSum + dp[days];
        System.out.println(answer);
    }
}


