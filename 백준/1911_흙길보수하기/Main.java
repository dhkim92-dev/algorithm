
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {

    static class WaterPit {
        int from;
        int to;

        public WaterPit(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    private int N, L;
    private WaterPit[] pits;
    private boolean[] watered;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        L = Integer.parseInt(tokens[1]);

        pits = new WaterPit[N];

        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            pits[i] = new WaterPit(from, to);
        }
    }

    private int solve() {
        Arrays.sort(pits, (x, y) -> {
            return x.from - y.from;
        });

        int cursor = 0;
        int usedCnt = 0;

        for(WaterPit pit : pits) {
            // 현재 커서가 물구덩이 영역의 좌측에 있는 경우,
            // 커서를 현재 물구덩이의 시작점으로 옮긴다.
            //System.out.printf("Pit info : %d %d\n", pit.from, pit.to);
            if (cursor < pit.from) {
                //System.out.println("Cursor moved to " + pit.from);
                cursor = pit.from;
            } else if (cursor >= pit.from && cursor <= pit.to) {
            } else {
                continue;
            }

            // 널빤지를 계속 사용해본다.
            while(cursor < pit.to) {
                cursor += L;
                usedCnt++;
                //System.out.println("Cursor moved to " + cursor + " usedCnt " + usedCnt);
            }
        }

        /* 
        111222..333444555.... // 길이 3인 널빤지
        .MMMMM..MMMM.MMMM.... // 웅덩이
        012345678901234567890 // 좌표
        */


        return usedCnt;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(solve());
        System.out.println(sb.toString());
    }
}
