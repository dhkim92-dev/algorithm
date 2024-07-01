import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {

    int[] paperCount = new int[7];

    int answer = 0;

    public Solution() {
        Scanner scan = new Scanner(System.in);
        for (int i = 1; i <= 6; i++) {
            paperCount[i] = Integer.parseInt(scan.nextLine());
        }
    }

    public void run() {

        // 4 x 4, 5 x 5, 6 x 6 은 무조건 하나의 판을 소모한다.
        // 6 x 6 종이를 사용한 경우 해당 판은 아무것도 더 붙일 수 없다.
        // 5 x 5 종이를 사용한 경우 해당 판에는 1x1 종이 11개를 사용할 수 있다.
        // 4 x 4 종이를 사용한 경우 해당 판에는 2x2 종이 5개를 1x1 종이 20개를 사용할 수 있다.

        int empty1by1 = 0;
        int empty2by2 = 0;
        int empty3by3 = 0;

        answer += paperCount[6] + paperCount[5] + paperCount[4]; // 어차피 6, 5, 4 는 무조건 종이 하나를 소비한다.

        empty1by1 = paperCount[5] * 11 + paperCount[4] * 20; // 5를 사용하고나면 1by1이 11개 남음 4를 사용하고나면 16칸의 1by1 이 가능
        empty2by2 = paperCount[4] * 5; // 4를 사용하고나면 2by2가 5개 가능하고,

        int mod = paperCount[3] % 4;
        answer += (paperCount[3] / 4) + (mod == 0 ? 0 : 1);
        int[] possible2by2 = {0, 5, 3, 1};
        int[] possible1by1 = {0, 27, 18, 9};
        empty1by1 += possible1by1[mod];
        empty2by2 += possible2by2[mod];

        int used2by2 = Math.min(empty2by2, paperCount[2]);
        empty1by1 -= 4*used2by2;

        paperCount[2] -= used2by2;

        mod = paperCount[2] % 9;
        answer += (paperCount[2] / 9) + (mod == 0 ? 0 : 1);

        if(mod > 0) {
            empty1by1 += (9 - mod) * 4;
        }

        paperCount[1] -= empty1by1;
        paperCount[1] = Math.max(paperCount[1], 0);
        answer += (paperCount[1] / 36) + (paperCount[1] % 36 == 0 ? 0 : 1);


        System.out.println(answer);
    }
}

