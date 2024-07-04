import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");
        System.out.println("base path : " + base.toAbsolutePath());
        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);
        File dir = new File(testFileDirName);
        File[] files = dir.listFiles();

        if(files == null) {
            return;
        }

        for(int i = 0 ; i<files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            reader.lines()
                    .forEach(System.out::println);
            reader.reset();

            System.out.println("answer ");
            new Solution(reader).run();
            reader.close();
        }
    }

    static void run() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        //testRun(2812);
        run();
    }
}

class Solution {

    int n;

    int k;

    String input;

    public Solution(BufferedReader reader) throws IOException {;
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());
        input = reader.readLine();
    }

    public void run() {
        StringBuilder sb = new StringBuilder();

        Stack<Integer> s = new Stack<>();

        int removeCount = 0;

        //System.out.println("n : "+ n+ " k : " + k);
        for(int i = 0 ; i < input.length() ; i++) {
            int ch = input.charAt(i) - '0';

            while(!s.isEmpty() && (removeCount < k)) {
                if(ch > s.peek()) {
                    s.pop();
                    removeCount++;
                    continue;
                }
                break;
            }

            s.add(ch);
        }

        while(removeCount < k) {
            removeCount++;
            s.pop();
        }

        Stack<Integer> reversed = new Stack<>();

        while(!s.isEmpty()) {
            reversed.add(s.pop());
        }

        while(!reversed.isEmpty()) {
            sb.append(reversed.pop());
        }

        String answer = sb.toString();
        System.out.println(answer);
    }
}

