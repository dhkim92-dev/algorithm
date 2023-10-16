import java.util.*;

class Solution
{
    private int limit = 0;
        
    private boolean isPalindrome(String target, int from, int to) {
        while(from <= to) {
            if(target.charAt(from) != target.charAt(to)) {
                return false;
            }
            from++;
            to--;
        }
        
        return true;
    }
    
    public int solution(String s)
    {
        limit = s.length();
        
        for(int length = limit ; length > 0 ; length--) {
            for(int start = 0 ; start + length <= limit ; start++) {
                if(isPalindrome(s, start, start+length-1)) {
                    return length;
                }
            }
        }

        return 0;
    }
}
