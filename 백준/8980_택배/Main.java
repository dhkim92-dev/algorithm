
import java.io.*;
import java.util.*;

class Solution {

    static class Delivery implements Comparable<Delivery> {

        int from;

        int sendTo;

        int amount;

        Delivery(int from, int sendTo, int amount) {
            this.from = from;
            this.sendTo = sendTo;
            this.amount = amount;
        }

        @Override
        public int compareTo(Delivery o) {
            if ( this.sendTo == o.sendTo ) {
                return Integer.compare(this.from, o.from);
            }
            return Integer.compare(this.sendTo, o.sendTo);
        }

        @Override
        public String toString() {
            return "Delivery{sendTo=" + sendTo + ", amount=" + amount + "}";
        }
    }

    static class Truck {

        final int maxCapacity;

        int[] capacity;

        Truck(int capacity, int n) {
            maxCapacity = capacity;
            this.capacity = new int[n-1];
            Arrays.fill(this.capacity, maxCapacity);
        }

        public int pickup(Delivery delivery) {
            int from = delivery.from;
            int sendTo = delivery.sendTo;
            int possible = Integer.MAX_VALUE;
            for ( int i = from ; i < sendTo ; ++i ) {
                possible = Math.min(possible, capacity[i]);
            }

            int diff = delivery.amount;

            if ( possible - delivery.amount < 0 ) {
                diff = possible;
            }

            for ( int i = from ; i < sendTo ; ++i ) {
                capacity[i] -= diff;
            }

//            System.out.println("Pickup from " + (from + 1) + " to " + (sendTo + 1) + " amount: " + delivery.amount);
//            for(int i = 0 ; i < capacity.length; i++) {
//                System.out.print(capacity[i] + " ");
//            }
//            System.out.println();

            return diff;
        }
    }

    private int N, M, Q;

    private List<Delivery> deliveries;

    private Truck truck;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        truck = new Truck(M, N);

        Q = Integer.parseInt(reader.readLine());
        deliveries = new ArrayList<>();

        for (int i = 0; i < Q; i++) {
            tokens = reader.readLine().split(" ");
            int from = Integer.parseInt(tokens[0]) - 1;
            int sendTo = Integer.parseInt(tokens[1] ) - 1;
            int amount = Integer.parseInt(tokens[2]);
            deliveries.add(new Delivery(from, sendTo, amount));
        }

        Collections.sort(deliveries);
    }

    private String simulate() throws IOException {
        StringBuilder sb = new StringBuilder();
        int answer = 0;
        for ( int i = 0 ; i < deliveries.size(); i++ ) {
            Delivery delivery = deliveries.get(i);
            int amount = truck.pickup(delivery);
            answer += amount;
        }
        sb.append(answer)
          .append('\n');
        return sb.toString();
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}


