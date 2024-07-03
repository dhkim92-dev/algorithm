import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {

    int L; // 길이

    int range; // 유효 사거리

    int damage; // 데미지

    int mines; // 지뢰 개수

    int[] zombies;

    int cursor = 0;

    public Solution() {
        Scanner scanner = new Scanner(System.in);

        L = Integer.parseInt(scanner.nextLine());
        StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine());
        range = Integer.parseInt(tokenizer.nextToken());
        damage = Integer.parseInt(tokenizer.nextToken());
        mines = Integer.parseInt(scanner.nextLine());
        zombies = new int[L];

        for(int i = 0 ; i < L ; i++) {
            zombies[i] = Integer.parseInt(scanner.nextLine());
        }

        scanner.close();
    }

    public void run() {
        boolean isSurvive = true;
        long[] psum = new long[L+1];
        Arrays.fill(psum, 0L);

        for(int i = 1 ; i <= L ; i++) {

            int dist = i <= range  ? 0 : i-range;
            long dmg = psum[i-1] - psum[dist];

            if(zombies[i-1] <= damage + dmg) {
                psum[i] = psum[i-1] + damage;
            } else {
                if(mines > 0) {
                    mines--;
                    psum[i] = psum[i-1];
                } else {
                    isSurvive = false;
                    break;
                }
            }
        }


        System.out.println( (isSurvive) ? "YES" : "NO" );
    }
}
