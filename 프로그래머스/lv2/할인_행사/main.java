import java.util.*;
import java.util.stream.Collectors;

class Solution {
    
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        
        
        if(discount.length < 10) {
            return 0;
        }
        
        Set<String> targets = Arrays.stream(want)
            .collect(Collectors.toSet());
        Map<String, Integer> targetCount = new HashMap<>();
        
        for(int i = 0 ; i < want.length ; i ++) {
            targetCount.put(want[i], number[i]);
        }
        
        for(int i = 0 ; i <= discount.length - 10 ; i++) {
            // 10일 이상일 때만 수행
            Map<String, Integer> discountNumber = new HashMap<>();
            
            //System.out.println("Search range " + i + ", " + (i+10));
            
            for(int start = i ; start < i + 10 ; start++) {
                int cnt = discountNumber.getOrDefault(discount[start], 0);
                //System.out.printf(" item name : %s count : %d\n", discount[start], cnt+1);
                discountNumber.put(discount[start], cnt+1);
            }
            
            int satisfied = 1;
            
            for(String name : targets) {
                int wantCount = targetCount.get(name);
                int discountCount = discountNumber.getOrDefault(name, 0);
                //System.out.printf(" target : %s wantCount : %d inventory : %d \n", name, wantCount, discountCount);
                if(wantCount != discountCount) {
                    satisfied = 0;
                    break;
                }
            }
            
            //if(satisfied == 1) {
                //System.out.println(" range satisfied.");
            //}
            answer+=satisfied;
        }
        
        return answer;
    }
}
