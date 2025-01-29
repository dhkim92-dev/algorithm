use std::io;
use std::cmp;

trait Solution {

    fn new()->Self ;
    fn run(&mut self);
}

struct Problem {
    N: usize,
    M: usize,
    board: Vec<Vec<i32>>,
    visited: Vec<Vec<bool>>,
    dirs:[[i32; 2]; 4]
}

impl Solution for Problem {

    fn new() -> Self {

        let tokens: Vec<usize> = IO::read_line()
            .split_whitespace()
            .map(|x| x.parse().unwrap())
            .collect();
        let n = tokens[0];
        let m = tokens[1];

        let mut board =Vec::with_capacity(n);
        let mut visited = Vec::with_capacity(n);

        for i in 0..n {
            let mut row = IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();

            let mut boolRow: Vec<bool> = Vec::with_capacity(m);
            boolRow.resize(m, false);
            board.push(row);
            visited.push(boolRow);
        }

        return Problem {
            N : n,
            M : m,
            board: board,
            visited: visited,
            dirs: [
                [1, 0],
                [0, 1],
                [-1, 0],
                [0, -1]
            ]
        }
    }

    fn run(&mut self) {
        //println!("board size: {}, {}", self.board.len(), self.board[0].len());
        //println!("visited size: {}, {}", self.visited.len(), self.visited[0].len());
        let mut answer = 0;

        for r in 0..self.N {
            for c in 0..self.M {
                self.visited[r][c] = true;
                answer = cmp::max(answer, self.dfs(0, r, c, self.board[r][c]));
                self.visited[r][c] = false;
            }
        }
        println!("{}", answer);
    }
}

impl Problem {

    fn dfs(&mut self, depth: i32, r: usize, c: usize, sum: i32) -> i32 {
        if depth == 3 {
            return sum;
        }

        let mut mx: i32 = sum;

        for i in 0..4 {
            let nr = r as i32 + self.dirs[i][0];
            let nc =  c as i32 + self.dirs[i][1];

            if nr < 0 || nr >= self.N as i32 || nc < 0 || nc >= self.M as i32 {
                continue;
            }

            if self.visited[nr as usize][nc as usize] {
                continue;
            }

            if depth == 1 {
                self.visited[nr as usize][nc as usize] = true;
                mx = cmp::max(mx, self.dfs(depth+1, r, c, sum + self.board[nr as usize][nc as usize]));
                self.visited[nr as usize][nc as usize] = false;
            }

            self.visited[nr as usize][nc as usize] = true;
            mx = cmp::max(mx, self.dfs(depth+1, nr as usize, nc as usize, sum+self.board[nr as usize][nc as usize]));
            self.visited[nr as usize][nc as usize] = false;
        }
        mx
    }
}

fn main() {
    let mut problem = Problem::new();
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
