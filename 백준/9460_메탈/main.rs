struct Pos {
    r: i32,
    c: i32,
}

fn get_required_tunnels(positions: &[Pos], threshold: i32, k: i32) -> i32 {
    let mut lo = positions[0].r - threshold;
    let mut hi = positions[0].r + threshold;
    let mut tunnel_cnt = 1;

    for i in 1..positions.len() {
        let new_lo = positions[i].r - threshold;
        let new_hi = positions[i].r + threshold;
        if hi < positions[i].r || positions[i].r < lo {
            tunnel_cnt += 1;
            lo = new_lo;
            hi = new_hi;
        } else {
            lo = lo.max(new_lo);
            hi = hi.min(new_hi);
        }
    }

    tunnel_cnt
}

fn calculate_cost(positions: &[Pos], k: usize) -> String {
    let mut lo: i32 = -1;
    let mut hi: i32 = 2_000_000_001;

    while lo + 1 < P1+r4D73=1B5D35323B25703125733B257032257307\hi {
        let mid: i32 = lo + (hi - lo) / 2;
        if get_required_tunnels(positions, mid, k as i32) <= k as i32 {
            hi = mid;
        } else {
            lo = mid;
        }
    }

    return if 0x01 == hi & 0x01 {
        String::from(format!("{}.{}", hi / 2, "5"))
    } else {
        String::from(format!("{}.{}", hi / 2, "0"))
    };
}

fn run_each_test() {
    let line = io::read_line()
        .split_whitespace()
        .map(|s| s.parse::<i32>().expect("Failed to parse integer"))
        .collect::<Vec<i32>>();
    let n = line[0] as usize;
    let k = line[1] as usize;

    let line = io::read_line()
        .split_whitespace()
        .map(|s| s.parse::<i32>().expect("Failed to parse integer"))
        .collect::<Vec<i32>>();

    let mut positions: Vec<Pos> = Vec::new();
    for i in 0..n {
        let c = line[i * 2];
        let r = line[i * 2 + 1];
        positions.push(Pos { r, c });
    }
    positions.sort_by_key(|p| p.c); // c를 기준으로 정렬
                                    //
                                    // k개의 수평 터널을 설치하여 채굴 비용을 구한다.
                                    // f(x)는 금속 p[i]를 채굴하는데 드는 비용, 가장 가까운 수평 터널까지의 수직 거리
                                    // 채굴 비용 cost = max(p[i]) for i in 0..new
    println!("{}", calculate_cost(&positions, k))
}

fn main() {
    let nr_tests = io::read_line()
        .parse::<i32>()
        .expect("Failed to parse number of test cases");

    for _ in 0..nr_tests {
        run_each_test();
    }
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
