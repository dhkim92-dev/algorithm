import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    static class Pos {
        int r, c;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos add(Pos other) {
            return new Pos(r + other.r, c + other.c);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pos) {
                Pos other = (Pos) obj;
                return r == other.r && c == other.c;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private int N, M;
    List<Pos> chickens;
    List<Pos> houses;
    private int[][] dists;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        chickens = new ArrayList<>();
        houses = new ArrayList<>();


        for (int i = 0; i < N; i++) {
            String[] line = reader.readLine().split(" ");

            for (int j = 0; j < N ; j++) {
                if (line[j].equals("2")) {
                    chickens.add(new Pos(i, j));
                } else if(line[j].equals("1")) {
                    houses.add(new Pos(i, j));
                }
            }
        }

        dists = new int[houses.size() + 1][chickens.size() + 1];
    }

    private int calc(int selected) {
        int sum = 0;

        for(int i = 0 ; i < houses.size() ; i++) {
            int minValue = Integer.MAX_VALUE;
            for(int j = 0 ; j < chickens.size() ; j++) {
                if((selected & (0x01 << j)) == 0) {
                    continue;
                }
                minValue = Math.min(minValue, dists[i][j]);
            }
            if(minValue != Integer.MAX_VALUE) {
                sum += minValue;
            }
        }
        return sum;
    }

    private int combination(int selected, int idx, int count) {
        if(count == M) {
            return calc(selected);
        }

        int minValue = Integer.MAX_VALUE;

        for(int i = idx ; i < chickens.size() ; i++) {
            if((selected & (0x01 << i)) != 0) {
                continue;
            }

            int value = combination(selected | (0x01 << i), i, count + 1);
            minValue = Math.min(minValue, value);
        }

        return minValue;
    }

    public void run () {
        int answer = Integer.MAX_VALUE;

        for(int i = 0 ; i < chickens.size() ; i++) {
            Pos chicken = chickens.get(i);
            for(int j = 0 ; j < houses.size() ; j++) {
                Pos house = houses.get(j);
                dists[j][i] = Math.abs(chicken.r - house.r) + Math.abs(chicken.c - house.c);
            }
        }

        answer = combination(0, 0, 0);

        StringBuilder sb = new StringBuilder();
        sb.append(answer).append("\n");
        System.out.println(sb.toString());
    }
}

