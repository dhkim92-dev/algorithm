import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

class Solution {
    
    private long convertToLong(String value) {
        long converted = 0;
        
        String reversed = new StringBuilder(value)
            .reverse()
            .toString();
        
        long power = 1;
        
        for ( int i = 0 ; i < reversed.length() ; i++ ) {
            converted += (power * (long)((reversed.charAt(i) - 'a') + 1)); 
            power *= 26;
        }
        
        return converted;
    }
    
    private String convertToString(long value) {
        StringBuilder sb = new StringBuilder();
        
        while ( value > 0 ) {
            //System.out.println("value : " + value);
            long remainder = value % 26;
            //System.out.println("remainder : " + remainder);
            value /= 26;
            
            if ( remainder == 0 ) {
                value--;
                sb.append('z');
            } else {
                sb.append( (char)('a' + remainder-1) );
            }
        }
        
        return sb.reverse().toString();
    }
    
    public String solution(long n, String[] bans) {
        String test = "za";
        long lv = convertToLong(test);
        //System.out.println(test + " is " + convertToLong(test));
        //System.out.println(lv + " is " + convertToString(lv));
        // 알파벳은 26자
        // n in 2^4 ~ 2^5
        // let n ^ 11 = 2^44 ~ 2^55
        // long 범위 내로 해결 가능
        // n번째 주문을 리턴해야함.
        List<Long> converted = Arrays
            .stream(bans)
            .map(x -> convertToLong(x))
            .collect(Collectors.toList());
        
        Collections.sort(converted);
        
        long target = n;
        for ( int i = 0 ; i < converted.size() ; i++ ) {
        	if ( converted.get(i) <= target ) {
                target++;
            }
        }
        
        String answer = convertToString(target);
        return answer;
    }
}
