import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {
    int N, M;
    List<Integer> positive = new ArrayList<>();
    List<Integer> negative = new ArrayList<>();

    public Solution() {

    }

    int getDist(List<Integer> arr) {
        int cursor = 0;
        int limit = arr.size();
        int dist = 0;

        if(arr.isEmpty()) return 0;

        while(true) {
            int localSum = 0;

            localSum += Math.abs(arr.get(cursor));
            dist += localSum*2;

            if(cursor + M >= limit) {
                break;
            }

            cursor+=M;
        }

        return dist;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        String[] line = scan.nextLine().split(" ");

        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);

        int positivePos = 0;
        int negativePos = 0;

        String[] nums = scan.nextLine().split(" ");

        for(String n : nums) {
            int num = Integer.parseInt(n);
            if(num >= 0) {
                positive.add(num);
            }else{
                negative.add(num);
            }
        }

        Collections.sort(positive, Collections.reverseOrder());
        Collections.sort(negative);

        int dist = getDist(positive) + getDist(negative);

        int maxVal = 0;
        if(!positive.isEmpty()) {
            maxVal = Math.max(Math.abs(positive.get(0)), maxVal);
        }

        if(!negative.isEmpty()) {
            maxVal = Math.max(Math.abs(negative.get(0)), maxVal);
        }
        dist -= maxVal;
        System.out.println(dist);

        scan.close();
    }
}
