import java.io.*;
import java.util.*;

class Solution {

    private int N, M;

    private static final int MUST_BE_SET = 1;

    private static final int MUST_NOT_BE_SET = 0;

    private static final int UNSET = -1;

    private List<Integer>[] adj;

    private int[] color;

    private boolean valid = true;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().trim().split(" ");
        N = Integer.parseInt(tokens[0]);
        M = Integer.parseInt(tokens[1]);
        adj = new ArrayList[N];
        color = new int[N];
        for ( int i = 0; i < N; i++) {
            adj[i] = new ArrayList<>();
            color[i] = UNSET;
        }

        for (int i = 0; i < M; i++) {
            String[] line = reader.readLine().trim().split(" ");
            int u = Integer.parseInt(line[0]) - 1;
            int v = Integer.parseInt(line[1]) - 1;
            int c = Integer.parseInt(line[2]);

            if ( c == 1 ) {
                adj[u].add(v);
                adj[v].add(u);
            }else {
                int ci = c == 2 ? MUST_BE_SET : MUST_NOT_BE_SET;
                valid &= marking(u, ci);
                valid &= marking(v, ci);
            }
        }
    }

    private boolean marking(int node, int c) {
        if ( color[node] != UNSET ) {
            return color[node] == c;
        }
        color[node] = c;
        return true;
    }

    // node 의 색을 확정하고,
    // 재귀적으로 해당 노드와 연결된 노드들의 색을 확정한다.
    // 이 때 색상이 모순되는 경우 false 를 반환한다.
    private boolean find(int node, int c, int[] cnt) {
        cnt[c]++;
        marking(node, c);

        for ( int next : adj[node] ) {
            if ( color[next] == UNSET ) {
                if ( !find(next, 1 - c, cnt) ) {
                    return false; // 만약 다음 노드에서 문제가 발생하면 false 반환
                }
            } else if ( color[next] == c ) {
                // 다음 노드가 현재 노드와 같은 색인 경우
                return false; // 모순 발생
            }
        }

        return true;
    }

    private String simulate() throws IOException {
        StringBuilder sb = new StringBuilder();
        int answer = 0;
        int[] cnt = new int[2];

        // 0. 전처리 단계에서 양쪽 모두 라운지가 설치 되어야하는 경우와 양쪽 모두 설치되면 안되는 경우에 대한
        // 색칠이 완료되어있다. 여기서 모순이 발견되었다면 이미 valid 는 false
        // 따라서 그래프에 속한 엣지들은 둘 중 하나에만 라운지가 설치되어야만 하는 경우다.

        // 1. 라운지 설치 여부가 확정 된 노드에 연결된 노드들에 대하여,
        //    해당 노드가 가지고 있는 엣지 정보를 참조해
        //    라운지 설치 상태를 업데이트 한다.
        for ( int i = 0 ; i < N && valid ; ++i ) {
            if ( color[i] != UNSET ) {
                valid &= find(i, color[i], cnt);
            }
        }

        // 2. 여기까지 진행된 상태에서 라운지 개수를 우선 구한다.
        for ( int i = 0 ; i < N && valid ; ++i ) {
            if ( color[i] == MUST_BE_SET ) {
                answer++;
            }
        }

        // 3. 아직 라운지 설치 여부가 확정되지 않은 노드가 존재하는 경우
        // 해당 노드가 속한 그래프는 모든 노드가 UNSET 상태이다.
        // 이 그래프는 설치하는 경우와 설치하지 않는 경우가 초기 입력값에 따라 반전될 뿐이므로
        // 라운지가 설치된 노드 수와 라운지가 설치되지 않은 노드 수 중 최소값을 취하면 된다.
        for ( int i = 0 ; i < N && valid ; ++i ) {
            if ( color[i] == UNSET ) {
                cnt[0] = 0;
                cnt[1] = 0;
                if ( !find(i, 0, cnt) ) {
                    valid = false;
                    break;
                }
                answer += Math.min(cnt[0], cnt[1]);
            }
        }

        if ( !valid ) {
            sb.append("impossible");
            return sb.toString();
        }

        sb.append(answer);

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
