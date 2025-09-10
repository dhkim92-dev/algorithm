import java.io.*;
import java.util.*;

class Solution {

    private final BufferedReader reader;

    private final BufferedWriter writer;

    private int T;

    public Solution(BufferedReader reader, BufferedWriter writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
        T = Integer.parseInt(reader.readLine());
    }

    private void runEachTestCase() throws IOException {
        int k = Integer.parseInt(reader.readLine());
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        Map<Integer, Integer> countMap = new HashMap<>();

        for ( int i = 0 ; i < k ; ++i ) {
            char command;
            int value;
            String[] line = reader.readLine().split(" ");
            command = line[0].charAt(0);
            value = Integer.parseInt(line[1]);
            // System.out.println("command : " + command + " value : " + value);

            switch(command) {
                case 'I' :
                    minHeap.offer(value);
                    maxHeap.offer(value);
                    countMap.put(value, countMap.getOrDefault(value, 0) + 1);
                    // System.out.println("Insert " + value + " cur count : " + countMap.get(value));
                    break;
                case 'D' :
                    if ( value == 1 ) {
                        // countMap에 존재하는 최대값을 찾아서 제거
                        while ( !maxHeap.isEmpty() ) {
                            int max = maxHeap.poll();
                            if ( countMap.containsKey(max) ) {
                                int count = countMap.get(max);
                                if ( count == 1 ) {
                                    countMap.remove(max);
                                } else {
                                    countMap.put(max, count - 1);
                                }
                                break;
                            }
                        }
                    } else if ( value == -1 ) {
                        // countMap에 존재하는 최소값을 찾아서 제거
                        while ( !minHeap.isEmpty() ) {
                            int min = minHeap.poll();
                            if ( countMap.containsKey(min) ) {
                                int count = countMap.get(min);
                                if ( count == 1 ) {
                                    countMap.remove(min);
                                } else {
                                    countMap.put(min, count - 1);
                                }
                                break;
                            }
                        }
                    }
                    break;
                default: 
                    break;
            }
        }

        Integer maxValue = null;
        Integer minValue = null;


        for( Integer key : countMap.keySet() ) {
            if ( maxValue == null || key > maxValue ) {
                maxValue = key;
            }
            if ( minValue == null || key < minValue ) {
                minValue = key;
            }
        }

        if ( maxValue == null || minValue == null ) {
            writer.write("EMPTY\n");
        } else {
            writer.write(maxValue + " " + minValue + "\n");
        }
    }

    public void run() throws IOException {

        for ( int i = 0 ; i < T ; ++i ) {
            runEachTestCase();
        }

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

