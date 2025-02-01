import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Pos) {
                Pos pos = (Pos)obj;
                return r == pos.r && c == pos.c;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private int N, M;
    private int[][] board;
    private int[][] tmp;
    private List<Pos> clouds;
    private static final int[] dr = {0, -1, -1, -1, 0, 1, 1, 1};
    private static final int[] dc = {-1, -1, 0, 1, 1, 1, 0, -1};
    private int[][] commands;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        board = new int[N][N];
        tmp = new int[N][N];
        clouds = new ArrayList<>();

        clouds.add(new Pos(N-1, 0));
        clouds.add(new Pos(N-1, 1));
        clouds.add(new Pos(N-2, 0));
        clouds.add(new Pos(N-2, 1));


        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            for(int j = 0 ; j < N ; j++) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        commands = new int[M][2];

        for(int i = 0 ; i < M ; i++) {
            tokens = reader.readLine().split(" ");
            int d = Integer.parseInt(tokens[0]) - 1;
            int s = Integer.parseInt(tokens[1]);
            commands[i][0] = d;
            commands[i][1] = s;
        }
    }

    private void moveClouds(int d, int s) {
        for(Pos cloud : clouds) {
            int nr = cloud.r + dr[d] * (s%N);
            int nc = cloud.c + dc[d] * (s%N);

            if(nr >= N) {
                nr -= N;
            } else if(nr < 0) {
                nr += N;
            }
            if(nc >= N) {
                nc -= N;
            } else if(nc < 0) {
                nc += N;
            }

            cloud.r = nr;
            cloud.c = nc;
        }
    }

    private void rain() {
        for(Pos cloud : clouds) {
            board[cloud.r][cloud.c]++;
        }
    }

    private Set<Pos> removeRains() {
        Set<Pos> removed = new HashSet<>();
        removed.addAll(clouds);
        clouds.clear();
        return removed;
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private void clearTmp() {
        for(int[] row : tmp) {
            Arrays.fill(row, 0);
        }
    }

    private void printBoard(int k) {
        System.out.println("--------------- status : " + k + " -----------------");
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }

    private void printCloud(int k) {
        System.out.println("--------------- status : " + k + " -----------------");
        for(int i = 0 ; i < N ; i++){
            for(int j = 0 ; j < N ; j++) {
                if(clouds.contains(new Pos(i, j))) {
                    System.out.print("C ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }

    private void copyWater(Set<Pos> clouds) {
        clearTmp();
        for(Pos cloud: clouds) {
            for(int i = 1 ; i < 8 ; i+=2) {
                int nr = cloud.r + dr[i];
                int nc = cloud.c + dc[i];

                if(!isInRange(nr, nc)) {
                    continue;
                }

                if(board[nr][nc] > 0) {
                    tmp[cloud.r][cloud.c]++;
                }
            }
        }

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                board[i][j] += tmp[i][j];
            }
        }
    }

    private void createClouds(Set<Pos> removed) {
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                if(board[i][j] >= 2 && !removed.contains(new Pos(i, j))) {
                    board[i][j] -= 2;
                    clouds.add(new Pos(i, j));
                }
            }
        }
    }

    public void run () {
        StringBuilder sb = new StringBuilder();

        int k = 0 ;
        for(int[] command : commands) {
            int d = command[0];
            int s = command[1];

            // 1. 모든 구름이 d 방향으로 s칸 이동한다.
//            printBoard(k);
            moveClouds(d, s);
//            printCloud(k);
            // 2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양이 1 증가한다.
            rain();
            // 3. 구름이 사라진다.
            Set<Pos> removed = removeRains();
            // 4. 물복사버그 마법을 시전한다.
            copyWater(removed);
            // 5. 구름이 생긴다.
            createClouds(removed);
//            printBoard(k);
//            printCloud(k);
            k++;
        }

        int sum = 0;

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < N ; j++) {
                sum += board[i][j];
            }
        }
        sb.append(sum);
        System.out.println(sb.toString());
    }
}

