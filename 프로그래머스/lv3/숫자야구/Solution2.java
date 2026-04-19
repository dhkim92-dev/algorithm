import java.util.function.Function;
import java.util.*;
import java.util.stream.*;

class Solution {

    public int solution(int n, Function<Integer, String> submit) {
        List<Integer> candidate = List.of(1,2,3,4,5,6,7,8,9);
        Set<Integer>[] pools = new HashSet[4];
        for(int i = 0 ; i < 4 ; ++i ) {
            pools[i] = new HashSet<Integer>(candidate);
        }
        
        for ( int i = 1 ; i <= 7 ; i += 2 ) {
            int digit = toDigit(i, i+1, i+1, i+1);
            String res = submit.apply(digit);
            
            int s = (int)(res.charAt(0) - '0');
            int b = (int)(res.charAt(3) - '0');
            
            if ( s == 4 ) {
                return digit;
            }
            
			if ( s == 1 && b == 0 ) { //
                // 첫 자리만 일치 
                poolOut(pools, i);
                poolOut(pools, i+1);
                pools[0].clear();
                pools[0].add(i);
            } else if ( s == 2 && b == 2) { //
                // 첫자리 일치, i+1도 남은 세자리에 존재
                poolOut(pools, i);
                pools[0].clear();
                pools[0].add(i);
            } else if ( s == 1 && b == 3 ) { //
                // i, i+1 이 두번째부터 네번째 자리 존재
                pools[0].remove(i);
                pools[0].remove(i+1);
            }else if ( s == 1 && b == 2) {
                // 첫자리 불일치, 남은 세 자리중 하나 i+1 존재
                poolOut(pools, i);
                pools[0].remove(i+1);
            } else if ( s == 0 && b == 1 ) {
                // 첫자리 불일치, 남은 세자리 중 하나 i
                poolOut(pools, i+1);
                pools[0].remove(i);
            } else if ( s == 0 && b == 3) {
                // 첫자리 불일치, 첫자리 수가 i+1 인 경우
                poolOut(pools, i);
                poolOut(pools, i+1);
                pools[0].clear();
                pools[0].add(i+1);
            } else if ( s == 0 && b == 4) {
                // i 가 2~4자리에 있고, i+1이 첫자리인 경우
                poolOut(pools, i+1);
                pools[0].clear();
                pools[0].add(i+1);
            } else if ( s == 0 && b == 0) {
                // 두 수 모두 존재하지 않음
                poolOut(pools, i);
                poolOut(pools, i+1);
            }
        }
        
        //for ( int i = 0 ; i < 4 ; ++i ) {
        //    System.out.println("pools[" + i + "] : " + pools[i].toString());
        //}

        // 여기까지 왔는데 pools[2 : 4] 사이즈가 4 면 9 제거
        if (pools[1].size() == 4) {
            for (int i = 1 ; i < 4 ; ++i ) {
                pools[i].remove(9);
            }
        }
        
        //for ( int i = 0 ; i < 4 ; ++i ) {
        //    System.out.println("pools[" + i + "] : " + pools[i].toString());
        //}
        
        // 여기까지 오면 가능한 숫자 풀은 F [X Y Z] [ X Y Z] [ X Y Z] 형태로 남게됨.
        // F X Y Y 형태로 제출
        int first = pools[0].iterator().next();
        Iterator<Integer> it = pools[1].iterator();
        int X = it.next();
        int Y = it.next();
        int Z = it.next();
        
        int fxyy = toDigit(first, X, Y, Y);
        String res = submit.apply(fxyy);
        //System.out.println("fxyy : "+fxyy);
        
        if ( res.equals("4S 0B")) return fxyy;
        int s = (int)(res.charAt(0) - '0') - 1;
        int b = (int)(res.charAt(3) - '0');
        
        // n = 4 인 상태이며,
        // s + b = 3 으로 무조건 고정이다.
        // XYZ XYY: 2s 1b, 둘째자리 X확정 F X [Y Z] [Y Z]
        // XZY XYY: 2s 1b, 둘째자리 X확정 F X [Y Z] [Y Z]
        // ZXY XYY: 1s 2b, 둘째자리 Z확정 F Z [Y X] [Y X]
        // ZYX XYY: 1s 2b, 둘째자리 Z확정 F Z [Y X] [Y X]
        // YXZ XYY: 3b,    둘째자리 Y확정 F Y [X Z] [X Z]
        // YZX XYY: 3b,    둘째자리 Y확정 F Y [X Z] [X Z]
        
        // n = 5
        if ( s == 2 ) {
            poolOut(pools, X);
            pools[1].clear();
            pools[1].add(X);
        } else if (s == 1) {
            poolOut(pools, Z);
            pools[1].clear();
            pools[1].add(Z);
        } else if ( s == 0 ) {
            poolOut(pools, Y);
            pools[1].clear();
            pools[1].add(Y);
        }
        //for ( int i = 0 ; i < 4 ; ++i ) {
        //    System.out.println("pools[" + i + "] : " + pools[i].toString());
        //}
        // n = 6
        // 남은건 F S [X Y] [X Y]
        // F S X Y 제출해보고 4s 아니면 F S Y X 정답
        
        it = pools[2].iterator();
        X = it.next();
        Y = it.next();
        int second = pools[1].iterator().next();
        
        int fsxy = toDigit(first, second, X, Y);
        int fsyx = toDigit(first, second, Y, X);
        res = submit.apply(fsxy);
        
        //System.out.println("fsxy : " + fsxy);
        //System.out.println("fsyx : " + fsyx);
        
        if (res.equals("4S 0B")) {
            return fsxy;
        }
        
    	
        return fsyx;
    }
    
