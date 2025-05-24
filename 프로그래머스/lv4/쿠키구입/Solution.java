import java.util.*;
class Solution {
    
    private int[] psum;
    private int maxLen;
    
    private void prefixSum(int[] cookie) {
        psum = new int[cookie.length + 1];
        maxLen = psum.length;
        for ( int i = 1 ; i <= cookie.length ; ++i ) {
            psum[i] = cookie[i-1] + psum[i-1];
        }
    }
    
    private int binarySearch(int left, int right) {
        int sum = psum[right+1] - psum[left];
        if ( sum % 2 != 0) return 0;
        
        int half = sum / 2;
        int lo = left - 1;
        int hi = right + 1;
        
        while ( lo + 1 < hi ) {
            int mid = lo + (hi - lo) / 2;
            int check = psum[mid + 1] - psum[left];
            
            if ( check == half ) {
                return half;
            } else if ( check < half) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        
        return 0;
    }
    
    public int solution(int[] cookie) {
        int answer = 0;
        prefixSum(cookie);
        
        for ( int i = 0 ; i < cookie.length ; ++i ) {
            for ( int j = i+1 ; j < cookie.length ; ++j ) {
                answer = Math.max(binarySearch(i, j), answer);
            }
        }
        
        return answer;
    }
}
