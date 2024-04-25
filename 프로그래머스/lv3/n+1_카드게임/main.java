import java.util.*; 
    
class Solution {
    public int solution(int coin, int[] cards) {
        int answer = 0;
        int index = cards.length / 3;
        int n = cards.length;
        int target = n + 1;
        
        HashSet<Integer> hands = new HashSet();
        HashSet<Integer> picked = new HashSet();
        
        for(int i = 0 ; i < index ; i++) {
            hands.add( cards[i] );
        }
        
        while(true) {
            answer++;
            if(index >= n) {
                break;
            }
            
            boolean proceedable = false;
            
            for(int i = 0 ; i < 2 ; i++) {
                picked.add( cards[index + i] );
            }
            
            for(int value : hands) {
                if( hands.contains( target - value) ) {
                    proceedable = true;
                    hands.remove(target - value);
                    hands.remove(value);
                    break;
                }
            }
            
            if(!proceedable && coin > 0) {
                // 한개만 추가 패에서 사용하는 경우 
                for(int hand_value : hands) {
                    if( picked.contains(target - hand_value) ) {
                        hands.remove(hand_value);
                        picked.remove(target - hand_value);
                        proceedable = true;
                        coin--;
                        break;
                    }
                }
            }
            
            if(!proceedable && coin > 1) {
                for(int value : picked) {
                    if( picked.contains(target - value) ) {
                        picked.remove(value);
                        picked.remove(target-value);
                        proceedable = true;
                        coin-=2;
                        break;
                    }
                }
            } 
            
            if(!proceedable) {
                break;
            }
            
            index += 2;
        }
        
        return answer;
    }
}
