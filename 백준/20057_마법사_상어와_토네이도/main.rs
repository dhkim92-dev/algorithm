use std::collections::BinaryHeap;
use std::cmp::{Ordering};

trait Solution {
    fn new() -> Self;
    fn run(&mut self);
}

struct Problem {
    n: usize,
    board: Vec<Vec<i32>>,
    wasted: i32
}

const dr: [i32; 4] = [0, 1, 0, -1];
const dc: [i32; 4] = [-1, 0, 1, 0];
const spread: [[[i32; 3]; 9];4] = [
    [
        [ 0, -2,  5],
        [-1, -1, 10],
        [ 1, -1, 10],
        [-1,  0,  7],
        [ 1,  0,  7],
        [-2,  0,  2],
        [ 2,  0,  2],
        [-1,  1,  1],
        [ 1,  1,  1]
    ],
    [
        [  2,  0,  5],
        [  1, -1, 10],
        [  1,  1, 10],
        [  0, -1,  7],
        [  0,  1,  7],
        [  0, -2,  2],
        [  0,  2,  2],
        [ -1, -1,  1],
        [ -1,  1,  1]
    ],
    [
        [  0,  2,  5],
        [ -1,  1, 10],
        [  1,  1, 10],
        [ -1,  0,  7],
        [  1,  0,  7],
        [ -2,  0,  2],
        [  2,  0,  2],
        [ -1, -1,  1],
        [  1, -1,  1]
    ],
    [
        [ -2,  0,  5],
        [ -1, -1, 10],
        [ -1,  1, 10],
        [  0, -1,  7],
        [  0,  1,  7],
        [  0, -2,  2],
        [  0,  2,  2],
        [  1, -1,  1],
        [  1,  1,  1]
    ]
];
const ratio: [f32; 9] = [
    0.05, 0.1, 0.1, 0.07, 0.07, 0.02, 0.02, 0.01, 0.01
];

impl Solution for Problem {

    fn new() -> Self {
        let n: usize = IO::read_line()
            .parse()
            .unwrap();

        let mut board: Vec<Vec<i32>> = vec![vec![0; n];n];

        for i in 0..n {
            let row: Vec<i32> = IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();

            board[i] = row;
        }

        Self {
            n: n,
            board: board,
            wasted: 0
        }
    }

    fn run(&mut self) {
        let sr: i32 = self.n as i32 / 2;
        let sc: i32 = self.n as i32 / 2;

        self.progress(sr, sc, 0, 1);

        println!("{}", self.wasted);
    }
}

impl Problem {

    fn is_in_range(&self, r: i32, c: i32) -> bool {
        r >= 0 && r < self.n as i32 && c >= 0 && c < self.n as i32
    }

    fn print_board(&self) {
        println!("------------------- test -----------------");
        for i in 0..self.n {
            for j in 0..self.n {
                print!("{} ", self.board[i][j]);
            }
            println!();
        }
    }

    fn progress(&mut self, r: i32, c: i32, d: usize, s: i32) {
        if !self.is_in_range(r, c) {
            return;
        }

        if r == 0 && c == 0 {
            return;
        }

        //println!("r c : {} {}", r, c);

        let limit_r: i32 = r + dr[d] * s;
        let limit_c: i32 = c + dc[d] * s;

        let mut cur_r: i32 = r;
        let mut cur_c: i32 = c;

        for i in 0..s {
            self.spread_out(cur_r, cur_c, d);
            cur_r += dr[d];
            cur_c += dc[d];
        }

        if (d +1) % 2 == 0 {
            self.progress(cur_r, cur_c, (d+1)%4, s+1);
        } else {
            self.progress(cur_r, cur_c, (d+1)%4, s);
        }
      }

    fn spread_out(&mut self, r: i32, c: i32, d: usize) {
        let mut reduced: i32 = 0;
        let mut vapor: i32 = 0;
        let next_r: i32 = (r + dr[d]);
        let next_c: i32 = (c + dc[d]);

        if !self.is_in_range(next_r, next_c) {
            return;
        }

        let next_r: usize = next_r as usize;
        let next_c: usize = next_c as usize;
        let sand: i32 = self.board[next_r][next_c];
        let reduced: i32 = 0;

        for i in 0..9 {
            let nr = next_r as i32 + spread[d][i][0];
            let nc = next_c as i32 + spread[d][i][1];
            let amount = ((sand as f32) * ratio[i]) as i32;

            if !self.is_in_range(nr, nc) {
                // 영역 밖으로 나간 경우
                vapor += amount;
                self.board[next_r][next_c] -= amount;
                continue;
            }

            let nr = nr as usize;
            let nc = nc as usize;
            self.board[nr][nc] += amount;
            self.board[next_r][next_c] -= amount;
        }

        let alpha_r = next_r as i32 + dr[d];
        let alpha_c = next_c as i32 + dc[d];

        if !self.is_in_range(alpha_r, alpha_c) {
            vapor += self.board[next_r][next_c];
        } else {
            self.board[alpha_r as usize][alpha_c as usize] += self.board[next_r][next_c];
        }
        self.board[next_r][next_c] = 0;
        self.wasted += vapor;
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
