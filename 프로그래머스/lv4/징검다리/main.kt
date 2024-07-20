import kotlin.math.*;

class Solution {
    fun solution(distance: Int, rocks: IntArray, n: Int): Int {
        var answer = 0
        var left = 0
        var right = distance;
        var mid = 0;
        rocks.sort();

        // Parametric Search
        // mid 에 대하여 이 mid 가 n개를 제거했을 때 최소값이 될 수 있는지 확인한다.
        // 0. [0, distance]에 대해 Parametric Search를 수행한다.
        // 1. mid를 설정한다.
        // 2. 주어진 mid에 대하여 rocks[i] - prev < mid for i in range(0, rocks.size) 라면
        // 3. 해당 바위를 제거해야만 현재의 mid가 최소값이 될 수 있다.
        // 4. 그렇지 않다면 prev를 rocks[i]로 설정하고 계속한다.
        // 5. 마지막 바위에서 마지막 prev 까지 거리가 mid보다 작다면 cnt 를 1 증가시킨다.
        // 6. cnt가 n보다 작거나 같다면 answer = max(answer, mid)
        // 7. left, right값 업데이트
        
        
        while(left <= right) {
            var cnt = 0
            var prev = 0
            
            mid = (left+right)/2//left + (right - left)/2
            
            for(i in 0 until rocks.size) {
                if(rocks[i] - prev < mid) {
                    cnt++;
                } else {
                    prev = rocks[i];
                }    
            }
            
            if(distance - prev < mid) {
                cnt++
            }
            
            if(cnt <= n) {
                answer = max(answer, mid)
                left = mid+1
            } else {
                right = mid-1
            }
        }
        
        return answer
    }
}
