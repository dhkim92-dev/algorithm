const PONIX_WIN: &str = "PONIX";
const DALGU_WIN: &str = "DALGU";

fn solve() {
    let n: usize = io::read_line().parse().unwrap();
    let b = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<i32>().unwrap())
        .collect::<Vec<i32>>();

    // 기본 전략 
    // b[i] != b[N - i - 1] 인 경우의 수 세기(asym)
    // b[i] == bN - i - 1] == 0 인 경우의 수 세기 (zero_paired)
    let mut asym = 0;
    let mut zero_paired = 0;
    let mut empty_count = b.iter().filter(|&&x| x == 0).count();
    let mut is_even = n % 2 == 0;
    let mut is_mid_zero = false;

    if !is_even {
        let mid = n / 2;
        is_mid_zero = b[mid] == 0;
    }

    for i in 0..(n / 2) {
        let left = b[i];
        let right = b[n - i - 1];
        if left != right {
            asym += 1;
        } else if left == right && left == 0 {
            zero_paired += 1;
        }
    }

    if empty_count <= 2 {
        println!("{}", DALGU_WIN);
        return;
    }

    let mut ponix_wins = false;

    if is_even || ( !is_even && !is_mid_zero ) {
        if asym > 2 {
            // 비대칭 페어가 3개 이상이면 PONIX가 한개를 없애도 DALGU가 비대칭 페어를 0으로 만들 수
            // 없으므로 PONIX가 승리한다.
            ponix_wins = true;
        } else if asym <= 2 && zero_paired == 0 {
            // 비대칭 페어가 2개 이하이면서 0으로 짝지어진 페어가 없다면, PONIX가 한수를 두면
            // asym은 1이하가 되고 DALGU 턴에 asym은 0이 되거나 이미 대칭인 상태이므로 DALGU가 승리한다.
            ponix_wins = false;
        } else if asym == 0 && zero_paired > 0 {
            // 0으로 이루어진 페어만 존재한다면, PONIX가 한 수를 둘 때마다 asym은 1이 되지만,
            // PONIX가 고른 위치의 페어를 DALGU가 선택하면 계속 대칭이므로 달구가 승리한다.
            ponix_wins = false;
        } else if asym > 0 && zero_paired > 0 {
            // 비대칭 페어가 존재하는데 0으로 짝지어진 페어도 존재하면, PONIX가 zero_paired에
            // 해당하는 칸에 돌을 두면 asym이 1이 증가하여 asym > 2가 되어 PONIX가 승리한다.
            ponix_wins = true;
        }
    } else {
        // 홀수 이면서 중앙이 0인 경우
        if asym >= 2 { 
            // asym이 2 이상이면서 중앙이 0이라면 PONIX가 중앙을 두면 asym이 3이 되어 PONIX 승리
            ponix_wins = true;
        } else if asym >= 1 && zero_paired > 0 {
            // asym이 1 이상이면서 zero_paired가 존재한다면 PONIX가 zero_paired에 해당하는 칸에
            // 돌을 두면 asym이 2 이상이 되어 PONIX 승리
            ponix_wins = true;
        } else if asym == 0 && zero_paired > 0 {
            // 모두 0으로 짝지어진 페어만 남아있다면, 현재 상태가 대칭이다. PONIX가 중앙에 두면
            // zero_paired만 남고 DALGU 턴에 무조건 zero_paired를 하나 선택하여 asym = 이 되며
            // PONIX 승리 
            ponix_wins = true;
        } 
        // 나머지 경우는 엣지케이스 사전 처리에서 걸러짐.
    }

    if ponix_wins {
        println!("{}", PONIX_WIN);
    } else {
        println!("{}", DALGU_WIN);
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

