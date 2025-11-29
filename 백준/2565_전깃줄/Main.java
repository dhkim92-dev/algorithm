import java.io.*;
import java.util.*;

/**
 * 2565 전깃줄
 * https://www.acmicpc.net/problem/2565
 * 기본적으로 LIS 문제이다. 가장 긴 증가하는 부분 수열을 구하고 전체 전깃줄에서 개수룰 뺀다.
 */
class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int N;

    private int[] conn;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        N = Integer.parseInt(reader.readLine());
        conn = new int[501];

        for ( int i = 0 ; i < N ; ++i ) {
            String[] tokens = reader.readLine().split(" ");
            int a = Integer.parseInt(tokens[0]);
            int b = Integer.parseInt(tokens[1]);
            conn[a] = b;
        }
    }

    private int solutionDp() {
        int[] length = new int[501];

        for ( int i = 0 ; i <= 500 ; ++i ) {
            if ( conn[i] != 0 ) {
                length[i] = 1;
            }
        }

        int mx = -1;

        for ( int i = 1 ; i <= 500 ; ++i ) {
            for ( int j = 1 ; j < i ; ++j ) {
                if ( conn[i] == -1 || conn[j] == -1 ) {
                    continue;
                }

                if ( conn[i] > conn[j] && length[i] < length[j] + 1 ) {
                    length[i] = length[j] + 1;
                }
            }
        }

        for ( int i = 0 ; i <= 500 ; ++i ) {
            if ( length[i] > mx ) {
                mx = length[i];
            }
        }

        return N - mx;
    }

    private int solutionBinarySearch() {
        List<Integer> lis = new ArrayList<>();

        for ( int i = 1 ; i <= 500 ; ++i ) {
            if ( conn[i] == 0 ) continue;
            if( lis.isEmpty() ) {
                lis.add(conn[i]);
                continue;
            }

            int seq = conn[i];
            int lo = -1, hi = lis.size();

            while ( lo + 1 < hi ) {
                int mid = ( lo + hi ) / 2;

                if ( lis.get(mid) < seq ) {
                    lo = mid;
                } else {
                    hi = mid;
                }
            }

            if ( hi == lis.size() ) {
                lis.add(seq);
            } else {
                lis.set(hi, seq);
            }
        }

        return N - lis.size();
    }

    public void run() throws IOException {
        int answer = 0;
        // answer = solutionDp();
        answer = solutionBinarySearch();
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

