import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private static final int BLACK = -1;
    private static final int RAINBOW = 0;
    private static final int EMPTY = -999;

    private int N, M;
    private int[][] board;
    private int[][] tmpBoard;
    private boolean[][] visited;

    private static final Pos[] DIR = {
            new Pos(-1, 0), // 상
            new Pos(1, 0), // 하
            new Pos(0, -1), // 좌
            new Pos(0, 1) // 우
    };

    static public class Pos {
        public int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos other) {
            return new Pos(this.r + other.r, this.c + other.c);
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) return true;
            if ( !(obj instanceof Pos) ) return false;
            Pos other = (Pos) obj;
            return this.r == other.r && this.c == other.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    static public class BlockGroup implements Comparable<BlockGroup> {
        private Set<Pos> blocks;
        public Pos standard = null;
        public int pivotIndex;
        private int index;
        private int rainbowCount = 0;

        public BlockGroup(int index, int[][] board, int r, int c) {
            blocks = new HashSet<>();
            pivotIndex = board[r][c];
            blocks.add(new Pos(r, c));
            this.standard = new Pos(r, c);
            this.index = index;
        }

        public void addBlock(int[][] board, int r, int c) {
            if ( board[r][c] == RAINBOW ) {
                rainbowCount++;
            }  
            // mark[r][c] = index;
            blocks.add(new Pos(r, c));
            updateStandard(board, r, c);
        }

        private void updateStandard(int[][] board, int r, int c) {
            if ( board[r][c] == RAINBOW ) return;
            if ( standard.r > r ) {
                standard.r = r;
                standard.c = c;
            } else if ( standard.r == r ) {
                if ( standard.c > c ) {
                    standard.r = r;
                    standard.c = c;
                }
            }
        }

        public int countBlocks() {
            return blocks.size();
        }

        public int getScore() {
            return blocks.size() * blocks.size();
        }

        public void removeBlocks(int[][] board) {
            for (Pos block : blocks) {
                board[block.r][block.c] = EMPTY;
            }
        }

        public Set<Pos> getBlocks() {
            return blocks;
        }

        @Override
        public int compareTo(BlockGroup other) {
            // 내림차순 정렬
            // 블록 개수가 큰 순서로 정렬
            // 블록 개수가 같다면, 포함된 무지개 블록 개수가 많은 순서로 정렬
            // 블록 개수가 같고, 포함된 무지개 블록 개수가 같다면,
            // 기준 블록의 행이 큰 순서로 정렬
            // 기준 블록의 행이 같다면, 기준 블록의 열이 큰 순서로 정렬

            if ( this.countBlocks() != other.countBlocks() ) {
                return Integer.compare(other.countBlocks(), this.countBlocks());
            } else if ( this.rainbowCount != other.rainbowCount ) {
                return Integer.compare(other.rainbowCount, this.rainbowCount);
            } else if ( this.standard.r != other.standard.r ) {
                return Integer.compare(other.standard.r, this.standard.r);
            } else {
                return Integer.compare(other.standard.c, this.standard.c);
            }
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);

        board = new int[N][N];
        tmpBoard = new int[N][N];
        visited = new boolean[N][N];

        for ( int i = 0 ; i < N ; ++i ) {
            tokens = reader.readLine().split(" ");
            for ( int j = 0 ; j < N ; ++j ) {
                board[i][j] = Integer.parseInt(tokens[j]);
            }
        }
    }

    private boolean isInRange( Pos p ) { 
        return p.r >= 0 && p.r < N && p.c >= 0 && p.c < N;
    }

    private void print(int[][] b) {
        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                if ( b[r][c] == EMPTY ) {
                    System.out.print("X ");
                } else if ( b[r][c] == BLACK ) {
                    System.out.print("B ");
                } else if ( b[r][c] == RAINBOW ) {
                    System.out.print("R ");
                } else {
                    System.out.print(b[r][c] + " ");
                }
            }
            System.out.println();
        }
    }

    private BlockGroup explore(int offset, int r, int c) {
        BlockGroup blockGroup = new BlockGroup(offset, board, r, c);

        Queue<Pos> queue = new LinkedList<>();
        queue.add(new Pos(r, c));

        for ( boolean[] row  : visited ) {
            Arrays.fill(row, false);
        }

        visited[r][c] = true;

        while ( !queue.isEmpty() ) {
            Pos cur = queue.poll();

            for ( int i = 0 ; i < 4 ; ++i ) {
                Pos next = cur.add(DIR[i]);

                if ( !isInRange(next) ) continue;
                if ( visited[next.r][next.c] ) continue;

                int value = board[next.r][next.c];

                if ( value == BLACK ) continue;
                if ( value == RAINBOW ) {
                    blockGroup.addBlock(board, next.r, next.c);
                    queue.add(next);
                    visited[next.r][next.c] = true;
                    continue;
                }
                if ( value != blockGroup.pivotIndex ) continue;

                blockGroup.addBlock(board, next.r, next.c);
                queue.add(next);
                visited[next.r][next.c] = true;
            }
        }

        return blockGroup;
    }

    private BlockGroup getLargestBlockGroup() {
        List<BlockGroup> blockGroups = new ArrayList<>();

        int offset = 0;

        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                if ( board[r][c] <= 0 ) continue;
                BlockGroup blockGroup = explore(offset, r, c);

                if ( blockGroup.countBlocks() >= 2 ) {
                    blockGroups.add(blockGroup);
                }

                offset++;
            }
        }

        Collections.sort(blockGroups);

        if ( blockGroups.isEmpty() ) {
            return null;
        }

        return blockGroups.get( 0 );
    }

    private void drop(int value, int r, int c) {
        if ( r == N - 1 ) {
            board[r][c] = value;
            return;
        }

        // 다음 행이 비어있으면 진행시킨다.
        // 다음 행이 비어있지 않으면 현재 위치에 놓고 탐색을 종료한다.
        if ( board[r + 1][c] == EMPTY ) {
            drop(value, r + 1, c);
        } else {
            board[r][c] = value;
        }
    }

    private void gravity() {
        for ( int r = N-1 ; r >= 0 ; --r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                if ( board[r][c] < 0 ) continue;
                int value = board[r][c];
                board[r][c] = EMPTY;
                drop(value, r, c);
            }
        }
    }

    private void rotate() { 
        // board 를 반시계 방향으로 90도 회전한다.
        for ( int[] row : tmpBoard ) {
            Arrays.fill(row, EMPTY);
        }

        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                tmpBoard[N - c - 1][r] = board[r][c];
            }
        }

        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                board[r][c] = tmpBoard[r][c];
            }
        }
    }

    private void printBoard(BlockGroup blockGroup) {
        int[][] b = new int[N][N];
        for ( Pos block : blockGroup.getBlocks() ) {
            b[block.r][block.c] = 1;
        }

        System.out.println("BlockGroup : " + blockGroup.countBlocks());

        for ( int r = 0 ; r < N ; ++r ) {
            for ( int c = 0 ; c < N ; ++c ) {
                if ( b[r][c] == 1 ) {
                    System.out.print("X ");
                } else {
                    System.out.print(b[r][c] + " ");
                }
            }
            System.out.println();
        }
    }

    private int simulate() {
        int answer = 0;

        // System.out.println("Initial state");
        // print(board);

        while ( true ) {
            BlockGroup blockGroup = getLargestBlockGroup();

            if ( blockGroup == null ) {
                break;
            }
            // printBoard(blockGroup);
            int score = blockGroup.getScore();

            answer += score;
            blockGroup.removeBlocks(board);
            // System.out.println("After remove blocks");
            // print(board);
            gravity();
            // System.out.println("After gravity");
            // print(board);
            rotate();
            // System.out.println("After rotate");
            // print(board);
            gravity();
            // System.out.println("After gravity");
            // print(board);
        }
        return answer;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate());
        System.out.println(sb.toString());
    }
}


