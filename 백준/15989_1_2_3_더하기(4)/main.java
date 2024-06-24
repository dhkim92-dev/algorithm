package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) throws IOException{

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    int[][] dp = new int[10001][4];

    int n = Integer.parseInt(reader.readLine());
    int[] targets = new int[n];
    int maxValue = 0;

    for(int i = 0 ; i < n ; i++) {
      targets[i] = Integer.parseInt(reader.readLine());
      maxValue = Math.max(targets[i], maxValue);
    }
    reader.close();

    //1을 만드는 경우
    dp[1][1] = 1; // 1;
    dp[1][2] = 0;
    dp[1][3] = 0;

    // 2를 만드는 경우
    dp[2][1] = 1; // 1+1
    dp[2][2] = 1; // 2
    dp[2][3] = 0;

    // 3을 만드는 경우
    dp[3][1] = 1; // 1+1+1
    dp[3][2] = 1; // 1+2
    dp[3][3] = 1; // 3

    // 중복 방지를 위해 dp[i][j] 에 들어가는 수식은 숫자가 오름차순으로 정렬되어있다고 가정한다
    // 예) 1+1+2 O, 1+2+1 X

    // dp[i][j] = i 를 만들 때 끝이 j로 끝나는 경우
    // dp[i][1] = dp[i-1][1] // i가 1로 끝나는 경우 i-1에 1로 끝나는 경우에 끝에 1을 더해서 구할 수 있다.
    // dp[i][2] = dp[i-2][2] // i가 2로 끝나는 경우 i-2에 1과 2로 끝나는 경우에다가 2를 더해서 구할 수 있다.
    // dp[i][3] = dp[i-3][1] + dp[i-3][2] + dp[i-3][3]; i-3 에 1,2,3으로 끝나는 경우에 3을 더해 구할 수 있다.

    // 예 i=4
    // dp[4][1] = dp[3][1] => 1 1 1 + 1
    // dp[4][2] = dp[2][1] + dp[2][2] = 1 1 + 2, 2 + 2
    // dp[4][3] = dp[1][1] + dp[1][2] + dp[1][3] = 1 + 3
    // 4개

    // 예 i=5
    // dp[5][1] = dp[4][1] = 1 (1+1+1+1+1)
    // dp[5][2] = dp[3][1] + dp[3][2] = 2 => (1+1+1+2, 1+2+2)
    // dp[5][3] = dp[2][1] + dp[2][2] + dp[2][3] => (1+1+3, 2+3)


    for(int i = 4 ; i <= maxValue ; i++) {
      dp[i][3] = dp[i - 3][1] + dp[i - 3][2] + dp[i - 3][3];
      dp[i][2] = dp[i - 2][1] + dp[i - 2][2];
      dp[i][1] = dp[i - 1][1];
    }

    for(int target : targets) {
      System.out.println((dp[target][1] + dp[target][2] + dp[target][3] ));
    }
  }
}
