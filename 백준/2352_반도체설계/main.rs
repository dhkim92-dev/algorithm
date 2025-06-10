trait Solution {
    fn new() -> Self;
    fn run(&mut self);
}

struct Problem {
    n: usize,
    arr: Vec<i32>,
}

impl Problem {
    pub fn lower_bound(&self, arr: &Vec<i32>, mut lo: usize, mut hi: usize, target: i32) -> usize {
        while lo + 1 < hi {
            let mid = lo + (hi - lo) / 2;
            if arr[mid] < target {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        return hi;
    }
}

impl Solution for Problem {
    fn new() -> Self {
        let n = IO::read_line().trim().parse::<usize>().unwrap();

        let arr: Vec<i32> = IO::read_line()
            .split_whitespace()
            .map(|x| (x.parse().unwrap()))
            .collect();
        Self { n: n, arr: arr }
    }

    fn run(&mut self) {
        let mut dp: Vec<i32> = vec![0; self.n + 1];
        dp[0] = 0;
        let mut answer: usize = 0;

        for i in 0..self.arr.len() {
            if dp[answer] < self.arr[i] {
                answer = answer + 1;
                dp[answer] = self.arr[i];
            } else {
                let idx = self.lower_bound(&dp, 0, answer, self.arr[i]);
                dp[idx] = self.arr[i];
            }
        }

        println!("{}", answer);
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
