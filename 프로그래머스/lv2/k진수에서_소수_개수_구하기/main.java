import java.util.*;

class Solution {
    
    private Stack<Integer> convertNary(int n, int k) {
        Stack<Integer> s = new Stack<>();
        
        while(n > 0) {
            int m = n % k;
            n = n / k;
            s.push(m);
        }
        
        return s;
    }
    
    private int isPrime(long v) {
        long limit = (long)Math.sqrt(v);
        
        if(v == 1) {
            return 0;
        }
        
        if(v == 2) {
            return 1;
        }
        
        if(v % 2 == 0) {
            return 0;
        }
        
        for(int i = 3 ; i <= limit ; i+=2) {
            if(v % i == 0) {
                return 0;
            }
        }
        return 1;
    }
    
    public int solution(int n, int k) {
        int answer = 0;
        Stack<Integer> nAry = convertNary(n, k);
        
        Stack<Integer> nonZero = new Stack<>();

        while(!nAry.isEmpty()) {
            int value = nAry.pop();
            
            if(value == 0) {
                long total = 0L;
                long cnt = 1L;
                while(!nonZero.isEmpty()) {
                    total += ((long)nonZero.pop() * cnt);
                    cnt*=10L;
                }
                
                if(total >= 2) {
                    answer += isPrime(total);
                }
            } else {
                nonZero.push(value);
            }
        }
        
        long total = 0L;
        long cnt = 1;
        while(!nonZero.isEmpty()) {
            total += ((long)nonZero.pop() * cnt);
            cnt*=10L;
        }
        
        answer += isPrime(total);
        
        return answer;
    }
}
