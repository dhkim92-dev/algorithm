const WHITE: u32 = 0;
const BLACK: u32 = 1;

fn is_in_range(r: u32, c: u32, R1: u32, R2: u32, C1: u32, C2: u32) -> bool {
    r >= R1 && r <= R2 && c >= C1 && c <= C2
}

fn is_in_mid_range(r: u32, c: u32, N: u32, K: u32) -> bool {
    let start = (N - K) / 2;
    let end = start + K - 1;
    r >= start && r <= end && c >= start && c <= end
}

fn explore_cells(
    depth: u32,
    r: u32,
    c: u32,
    color: u32,
    s: u32,
    N: u32,
    K: u32,
    R1: u32,
    R2: u32,
    C1: u32,
    C2: u32,
    result: &mut Vec<Vec<u8>>,
) {
    if depth > s {
        return;
    }

    // 백트랙킹, 현재 depth의 r,c 가 depth s 에서 어떤 영역까지 분할되는 셀인지 확인 한 뒤, 
    // R1,R2,C1,C2 범위와 겹치지 않으면 탐색하지 않음
    if depth < s {
        let size = N.pow(s - depth);
        let min_r = r * size;
        let max_r = (r + 1) * size - 1;
        let min_c = c * size;
        let max_c = (c + 1) * size - 1;
        if max_r < R1 || min_r > R2 || max_c < C1 || min_c > C2 {
            return;
        }
    }

    if depth == s && is_in_range(r, c, R1, R2, C1, C2) {
        let rr = (r - R1) as usize;
        let cc = (c - C1) as usize;
        result[rr][cc] = if color == BLACK { 1 } else { 0 };
    }

    let r_offset = N * r;
    let c_offset = N * c;
    for i in 0u32..N {
        for j in 0u32..N {
            let nr = r_offset + i;
            let nc = c_offset + j;
            if is_in_mid_range(i, j, N, K) {
                explore_cells(
                    depth + 1,
                    nr,
                    nc,
                    BLACK,
                    s,
                    N,
                    K,
                    R1,
                    R2,
                    C1,
                    C2,
                    result,
                );
            } else {
                if color == BLACK {
                    explore_cells(
                        depth + 1,
                        nr,
                        nc,
                        BLACK,
                        s,
                        N,
                        K,
                        R1,
                        R2,
                        C1,
                        C2,
                        result,
                    );
                } else {
                    explore_cells(
                        depth + 1,
                        nr,
                        nc,
                        WHITE,
                        s,
                        N,
                        K,
                        R1,
                        R2,
                        C1,
                        C2,
                        result,
                    );
                }
            }
        }
    }
}

fn solve() {
    let inputs = io::read_line();
    let mut it = inputs.split_whitespace();
    let s: u32 = it.next().unwrap().parse::<u32>().unwrap();
    let N: u32 = it.next().unwrap().parse::<u32>().unwrap();
    let K: u32 = it.next().unwrap().parse::<u32>().unwrap();
    let R1: u32 = it.next().unwrap().parse::<u32>().unwrap();
    let R2: u32 = it.next().unwrap().parse::<u32>().unwrap();
    let C1: u32 = it.next().unwrap().parse::<u32>().unwrap();
    let C2: u32 = it.next().unwrap().parse::<u32>().unwrap();

    let rows = (R2 - R1 + 1) as usize;
    let cols = (C2 - C1 + 1) as usize;
    let mut result = vec![vec![0u8; cols]; rows];

    explore_cells(
        0u32,
        0u32,
        0u32,
        WHITE,
        s,
        N,
        K,
        R1,
        R2,
        C1,
        C2,
        &mut result,
    );

    for r in 0..rows {
        for c in 0..cols {
            print!("{}", if result[r][c] == 1 { '1' } else { '0' });
        }
        println!();
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

