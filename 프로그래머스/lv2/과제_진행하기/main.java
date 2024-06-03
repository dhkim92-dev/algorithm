import java.util.*;
import java.util.stream.Collectors;


class Solution {
        
    class Plan {
        
        public int total;
        
        public String name;
        
        public Integer start;
        
        public Integer playtime;
        
        public Plan(String[] plan) {
            name = plan[0];
            start = Integer.parseInt(plan[1].substring(0, 2)) * 60
                + Integer.parseInt(plan[1].substring(3,5));
            playtime = Integer.parseInt(plan[2]);
        }
        
        @Override
        public String toString() {
            return "name : " + name + "\n" + 
                "start : " + start.toString() + "\n" + 
                "playtime : " + playtime;
        }
    }
    
    private int timeToInt(String s) {
        return Integer.parseInt(s.substring(0, 2)) * 60
                + Integer.parseInt(s.substring(3,5));   
    }
    
    public String[] solution(String[][] plans) {
        int total = plans.length;
        String[] answer = new String[total];
        
        Arrays.sort(plans, (a, b) -> timeToInt(a[1]) - timeToInt(b[1]));
        Queue<Plan> tasks = new LinkedList<Plan>(
            Arrays.stream(plans)
            .map(p -> new Plan(p))
            .collect(Collectors.toList())
        );
        Stack<Plan> pendings = new Stack<>();
        
        Plan current = tasks.poll();
        int ts = current.start;
        int offset = 0;
        
        while(!tasks.isEmpty()) {
            Plan next = tasks.peek();
            int gap = next.start - (ts + current.playtime);
            
            if(gap <= 0) {
                // 작업이 중단되어야 함
                // 플레이타임을 감소시키고 스택에 추가
                // 현재 타임스탬프를 업데이트
                // 그리고 현재 작업을 신규 작업으로 변경
                current.playtime -= (next.start - ts);
                ts = next.start;
                
                if(gap != 0) {
                    pendings.push(current);
                } else {
                    answer[offset++] = current.name;
                }
                
                current = next;
                tasks.poll();
            } else {
                // 신규 작업의 시작 시각 이전 작업이 완료된 경우임
                // 현재 작업을 완료된 작업으로 집어넣고
                // 중단된 작업이 있다면 그걸 먼저 실행해야함
                if(!pendings.isEmpty()) {
                    Plan last = pendings.pop();
                    answer[offset++] = current.name;
                
                    ts += current.playtime;
                    current = last;
                } else {
                    ts = next.start;
                    answer[offset++] = current.name;
                    current = next;
                    tasks.poll();
                }
            }
        }
    
        answer[offset++] = current.name;
        // 모든 작업큐를 소모했다면 스택에 있는 것들을 차례로 소모한다.
        while(!pendings.isEmpty()) {
            Plan p = pendings.pop();
            answer[offset++] = p.name;
        }
        
        return answer;
    }
}
