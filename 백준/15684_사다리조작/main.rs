trait Solution {
    fn new()->Self ;
    fn run(&mut self);
}

struct Problem {
    n: usize,
    m: usize,
    h: usize,
    visited: Vec<Vec<bool>>,
}

impl Solution for Problem {

    fn new() -> Self {
        let tokens: Vec<usize> = IO::read_line()
            .split_whitespace()
            .map(|x| x.parse().unwrap())
            .collect();

        let n = tokens[0];
        let m = tokens[1];
        let h = tokens[2];

        let mut visited = vec![vec![false; h+2]; n+2];

        for i in 0..m {
            let tokens: Vec<usize> = IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();
            visited[tokens[1]][tokens[0]] = true;
        }

        Self {
            n: n,
            m: m,
            h: h,
            visited: visited
        }
    }

    fn run(&mut self) {
        let mut minTryCount = i32::MAX;
        for tryCount in 0..4 {
            if self.dfs(0, 1, tryCount) {
                minTryCount = tryCount;
                break;
            }
        }

        if minTryCount == i32::MAX {
            minTryCount = -1;
        }

        println!("{}", minTryCount);
    }
}

impl Problem {

    fn check(&self) -> bool {
        let mut result = true;

        for i in 1..self.n+1 {
            let mut cur = i;

            for r in 1..self.h+1 {
                if self.visited[cur][r] {
                    cur += 1;
                } else if self.visited[cur-1][r] {
                    cur -= 1;
                }
            }

            if cur != i {
                result = false;
                break;
            }
        }

        result
    }

    fn dfs(&mut self, depth: i32, col: usize, limit: i32) -> bool {
        if depth == limit {
            return self.check()
        }

        for c in col..self.n {
            for r in 1..self.h+1 {

                if self.visited[c][r] || self.visited[c-1][r] || self.visited[c+1][r] {
                    continue;
                }

                self.visited[c][r] = true;
                let res = self.dfs(depth+1, c, limit);
                self.visited[c][r] = false;

                if(res) {
                    return res;
                }
            }
        }

        false
    }
}

fn main() {
    let mut problem: Problem = Problem::new();
    problem.run();
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
