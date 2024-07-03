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

    BufferedReader reader;
    public Solution() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        L = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        range = Integer.parseInt(tokenizer.nextToken());
        damage = Integer.parseInt(tokenizer.nextToken());
        mines = Integer.parseInt(reader.readLine());
    }

    public void run() throws IOException {

        boolean isSurvive = true;

        int usedMineCount = 0;

        Queue<Integer> history = new LinkedList<>();

        int recentMineUse = 0;
        int accumulatedDamage = 0;
        boolean isMineNeed = false;

        for(int i = 1 ; i <= L ; i++) {
            int hp = Integer.parseInt(reader.readLine());
            accumulatedDamage = (Math.min(i, range) - recentMineUse) * damage;
            isMineNeed = accumulatedDamage < hp;

            if(isMineNeed) {
                if(mines-- == 0) {
                    isSurvive = false;
                    break;
                }
                recentMineUse++;
            }
            history.add(isMineNeed ? 1 : 0);

            if(history.size() > range) {
                recentMineUse -= history.poll();
            }
        }

        reader.close();
        System.out.println( (isSurvive) ? "YES" : "NO" );
    }
}
