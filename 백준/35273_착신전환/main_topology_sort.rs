// const UNVISITED: u8 = 0;
// const VISITING: u8 = 1;
// const VISITED: u8 = 2;

fn solve() {
    let N = io::read_line()
            .trim()
            .parse::<usize>()
            .unwrap();

    let mut bypass = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<usize>().unwrap())
        .collect::<Vec<usize>>();


    // let mut state = vec![UNVISITED; N];
    let mut answer = vec![0; N];
    let mut indegree = vec![0usize; N];

    for i in 0..N {
        bypass[i]-=1;
        indegree[bypass[i]] += 1;
    }

    let mut topology_sort: Vec<_> = (0..N).filter(|&i| indegree[i] == 0).collect();
    let mut cursor = 0usize;

    // bfs
    while let Some(&node) = topology_sort.get(cursor) {
        cursor += 1;
        let nxt = bypass[node];
        indegree[nxt] -= 1;
        if indegree[nxt] == 0 {
            topology_sort.push(nxt);
        }
    }

    // indegree[i] != 0 이면 사이클이 존재 
    // 사이클이 존재하는 노드들을 먼저 처리해서 answer를 입력한다.
    for mut i in 0..N {
        if indegree[i] == 0 {
            continue;
        }

        let mut tmp = Vec::new();

        loop {
            tmp.push(i);
            indegree[i] = 0;
            let nxt = bypass[i];

            if indegree[nxt] == 0 {
                break;
            }

            i = nxt; // note. i를 여기서 변경해도 외부 루프에는 영향이 없다. rust 특징 
         }

        // 현재 사이클을 역순 탐색하여 answer에 입력 
        for (&prev, &next) in tmp.iter().zip(tmp.iter().cycle().skip(1)) {
            answer[next] = prev;
        }
    }

    // 사이클 처리는 끝났고, topology_sort에 있는 노드들은 사이클이 없는 노드들이다.

    for &node in topology_sort.iter().rev() {
        answer[node] = answer[bypass[node]];    
    }

    for i in 0..N {
        print!("{} ", answer[i]+1);
    }
}

fn main() {
    solve();
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
