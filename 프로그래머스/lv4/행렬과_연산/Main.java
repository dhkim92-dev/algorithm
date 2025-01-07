import java.util.*;

class Solution {
    
    private int R, C;
    private int[] shiftTmp;
    
    //Deque<Deque<Integer>> qCtr = new ArrayDeque<>();
    Deque<Integer> dqTop = new ArrayDeque<>();
    Deque<Integer> dqBot = new ArrayDeque<>();
    Deque<Integer> dqLeft = new ArrayDeque<>();
    Deque<Integer> dqRight = new ArrayDeque<>();
    Deque<Deque<Integer>> dqInt = new ArrayDeque<>();
    
    
    private void shiftRow(int[][] mat) {
        dqInt.push(dqInt.pollLast());
        dqLeft.push(dqLeft.pollLast());
        dqRight.push(dqRight.pollLast());
    }
    
    private void rotate(int[][] mat) {
        // 회전
        dqTop = dqInt.peekFirst();
        dqBot = dqInt.peekLast();
        int tmp = dqLeft.pollFirst();
        dqTop.push(tmp);
        tmp = dqTop.pollLast();
        dqRight.push(tmp);
        tmp = dqRight.pollLast();
        dqBot.add(tmp);
        tmp = dqBot.pollFirst();
        dqLeft.add(tmp);
    }
    
    private void initDeque(int[][] mat) {
        for(int r = 0 ; r < R ; r++) {
            dqLeft.add(mat[r][0]);
            dqRight.add(mat[r][C-1]);
        }
        
        for(int r = 0 ; r < R ; r++) {
            Deque<Integer> queue = new ArrayDeque<>();
            for(int c = 1 ; c < C-1; c++) {
                queue.add(mat[r][c]);
            }
            dqInt.add(queue);
        }
    }
    
    private void toMat(int[][] mat) {
        for(int r = 0 ; r < R ; r++) {
            mat[r][0] = dqLeft.pollFirst();
            mat[r][C-1] = dqRight.pollFirst();
        }
        
        int idx = 0;
        
        for(Deque<Integer> dq : dqInt) {
            for(int c = 1 ; c < C-1 ; c++) {
                mat[idx][c] = dq.pollFirst();
            }
            idx++;
        }
    }
    
    public int[][] solution(int[][] mat, String[] operations) {
        R = mat.length;
        C = mat[0].length;
        
        initDeque(mat);
        
        for(String op: operations) {
           if(op.equals("Rotate")) {
                rotate(mat);
            } else {
               shiftRow(mat);
            }
        }
        
        toMat(mat);
        
        return mat;
    }
}
