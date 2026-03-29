fn dfs(
    idx: usize,
    mut mask: usize,
    cost: u32,
    a: &Vec<Vec<u32>>,
    dp: &mut Vec<Vec<u32>>,
) -> u32 {
    // 이미 방문한 집합이라면 패스 
    mask = mask | (1 << idx);
    // println!("idx: {}, mask: {:b}, cost: {}", idx, mask, cost);

    if dp[idx][mask] != 0 {
        return dp[idx][mask];
    }

    for nxt in 0..a.len() {
        if ((mask & (1 << nxt)) != 0) || (a[idx][nxt] < cost) {continue;}
        dp[idx][mask] = dp[idx][mask].max(dfs(nxt, mask, a[idx][nxt], a, dp) + 1);
    }

    dp[idx][mask]
}

fn solve() {
    let line = io::read_line();
    // 그림을 팔 때 산 가격보다 크거나 같은 가격으로 팔아야함. 
    // 같은 그림을 두번 이상 살 수 없음
    // 첫줄 예술가의 수 N 
    // 이후 N줄에 걸쳐 ijk 형태로 한자리 숫자가 세개 주어짐. (i번째 예술가가 j번째 예술가의 그림을
    // k원에 사며, k는 0을 포함한 9 이하의 양의정수이다.)
    // 1번 아티스트가 0원에 그림을 구매했을 때, 그림을 소유했던 예술가들의 수 최대값을
    // 구해야한다.
    // N <= 15, bitmasking 이용 가능할듯 
    // 각 예술가마다 그림을 구매할 수 있는 예술가들의 집합을 bitmask로 표현하자 
    //
    let mut it = line.split_whitespace();
    let n = it.next().unwrap().parse::<usize>().unwrap();
    let mut a = vec![vec![0u32; n]; n];

    for i in 0..n {
        let line = io::read_line();
        // let mut it = line.split_whitespace();
        for j in 0..n {
            a[i][j] = line.chars().nth(j).unwrap().to_digit(10).unwrap();
        }
    }

    let mut dp = vec![ vec![0u32; 1 << n]; n];
    // dp[1] = 1;
    dfs(0, 0, 0, &a, &mut dp);
    
    let mut ans = 0;
    for i in 0..(1 << (n)) {
        for j in 0..n {
            ans = ans.max(dp[j][i]);
        }
    }

    println!("{}", ans+1);
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

