import java.util.*;

class Solution {
    
    public int[] solution(int[] sequence, int k) {
        int[] answer = {99999999, 999999999};
        int limit = sequence.length;
        int left = 0;
        int right = 0;
        int sum = sequence[left];
        
        while(left <= right) {
            // System.out.printf("left : %d, right : %d, sum : %d\n", left, right, sum);
            if( (left == limit) && (right == limit)) {
                break;
            }
            
            if(sum == k) {
                int length = right - left;
                
                if(length < answer[1] - answer[0]) {
                    answer[0] = left;
                    answer[1] = right;
                    // System.out.println("updated");
                }
                if(right + 1 < limit) {
                    right++;
                    sum += sequence[right];
                } else {
                    sum -= sequence[left++];
                }
            } else if(sum < k) {
                if(right + 1 < limit) {
                    right++;
                    sum += sequence[right];
                } else {
                  sum -= sequence[left++];
                }
            } else {
                if( left + 1 < limit ) {
                    sum -= sequence[left++];
                } else {
                    left++;   
                }
            }
        }
        
        return answer;
    }
}
