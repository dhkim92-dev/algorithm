fn solve() -> i64 {
    let mut tok = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<usize>().unwrap())
        .collect::<Vec<usize>>();
    let mut N = tok[0];
    let mut M = tok[1];

    let mut A: Vec<Vec<i32>> = vec![vec![0; M]; N];
    for i in 0..N {
        let line: Vec<i32> = io::read_line()
            .split_whitespace()
            .map(|x| x.parse::<i32>().unwrap())
            .collect();
        A[i] = line;
    }

    // 항상 N <= M 되도록
    if N > M {
        let mut B = vec![vec![0; N]; M];
        for i in 0..N {
            for j in 0..M {
                B[j][i] = A[i][j];
            }
        }
        A = B;
        std::mem::swap(&mut N, &mut M);
    }

    let mut result: i64 = 0;

    // 행 쌍 기준
    for x1 in 0..N {
        for x2 in x1 + 1..N {
            let mut freq = [0i64; 19];

            for j in 0..M {
                let s = (A[x1][j] + A[x2][j]) as usize;
                if s <= 18 {
                    result += freq[20 - s];
                    freq[s] += 1;
                }
            }
        }
    }

    result
}

fn main() {
    println!("{}", solve());
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
