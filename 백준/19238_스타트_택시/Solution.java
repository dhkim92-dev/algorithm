import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private static class Pos {
        public int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos p) {
            return new Pos(this.r + p.r, this.c + p.c);
        }
    }

    private static class Passenger {
        public int id;
        public Pos pos;
    }

    private static class Item implements Comparable<Item> {
        public Pos p;
        public int dist;

        public Item(Pos p, int dist) {
            this.p = p;
            this.dist = dist;
        }

        @Override
        public int compareTo(Item o) {
            if ( this.dist == o.dist) {
                if( this.p.r == o.p.r ) {
                    return this.p.c - o.p.c;
                }

                return this.p.r - o.p.r;
            }

            return this.dist - o.dist;
        }
    }

    private Pos[] dirs = {
        new Pos(-1, 0),
        new Pos(0, 1),
        new Pos(1, 0),
        new Pos(0, -1)
    };

    private int[][] board;
    private boolean[][] visited;
    private int boardSize, nrPassengers, fuels;
    private Passenger[] passengers;
    private Pos[] destinations;
    private Pos taxi;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        boardSize = Integer.parseInt(tokens[0]);
        nrPassengers = Integer.parseInt(tokens[1]);
        fuels = Integer.parseInt(tokens[2]);

        board = new int[boardSize][boardSize];
        visited = new boolean[boardSize][boardSize];
        passengers = new Passenger[nrPassengers];
        destinations = new Pos[nrPassengers];

        for ( int i = 0 ; i < boardSize ; i++) {
            tokens = reader.readLine().split(" ");
            for ( int j = 0 ; j < boardSize ; j++ ) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        // System.out.println("Board size : " + boardSize);

        tokens = reader.readLine().split(" ");
        taxi = new Pos(Integer.parseInt(tokens[0]) - 1, Integer.parseInt(tokens[1]) - 1);

        for ( int i = 0 ; i < nrPassengers ; i++) {
            tokens = reader.readLine().split(" ");
            int r = Integer.parseInt(tokens[0]) - 1;
            int c = Integer.parseInt(tokens[1]) - 1;
            int destR = Integer.parseInt(tokens[2]) - 1;
            int destC = Integer.parseInt(tokens[3]) - 1;

            // System.out.println("r, c : " + r + ", " + c);
            // System.out.println("destR, destC : " + destR + ", " + destC);
            passengers[i] = new Passenger();
            passengers[i].id = i;
            passengers[i].pos = new Pos(r, c);

            board[r][c] = -(i+1);
            destinations[i] = new Pos(destR, destC);
        }
    }

    private boolean isInRange(Pos p) {
        return p.r >= 0 && p.r < boardSize && p.c >= 0 && p.c < boardSize;
    }

    private int findPassenger(Pos from) {
        Queue<Item> queue = new LinkedList<>();
        for ( boolean[] row : visited) {
            Arrays.fill(row, false);
        }
        queue.add(new Item(from, 0));
        visited[from.r][from.c] = true;
        PriorityQueue<Item> pq = new PriorityQueue<>();

        while (!queue.isEmpty()) {
            Item item = queue.poll();

            if (board[item.p.r][item.p.c] < 0) {
                // System.out.println("  to passenger " + (-board[item.p.r][item.p.c] -1 ) + " dist: " + item.dist + " at : " + item.p.r + " " + item.p.c);
                pq.add(item);
                continue;
            }

            for (Pos dir : dirs) {
                Pos next = item.p.add(dir);

                if ( !isInRange(next) || 
                     visited[next.r][next.c] || 
                     board[next.r][next.c] == 1) continue;

                visited[next.r][next.c] = true;
                queue.add(new Item(next, item.dist + 1));
            }
        }

        if ( pq.isEmpty() ) return -1;
        Item item = pq.poll();
        taxi = item.p;

        return item.dist;
    }

    private int moveTo(Pos from, Pos dest) {
        Queue<Item> queue = new LinkedList<>();
        for ( boolean[] row : visited) {
            Arrays.fill(row, false);
        }
        queue.add(new Item(from, 0));
        visited[from.r][from.c] = true;

        while (!queue.isEmpty()) {
            Item item = queue.poll();

            if (item.p.r == dest.r && item.p.c == dest.c) {
                taxi = item.p;
                return item.dist;
            }

            for (Pos dir : dirs) {
                Pos next = item.p.add(dir);

                if ( !isInRange(next) || 
                     visited[next.r][next.c] || 
                     board[next.r][next.c] == 1) continue;

                visited[next.r][next.c] = true;
                queue.add(new Item(next, item.dist + 1));
            }
        }

        return -1;
    }

    public int simulate() {
        for ( int i = 0 ; i < nrPassengers ; ++i ) {
            // System.out.println("Start taxi from : " + taxi.r + ", " + taxi.c + " with fuels : " + fuels);
            int dist = findPassenger(taxi);
            // System.out.println("Taxi found passenger at : " + taxi.r + ", " + taxi.c + ", dist " + dist);

            if ( dist == -1 ) return -1;

            fuels -= dist;

            if (fuels < 0) return -1;

            int passengerId = Math.abs(board[taxi.r][taxi.c]) - 1; 
            board[taxi.r][taxi.c] = 0;
            // System.out.println("Taxi pick up passenger at : " + taxi.r + ", " + taxi.c);
            int destDist = moveTo(taxi, destinations[passengerId]);
            // System.out.println("Taxi reach to destination at : " + taxi.r + ", " + taxi.c + ", dist " + destDist);
            
            if ( destDist == -1 ) return -1;
            fuels -= destDist;

            if (fuels < 0) return -1;
            fuels += destDist * 2;
        }

        return fuels;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate());
        System.out.println(sb.toString());
    }
}

