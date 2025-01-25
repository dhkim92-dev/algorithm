class Solution {

    private int N, M, T;

    private int[][] rotors;

    private int[][] rotations;

    private boolean[][] visited;

    private int[] sum;

    private int[] tmp;

    static class Pos {
        int rotor;
        int offset;

        Pos(int rotor, int offset) {
            this.rotor = rotor;
            this.offset = offset;
        }

        Pos add(Pos p, int size) {
            int nextOff = this.offset + p.offset;
            nextOff = nextOff < 0 ? size + nextOff : nextOff % size;
            return new Pos(this.rotor + p.rotor, nextOff);
        }
    }

    private static final Pos[] dirs = {
        new Pos(0, 1),
        new Pos(0, -1),
        new Pos(1, 0),
        new Pos(-1, 0)
    };

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        T = Integer.parseInt(tokens[2]);

        rotors = new int[N][M];
        rotations = new int[T][3];
        visited = new boolean[N][M];
        sum = new int[N];
        tmp = new int[M];

        for(int i = 0 ; i < N ; i++) {
            tokens = reader.readLine().split(" ");
            for(int j = 0 ; j < M ; j++) {
                rotors[i][j] = Integer.parseInt(tokens[j]);
                sum[i] += rotors[i][j];
            };
        }

        for(int i = 0 ; i < T ; i++) {
            tokens = reader.readLine().split(" ");
            rotations[i][0] = Integer.parseInt(tokens[0]);
            rotations[i][1] = Integer.parseInt(tokens[1]);
            rotations[i][2] = Integer.parseInt(tokens[2]);
        }
    }

    private void rotate(int rotorIdx, int dir, int k ) {
        int[] rotor = rotors[rotorIdx];
        if(dir == 0) {
            // 시계 방향
            for(int i = 0 ; i < M ; i++) {
                int value = rotor[i];
                int next = (i + k)%M;
                tmp[next] = value;
            }
        } else {
            // 반시계 방향
            for(int i = 0 ; i < M ; i++) {
                int abs = Math.abs(k);
                abs %= M;
                int value = rotor[i];
                int next = i - abs < 0 ? M + i - abs : i - abs;
                tmp[next] = value;
            }
        }

        for(int i = 0 ; i < M ; i++) {
            rotor[i] = tmp[i];
        }
    }

    private void clearVisitedArray() {
        for(boolean[] row: visited) {
            Arrays.fill(row, false);
        }
    }

    private boolean removeClosedNumbers(Pos p, int target) {
//        System.out.println("remove query " + p.rotor + " " + p.offset + " target : " + target);
        clearVisitedArray();
        Queue<Pos> q = new LinkedList<>();
         visited[p.rotor][p.offset] = true;
        q.add(p);
        boolean erased = false;

        if(target == -1) {
            return false;
        }

        while(!q.isEmpty()) {
            Pos cur = q.poll();
//            System.out.println("cur : " + cur.rotor + " " + cur.offset);
//            if(rotors[cur.rotor][cur.offset] == -1) continue;

            for(int i = 0 ; i < 4 ; i++) {
                Pos next = cur.add(dirs[i], M);
//                System.out.println("next : " + next.rotor + " " + next.offset);
                if(!isInRange(next.rotor, next.offset)) continue;
//                System.out.println("    value: " + rotors[next.rotor][next.offset]);
                if(visited[next.rotor][next.offset]) continue;
                if(rotors[next.rotor][next.offset] != target) continue;
                if(rotors[next.rotor][next.offset] == -1) continue;
//                System.out.println("    confirmed : " + next.rotor + " " + next.offset);

                visited[next.rotor][next.offset] = true;
                q.add(next);
                rotors[next.rotor][next.offset] = -1;
                sum[next.rotor] -= target;
                erased = true;
            }
        }

        if(erased) {
            sum[p.rotor] -= target;
            rotors[p.rotor][p.offset] = -1;
        }

        return erased;
    }

    private boolean isInRange(int rotorIdx, int idx) {
        return (rotorIdx >= 0 && rotorIdx < N) && (idx >= 0 && idx < M);
    }

    private void updateRotors(int rotorIdx) {
        int cnt = 0;
        int totalSum = 0;

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                if(rotors[i][j] == -1) continue;
                totalSum += rotors[i][j];
                cnt += 1;
            }
        }

        if(cnt == 0) return;
        double avg = (double)totalSum / cnt;

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                if(rotors[i][j] == -1) continue;
                if(rotors[i][j] > avg) {
                    rotors[i][j] -= 1;
                } else if(rotors[i][j] < avg) {
                    rotors[i][j] += 1;
                }
            }
        }
    }

    private void printRotor(int targetRotor) {
//        System.out.println("rotor " + targetRotor + " : " + "\n\t");
        for(int j = 0 ; j < M ; j++) {
            System.out.print(rotors[targetRotor][j] + " ");
        }
        System.out.println();
    }

    private void printAllRotors() {
        System.out.println("Rotor status");
        for(int i = 0 ; i < N ; i++) {
            printRotor(i);
        }
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < T ; i++) {
            int rotorBase = rotations[i][0];
            int dir = rotations[i][1];
            int cnt = rotations[i][2];
//            printAllRotors();
//            System.out.println("rotorBase : " + rotorBase + " dir : " + dir + " cnt : " + cnt);

            for(int j = rotorBase ; j <= N ; j += rotorBase) {
                rotate(j-1, dir, cnt);
            }
//            printAllRotors();

            // 인접한 수를 모두 지운다.
            boolean erased = false;
            for(int rIdx = 0 ; rIdx< N ; rIdx++) {
                for(int offset = 0 ; offset < M ; offset++) {
                    if(rotors[rIdx][offset] == -1) continue;
                    Pos p = new Pos(rIdx, offset);
                    erased |= removeClosedNumbers(p, rotors[rIdx][offset]);
                }
            }
//            System.out.println("erased : " + erased);

            if(!erased) {

                updateRotors(rotorBase - 1);
            }
//            printAllRotors();
        }
//        printAllRotors();

//        int answer = Arrays.stream(sum).sum();
        int answer = 0;

        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                if(rotors[i][j] == -1) continue;
                answer += rotors[i][j];
            }
        }
        sb.append(answer);
        System.out.println(sb);
    }
}
