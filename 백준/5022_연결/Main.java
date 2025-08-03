import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N, M;

    private Item A1,A2,B1,B2;

    private Item[] dirs = {
        new Item(-1, 0, 1), // Up
        new Item(0, 1, 1),   // Right
        new Item(1, 0, 1),  // Down
        new Item(0, -1, 1), // Left
    };

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;

        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]) + 1;
        M = Integer.parseInt(tokens[1]) + 1;

        tokens = reader.readLine().split(" ");
        A1 = new Item(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), 0);
        tokens = reader.readLine().split(" ");
        A2 = new Item(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), 0);
        tokens = reader.readLine().split(" ");
        B1 = new Item(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), 0);
        tokens = reader.readLine().split(" ");
        B2 = new Item(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), 0);
    }

    private boolean isBounded(Item p) {
        return (0 <= p.r && p.r < N) && (0 <= p.c && p.c < M);
    }

    private void printVisited(String message, boolean[][] visited) {
        System.out.println("-----------Visited : " + message + " -----------");
        for ( boolean[] row : visited ) {
            for ( boolean cell : row ) {
                System.out.print((cell ? 1 : 0) + " ");
            }
            System.out.println();
        }

        System.out.println("-----------------------------------");
    }

    private int bfs(Item a0, Item a1, Item b0, Item b1) {
        Queue<Item> q = new LinkedList<>();
        boolean[][] visited = new boolean[N+1][M+1];
        q.add(a0);
        visited[a0.r][a0.c] = true;
        visited[b0.r][b0.c] = true;
        visited[b1.r][b1.c] = true;

        int dist1 = Integer.MAX_VALUE;
        Item temp = null;

        while ( !q.isEmpty() ) {
            Item cur = q.poll();

            if (cur.r == a1.r && cur.c == a1.c) {
                dist1 = cur.dist;
                temp = cur;
                break;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                Item next = cur.add(dirs[i]);
                if ( !isBounded(next) || visited[next.r][next.c] ) continue;
                visited[next.r][next.c] = true;
                next.setPrev(cur);
                q.add(next);
            }
        }

        for ( boolean[] row : visited ) {
            Arrays.fill(row, false);
        }
//
//        System.out.println("TEMP");
        while ( temp != null) {
//            System.out.println(temp.r + "," + temp.c);
            visited[temp.r][temp.c] = true;
            temp = temp.prev;
        }
//        printVisited("Phase 1 finished", visited);

        q.clear();
        q.add(b0);
        visited[b0.r][b0.c] = true;
        visited[a0.r][a0.c] = true;
        visited[a1.r][a1.c] = true;

        int dist2 = Integer.MAX_VALUE;
        temp = null;

        while ( !q.isEmpty() ) {
            Item cur = q.poll();

            if (cur.r == b1.r && cur.c == b1.c) {
                dist2 = cur.dist;
                temp = cur;
                break;
            }

            for ( int i = 0 ; i < 4 ; ++i ) {
                Item next = cur.add(dirs[i]);
                if ( !isBounded(next) || visited[next.r][next.c] ) continue;
                visited[next.r][next.c] = true;
                next.setPrev( cur );
                q.add(next);
            }
        }
//        printVisited("Phase 2 finished", visited);

        if ( dist1 == Integer.MAX_VALUE || dist2 == Integer.MAX_VALUE ) {
            return Integer.MAX_VALUE;
        }
//
//        System.out.println("Distance from " + a0.r + "," + a0.c + " to " + a1.r + "," + a1.c + " is: " + dist1);
//        System.out.println("Distance from " + b0.r + "," + b0.c + " to " + b1.r + "," + b1.c + " is: " + dist2);
//        System.out.println("Stack trace : ");
//        while ( temp != null ) {
//            System.out.println(temp.r + "," + temp.c);
//            temp = temp.prev;
//        }

        return dist1 + dist2;
    }

    public void run() throws IOException {
        int minDist = Integer.MAX_VALUE;
        minDist = Math.min(minDist, bfs(A1, A2, B1, B2));
        minDist = Math.min(minDist, bfs(B1, B2, A1, A2));

        if ( minDist == Integer.MAX_VALUE ) {
            writer.write("IMPOSSIBLE\n");
        } else {
            writer.write(minDist + "\n");
        }

        writer.flush();
    }

    private static class Item {

        int r, c;

        int dist;

        Item prev;

        public Item(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }

        Item add(Item other) {
            return new Item(this.r + other.r, this.c + other.c, this.dist + other.dist);
        }

        void setPrev(Item prev) {
            this.prev = prev;
        }
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


