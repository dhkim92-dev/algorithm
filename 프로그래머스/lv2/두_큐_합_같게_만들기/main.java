import java.util.*;
import java.util.stream.Collectors;

class Solution {
    
    public long getSum(int[] q1) {
        long sum = 0;
        
        for(int i = 0 ; i < q1.length ; i++) {
            sum += (long)q1[i];
        }
        return sum;
    }
    
    
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        long total = 0;
        long queue1Sum = 0L;
        long target = 0L;
        
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        
        for(int i = 0 ; i < queue1.length ; i++) {
            queue1Sum += queue1[i];
            total += queue1[i] + queue2[i];
            q1.add(queue1[i]);
            q2.add(queue2[i]);
        }
        target = total / 2;
        
        if(total % 2 == 1) return -1;
        
        while(answer < queue1.length * 4) {
            
            //System.out.println("q1 sum : " + queue1Sum  + " target : " + target);
            if(queue1Sum==target) {
                break;
            } else if(queue1Sum > target) {
                
                int q1Front = q1.poll();
                queue1Sum -= q1Front;
                q2.add(q1Front);
            } else if(queue1Sum < target){
                int q2Front = q2.poll();
                queue1Sum += q2Front;
                q1.add(q2Front);
            }
            
            answer++;
        }
        
        if(queue1Sum != target) {
            return -1;
        }
        
        return answer;
    }
}
