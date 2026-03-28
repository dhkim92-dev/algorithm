fn solve() {
    let line = io::read_line();
    let mut it = line.split_whitespace();
    let n: usize = it.next().unwrap().parse().unwrap();
    let m: u64 = it.next().unwrap().parse().unwrap();
    let c: usize = it.next().unwrap().parse().unwrap();

    let mut t = vec![0u64; n + 1];
    let mut f = vec![0u64; n + 1];
    for i in 1..=n {
        let line = io::read_line();
        let mut it = line.split_whitespace();
        t[i] = it.next().unwrap().parse().unwrap();
        f[i] = it.next().unwrap().parse().unwrap();
    }

    // dp[i] = i번째 학생까지 숙소로 옮긴 뒤 엘리베이터가 1층에 돌아오는 가장 빠른 시각
    // dp[0] = 0 (아무도 배달하지 않은 초기 상태)
    let mut dp = vec![u64::MAX; n + 1];
    dp[0] = 0;

    //  학생 (j+1..=i)를 한 그룹으로 태움 (i - j <= C)
    //   출발 시각 = max(dp[j], t[i])   
    //   max_floor = max(f[j+1..=i])
    //   왕복 비용 = 2 * (max_floor - 1)
    for i in 1..=n {
        let lo = if i >= c {i-c} else {0};
        let mut max_floor = 0u64;

        for j in (lo..i).rev() {
            max_floor = max_floor.max(f[j+1]);

            if dp[j] == u64::MAX {
                continue;
            }

            let start = dp[j].max(t[i]);
            let finish = start + 2 * (max_floor - 1);
            if finish < dp[i] {
                dp[i] = finish;
            }
        }
    }

    let mut ans = u64::MAX;
    // 마지막 그룹 편도거리 계산
    //
    let mut max_floor = 0u64;
    let mut lo = if n >= c {n-c} else {0};

    for j in (lo..n).rev() {
        max_floor = max_floor.max(f[j+1]);

        if dp[j] == u64::MAX {
            continue;
        }

        let start = dp[j].max(t[n]);
        let finish = start + (max_floor - 1);
        if finish < ans {
            ans = finish;
        }
    }

    println!("{}", ans);
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

