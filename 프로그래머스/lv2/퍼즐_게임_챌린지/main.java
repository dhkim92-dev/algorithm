import java.util.*;
import java.util.stream.*;

class Solution {
    
    private long calcTime(int diff, int lv, int time_cur, int time_prev) {
        long count = (diff - lv) <= 0 ? 0 : diff-lv;
        
        return count == 0L ? (long)time_cur : ((long)time_prev + (long)time_cur) * count + (long)time_cur;
    }
    
    private long calcTimes(int[] diffs, int[] times, int level) {
        long reducedTime = 0;
        
        for(int i = 1 ; i < diffs.length ; i++) {
            reducedTime += calcTime(diffs[i], level, times[i], times[i-1]);
        }
        
        return reducedTime;
    }
    
    private int parametricSearch(int[] _diffs, int[] _times, long limit) {
        int[] diffs = new int[_diffs.length+1];
        int[] times = new int[_times.length+1];
        for(int i = 1 ; i < diffs.length ; i++) {
            diffs[i] = _diffs[i-1];
            times[i] = _times[i-1];
        }
        
        int left=1, right=0, mid = 0;
        
        // 레벨 최대값 후보 검색
        for(int i = 1 ; i < diffs.length ; i++) {
            right = Math.max(right, diffs[i]);
        }
        int answer = right;
        while(left < right) {
            mid = (left + right) / 2;
            
            long spentTimes = calcTimes(diffs, times, mid);
            
            if(spentTimes <= limit) {
                //answer = Math.min(answer, mid);
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return right;
    }
    
    public int solution(int[] diffs, int[] times, long limit) {
        int answer = 0;
        
        // 1. level >= diff 라면 퍼즐을 틀리지 않고, times[i] 에 해결 가능하다.
        // 2. level < diff 라면 퍼즐을 diff-level번 틀린다.
        answer = parametricSearch(diffs, times, limit);
        
        return answer;
    }
}
