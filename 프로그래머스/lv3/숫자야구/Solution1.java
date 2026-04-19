import java.util.function.Function;
import java.util.*;
import java.util.stream.*;

class Solution {

    public int solution(int n, Function<Integer, String> submit) {
        
        List<Integer> pool = createPool();
        int callCnt = 0;
        while ( pool.size() > 1 ) {
            callCnt++;
            var candidate = pool.get(0);
            var result = submit.apply(candidate);
            
            if (result.equals("4S 0B")) {
                return candidate;
            }
            
            pool = getNextPool(pool, result, candidate);
            if (callCnt >= 6) break;
        }
        
        return pool.get(pool.size() - 1);
    }
    
    private boolean guess(String expected, String value, int strike, int ball) {
        int realStrike = 0;
        int realBall = 0;
        for ( int i = 0 ; i < 4 ; ++i ) {
            char e = expected.charAt(i);
            char v = value.charAt(i);
            
            if (e == v) {
                realStrike++;
            } else if ( expected.indexOf(v) != -1 ) {
                realBall++;
            }
        }
        
        return realStrike == strike && realBall == ball;
    }
    
    private List<Integer> getNextPool(List<Integer> pool, String result, int expected) {
        // candidate와 value가 같은 결과를 낼 것으로 추측되면 true 
        int strike = (int)(result.charAt(0) - '0');
        int ball = (int)(result.charAt(3) - '0');
        String expectedS = String.valueOf(expected);
        return pool.stream()
            .filter( x -> guess(expectedS, String.valueOf(x), strike, ball ) )
            .collect(Collectors.toList());
    } 
    
    private List<Integer> createPool() {
        List<Integer> pool = new ArrayList<>();
        
        for ( int i = 1 ; i <= 9 ; ++i ) {
            for ( int j = 1 ; j <= 9 ; ++j ) {
                if (i == j) continue;
                
                for ( int k = 1 ; k <= 9 ; ++k ) {
                    if ( i== k || j == k ) continue;
                    
                    for ( int l = 1 ; l <= 9 ; ++l ) {
                        if ( l == i || l == j || l == k) continue;
                        pool.add( 1000 * i + 100 * j + 10 * k + l );
                    }
                }
            }
        }
        return pool;
    }
}

