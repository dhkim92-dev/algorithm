
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.io.*;

public class Main {

    static void testRun(int no) throws IOException {
        Path base = Paths.get("");

        String testFileDirName = base.toAbsolutePath() + "/src/main/resources/examples/" + String.valueOf(no);

        File dir = null;
        try {
            dir = new File(testFileDirName);
        }catch(NullPointerException e) {
            return; 
        }
        File[] files = dir.listFiles();

        if(files == null) {
            return;
        }

        for(int i = 0 ; i<files.length ; i++){
            String fileName = files[i].getName();
            String fullPath = testFileDirName + "/" + fileName;
            System.out.println("Test file name : " + fullPath);
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            reader.mark(262144);
            reader.lines()
                    .forEach(System.out::println);
            reader.reset();

            System.out.println("answer ");
            new Solution(reader).run();
            reader.close();
        }
    }

    static void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Solution(reader).run();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        testRun(2234);
        //run();
    }
}

class Solution {

    public static class Pos {

        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos add(Pos o) {
            return new Pos(r + o.r, c + o.c);
        }
    }
    

    int N, M;

    // W N E N
    int[] walls = {0x01, 0x01<<1, 0x01<<2, 0x01<<3};

    Pos[] dirs = {new Pos(0, -1), new Pos(-1, 0), new Pos(0, 1), new Pos(1, 0) };

    int[][] topology;

    int[][] roomIndex;

    List<Integer> roomSize = new ArrayList<>();

    List<Set<Integer>> graph;

    public Solution(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);

        topology = new int[M][N];
        roomIndex = new int[M][N];

        for(int i = 0 ; i < M ; i++) {
            line = reader.readLine().split(" ");
            for(int j = 0  ; j < N ; j++) {
                topology[i][j] = Integer.parseInt(line[j]);
            }
        }
    }

    private boolean isInRange(Pos p) {
        return (0<=p.r && p.r < M) && (0 <= p.c && p.c < N);
    }

    private int getRoomSize(int idx,  Pos start, int[][] visited) {
        Queue<Pos> q = new LinkedList<>();
        q.add(start);
        visited[start.r][start.c] = idx;
        int area = 0;

        while(!q.isEmpty()) {
            Pos cur = q.poll();
            area++;
            
            for(int i = 0 ; i < 4 ; i++) {
                if((topology[cur.r][cur.c] & walls[i]) == walls[i]) continue; // 벽이 있음
                // 다음 영역의 범위에 대한 체크는 필요하지 않다. 어차피 벽 정보에서 걸린다.
                Pos nxt = cur.add(dirs[i]);

                if(visited[nxt.r][nxt.c] != -1) continue;
                q.add(nxt);
                visited[nxt.r][nxt.c] = idx;
            }
        }

        // System.out.println("################################");
        // for(int i = 0 ; i < M ; i++) {
        //     for(int j = 0 ; j < N ; j++) {
        //         System.out.print(roomIndex[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        return area;
    }

    private List<Set<Integer>> makeGraph() {
        graph = new ArrayList<>();
        for(int i = 0 ; i < roomSize.size()  ; i++) {
            graph.add(new HashSet<Integer>());
        }

        for(int idx = 0 ; idx < roomSize.size() ; idx++) {

            for(int i = 0 ; i < M ; i++) {
                for(int j = 0 ; j< N ; j++) {
                    if(roomIndex[i][j] != idx) continue;

                    // 4방향 체크
                    for(int k = 0 ; k < 4 ; k++) {
                        if((topology[i][j] & walls[k]) != walls[k]) continue; // 벽이 없다면 패스

                        Pos nxt = new Pos(i + dirs[k].r, j + dirs[k].c); // 인접 칸 좌표
                        if(!isInRange(nxt)) continue; // 영역 밖이라면 패스
                        int adjIdx = roomIndex[nxt.r][nxt.c]; // 인접 칸의 인덱스
                        if(adjIdx == idx) continue; // 현재 탐색중인 인덱스와 동일하면 패스

                        // System.out.println("추가");
                        graph.get(idx).add(adjIdx);
                        graph.get(adjIdx).add(idx);
                    }
                }
            }
        }

        return graph;
    }

    public void run() {

        for(int[] row : roomIndex) {
            Arrays.fill(row, -1);
        }

        int index=0;

        for(int i = 0 ; i < M ; i++) {
            for(int j = 0 ; j < N ; j++) {
                if(roomIndex[i][j] == -1) {
                    int size = getRoomSize(index, new Pos(i, j), roomIndex);
                    roomSize.add(size);
                    index++;
                }
            }
        }

        List<Set<Integer>> adj = makeGraph();

        int mergedArea = 0;

        for(int i = 0 ; i < roomSize.size() ; i++) {
            int area1 = roomSize.get(i);
            Set<Integer> adjRooms = adj.get(i);

            for(int roomIdx : adjRooms) {
                int area2 = roomSize.get(roomIdx);
                // System.out.println("merge " + i + " , " + roomIdx + " area : " + area1 + area2);
                mergedArea = Math.max(mergedArea, area1+area2);
            }
        }

        System.out.println(roomSize.size());
        System.out.println(roomSize.stream().max((a, b) -> a-b).orElse(0));
        System.out.println(mergedArea);
    }
}

