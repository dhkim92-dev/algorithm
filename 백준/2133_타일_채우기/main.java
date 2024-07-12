package org.example;
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
        testRun(2133);
        // run();
    }
}

class Solution {
    
    int n = 0;
    
    public Solution(BufferedReader reader) throws IOException {
        n = Integer.parseInt(reader.readLine());
    }

    public void run() {
        int answer = 0;

        int[] dp = new int[n+3];
        dp[0] = 1;
        dp[1] = 0;
        dp[2] = 3;
        dp[3] = 0;

        /*
        홀수 일땐 생성되지 않는다.
        dp를 2차원 배열이라고 가정하자
        dp[i] 는 n = i일때 나올 수 있는 모든 경우의 수
        dp[i][1], n=i 일때 나오는 고유 패턴 경우의 수
        dp[i][0] = 고유한 패턴 개수를 제외한 나머지
        F[i], n=i일때 패턴 | F'[i], n=i일 때 고유 패턴
        n(F'[i]) = 2 로 고정
        P[4] = |-------|
               |F2 | F2|
               |-------|
               유니크 패턴 2
        dp[4] = dp[2] * dp[2] + 2 = 11
        P[6] = |-------|
               |F'4| F2|
               |-------|
               |-------|
               |F2 |F'4|
               |-------|
               |-------|
               |F4 | F2|
               |-------|
               유니크 패턴 2
        dp[6] = dp[4][0] * dp[2] + 2 * dp[4][1] * dp[2] +2 = 41
              = dp[4]*dp[2] + dp[4][1] + dp[2] + 2
        P[8] = |-------|
               |F'6| F2|
               |-------|
               |-------|
               |F2 |F'6|
               |-------|
               |-------|
               |F4 |F`4|
               |-------|
               유니크 패턴 2
        dp[8] = dp[6][0] * dp[2] + 2*dp[6][1]*dp[2] + dp[4][0]*dp[4][1] 
              = dp[6]*dp[2] + dp[6][1] *dp[2] + dp[4][0]*dp[4][1] + 2; 
              = dp[6]*dp[2] + (dp[4] * 2) + (dp[2]*dp[6][1]) + 2
              = dp[6]*dp[2] + dp[4]*(F[4] 고유 개수) + dp[2] * (F6 고유 개수) + F8 고유개수
              = 123 + 6 + 22 + 2  = 153

        dp[i] = 3*dp[i-2] + 2*dp[i-4] + 2*dp[i-6] + ... + 2;
        dp[i-2] = 3*dp[i-4] + 2*dp[i-6] + ... + 2;
        dp[i] = 3*dp[i-2] + (3*dp[i-4] + ... + 2) -dp[i-4]
        dp[i] = 4*dp[i-2] - dp[i-4];
        */

        for(int i = 4 ; i <= n ; i+=2) {
            dp[i] = 4*dp[i-2] - dp[i-4];
        }
        answer = dp[n];
    

        System.out.println(answer);
    }
}
