package algorithm;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private String input;

    public Solution(BufferedReader reader) throws IOException {
        input = reader.readLine();
    }

    private int findPattern(List<char[]> data) {
        for(int i = 0 ; i <= data.size() - 3 ; i++) {
            if(data.get(i)[0] == '1' && data.get(i + 1)[0] == '1' && data.get(i + 2)[0] == '0') {
                return i;
            }

            if(data.get(i)[0] == '0' && data.get(i + 1)[0] == '0' && data.get(i + 2)[0] == '1') {
                return i;
            }
        }
        return -1;
    }

    private boolean isReducable(List<char[]> data) {
        for(int i = 0 ; i < data.size() - 1 ; i++) {
            if(data.get(i)[0] == data.get(i + 1)[0]) {
                return true;
            }
        }
        return false;
    }

    private int findLongestLength(String s) {
        List<char[]> listString = new LinkedList<>();

        for(char c : s.toCharArray()) {
            listString.add(new char[]{c});
        }

        int index = 0;
        while((index = findPattern(listString)) != -1) {
//            System.out.print("원본 문자열 : ");
//            listString.forEach(c -> System.out.print(c[0]));
//            System.out.println();
            // index가 2 이상이면 index + 1 만큼 리스트의 앞에서 제거한다.
            // index가 1 이면 2번만 제거한다
            // index가 0 이면 1번만 제거한다.
//            System.out.println("index : " + index);
            if(index >= 2) {
                for(int i = 0 ; i <= index ; i++) {
                    listString.remove(0);
                }
            } else if(index == 1) {
                listString.remove(0);
                listString.remove(0);
            } else {
                listString.remove(0);
            }

//            System.out.print("제거 문자열 : ");
//            listString.forEach(c -> System.out.print(c[0]));
//            System.out.println();
        }

        // 이 과정을 끝내고 나온 문자열 리스트는 모두 같은 수로 이루어져 있거나
        // 110 또는 001의 패턴이 존재하지 않는 문자열이다.
        while(isReducable(listString)) {
            for(int i = 0 ; i < listString.size() - 1 ; i++) {
                if(listString.get(i)[0] == listString.get(i + 1)[0]) {
                    listString.remove(i);
                    break;
                }
            }
        }

        return listString.size();
    }

    public void run() throws IOException {
        // 011 또는 100 뒤에 오는 수는 무조건 제거가 가능하다.
        // 예)
        // 011 0 이면 팰린드롬이므로 절반 제거 가능  01
        // 011 1 이면 1이 중복되기 때문에 1을 두번 제거 가능하다. 01
        // 100 0 이면 0이 중복되기 때문에 0을 두번 제거 가능하다. 10
        // 100 1 이면 팰린드롬이므로 10 으로 제거 가능
        //
        StringBuilder sb = new StringBuilder();
        // 계산 편의를 위해 문자열을 뒤집고 시작
        String reverse = new StringBuilder(input)
                .reverse()
                .toString();
        System.out.println(findLongestLength(reverse));
    }
}


