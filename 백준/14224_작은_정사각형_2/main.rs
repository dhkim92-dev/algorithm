#[derive(Clone)]
struct Pos {
    r: i64,
    c: i64,
}

impl Pos {
    fn new(r: i64, c: i64) -> Self {
        Pos { r, c }
    }
}

fn check(n: i32, k: i32, positions: &mut Vec<Pos>, length: i64) -> bool {
    // 한변의 길이가 size인 정사각형을 만들 때, 최소 K개의 점이 정사각형 내부에 있는지 확인
    // 하는 정사각형의 범위 내에 포함되는 정점의 개수를 세어 K 이상이 가능한지 확인

    for i in 0..n as usize {
        // 현재 정점의 x 좌표를 기준으로 하여, x축 범위를 정한다.
        let min_x = positions[i].c;
        let max_x = positions[i].c + length;
        for j in 0..n as usize {
            // 현재 또는 다른 정점의 y좌표를 기준으로 y축 범위를 정한다.
            let min_y = positions[j].r;
            let max_y = positions[j].r + length;
            let mut cnt = 0;

            // 해당 영역에 속하는 정점의 수를 구한다.
            let mut filtered: Vec<Pos> = Vec::new();
            for p in 0..n as usize {
                if positions[p].c >= min_x && positions[p].c <= max_x {
                    filtered.push(positions[p].clone());
                }
            }

            for pos in filtered {
                if pos.r >= min_y && pos.r <= max_y {
                    cnt += 1; // 해당 정점이 정사각형 내부에 포함됨
                }
            }

            if cnt >= k {
                return true; // K개 이상의 점이 포함된 정사각형을 찾음
            }
        }
    }
    false
}

fn solve(n: i32, k: i32, positions: &mut Vec<Pos>) -> i64 {
    let mut lo: i64 = -1;
    let mut hi: i64 = 2_000_000_002; // 10^9
    while lo + 1 < hi {
        let mid = lo + (hi - lo) / 2;
        if check(n, k, positions, mid) {
            hi = mid;
        } else {
            lo = mid;
        }
    }
    hi += 2;

    hi * hi
}

fn main() {
    let line = io::read_line()
        .split_whitespace()
        .map(|s| s.parse::<i32>().unwrap())
        .collect::<Vec<i32>>();
    let n = line[0];
    let k = line[1];

    let mut positions: Vec<Pos> = Vec::new();

    for _ in 0..n {
        let line = io::read_line();
        let parts: Vec<&str> = line.split_whitespace().collect();
        let r = parts[0].parse::<i64>().unwrap();
        let c = parts[1].parse::<i64>().unwrap();
        positions.push(Pos { r, c });
    }

    positions.sort_by(|a, b| a.c.cmp(&b.c).then(a.r.cmp(&b.r)));

    println!("{}", solve(n, k, &mut positions));
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
