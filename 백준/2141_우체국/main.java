import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static class Village {

        int index;

        int population;

        long dist = 0L;

        public Village(int id, int pop) {
            this.index = id;
            this.population = pop;
        }
    }

    public static class Solution {

        int N;

        List<Village> villages = new ArrayList();

        public Solution()  throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            this.N = Integer.parseInt(reader.readLine());
            for(int i = 0 ; i < N ; i++) {
                String[] s = reader.readLine().split(" ");
                villages.add(new Village( Integer.parseInt(s[0]), Integer.parseInt(s[1]) ));
            }

            villages.sort((a, b)->a.index - b.index);
        }

        public int run() {
            int answer = -1;
            long totalPopulation = 1;
            for(Village v : villages) {
                totalPopulation += (long)v.population;
            }
            long median = totalPopulation/2;

            totalPopulation=0L;
            for(Village v : villages) {
                totalPopulation+=(long)v.population;
                if(totalPopulation >= median) {
                    answer = v.index;
                    break;
                }
            }

            return answer;
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}
