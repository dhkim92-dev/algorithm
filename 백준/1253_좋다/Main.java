class Solution {

    private int N;

    private long[] arr;

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        arr = new long[N];
        String[] tokens = reader.readLine().split(" ");
        for(int i = 0 ; i < N ; i++) {
            arr[i] = Long.parseLong(tokens[i]);
        }
    }

    private int isGood(int idx, long target) {

        int left = 0;
        int right = N-1;

        while(left < right) {
            if(left == idx) {
                left++;
                continue;
            }

            if(right == idx) {
                right--;
                continue;
            }

            long sum = arr[left] + arr[right];
            if(sum == target) {
                return 1;
            } else if(sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return 0;
    }

    public void run() {
        // arr[i] 를 arr[j] + arr[k] 로 나타낼 수 있는 경우 GOOD이다
        int answer = 0;
        Arrays.sort(arr);

        for(int i = 0 ; i < N ; i++) {
            answer += isGood(i, arr[i]);
        }

        System.out.println(answer);
    }
}
