import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {
    int N;

    public Solution() {

    }

    int isPossible(String S, String N) {
        int len = N.length();
        int sLen = S.length();

        while(sLen < len) {
            char ch = N.charAt(len-1);

            StringBuffer sb = new StringBuffer(N.substring(0, len-1));
            if(ch == 'B') {
                N = sb.reverse().toString();
            } else {
                N = sb.toString();
            }
            len--;
        }

        return S.equals(N) ? 1 : 0;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        String S = scan.nextLine();
        String N = scan.nextLine();
        System.out.println(isPossible(S, N));

        scan.close();
    }
}
