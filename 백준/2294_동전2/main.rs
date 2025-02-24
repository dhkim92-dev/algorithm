use std::collections::BinaryHeap;
use std::cmp::{Ordering};

trait Solution {
    fn new() -> Self;
    fn run(&mut self);
}

struct Problem {
    n: usize,
    k: i32,
    costs: Vec<i32>
}

impl Solution for Problem {

    fn new() -> Self {
        let arr: Vec<i32> = IO::read_line()
            .split_whitespace()
            .map(|x| (x.parse().unwrap()))
            .collect();
        let n: usize = arr[0] as usize;
        let k = arr[1];

        let mut costs: Vec<i32> = vec![0; n];

        for i in 0..n {
            costs[i] = IO::read_line()
                .parse()
                .unwrap();
        }

        Self {
            n: n,
            k: k,
            costs: costs
        }
    }

    fn run(&mut self) {
        let limit: usize = 10001;
        let mut dp: [i32; 10001] = [i32::MAX; 10001];

        for i in 0..self.costs.len() {
            if self.costs[i] < limit as i32 {
                dp[self.costs[i] as usize] = 1;
            }
        }

        for i in 0..limit {
            if dp[i] == i32::MAX { continue; }

            for j in 0..self.costs.len() {
                let cur_idx = i + self.costs[j] as usize;
                if cur_idx >= limit { continue; }
                dp[cur_idx] = std::cmp::min(dp[cur_idx], dp[i] + 1);
            }
        }

        if dp[self.k as usize] == i32::MAX {
            println!("{}", -1);
        } else {
            println!("{}", dp[self.k as usize]);
        }
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
