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

    void countChar(String s, int[] charCount) {
        for(int i = 0 ; i < s.length() ; i++) {
            charCount[s.charAt(i)-'a']++;
        }
    }

    int getType(String word, int left, int right, int removeCount) {
        if(left > right) {
            return removeCount == 1 ? 1: 0; // 이게 사실상 회문인지 유사회문인지 판별하는 척도이다.
        }

        if(word.charAt(left) == word.charAt(right)){
            return getType(word, left + 1, right - 1, removeCount);
        }

        if(removeCount > 0) {
            return 2;
        }

        int leftResult = getType(word, left+1, right, removeCount + 1);
        int rightResult = getType(word, left, right-1, removeCount + 1);
        return (leftResult != 2 || rightResult != 2) ? 1 : 2;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        N = Integer.parseInt(scan.nextLine());

        int[] charCount = new int[26];

        for(int i = 0 ; i < N ; i++) {
            String word = scan.nextLine();
            Arrays.fill(charCount, 0);
            countChar(word, charCount);
            int wordLen = word.length();
            System.out.println(getType(word, 0, wordLen-1, 0));
        }

        scan.close();
    }
}
