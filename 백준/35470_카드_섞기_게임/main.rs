fn solve() {
    // 첫 줄: N과 K
    let mut inputs = io::read_line();
    let mut it = inputs.split_whitespace();
    let n = it.next().unwrap().parse::<usize>().unwrap();
    let k = it.next().unwrap().parse::<usize>().unwrap();

    // 둘째 줄: 카드에 적힌 알파벳들
    let mut chars = io::read_line().chars().collect::<Vec<char>>();

    // 셋째 줄: 섞기 규칙(1-based 입력을 0-based로 변환)
    let orders = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<usize>().unwrap() - 1)
        .collect::<Vec<usize>>();
    let mut visited = vec![false; n];
    let mut final_pos = vec![0usize; n]; // final_pos[i] = i가 K번 섞인 뒤 위치하는 index

    for i in 0..n {
        if visited[i] {
            continue;
        }

        // 하나의 사이클을 추적하여 cycle 벡터에 저장
        let mut cycle = Vec::new();
        let mut cur = i;
        while !visited[cur] {
            visited[cur] = true;
            cycle.push(cur);
            cur = orders[cur];
        }

        let m = cycle.len();
        if m == 0 { continue; }
        let r = k % m; // 이동량

        // cycle 내의 각 노드에 대해 K번 적용한 결과 위치를 기록
        for (idx, &node) in cycle.iter().enumerate() {
            final_pos[node] = cycle[(idx + r) % m];
        }
    }

    // final_pos를 (final_pos, original_index) 쌍으로 모아 final_pos 기준으로 정렬
    let mut pairs = (0..n).map(|i| (final_pos[i], i)).collect::<Vec<(usize, usize)>>();
    pairs.sort_by_key(|&(fp, _)| fp);

    // 문자들을 오름차순으로 정렬한 뒤, final_pos가 작은 순서대로 배치
    chars.sort_unstable();
    let mut result = vec![' '; n];
    for (i, &(_fp, init_idx)) in pairs.iter().enumerate() {
        result[init_idx] = chars[i];
    }

    for ch in result {
        print!("{}", ch);
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

