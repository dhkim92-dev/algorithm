import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N;
    private int[][] board;
    private int[][] students;
    private int[] dr = {-1, 0, 1, 0};
    private int[] dc = {0, 1, 0, -1};
    private Set<Integer>[] friends;

    static class CellInfo implements Comparator<CellInfo> {
        int r, c, empty;

        CellInfo(int r, int c, int empty) {
            this.r = r;
            this.c = c;
            this.empty = empty;
        }

        @Override
        public int compare(CellInfo o1, CellInfo o2) {
            if(o1.empty == o2.empty) {
                if(o1.r == o2.r) {
                    return o1.c - o2.c;
                }
                return o1.r - o2.r;
            }
            return o1.empty - o2.empty;
        }
    }


    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        board = new int[N][N];
        students = new int[N*N][5];
        friends = new Set[N*N];

        for(int[] row : board) {
            Arrays.fill(row, -1);
        }

        for(int i = 0 ; i < N*N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            int student = Integer.parseInt(tokens[0]) - 1;
            students[i][0] = student;
            students[i][1] = Integer.parseInt(tokens[1]) - 1;
            students[i][2] = Integer.parseInt(tokens[2]) - 1;
            students[i][3] = Integer.parseInt(tokens[3]) - 1;
            students[i][4] = Integer.parseInt(tokens[4]) - 1;

            friends[student] = new HashSet<>();
            friends[student].add(students[i][1]);
            friends[student].add(students[i][2]);
            friends[student].add(students[i][3]);
            friends[student].add(students[i][4]);
        }
    }

    private int getDist(int r0, int c0, int r1, int c1) {
        return Math.abs(r0 - r1) + Math.abs(c0 - c1);
    }

    private boolean isInRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    public void run () {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < students.length ; i++) {
            int stuIdx = students[i][0];
            int friendsCount = Integer.MIN_VALUE;
            int emptyCount = Integer.MIN_VALUE;
            int targetR=0, targetC=0;

            for(int r = 0 ; r < N ; r++) {
                for(int c = 0 ; c < N ; c++) {
                    // 현재 탐색 지점에 이미 번호가 할당 된 경우 패스
                    if(board[r][c] != -1) {
                        continue;
                    }

                    // 현재 탐색 지점에서 인접한 친구 수를 체크한다.
                    int curFriendsCount = 0;
                    int curEmptyCount = 0;

                    for(int d = 0 ; d < 4 ; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];

                        if(!isInRange(nr, nc)) { continue; }

                        if(friends[stuIdx].contains(board[nr][nc])) {
                            curFriendsCount++;
                        } else if(board[nr][nc] == -1) {
                            curEmptyCount++;
                        }
                    }

                    if(curFriendsCount > friendsCount) {
                        friendsCount = curFriendsCount;
                        emptyCount = curEmptyCount;
                        targetR = r;
                        targetC = c;
                    } else if(curFriendsCount == friendsCount) {
                        if(curEmptyCount > emptyCount) {
                            emptyCount = curEmptyCount;
                            targetR = r;
                            targetC = c;
                        } else if(curEmptyCount == emptyCount) {
                            if(r < targetR) {
                                targetR = r;
                                targetC = c;
                            } else if(r == targetR) {
                                if(c < targetC) {
                                    targetC = c;
                                }
                            }
                        }
                    }
                }
            }

            board[targetR][targetC] = stuIdx;
        }

        int score = 0;

        for(int r = 0 ; r < N ; r++) {
            for(int c = 0 ; c < N ; c++) {
                int stuIdx = board[r][c];
                int friendsCount = 0;

                for(int d = 0 ; d < 4 ; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if(!isInRange(nr, nc)) { continue; }

                    if(friends[stuIdx].contains(board[nr][nc])) {
                        friendsCount++;
                    }
                }

                if(friendsCount == 1) {
                    score += 1;
                } else if(friendsCount == 2) {
                    score += 10;
                } else if(friendsCount == 3) {
                    score += 100;
                } else if(friendsCount == 4) {
                    score += 1000;
                }
            }
        }

        sb.append(score);
        System.out.println(sb.toString());
    }
}
