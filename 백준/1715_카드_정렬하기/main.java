import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}

class Solution {
    int N = 0;

    PriorityQueue<Integer> pq = new PriorityQueue<>();

    int answer = 0;

    public Solution() {
        Scanner scan = new Scanner(System.in);
        N = Integer.parseInt(scan.next());

        for(int i = 0 ; i < N ; i++) {
            pq.add( Integer.parseInt(scan.next()) );
        }

        scan.close();
    }

    public int run() {

        if(pq.size()==1) {
            return answer;
        }

        while(pq.size() >= 2) {
            int p1 = pq.poll();
            int p2 = pq.poll();
            answer += p1+p2;

            pq.add((p1 + p2));
        }

        return answer;
    }
}
