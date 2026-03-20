fn check(N: i64, M: i64, L:i64, x: i64, A: &Vec<i64>, diff: &mut Vec<i64>) -> bool {
    diff.fill(0);
    let mut used: i64 = 0; // 사용횟수 
    let mut i: usize = 0;

    while i < N as usize {
        let curr = A[i] + diff[i];

        if curr <= 0 {
            i+=1;
            continue;
        }
        let quot: i64 = curr / x;
        let remainder: i64 = curr % x;

        if quot > 0 {
            used += quot ;
            if used > M {
                return false;
            } 
            diff[i] -= (quot * x);
        }

        if remainder == 0 {
            i+=1;
            continue;
        }

        let mut remaining: i64 = x - remainder;
        diff[i] -= remainder;
        used+=1;

        if used > M  {
            return false; 
        }
        
            // j = [i+1, ...] 에 대해 remainder 를 diff에 누적시킬것 
        let limit: usize = std::cmp::min(N as usize, i+L as usize);
        for j in i+1..limit as usize {
            let j_curr = A[j] + diff[j];

            if j_curr <= 0 {
                continue;
            }

            let removed:i64 = std::cmp::min( j_curr, remaining );
            diff[j]-=removed;
            remaining -= removed;

            if remaining == 0 {
                break;
            }
        }

        i+=1;
    }

    true
}

fn solve(N: i64, M: i64, L: i64) -> i64 {
    let A = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<i64>().unwrap())
        .collect::<Vec<i64>>();

    let mut lo: i64 = 0;
    let mut hi: i64 = A.iter().sum::<i64>() + 1 as i64;
    let mut diff: Vec<i64> = vec![0; (N+1) as usize];

    while lo + 1 < hi {
        let mid: i64 = lo + (hi - lo) / 2;
        // println!("mid : {}", mid);

        if !check(N,M,L, mid, &A, &mut diff) {
            lo = mid
        } else {
            hi = mid
        }
    }

    if check(N, M, L, hi, &A, &mut diff) { hi } else {-1}
}

fn main() {
    let tok = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<i64>().unwrap())
        .collect::<Vec<i64>>();
    // for t in tok {
        // println!("t : {}",t);
    // }
    // println!("show inputs");
    // println!("{} {} {}", tok[0], tok[1], tok[2]);
    println!("{}", solve(tok[0], tok[1], tok[2]));
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
