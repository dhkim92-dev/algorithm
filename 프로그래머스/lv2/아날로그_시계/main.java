class Solution {
    
    private int timeToSeconds(int h, int m, int s) {
        return h * 3600 + 60 * m + s; 
    }
    
    private int isStartOverlap(int h, int m, int s) {
        double curSDegree = s * 6 ;
        double curMDegree = m * 6 + s * 0.1;
        double curHDegree = (h%12) * 30 + (m * 0.5) + s * (0.5/60);
        
        return (curHDegree == curSDegree || curMDegree == curSDegree) ? 1 : 0;
    }
    
    // 0시 0분 0초부터 특정 시각까지 시침과 초침, 분침과 초침이 겹치는 횟수를 구한다.
    private int overlapCount(int ts) {
        int minutesOverlap = (ts*59)/3600;
        int hoursOverlap = (ts*719)/43200;
        
        // 시간이 12시간 이상이라면 12시 0분 0초에서 시침 초침 분침이 모두 일치하게 된다. 이때를 계산하고
        // 이 함수의 기준은 0시 0분 0초이므로 무조건 처음에 한번 울리기 때문에 1을 더해줘야한다.
        return 1 + hoursOverlap + minutesOverlap - ( ts >= 12 * 3600 ? 1 : 0);
    }
    
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int answer = 0;
        
        // 시침이 한바퀴를 돌면, 무조건 분침과 초침 한번씩 만나게 되어 있다.
        // 분침이 한바퀴 도는데 필요한 초, 60 * 60 = 3600초 
        // 시침이 한바퀴 도는데 필요한 초, 60 * 60 * 12 = 43200초
        // 분침이 한바퀴 돌면 초침과 59번 만난다. 즉 분침에 의한 알람은 3600/59초 주기로 울린다.
        // 시침이 한바퀴 돌면 초침과 719번 만난다. 시침에 의한 알람은 43200/719초 주기로 울린다.
           
        int startTs = timeToSeconds(h1, m1, s1);
        int endTs = timeToSeconds(h2, m2, s2);
        
        answer = overlapCount(endTs) - overlapCount(startTs);
        // 0시 0분 0초에서 종료 시각까지 알람 횟수를 구하고
        // 0시 0분 0초에서 시작 시각까지의 알람 횟수를 빼서
        // 알람 횟수를 구한다.
        
        // 시작 시점에서 알람이 울리는지 체크한다.
        answer += isStartOverlap(h1, m1, s1);
        
        return answer;
    }
}
