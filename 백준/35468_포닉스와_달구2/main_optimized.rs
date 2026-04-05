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
    let mut ponix_wins = false;

    if empty_count < 3 {
        // 비어있는 칸이 3개 미만이면 무조건 달구가 이긴다. 
        ponix_wins = false;
    } else {
        for i in 0..(n / 2) {
            if b[i] != b[n - i - 1] {
                asym += 1;
            } else if b[i] == 0 {
                zero_paired += 1;
            }
        }

        if !is_even && b[n / 2] == 0 {
            is_mid_zero = true;
        }

        // 비어있는 칸은 최소 3칸 이상이 보장되는 상황이고, 이 상황에서 asym 이 1 이상이면 무조건
        // 포닉스가 이긴다. (최소 1개 이상의 asym이 존재하고 zero_paired 도 존재한다는 말인데,
        // 포닉스가 어떻게든 asym을 2개 이상으로 유지할 수 있기 때문) 
        if asym > 0 {
            ponix_wins = true;
        } else {
            // 현재 대칭 상태인 경우, 중앙이 0이면 PONIX가 이긴다. 포닉스가 중앙을 1로 바꾸면 달구는
            // 무조건 비대칭을 만들 수 밖에 없다. 
            // 반면 중앙이 1이면 달구가 이긴다. 

            if is_mid_zero {
                ponix_wins = true;
            } else {
                ponix_wins = false;
            }
        }
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

