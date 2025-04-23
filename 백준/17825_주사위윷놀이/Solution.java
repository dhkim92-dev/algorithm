import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int[][] routes; // 경로 분기
    private final int[] scores = {
        // 0  1  2  3  4
           0, 2, 4, 6, 8,
        // 5   6   7   8   9
           10, 12, 14, 16, 18,
        // 10  11  12  13  14 
           20, 22, 24, 26, 28,
        // 15  16  17  18  19 
           30, 32, 34, 36, 38, 
        // 20  21 
           40, 0,
        // 22  23  24 
           13, 16, 19,
        // 25  26 
           22, 24, 
        // 27  28  29 
           28, 27, 26, 
        // 30  31  32
           25, 30, 35 
    }; // 점수
    private int[] inputs; 
    private int[] pawns = {0, 0, 0, 0};
    private boolean[] exists;
    private final int GOAL=21;
    private final int TOTAL_NODES=33;

    private int answer = 0;

    public Solution(BufferedReader reader) throws IOException {
        inputs = new int[10];
        String[] tokens = reader.readLine().split(" ");

        for ( int i = 0; i < 10 ; ++i ) {
            inputs[i] = Integer.parseInt(tokens[i]);
        }

        routes = new int[TOTAL_NODES][2];
        exists = new boolean[TOTAL_NODES];
    }

    private void init() {
        
        for ( int[] row : routes ) {
            Arrays.fill(row, -1);
        }

        for ( int i = 0 ; i <= 20 ; ++i ) {
            routes[i][0] = i + 1;
        }

        routes[5][1] = 22;
        routes[10][1] = 25;
        routes[15][1] = 27;

        for ( int i = 22 ; i <= 24 ; ++i ) {
            routes[i][0] = i != 24 ? i + 1 : 30;
        }

        for ( int i = 25 ; i <= 26 ; ++i ) { 
            routes[i][0] = i != 26 ? i + 1 : 30;
        }

        for ( int i = 27 ; i <= 29 ; ++i ) { 
            routes[i][0] = i != 29 ? i + 1 : 30;
        }

        for ( int i = 30 ; i <= 32 ; ++i ) {
            routes[i][0] = i != 32 ? i + 1 : 20;
        }
        routes[GOAL][0] = GOAL;
        routes[GOAL][1] = GOAL;
    }

    private void move(int depth, int score) {
        if ( depth == 10 ) {
            answer = Math.max(answer, score);
            return;
        }

        for ( int i = 0 ; i < 4 ; ++i ) {
            int cur = pawns[i];
            int next = routes[cur][1] != -1 ? routes[cur][1] : routes[cur][0];

            for ( int j = 1 ; j < inputs[depth] ; ++j ) {
                next = routes[next][0];
            }

            if ( next == GOAL || ( next != GOAL && !exists[next] ) ) {
                pawns[i] = next;
                exists[cur] = false;
                exists[next] = true;
                int nextScore = score + scores[next];
                if ( next == GOAL ) {
                    exists[next]  = false;
                }
                move(depth + 1, nextScore);
                pawns[i] = cur;
                exists[next] = false;
                exists[cur] = true;

            }
        }
    }

    public void run () {
        init();
        StringBuilder sb = new StringBuilder();

        move(0, 0);
        sb.append(answer);

        System.out.println(sb.toString());
    }
}
