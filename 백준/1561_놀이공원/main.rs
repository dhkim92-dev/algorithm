trait Solution {
    fn run(&mut self);
}

struct Problem {
    n: i64,
    m: i64,
    arr: Vec<i64>,
}

impl Problem {
    fn new() -> Self {
        let lines = io::read_line()
            .split_whitespace()
            .map(|x| x.parse::<i64>().unwrap())
            .collect::<Vec<i64>>();

        let arr = io::read_line()
            .split_whitespace()
            .map(|x| x.parse::<i64>().unwrap())
            .collect::<Vec<i64>>();

        Self {
            n: lines[0],
            m: lines[1],
            arr: arr,
        }
    }

    fn get_child_count(&self, time: i64) -> i64 {
        let mut count = self.m;
        for &x in &self.arr {
            count += time / x;
        }
        count
    }
}

impl Solution for Problem {
    fn run(&mut self) {
        // 최대 운행 가능 시간
        // 2,000,000,000 x 10,000 x 30 = 6,000,000,000,000,000
        let mut lo = -1;
        let mut hi = self.n * 30 + 1;

        if self.n <= self.m {
            println!("{}", self.n);
            return;
        }

        while lo + 1 < hi {
            let mid = lo + (hi - lo) / 2;
            let mut total = self.get_child_count(mid);

            if total >= self.n {
                hi = mid;
            } else {
                lo = mid;
            }
        }

        // let mut child = self.m;
        let mut child = self.get_child_count(hi - 1);
        // println!("{} 시간 까지 처리량 : {} ", hi - 1, child);

        for i in 0..self.m {
            if hi % self.arr[i as usize] == 0 {
                child += 1;
            }

            if child == self.n {
                println!("{}", i + 1);
                break;
            }
        }
    }
}

fn main() {
    let mut solution = Problem::new();
    solution.run();
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
