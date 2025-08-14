import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

//    private List<Integer> gadgetArray;
    private int[] gadgets;

    private int[] lastPos;

    private int nrPlugholes;

    private int nrGadgets;


    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        this.nrPlugholes = Integer.parseInt(tokens[0]);
        this.nrGadgets = Integer.parseInt(tokens[1]);
        this.gadgets = new int[nrGadgets];
        this.lastPos = new int[101]; // 가구 번호는 1부터 100까지

        tokens = reader.readLine().split(" ");
        for ( int i = 0 ; i < nrGadgets ; i++ ) {
            gadgets[i] = Integer.parseInt(tokens[i]);
            lastPos[gadgets[i]] = i;
        }
    }

    public void run() throws IOException {
        int answer = 0;
        Set<Integer> plugged = new HashSet<>();

        for ( int i = 0 ; i < nrGadgets ; ++i ) {
            int gadgetNo = gadgets[i];
            // 4가지 케이스로 분류
            // 1. 이미 꽂혀있는 경우 continue
            // 2. 플러그가 남아있는 경우 add 후 continue
            // 3. 플러그가 꽉 찬 경우
            //    3.1 플러그에 꽂힌 가구 중 앞으로 사용되지 않는 가구가 있다면 제거
            //    3.2 플러그에 꽂힌 가구 중 가장 나중에 다시 쓰이는 가구를 제거

            // 1. 이미 꽂혀있다면 continue
            if ( plugged.contains(gadgetNo) ) continue;

            // 2. 플러그가 남아있는 경우 꽂는다.
            if ( plugged.size() < nrPlugholes ) {
                plugged.add(gadgetNo);
                continue;
            }

            // 3. 플러그가 꽉차있다.

            // 3.1 앞으로 사용하지 않는 가구가 있다면 제거한다.
            int notUsedGadget = -1;
            for ( int enqueGadget : plugged ) {
                if ( lastPos[enqueGadget] < i ) {
                    notUsedGadget = enqueGadget;
                    break;
                }
            }

            if ( notUsedGadget != -1 ) {
                plugged.remove(notUsedGadget);
                plugged.add(gadgetNo);
                answer++;
                continue;
            }

            // 3.2 앞으로 가장 나중에 사용되는 가구를 제거한다.
            int lastUsedGadget = -1;
            int lastUsedPos = -1;
            for ( int enqueGadget : plugged ) {
                for ( int j = i + 1 ; j < nrGadgets ; ++j ) {
                    if ( gadgets[j] == enqueGadget ) {
                        if ( lastUsedPos < j ) {
                            lastUsedPos = j;
                            lastUsedGadget = enqueGadget;
                        }
                        break; // 다음 가구로 넘어간다.
                    }
                }
            }

            if ( lastUsedGadget != -1 ) {
                plugged.remove(lastUsedGadget);
                plugged.add(gadgetNo);
                answer++;
            }
        }

        writer.write(String.valueOf(answer));
        writer.flush();
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        new Solution(reader, writer).run();
        reader.close();
        writer.close();
    }
}


