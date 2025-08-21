import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    private BufferedWriter writer;

    private boolean[] arr;

    private int N, S;
//`
    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        String[] tokens = reader.readLine().split(" ");
        this.N = Integer.parseInt(tokens[0]);
        this.S = Integer.parseInt(tokens[1]);
        this.arr = new boolean[N];

        tokens = reader.readLine().split(" ");

        for ( int i = 0 ; i < N ; ++i ) {
            this.arr[i] = Integer.parseInt(tokens[i]) % 2 == 0;
        }
    }

    public void run() throws IOException {
        int l = 0, r = 0;
        int rmCnt = 0;
        int maxLength = 0;
        int L = arr.length;

        while ( r < N ) {
            if ( arr[r] ) {
                r++;
                maxLength = Math.max(maxLength, r - l - rmCnt);
            } else {
                // 아직 지울 수 있는 경우
                if ( rmCnt < S ) {
                    r++;
                    rmCnt++;
                    maxLength = Math.max(maxLength, r - l - rmCnt);
                } else {
                    // 지울 수 없는 경우
                    if ( arr[l] ) {
                        // l이 짝수인 경우
                        l++;
                    } else {
                        l++;
                        rmCnt--;
                    }
                }
            }
        }
        writer.write(String.valueOf(maxLength));
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
