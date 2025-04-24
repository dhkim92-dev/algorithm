import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int[][] greenBoard, blueBoard;
    private int N;
    private int[][] inputs;

    static class Block {
        public int type;
        public int[][] cells;

        public Block(int type, int r, int c) {
            this.type = type;
            
            if ( type == 1 ) {
                cells = new int[1][2];
                cells[0][0] = r;
                cells[0][1] = c;
            } else if ( type == 2 ) {
                cells = new int[2][2];
                cells[0][0] = r;
                cells[0][1] = c;
                cells[1][0] = r;
                cells[1][1] = c + 1;
            } else if ( type == 3 ) {
                cells = new int[2][2];
                
                cells[0][0] = r;
                cells[0][1] = c;
                cells[1][0] = r + 1;
                cells[1][1] = c;
            }
        }

        public void move() {
            for ( int i = 0 ; i < cells.length ; ++i ) {
                cells[i][0] += 1;
            }
        }

        public void fallback() {
            for ( int i = 0 ; i < cells.length ; ++i ) {
                cells[i][0] -= 1;
            }
        }

        public boolean isOverlapped(int[][] board) {
            for ( int i = 0 ; i < cells.length ; ++i ) {
                int r = cells[i][0];
                int c = cells[i][1];

                if ( r < 0 || r >= board.length || c < 0 || c >= board[0].length ) {
                    return true;
                }

                if ( board[r][c] != 0 ) {
                    return true;
                }
            }
            return false;
        }

        public void place(int[][] board) {
            for ( int i = 0 ; i < cells.length ; ++i ) {
                int r = cells[i][0];
                int c = cells[i][1];

                if ( r >= 0 && r < board.length && c >= 0 && c < board[0].length ) {
                    board[r][c] = 1;
                }
            }
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        inputs = new int[N][3];

        String[] tokens;

        for (int i = 0; i < N; i++) {
            tokens = reader.readLine().split(" ");
            inputs[i][0] = Integer.parseInt(tokens[0]);
            inputs[i][1] = Integer.parseInt(tokens[1]);
            inputs[i][2] = Integer.parseInt(tokens[2]);
        }
        blueBoard = new int[10][4];
        greenBoard = new int[10][4];
    }

    private Block generateBlock(int type, int r, int c, boolean transposed) {
        Block block = null;
        if ( !transposed ) {
            block = new Block(type , r, c);
        } else {
            if ( type == 2 ) {
                type = 3;
            } else if ( type == 3 ) {
                type = 2;
            }
            block = new Block(type, c, r);
        }
        return block;
    }

    private int countLiteAreaOverlapped(int[][] board) {
        int cnt = 0;

        for ( int row = 4 ; row <= 5 ; row++ ) {
            for ( int col = 0 ; col < 4 ; ++col ) {
                if ( board[row][col] == 1 ) {
                    cnt++;
                    break;
                }
            }
        }

        return cnt;
    }

    private void pullBoard(int[][] board, int from, int cnt) {
        if ( cnt == 0 ) return;

        for ( int row = from ; row > 4 ; --row ) {
            for (int col = 0 ; col < 4 ; ++col ) {
                board[row][col] = board[row - cnt][col];
            }
        }
        // clear the top 2 rows
        for ( int row = 0 ; row < 2 ; ++row ) {
            for ( int col = 0 ; col < 4 ; ++col ) {
                board[4+row][col] = 0;
            }
        }
    }

    private int clearLines(int[][] board) {
        int cnt = 0;

        for ( int row = 6 ; row < 10 ; ++row ) {
            boolean isFull = true;

            for ( int col = 0 ; col < 4  && isFull ; ++col ) {
                if ( board[row][col] != 0 ) continue;
                isFull = false;
            }

            if ( !isFull ) continue;
            cnt++;
            // pull one row down from row;]
            pullBoard(board, row, 1);
        }

        return cnt;
    }

    private int process(int[][] board, int type, int r, int c, boolean transposed) {
        Block block = generateBlock(type, r, c, transposed);
        // System.out.println("Block type : " + type + " " + r + " " + c);

        do {
            block.move();
        } while( !block.isOverlapped(board) );
        block.fallback();
        block.place(board);
        int cleared = clearLines(board);
        int liteOverlapped = countLiteAreaOverlapped(board);
        // System.out.println("lite overlapped : " + liteOverlapped);
        pullBoard(board, 9, liteOverlapped);
        // if ( !transposed ) {
            // printBoard(board, transposed ? "blue" : "green");
        // }

        return cleared;
    }

    private int countBlocks(int[][] board) {
        int cnt = 0;
        for ( int row = 4 ; row < board.length ; ++row ) {
            for ( int col = 0 ; col < 4 ; ++col ) {
                cnt += board[row][col] == 1 ? 1 : 0;
            }
        }
        return cnt;
    }

    private void printBoard(int[][] board, String name) {
        System.out.println(name);
        for ( int row = 0 ; row < board.length ; ++row ) {
            for ( int col = 0 ; col < board[0].length ; ++col ) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int simulate() { 
        int score = 0;
        for ( int i = 0 ; i < N ; ++i ) {
            score += process(greenBoard, inputs[i][0], inputs[i][1], inputs[i][2], false);
            score += process(blueBoard, inputs[i][0], inputs[i][1], inputs[i][2], true);
        }

        return score;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate())
            .append("\n")
            .append(countBlocks(greenBoard) + countBlocks(blueBoard));
        System.out.println(sb.toString());
    }
}
