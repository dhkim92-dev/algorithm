
class Solution {

    private String text;

    public Solution(BufferedReader reader) throws IOException {
        text = reader.readLine();
    }

    private int getLength(String input) {
        int n = input.length();
        int answer = Integer.MIN_VALUE;
        int idx = 0;
        int[] pi = new int[n];

        for(int i = 1 ; i < n ; i++) {
            while(idx > 0 && input.charAt(i) != input.charAt(idx)) {
                idx = pi[idx - 1];
            }

            if( input.charAt(i) == input.charAt(idx) ) {
                pi[i] = ++idx;

                if(answer < pi[i]) {
                    answer = pi[i];
                }
            }
        }

        return answer;
    }

    public void run() {
        int n = text.length();
        int answer = 0;
        for(int i = 0 ; i < n ; i++) {
            String pattern = text.substring(i);
            int length = getLength(pattern);
            answer = Math.max(answer, length);
        }

        System.out.println(answer);
    }
}
