import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;

        Arrays.sort(targets,(a,b)-> a[1]-b[1] );
        
        int lastLaunched = 0;
        
        for(int[] target : targets) {
            int start = target[0];
            
            if(lastLaunched <= start) {
                answer++;
                lastLaunched = target[1];
            }
        }
        
        return answer;
    }
}
