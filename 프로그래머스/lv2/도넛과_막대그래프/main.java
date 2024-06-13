import java.util.*;

class Solution {
    
    private int findMaxNodeIndex(int [][] edges) {
        int index = -1;
        for(int[] e : edges) {
            index = Math.max(index, e[1]);
            index = Math.max(index, e[0]);
        }
        
        return index;
    }
    
    private void makeGraph(int[][] edges, int[] income, Map<Integer, List<Integer>> adj, Set<Integer> exists) {
        // dir == 0 from, dir==1 to
        int[] answer = new int[4];
        Arrays.fill(income, 0);
        
        for(int[] e : edges) {
            int from = e[0];
            int to = e[1];
            income[to]++;
            exists.add(from);
            exists.add(to);
            if(!adj.containsKey(Integer.valueOf(from))) {
                adj.put(Integer.valueOf(from), new ArrayList<>());
            }
            adj.get(Integer.valueOf(from)).add(Integer.valueOf(to));
        }
    }
    
    void dfs(Map<Integer, List<Integer>> g, int[] visited, int cur, int limit) {
        if(visited[cur] >= limit) {
            return ; // 방문 횟수 제한과 동일하다면 탐색이 완료된 것이다.
        }
        
        visited[cur]++;
        
        if(!g.containsKey(Integer.valueOf(cur))) {
            return;
        }
        List<Integer> nexts = g.get(Integer.valueOf(cur));
        
        for(int i : nexts) {
            if(visited[cur] >= limit) continue;
            dfs(g, visited, i, limit);
        }
    }
    
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        int maxIdx = findMaxNodeIndex(edges);
        int[] income = new int[maxIdx+1];
        int[] outcome = new int[maxIdx+1];
        Set<Integer> existEdges = new HashSet<>();
        Map<Integer, List<Integer>> adj = new HashMap<>();
        
        makeGraph(edges, income, adj, existEdges);
        
        for(Integer k : adj.keySet()) {
            outcome[k] = adj.get(k).size();
        }

        int fakeNodeIdx=0;
        List<Integer> stickCandidates = new ArrayList<>();
        List<Integer> eightCandidates = new ArrayList<>();
        
        int stickCount = 0;
        int donutCount = 0;
        int eightCount = 0 ;
        
        for(int i = 1 ; i <= maxIdx ; i++) {
            // 들어오는 간선 없고, 나가는 간선 2개 이상이면 fake node
            if( income[i] == 0 && outcome[i] >= 2 ) {
                fakeNodeIdx = i;
                continue;
            }
        }
        
        // 추가된 간선에 의한 income 간선 제거한다
        
        for(int [] e : edges) {
            int from = e[0];
            int to = e[1];
            
            if(from == fakeNodeIdx) {
                income[to]--;
            }
        }
        
        
        for(int i = 1 ; i <= maxIdx ; i++) {
            if(i == fakeNodeIdx) continue;
            //System.out.printf("Node : %d income : %d outcome : %d\n", i, income[i], outcome[i]);
            // 들어오는 간선 2개이고, 나가는 간선 2개면 8자형에 속한 노드이다.
            
            if(  income[i] == 2 && outcome[i] == 2) {
                //System.out.println("eight");
                eightCandidates.add(i);
                continue;
            } else if(existEdges.contains(i) && outcome[i] <= 1 && income[i] == 0  ){
                // 나가는 노드가 한개 이하로 있고 들어오는 노드가 없으면 스틱형 노드이다.
                //System.out.println("stick");
                stickCandidates.add(i);
            } 
        }
        
       
        int[] visited = new int[maxIdx+1];
        Arrays.fill(visited, 0);
        stickCount = stickCandidates.size(); // 스틱형은 루트의 갯수가 곧 스틱형의 개수이다.
        
        // 8자형 탐색
        for(int i : eightCandidates) {
            if(visited[i] > 0) {
                continue;
            }
            eightCount++;
            dfs(adj, visited, i, 2);
        }
        
        // 8자형과 스틱형 개수를 구했다면
        // 도넛형은  추가된 노드에서 나가는 간선 수 - 스틱형 + 8자형
        donutCount = adj.get(fakeNodeIdx).size() - (eightCount + stickCount);

        answer[0] = fakeNodeIdx;
        answer[1] = donutCount;
        answer[2] = stickCount;
        answer[3] = eightCount;

        return answer;
    }
}
