
class Solution {

    private int G, P;

    private int[] gi;

    private int[] parents;

    public Solution(BufferedReader reader) throws IOException {
        G = Integer.parseInt(reader.readLine());
        P = Integer.parseInt(reader.readLine());

        gi = new int[P+1];
        parents = new int[G + 1];

        for(int i = 0 ; i <= G ; i++) {
            parents[i] = i;
        }

        for(int i = 1 ; i <= P ; i++) {
            gi[i] = Integer.parseInt(reader.readLine());
        }
    }

    private int find(int x) {
        if(parents[x] == x) {
            return x;
        }
        return parents[x] = find(parents[x]);
    }

    private void union(int x, int y) {
        x = find(x);
        y = find(y);

        if(x < y) {
            parents[y] = x;
        } else {
            parents[x] = y;
        }
    }

    public void run() {
        int answer = 0;
        StringBuilder sb = new StringBuilder();

        for(int i = 1 ; i <= P ; i++) {
            // 주어진 gi[i] 에 대해 가장 큰 번호의 게이트부터 할당한다.
            // 게이트가 할당되면 해당 게이트의 부모를 좌측 게이트와 합친다.
            int gIdx = gi[i];
            int parent = find(gIdx);

            if(parent == 0) {
                break;
            }
            union(parent, parent - 1);
            answer++;
        }
        sb.append(answer);
        System.out.println(sb);
    }
}
