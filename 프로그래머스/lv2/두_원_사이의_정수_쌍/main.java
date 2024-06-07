import java.util.*;

class Solution {
    
    public long solution(int r1, int r2) {
        long answer = 0L; // 전체 점의 개수
        
        // 1사 분면만 구하고 4배
        // 겹치는 부분만 제외
        long r1Square = (long) Math.pow(r1, 2);
        long r2Square = (long) Math.pow(r2, 2);
        long onSmallCircle = 0;
        
        for(int x = 0 ; x <= r2 ; x++) {
            // 현재 X포인트를 기준으로 하여
            // 1사분면 상의 범위를 만족하는 Y  좌표만 구한다.
            long xs = (long)Math.pow(x, 2);
            double smallCircleY = Math.sqrt(r1Square - xs);
            double bigCircleY = Math.sqrt(r2Square - xs);
            
            // 작은 원의 테두리에 겹치는 점을 따로 세야한다.
            if(smallCircleY % 1 == 0) { 
                // 더블형 모듈로 연산 결과가 0이라면 작은 원의 Y 개수가 정수로 딱 나누어진다는 소리
                // 즉 원 가장자리 위에 존재하는 점이라는 뜻
                onSmallCircle++;
            }

            answer += ((long)bigCircleY - (long)smallCircleY) * 4;
        }
        
        answer += (onSmallCircle) * 4 - 4;
     
        return answer;
    }
}
