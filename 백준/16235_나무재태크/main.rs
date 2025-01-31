use std::collections::BinaryHeap;
use std::cmp::{Ordering};
use std::collections::{VecDeque};

trait Solution {
    fn new() -> Self;
    fn run(&mut self);
}

#[derive(Debug, PartialEq, Eq)]
struct Pos {
    r: i32,
    c: i32
}

impl Pos {
    
    fn add(&self, other: &Pos) -> Pos {
        Pos {
            r: self.r + other.r,
            c: self.c + other.c
        }
    }
}

struct Problem {
    n: usize,
    m: usize,
    k: usize,
    board: Vec<Vec<i32>>,
    nutrients: Vec<Vec<i32>>,
    trees: Vec<Vec<VecDeque<i32>>>,
    dead_trees: Vec<Vec<Vec<i32>>>
}

impl Solution for Problem {
    fn new() -> Self {
        let tokens: Vec<usize> = IO::read_line()
            .split_whitespace()
            .map(|x| x.parse().unwrap())
            .collect();

        let board: Vec<Vec<i32>> = vec![vec![5; tokens[0]]; tokens[0]];
        let mut nutrients: Vec<Vec<i32>> = vec![vec![0; tokens[0]]; tokens[0]];
        let mut trees: Vec<Vec<VecDeque<i32>>> = vec![vec![VecDeque::new(); tokens[0]]; tokens[0]];
        let mut dead_trees: Vec<Vec<Vec<i32>>> = vec![vec![vec![0;0]; tokens[0]]; tokens[0]];

        for i in 0..tokens[0] {
            let rows: Vec<i32> = IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();
            for j in 0..tokens[0] {
                nutrients[i][j] = rows[j];
            }
        }

        for i in 0..tokens[1] {
            let tree: Vec<usize> = IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();
            trees[tree[0]-1][tree[1]-1].push_front(tree[2] as i32);
        }

        Self {
            n: tokens[0],
            m: tokens[1],
            k: tokens[2],
            board: board,
            nutrients: nutrients,
            trees: trees,
            dead_trees: dead_trees
        }
    }

    fn run(&mut self) {
        let mut answer: usize = 0;

        for i in 0..self.k {
            self.spring();
            self.summer();
            self.autumn();
            self.winter();
        }


        for i in 0..self.n {
            for j in 0..self.n {
                answer += self.trees[i][j].len();
            }
        }

        println!("{}", answer);
    }
}

impl Problem {

    fn spring(&mut self) {
        // 봄에는 양분을 흡수한다.
        for i in 0..self.n {
            for j in 0..self.n {
                self.trees[i][j].make_contiguous().sort();
                self.dead_trees[i][j].clear();
                for k in 0..self.trees[i][j].len() {
                    if self.board[i][j] >= self.trees[i][j][k] {
                        self.board[i][j] -= self.trees[i][j][k];
                        self.trees[i][j][k] += 1;
                    } else {
                        self.dead_trees[i][j].push(self.trees[i][j][k]);
                    }
                }

                // 죽은 나무 제거
                for k in 0..self.dead_trees[i][j].len() {
                    self.trees[i][j].pop_back();
                }
            }
        }
    }

    fn summer(&mut self) {
        // 죽은 나무가 양분으로
        for i in 0..self.n {
            for j in 0..self.n {
                for k in 0..self.dead_trees[i][j].len() {
                    self.board[i][j] += ((self.dead_trees[i][j][k] as f32) / 2.0) as i32;
                }
            }
        }
    }

    fn is_in_range(&self, r: i32, c: i32) -> bool {
        0<=r && r < self.n as i32 && 0 <= c && c < self.n as i32
    }

    fn autumn(&mut self) {
        const dirs: [Pos; 8] = [
            Pos {r: -1, c:  0},
            Pos {r: -1, c:  1},
            Pos {r:  0, c:  1},
            Pos {r:  1, c:  1},
            Pos {r:  1, c:  0},
            Pos {r:  1, c: -1},
            Pos {r:  0, c: -1},
            Pos {r: -1, c: -1}
        ];

        for i in 0..self.n {
            for j in 0..self.n {
                for k in 0..self.trees[i][j].len() {
                    if self.trees[i][j][k] % 5 == 0 {
                        for d in 0..8 {
                            let nr: i32 = i as i32 + dirs[d].r;
                            let nc: i32  = j as i32  + dirs[d].c;
                            if !self.is_in_range(nr, nc) {
                                continue;
                            }

                            self.trees[nr as usize][nc as usize].push_front(1);
                        }
                    }
                }
            }
        }
    }

    fn winter(&mut self) {
        for r in 0..self.n {
            for c in 0..self.n {
                self.board[r][c] += self.nutrients[r][c];
            }
        }
    }
}

fn main() {
    let mut solution = Problem::new();
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

