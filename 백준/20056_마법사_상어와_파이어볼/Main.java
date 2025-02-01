import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, M, K;

    static class Fireball {
        int r, c, m, s, d;

        Fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    private List<Fireball>[][] board;
    private List<Fireball> fireballs;
    private static final int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        K = Integer.parseInt(tokens[2]);

        board = new List[N][N];
        fireballs = new ArrayList<>();

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                board[i][j] = new ArrayList<>();
            }
        }

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int r = Integer.parseInt(tokens[0]) - 1;
            int c = Integer.parseInt(tokens[1]) - 1;
            int m = Integer.parseInt(tokens[2]);
            int s = Integer.parseInt(tokens[3]);
            int d = Integer.parseInt(tokens[4]);
            Fireball fireball = new Fireball(r, c, m, s, d);
            board[r][c].add(fireball);
            fireballs.add(fireball);
        }
    }

    private void move(int turn) {
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                board[i][j].clear();
            }
        }

        for(Fireball f : fireballs) {
            int r = f.r;
            int c = f.c;
            int m = f.m;
            int s = f.s;
            int d = f.d;

            int nr = (r + dr[d] * s) % N;
            int nc = (c + dc[d] * s) % N;

            if(nr < 0) {
                nr += N;
            }

            if(nc < 0) {
                nc += N;
            }

            f.r = nr;
            f.c = nc;

//            board[r][c].remove(f);
            board[nr][nc].add(f);
        }
    }

    private void merge(int turn) {
        for(int r = 0 ; r < N ; r++) {
            for(int c = 0 ; c < N ; c++) {
                if(board[r][c].size() < 2) {
                    continue;
                }

                int sumM = 0;
                int sumS = 0;
                boolean isOdd = true;
                boolean isEven = true;

                for(Fireball fireball : board[r][c]) {
                    sumM += fireball.m;
                    sumS += fireball.s;
                    if(fireball.d % 2 == 0) {
                        isOdd = false;
                    } else {
                        isEven = false;
                    }
                    fireballs.remove(fireball);
                }

                int newM = sumM / 5;
                int newS = sumS / board[r][c].size();

                board[r][c].clear();

                if(newM <= 0) {
                    continue;
                }

                for(int i = 0 ; i < 4 ; i++) {
                    int newD = (isOdd || isEven) ? i * 2 : i * 2 + 1;
                    Fireball newFireball = new Fireball(r, c, newM, newS, newD);
//                    board[r][c].add(newFireball);
                    fireballs.add(newFireball);
                }
            }
        }
    }

    public void run () {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < K ; i++) {
            move(i);
            merge(i);
//            fireballs.clear();
//            for(int r = 0 ; r < N ; r++) {
//                for(int c = 0 ; c < N ; c++) {
//                    fireballs.addAll(board[r][c]);
//                }
//            }
        }

        int sum = 0;
        for(Fireball fireball : fireballs) {
            sum += fireball.m;
        }
        sb.append(sum).append("\n");
        System.out.println(sb.toString());
    }
}
