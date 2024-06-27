import java.util.*;
import java.util.stream.Collectors;

class Solution {
    
    public int[] solution(int n, long left, long right) {
        int[] answer;// = new int[left - right + 1];
        List<Integer> arr = new ArrayList<>();
        
        for(long i = left ; i <= right ; i++) {
            int rowId = (int)(i / n);
            int colId = (int)(i % n);
            
            arr.add(Math.max(rowId, colId) + 1);
        }
        answer = new int[arr.size()];
        
        for(int i = 0 ; i < arr.size() ; i++){
            answer[i] = arr.get(i);
        }
        
        return answer;
    }
}
