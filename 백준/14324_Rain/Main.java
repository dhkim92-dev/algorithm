import java.io.*;
import java.util.*;

class Solution {

    private final int nrTestCases;
    private BufferedReader reader;
    private int R, C;
    private final int[][] islands;
    private final int[][] tmp;
    private final int[] dr = { -1, 0, 1, 0 };
    private final int[] dc = { 0, 1, 0, -1 };

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
        this.nrTestCases = Integer.parseInt(reader.readLine());
        this.islands = new int[10][10];
        this.tmp = new int[10][10];
    }

    private void clearArray(int[][] target) {
        for ( int[] row : target ) {
            Arrays.fill(row, 0);
        }
    }

    private boolean isBorder(int r, int c) {
        return (r == 0 || r == R - 1 || c == 0 || c == C - 1);
    }

    private void initTestCase() throws IOException {
        clearArray(islands);
        clearArray(tmp);

        String[] tokens = reader.readLine().split(" ");
        R = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);
        for ( int r = 0 ; r < R ; ++r ) {
            tokens = reader.readLine().split(" ");
            for ( int c = 0 ; c < C ; ++c ) {
                islands[r][c] = Integer.parseInt(tokens[c]);

                if ( isBorder(r, c) ) {
                    tmp[r][c] = islands[r][c];
                } else {
                    tmp[r][c] = 1000;
                }
            }
        }
    }

    private int countWaterAmount() {
        int waterAmount = 0;
        for ( int r = 0 ; r < R ; ++r ) {
            for ( int c = 0 ; c < C  ; ++c ) {
                if( tmp[r][c] > islands[r][c] ) {
                    waterAmount += tmp[r][c] - islands[r][c];
                }
            }
        }
        return waterAmount;
    }

    private int simulate() {
        int answer = 0;
        boolean changed = true;

        while ( changed ) {
            changed = false;
            for ( int r = 1 ; r < R - 1 ; ++r ) {
                for ( int c = 1 ; c < C - 1 ; ++c ) {
                    int current = tmp[r][c];
                    for ( int i = 0 ; i < 4 ; ++i ) {
                        int nxtR = r + dr[i];
                        int nxtC = c + dc[i];
                        current = Math.min(current, Math.max(islands[nxtR][nxtC], tmp[nxtR][nxtC]));
                    }

                    if ( current < tmp[r][c] ) {
                        tmp[r][c] = current;
                        changed = true;
                    }
                }
            }
        }

        return countWaterAmount();
    }

    public void run () throws IOException {
        StringBuilder sb = new StringBuilder();
        for ( int testCaseNo = 1 ; testCaseNo <= nrTestCases ; ++testCaseNo ) {
            initTestCase();
            sb.append("Case #").append(testCaseNo)
                    .append(": ")
                    .append(simulate())
                    .append("\n");
        }
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution(new BufferedReader(new InputStreamReader(System.in))).run();
    }
}
