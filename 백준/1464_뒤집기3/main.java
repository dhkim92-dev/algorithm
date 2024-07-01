package org.example;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}

class Solution {

    String S;

    public Solution() {
        Scanner scan = new Scanner(System.in);
        S = scan.nextLine();
        scan.close();
    }

    public void run() {
        Deque<String> dq = new LinkedList<>();

        dq.addFirst(String.valueOf(S.charAt(0)));

        for(int i = 1 ; i < S.length() ; i++) {
            char ch = dq.peekFirst().charAt(0);

            if(ch < S.charAt(i)) {
                dq.addLast(S.substring(i, i+1));
            }else{
                dq.addFirst(S.substring(i, i+1));
            }
        }

        while(!dq.isEmpty()) {
            System.out.print(dq.pollFirst());
        }
        System.out.println();
    }
}

