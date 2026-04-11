fn convert_to_nary(mut num: usize, base: usize) -> Vec<usize> {
    let mut digits = Vec::new();

    while num > 0 {
        digits.push(num % base);
        num /= base;
    }

    digits.reverse();
    digits
}

fn convert_to_decimal(digits: &Vec<usize>, base: usize) -> usize {
    let mut num = 0;

    for d in digits {
        num = num * base + d;
    }

    num
}

fn count_nm1(mut x: usize, n: usize) -> usize {
    let mut cnt = 0;
    while x > 0 {
        if x % n == n - 1 {
            cnt += 1;
        }
        x /= n;
    }
    cnt
}

fn solve() {
    let mut T: usize = io::read_line().parse().unwrap();

    while T > 0 {
        let s = io::read_line();
        let mut iter = s.split_whitespace();

        let (L, R, N) = (
            iter.next().unwrap().parse::<usize>().unwrap(),
            iter.next().unwrap().parse::<usize>().unwrap(),
            iter.next().unwrap().parse::<usize>().unwrap(),
        );

        let mut best_value = R;
        let mut best_count = count_nm1(R, N);

        let digits = convert_to_nary(R, N);
        let len = digits.len();

        for i in 0..len {
            if digits[i] == 0 {
                continue;
            }

            let mut value = 0;

            // prefix
            for j in 0..i {
                value = value * N + digits[j];
            }

            // 한 자리 감소
            value = value * N + (digits[i] - 1);

            // 뒤를 N-1로 채움
            for _ in i + 1..len {
                value = value * N + (N - 1);
            }

            if value < L {
                continue;
            }

            let cnt = count_nm1(value, N);

            if cnt > best_count || (cnt == best_count && value > best_value) {
                best_count = cnt;
                best_value = value;
            }
        }

        println!("{}", best_value);

        T -= 1;
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
