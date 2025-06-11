struct Problem {
    n: i64,
    m: i64,
    arr: Vec<i64>,
}

impl Problem {
    pub fn calculate_throughput(&self, mid: i64) -> i64 {
        let mut total: i64 = 0;
        for t in self.arr.iter() {
            total = total + (mid / t);
            if total > self.m {
                break;
            }
        }
        return total;
    }
}

impl Solution for Problem {
    fn new() -> Self {
        let tokens: Vec<String> = IO::read_line()
            .trim()
            .split_whitespace()
            .map(|s| s.to_string())
            .collect();

        let n: i64 = tokens[0].parse().unwrap();
        let m: i64 = tokens[1].parse().unwrap();

        let mut arr: Vec<i64> = vec![0; n as usize];

        for i in 0..n as usize {
            let num: i64 = IO::read_line().trim().parse().unwrap();
            arr[i] = num;
        }

        Self {
            n: n,
            m: m,
            arr: arr,
        }
    }

    fn run(&mut self) {
        let mut lo = -1;
        let mut hi = 1000000000;
        hi *= hi;
        hi += 1;
        let mut mid = 0;
        let request = self.m;
        self.arr.sort();

        while (lo + 1 < hi) {
            mid = lo + (hi - lo) / 2;
            let throughput = self.calculate_throughput(mid);

            if throughput < request {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        println!("{}", hi);
    }
}

fn main() {
    let mut solution = Problem::new();
    //solution.print_board();
    solution.run();
}

mod IO {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }

    pub fn read_copy_line(buf: &mut String) {
        std::io::stdin().read_line(buf).unwrap();
    }
}
