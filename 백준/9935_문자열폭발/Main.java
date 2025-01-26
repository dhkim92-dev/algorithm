import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private String a, b;

    static class Range {
        int start, end;

        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        a = reader.readLine();
        b = reader.readLine();
    }

    private String bomb(String input) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < input.length() ; i++) {
            char c = input.charAt(i);
            stack.push(c);

            if(stack.size() >= b.length()) {
                boolean isBomb = true;
                int size = stack.size();
                for(int j = 0 ; j < b.length() ; j++) {
                    if(stack.get(size - b.length() + j) != b.charAt(j)) {
                        isBomb = false;
                        break;
                    }
                }
                if(isBomb) {
                    for(int j = 0 ; j < b.length() ; j++) {
                        stack.pop();
                    }
                }
            }
        }

        for(char c : stack) {
            sb.append(c);
        }

        return sb.toString();
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        String after = bomb(a);
        if(after.isEmpty()) {
            sb.append("FRULA");
        } else {
            sb.append(after);
        }
        System.out.println(sb);
    }
}
