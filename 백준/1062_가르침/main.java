import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}

class Solution {

    int N, K;

    List<String> words = new ArrayList<>();

    Map<Integer, Integer> chCount = new HashMap<>();

    int answer = 0;

    public Solution() throws IOException {
        Scanner scanner = new Scanner(System.in);
        N = Integer.parseInt(scanner.next());
        K = Integer.parseInt(scanner.next()) - 5;

        for(int i = 0 ; i < N ; i++) {
            String word = scanner.next();
            words.add(word);
        }
        scanner.close();
    }

    private void count(boolean[] selected, int idx, int cnt) {
        if(cnt == K) {
            int possible = 0;

            for(String word : words) {
                int found = 1;
                for(int i = 0 ; i < word.length() ; i++) {
                    int index = (int)(word.charAt(i) - 'a');
                    if(!selected[index]) {
                        found = 0;
                        break;
                    }
                }
                possible+=found;
            }

            answer = Math.max(possible, answer);
            return;
        }

        for(int i = idx ; i < 26 ; i++) {
            if(selected[i] == true) {
                continue;
            }
            selected[i] = true;
            count(selected, i + 1, cnt+1);
            selected[i] = false;
        }
    }

    public int run() {
        if(K < 0) return 0; // anta tica => a,c,i,t,n 5개 문자 밑이면 읽을 수 없다.
        boolean[] selected = new boolean[26];
        selected[(int)('a'-'a')] = true;
        selected[(int)('n'-'a')] = true;
        selected[(int)('t'-'a')] = true;
        selected[(int)('i'-'a')] = true;
        selected[(int)('c'-'a')] = true;
        count(selected, 0, 0);

        return answer;
    }
}
