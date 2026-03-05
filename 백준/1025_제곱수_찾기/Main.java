import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;
    private final BufferedWriter writer;

    private int N, M;
    private int[][] cells;
    private long answer = -1;


    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        // System.out.printf(" N : %d M : %d\n", N, M);
        cells = new int[N][M];
        for (int i = 0; i < N; i++) {
            String line = reader.readLine();
            for (int j = 0; j < M; j++) {
                cells[i][j] = line.charAt(j) - '0';
            }
        }
    }

    long toScore(List<Integer> values) {
        int length = values.size();
        long score = 0;
        for ( int v : values ) {
            score = score * 10 + v;
        }
        return score;
    }

    boolean isSquaredNumber(long value) {
        if ( value < 0) return false;
        long r = (long) Math.sqrt((double)value);
        while ( r*r < value ) r++;
        while ( r*r > value ) r--;
        return r*r == value;
    }

    boolean isBounded(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < M;
    }

    /**
     * 주어진 위치에서 시작하여 dr, dc 방향으로 이동하면서 숫자들을 탐색하는 한다.
     *  @param i 시작 행
     *  @param j 시작 열
     *  @param dr 행 방향 이동량
     *  @param dc 열 방향 이동량
     *  @param values 현재까지 탐색한 숫자들의 리스트
     */
    void explore( int i, int j, int dr, int dc, List<Integer> values ) {
        if (!isBounded(i, j)) return;
        values.add(cells[i][j]);
        long score = toScore(values);
        if (isSquaredNumber(score)) {
            answer = Math.max(answer, score);
        }
        // dr, dc가 모두 0이면 더 이상 진행하지 않음
        if (dr == 0 && dc == 0) {
            values.remove(values.size() - 1);
            return;
        }
        explore(i + dr, j + dc, dr, dc, values);
        values.remove(values.size() - 1);
    }

    public void run() throws IOException {
        List<Integer> workspace = new ArrayList<>();
        for ( int i = 0 ; i < N ; i++ ) {
            for ( int j = 0 ; j < M ; j++ ) {
                for ( int dr = -N ; dr < N ; dr++ ) {
                    for ( int dc = -M ; dc < M ; dc++ ) {
                        explore(i, j, dr, dc, workspace);
                        workspace.clear();
                    }
                }
            }
        }
        writer.write(String.valueOf(answer));
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

