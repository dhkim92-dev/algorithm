import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private int M, S;

    class Fish {
        int d;

        Fish(int d) {
            this.d = d;
        }
    }

    class Shark {
        int r, c;

        Shark(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private int[][] fishDirs = {
        {0, -1}, // 0: left
        {-1, -1}, // 1: up-left
        {-1, 0}, // 2: up
        {-1, 1}, // 3: up-right
        {0, 1}, // 4: right
        {1, 1}, // 5: down-right
        {1, 0}, // 6: down
        {1, -1} // 7: down-left
    };

    private int[][] sharkDirs = {
        {-1, 0}, // Up,
        {0, -1}, // Left,
        {1, 0}, // Down,
        {0, 1} // Right
    };

    private List<Fish>[][] fishMap;

    private List<Fish> fishes;

    private Shark shark;

    private int[][] scentMap = new int[4][4];

    private int maxFishCount = 0;


    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] line = reader.readLine().split(" ");
        M = Integer.parseInt(line[0]);
        S = Integer.parseInt(line[1]);
        fishMap = new ArrayList[4][4];
        fishes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                fishMap[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            line = reader.readLine().split(" ");
            int r = Integer.parseInt(line[0]) - 1;
            int c = Integer.parseInt(line[1]) - 1;
            int d = Integer.parseInt(line[2]) - 1;
            Fish fish = new Fish(d);
            fishMap[r][c].add(fish);
            fishes.add(fish);
        }

        line = reader.readLine().split(" ");
        int sharkR = Integer.parseInt(line[0]) - 1;
        int sharkC = Integer.parseInt(line[1]) - 1;
        shark = new Shark(sharkR, sharkC);
    }

    private void copyFishes(List<Fish>[][] dst) {
        // List<Fish> copiedFishes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!fishMap[i][j].isEmpty()) {
                    for (Fish fish : fishMap[i][j]) {
                        dst[i][j].add(new Fish(fish.d));
                    }
                }
            }
        }
    }

    private void moveFishes(int turn) {
        List<Fish>[][] newFishMap = new ArrayList[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newFishMap[i][j] = new ArrayList<>();
            }
        }

        for ( int i = 0 ; i < 4 ; ++i ) {
            for ( int j = 0 ; j < 4 ; ++j ) {
                List<Fish> fishList = fishMap[i][j];
                if (fishList.isEmpty()) continue;

                for ( Fish fish : fishList ) {
                    int d = fish.d;
                    boolean moved = false;

                    for (int k = 0; k < 8; k++) {
                        int nd = (8 + d - k) % 8;
                        int nr = i + fishDirs[nd][0];
                        int nc = j + fishDirs[nd][1];

                        if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4) continue; // out of bounds
                        if (scentMap[nr][nc] != 0 && turn - scentMap[nr][nc] <= 2) continue; // blocked by shark scent
                        if (nr == shark.r && nc == shark.c) continue; // cannot move to shark position

                        newFishMap[nr][nc].add(new Fish(nd));
                        moved = true;
                        break; // move the fish and break the loop
                    }

                    if (!moved) {
                        newFishMap[i][j].add(new Fish(d)); // stay in place if no valid move found
                    }
                }
            }
        }

        fishMap = newFishMap;
    }

    private int countRemovedFishes(int[] path) {
        int count = 0;
        int r = shark.r;
        int c = shark.c; 
        int[][] counts = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                counts[i][j] = fishMap[i][j].size();
            }
        }

        for (int i = 0; i < 3 ; ++i) {
            int nr = r+sharkDirs[path[i]][0];
            int nc = c+sharkDirs[path[i]][1];
            if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4) {
                return 0;
            }
            count += counts[nr][nc];
            counts[nr][nc] = 0; // remove fishes at this position
            r = nr;
            c = nc;
        }

        // System.out.println("removed fish count: " + count);
        return count;
    }

    private void findPath(int depth, int r, int c, int[] path, int[] bestPath) {
        if ( depth == 3 ) {
            int fishCount = countRemovedFishes(path);
            if (fishCount > maxFishCount) {
                maxFishCount = fishCount;
                bestPath[0] = path[0];
                bestPath[1] = path[1];
                bestPath[2] = path[2];
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            int prior = path[depth];
            path[depth] = i;
            int nr = r + sharkDirs[i][0];
            int nc = c + sharkDirs[i][1];
            if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4) {
                path[depth] = prior; // reset to prior value
                continue; // out of bounds
            }
            findPath(depth + 1, nr, nc, path, bestPath);
            path[depth] = prior;
        }
    }

    private int[] findBestPath() {
        int[] path = {0, 0, 0};
        int[] bestPath = {9, 9, 9};
        maxFishCount = -1;
        findPath(0, shark.r, shark.c, path, bestPath);
        return bestPath;
    }

    private void moveShark(int turn, int[] bestPath) {
        // System.out.println("From shark position (" + shark.r + ", " + shark.c + ") to best path: " 
            // + bestPath[0] + ", " + bestPath[1] + ", " + bestPath[2]);
        for (int i = 0; i < 3; i++) {
            int dir = bestPath[i];
            if (dir < 0 || dir >= 4) {
                continue; // invalid direction
            }
            shark.r =  shark.r + sharkDirs[dir][0];
            shark.c =  shark.c + sharkDirs[dir][1];
            if ( fishMap[shark.r][shark.c].isEmpty() ) {
                continue; // no fish to eat
            }
            scentMap[shark.r][shark.c] = turn; // mark scent for 3 turns
            fishMap[shark.r][shark.c].clear();
        }
    }

    private void applyCopiedFishes(List<Fish>[][] copiedFishes) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!copiedFishes[i][j].isEmpty()) {
                    fishMap[i][j].addAll(copiedFishes[i][j]);
                    copiedFishes[i][j].clear(); // clear copied fishes after applying
                }
            }
        }
    }

    private void printScentMap(int turn, String message) {
        System.out.println("Scentmap Turn " + turn + " : " + message);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(scentMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printFishMap(int turn, String message) {
        System.out.println("Fishmap Turn " + turn + " : " + message);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(fishMap[i][j].size() + " ");
            }
            System.out.println();
        }
    }

    private void simulate() throws IOException { 
        List<Fish>[][] newFishMap = new ArrayList[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newFishMap[i][j] = new ArrayList<>();
            }
        }

        int answer = 0;
        for ( int turn = 1 ; turn <= S ; ++turn ) {
            // System.out.println("Turn " + turn + " : shark pos : " + shark.r + ", " + shark.c);
            copyFishes(newFishMap);
            // printFishMap(turn, "before moving fishes");
            // printScentMap(turn, "before moving fishes");
            moveFishes(turn);
            // printFishMap(turn, "after moving fishes");
            int[] bestPath = findBestPath();
            moveShark(turn, bestPath);
            // printFishMap(turn, "after moving shark");
            applyCopiedFishes(newFishMap);
            // printFishMap(turn, "after copy fishes");
        }

        for (int i = 0 ; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                answer += fishMap[i][j].size();
            }
        }

        writer.write(String.valueOf(answer) + "\n");
    }

    public void run() throws IOException {
        simulate();
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
