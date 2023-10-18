import java.util.*;

class Solution {
    
    public int calculateTask(int time, int[] cores) {
        int count = cores.length;
        
        if(time == 0) {
            return count;
        }
        
        for(int core : cores) {
            count += (time / core);
        }
        
        return count;
    }
    
    public int solution(int n, int[] cores) {
        int answer = 0;
        int left = 0;
        int right = 0;
        int completedTasks = 0;
        int time = 0;
        
        if(n <= cores.length) {
            return n;
        }
        
        right = 10000 * n;
        
        while(left <= right) {
            int mid = (left + right) / 2;
            int tasks = calculateTask(mid, cores);
            
            if(tasks < n) {
                left = mid + 1;
            } else {
                right = mid - 1;
                time = mid;
                completedTasks = tasks;
            }
        }
        
        completedTasks -= n;
        
        for(int i = cores.length-1 ; i >=0 ; i--) {
            if(time%cores[i] != 0) continue;
                
            if(completedTasks == 0) {
                answer = i + 1;
                break;
            }
            completedTasks--;
        }
        
        return answer;
    }
}