    private void poolOut(Set<Integer>[] pools, int v) {
        for (int i = 0 ; i < 4 ; ++i) {
            if (pools[i].contains(v))
                pools[i].remove(v);
        }
    }
    
    private int toDigit(int a, int b, int c, int d) {
        return a * 1000 + b * 100 + c * 10 + d;
    }
}


/* 테스트 1 〉	통과 (9.72ms, 83.9MB)
테스트 2 〉	통과 (7.31ms, 98MB)
테스트 3 〉	통과 (11.79ms, 97.8MB)
테스트 4 〉	통과 (9.38ms, 85.6MB)
테스트 5 〉	통과 (7.48ms, 85.1MB)
테스트 6 〉	통과 (8.17ms, 86.2MB)
테스트 7 〉	통과 (7.84ms, 77.7MB)
테스트 8 〉	통과 (10.01ms, 83.2MB)
테스트 9 〉	통과 (10.21ms, 97MB)
테스트 10 〉	통과 (9.33ms, 94.4MB)
테스트 11 〉	통과 (16.76ms, 100MB)
테스트 12 〉	통과 (9.81ms, 88MB)
테스트 13 〉	통과 (8.00ms, 96.1MB)
테스트 14 〉	통과 (8.62ms, 97MB)
테스트 15 〉	통과 (10.59ms, 97.5MB)
테스트 16 〉	통과 (10.20ms, 79.5MB)
테스트 17 〉	통과 (14.07ms, 78MB)
테스트 18 〉	통과 (10.66ms, 93.7MB)
테스트 19 〉	통과 (8.06ms, 79.9MB)
테스트 20 〉	통과 (9.22ms, 81.1MB)
테스트 21 〉	통과 (8.80ms, 91.7MB)
테스트 22 〉	통과 (10.46ms, 95.9MB)
테스트 23 〉	통과 (10.89ms, 86.4MB)
테스트 24 〉	통과 (10.37ms, 107MB)
테스트 25 〉	통과 (8.98ms, 84.8MB)
테스트 26 〉	통과 (9.60ms, 101MB)
테스트 27 〉	통과 (10.86ms, 82MB)
테스트 28 〉	통과 (15.87ms, 80.7MB)
테스트 29 〉	통과 (7.39ms, 96.9MB)
테스트 30 〉	통과 (7.76ms, 78.1MB)
테스트 31 〉	통과 (10.97ms, 94.8MB)
테스트 32 〉	통과 (11.52ms, 78.6MB)
테스트 33 〉	통과 (11.41ms, 97.4MB)
테스트 34 〉	통과 (9.74ms, 80.6MB)
테스트 35 〉	통과 (11.66ms, 91MB)
테스트 36 〉	통과 (8.76ms, 87MB)
테스트 37 〉	통과 (9.27ms, 79.1MB)
테스트 38 〉	통과 (10.66ms, 94.6MB)
테스트 39 〉	통과 (10.19ms, 80.4MB)
테스트 40 〉	통과 (9.57ms, 83.6MB) */
