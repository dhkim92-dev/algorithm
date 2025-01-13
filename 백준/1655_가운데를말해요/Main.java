
class Solution {

    private int N;
    private int[] inputs;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        inputs = new int[N];

        for(int i = 0 ; i < N ; i++) {
            inputs[i] = Integer.parseInt(reader.readLine());
        }
    }

    public void run() {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        StringBuilder sb = new StringBuilder();


        for(int i = 0 ; i < N ; i++) {
            int val = inputs[i];
            // minHeap, maxHeap 이 같은 사이즈라면 maxHeap에 추가
            // 다르다면 minHeap에 추가
            // 입력 값이 maxHeap의 Top보다 크다면 maxHeap의 Top과 교체

            if(minHeap.size() == maxHeap.size()) {
                maxHeap.add(val);
            } else {
                minHeap.add(val);
            }

            if(!minHeap.isEmpty() && !maxHeap.isEmpty() && minHeap.peek() < maxHeap.peek()) {
                int minTop = minHeap.poll();
                int maxTop = maxHeap.poll();

                minHeap.add(maxTop);
                maxHeap.add(minTop);
            }

            sb.append(maxHeap.peek())
                    .append("\n");
        }

        System.out.println(sb);
    }
}
