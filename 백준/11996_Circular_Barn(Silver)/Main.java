import java.io.*;
import java.util.*;

class Solution {

    private final int N;

    private final int[] rooms;

    private final int[] dist;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine().trim());
        rooms = new int[N];
        dist = new int[N];

        for ( int i = 0 ; i < N ; ++i ) {
            rooms[i] = Integer.parseInt(reader.readLine().trim());
        }
    }

    private int findEmptyRoom() {
        for ( int i = 0 ; i < N ; ++i ) {
            if ( rooms[i] == 0 ) return i;
        }
        return -1; // Should not happen
    }

    private int findCandidate(int emptyRoomIdx) {
        for ( int i = 0 ; i < N ; ++i ) {
            int offset = (emptyRoomIdx - i);
            if ( offset < 0 ) offset += N; // Wrap around
            if ( rooms[offset] > 0 ) {
                return offset;
            }
        }

        return -1;
    }

    private int calcDistance(int emptyRoomIdx, int candidate) {
        int accumulatedDistance = dist[candidate];
        if ( emptyRoomIdx < candidate ) {
            int physicalDistance = N - (candidate - emptyRoomIdx);
            return accumulatedDistance + physicalDistance;
        } else {
            return accumulatedDistance + (emptyRoomIdx - candidate);
        }
    }

    private int simulate() {
        int cost = 0 ;

        while (true) {
            int emptyRoomIdx = findEmptyRoom();
            if( emptyRoomIdx == -1 ) break;
            int candidate = findCandidate(emptyRoomIdx);
            int d = calcDistance(emptyRoomIdx, candidate);

            rooms[candidate]--;
            rooms[emptyRoomIdx]++;
//            for ( int i = 0 ; i < N ; ++i ) {
//                System.out.print(rooms[i] + " ");
//            }
//            System.out.println();
            dist[emptyRoomIdx] += d;
            dist[candidate]=0;
        }

        for ( int i = 0 ; i < dist.length ; ++i ) {
            if ( dist[i] == 0 ) continue;
            cost += dist[i]*dist[i];
        }

        return cost;
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
