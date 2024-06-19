class Solution {
    
    private boolean checkUpperLeftToBottomRight(String[] board, char target) {
        return (board[0].charAt(0) == target) 
            && (board[1].charAt(1) == target) 
            && (board[2].charAt(2) == target);
    }
    
    private boolean checkUpperRightToBottomLeft(String[] board, char target) {
        return (board[0].charAt(2) == target) 
            && (board[1].charAt(1) == target)
            && (board[2].charAt(0) == target);
    }
    
    
    private boolean checkRow(String[] board, int rowNo, char target) {
        String row = board[rowNo];
        int cnt = 0 ;
        for(int i = 0 ; i < row.length() ; i++) {
            if(row.charAt(i) == target) {
                cnt++;
            }
        }
        
        return cnt == 3;
    }
    
    private boolean checkColumn(String[] board, int colNo, char target) {
        int cnt = 0;
        for(int i = 0 ; i < board.length ; i++) {
            if(board[i].charAt(colNo) == target) {
                cnt++;
            }
        }
        
        return cnt == 3;
    }
    
    private int countChar(String[] board, char target) {
        int cnt = 0;
        
        for(int i = 0 ; i < board.length ; i++) {
            String row = board[i];
            for(int j = 0 ; j < row.length() ; j++) {
                if(row.charAt(j) == target) {
                    cnt++;
                }
            }
        }
        
        return cnt;
    }
    
    private boolean isGameOverBy(String[] board, char target) {
        return checkRow(board, 0, target) 
            || checkRow(board, 1, target)
            || checkRow(board, 2, target)
            || checkColumn(board, 0, target)
            || checkColumn(board, 1, target)
            || checkColumn(board, 2, target)
            || checkUpperLeftToBottomRight(board, target)
            || checkUpperRightToBottomLeft(board, target);
    }
    
    private int checkBoard(String[] board) {
        int oCount = countChar(board, 'O');
        int xCount = countChar(board, 'X');
        int answer = 1;
        
        if(oCount - xCount >= 2) {
            answer = 0;
        } else if( (oCount - xCount == 1) && isGameOverBy(board, 'X')) {
            answer = 0;
        } else if( (oCount == xCount ) && isGameOverBy(board, 'O') ) {
            answer = 0;
        } else if( oCount < xCount ) {
            answer = 0;
        }
        
        return answer;
    }
    
    public int solution(String[] board) {
        int answer = checkBoard(board);
        
        return answer;
    }
}
