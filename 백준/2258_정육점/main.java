import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

    public static class Meat {

        int amount;

        int price;

        public Meat(int amount, int price) {
            this.amount = amount;
            this.price = price;
        }
    }

    public static class Solution {

        int N, M;

        List<Meat> meats = new ArrayList<Meat>();

        public Solution() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);

            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            for(int i = 0 ; i < N ; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                int amount = Integer.parseInt(tokenizer.nextToken());
                int price = Integer.parseInt(tokenizer.nextToken());
                meats.add(new Meat(amount, price));
            }
        }

        public int run() {
            // 1. 고기를 가격순 오름차순 정렬한다.
            // 2. 고기 가격이 동일하다면 무게 순으로 내림차순 정렬한다.
            // 3. 가장 싸고 양 많은 고기를 고르고, 하나씩 선택해본다.
            // 4. 목표치이상이면 현재 가격을 업데이트하고 다음으로 비싼 고기에 대해 동일 연산을 진행한다.

            int minPrice = Integer.MAX_VALUE;
            meats.sort((a, b) -> {
                if(a.price == b.price) {
                    return b.amount - a.amount;
                }
                return a.price - b.price;
            });

            int totalAmount = 0;

            for(Meat meat : meats) {
                totalAmount += meat.amount;
            };

            if(totalAmount < M) return -1;

            totalAmount=0;
            int totalPrice = 0;
            int lastPrice = 0;

            for(int i = 0 ; i < meats.size() ; i++) {
                Meat curMeat = meats.get(i);
                totalAmount += curMeat.amount;

                if(lastPrice != curMeat.price) {
                    totalPrice = lastPrice = curMeat.price;
                } else {
                    totalPrice += curMeat.price;
                }

                if(totalAmount >= M) {
                    minPrice = Math.min(minPrice, totalPrice);
                }
            }


            return minPrice;
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println(new Solution().run());
    }
}
