import java.io.*;
import java.util.*;

class Solution {

    private BufferedReader reader;

    public Solution(BufferedReader reader) throws IOException {
        this.reader = reader;
    }

    private int count(char[][] board, char c) {
        int count = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == c) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isWin(char[][] board, char c) {
        for ( int i = 0 ; i < 3 ; ++i ) {
            if ( board[i][0] == c && board[i][1] == c && board[i][2] == c ) {
                return true;
            }

            if ( board[0][i] == c && board[1][i] == c && board[2][i] == c ) {
                return true;
            }

            if ( board[0][0] == c && board[1][1] == c && board[2][2] == c ) {
                return true;
            }

            if ( board[0][2] == c && board[1][1] == c && board[2][0] == c ) {
                return true;
            }
        }

        return false;
    }

    private String playGame(char[][] board) {
        int xCount = count(board, 'X');
        int oCount = count(board, 'O');
        String answer = "yes";

        if ( xCount < oCount || Math.abs(xCount - oCount) > 1 ) {
            answer = "no";
        } else if ( xCount == oCount + 1 && isWin(board, 'O') ) {
            answer = "no";
        } else if (xCount == oCount && isWin(board, 'X')) {
            answer = "no";
        }
        return answer;
    }

    private String simulate() throws IOException {
        int N = Integer.parseInt(reader.readLine());
        StringBuilder sb = new StringBuilder();

        for ( int i = 0 ; i < N ; ++i ) {
            char[][] board = new char[3][];
            for ( int j = 0 ; j < 3 ; ++j ) {
                board[j] = reader.readLine().toCharArray();
            }

            sb.append(playGame(board))
              .append("\n");
            reader.readLine();
        }
        return sb.toString();
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
          .append("\n");
        System.out.println(sb.toString());
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }
}
