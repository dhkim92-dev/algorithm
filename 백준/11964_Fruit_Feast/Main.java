
import java.io.*;
import java.util.*;

class Solution {

    private final int T, A, B;

    static class Status {
        int fullness;
        int orange;
        int lemon;
        int water;

        Status(int fullness, int orange, int lemon, int water) {
            this.fullness = fullness;
            this.orange = orange;
            this.lemon = lemon;
            this.water = water;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        T = Integer.parseInt(tokens[0]);
        A = Integer.parseInt(tokens[1]);
        B = Integer.parseInt(tokens[2]);
    }

    private int simulate() {
        Queue<Status> q = new LinkedList<>();
        q.add(new Status(A, 1, 0, 0));
        q.add(new Status(B, 0, 1, 0));
        boolean[] visited = new boolean[T+1];
        visited[A] = true;
        visited[B] = true;

        int maxFullness = -1;
        while ( !q.isEmpty() ) {
            Status current = q.poll();
//            System.out.println("Current Status: " + current.fullness + ", Orange: " + current.orange + ", Lemon: " + current.lemon + ", Water: " + current.water);
            int currentFullness = current.fullness;
            maxFullness = Math.max(maxFullness, currentFullness);

            // 오렌지를 선택 가능한지 확인
            if ( (current.fullness + A <= T) && !visited[current.fullness+A] ) {
//                System.out.println("    Current Status " + current.fullness + ", " + current.orange + ", " + current.lemon + ", " + current.water + " | " + "Adding Orange: " + (current.fullness + A));
                visited[current.fullness + A] = true;
                q.add(new Status(currentFullness + A, current.orange + 1, current.lemon, current.water));
            }

            if ( (current.fullness + B <= T) && !visited[current.fullness+B] ) {
//                System.out.println("    Current Status " + current.fullness + ", " + current.orange + ", " + current.lemon + ", " + current.water + " | " + "Adding Lemon: " + (current.fullness + B));
                visited[current.fullness + B] = true;
                q.add(new Status(currentFullness + B, current.orange, current.lemon + 1, current.water));
            }

            // 물을 마시는 경우
            if ( current.water == 0 && !visited[current.fullness/2] ) {
//                System.out.println("    Current Status " + current.fullness + ", " + current.orange + ", " + current.lemon + ", " + current.water + " | " + "Adding Water: " + (current.fullness/2));
                visited[current.fullness / 2] = true;
                q.add(new Status(currentFullness / 2, current.orange, current.lemon, 1));
            }
        }

        return maxFullness;
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
