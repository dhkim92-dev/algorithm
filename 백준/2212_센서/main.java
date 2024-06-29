import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static class Solution {

        int N;

        int K;

        Integer[] sensors;

        int answer = 0;

        void init() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(reader.readLine());
            K = Integer.parseInt(reader.readLine());
            Set<Integer> used = new HashSet<>();

            StringTokenizer st = new StringTokenizer(reader.readLine());

            for(int i = 0 ; i < N ; i++) {
                used.add(Integer.parseInt(st.nextToken()));
            }
            N = used.size();
            sensors = new Integer[N];
            used.stream()
                    .collect(Collectors.toList())
                    .toArray(sensors);
        }

        public Solution() throws IOException {
            init();
        }


        public int run() {
            Arrays.sort(sensors);
            int[] diff = new int[sensors.length - 1];

            for(int i = 0 ; i < N - 1 ; i++) {
                diff[i] = sensors[i+1].intValue() - sensors[i].intValue();
            }

            Arrays.sort(diff);

            for(int i = 0 ; i < diff.length - (K-1) ; i++) {
                answer+=diff[i];
            }

            return answer;
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}
