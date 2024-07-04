import java.util.*;
import java.io.*;

public class Main {

    static void run() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(reader.readLine());
        int[][] plans = new int[N][2];

        for(int i = 0 ; i < N ; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            plans[i][0] = Integer.parseInt(tokenizer.nextToken());
            plans[i][1] = Integer.parseInt(tokenizer.nextToken());
        }

        new Solution(N, plans).run();

        reader.close();
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}

class Solution {

    int n;

    List<Plan> plans;

    public Solution(int n, int[][] plans) {
        this.n = n;
        this.plans = new ArrayList<>();

        for(int i = 0 ; i < n ; i++) {
            Plan p = new Plan(plans[i][1], plans[i][0]);
            this.plans.add(p);
        }
    }

    public void run() {
        int answer = 0;
        Collections.sort(plans);

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(Plan plan : plans) {
            pq.add(plan.getPrice());
            if(pq.size() > plan.getDayLimit()) {
                pq.poll();
            }
        }

//        System.out.println("start");
        while(!pq.isEmpty()) {
            answer += pq.poll();
        }

        System.out.println(answer);
    }
}



class Plan implements Comparable<Plan> {

    private int dayLimit, price;

    public Plan(int day, int price) {
        this.dayLimit = day;
        this.price = price;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int compareTo(Plan o) {
        // 마감일을 기준으로 오름차순 정렬한다.
        return this.dayLimit == o.dayLimit ? this.price - o.price : this.dayLimit - o.dayLimit;
    }

    @Override
    public String toString() {
        return "price : " + price +
                " day limit : " + dayLimit;
    }
}
