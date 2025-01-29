use std::collections::{HashMap, VecDeque};

trait Solution {

    fn new()->Self ;
    fn run(&mut self);
}

#[derive(Eq, PartialEq, Hash, Clone)]
struct Pos {
    r: i32,
    c: i32
}

impl Pos {

    fn new(r: i32, c: i32) -> Self {
        Self {
            r: r,
            c: c
        }
    }

    fn add(&self, o: &Pos) -> Pos {
        Pos {
            r: self.r + o.r,
            c: self.c + o.c
        }
    }
}

struct Problem {
    N: usize,
    K: usize,
    L: usize,
    board: Vec<Vec<i32>>,
    commands: HashMap<i32, i32>
}

impl Solution for Problem {

    fn new() -> Self {
        let n: usize = IO::read_line().parse().unwrap();
        let k: usize = IO::read_line().parse().unwrap();
        let mut board = vec![vec![0; n]; n];

        for _ in 0..k {
            let tokens: Vec<i32> = IO::read_line().split_whitespace()
                        .map(|x| (x.parse().unwrap()))
                        .collect();
            board[tokens[0] as usize-1][tokens[1] as usize-1] = 1;
        }

        let l: usize = IO::read_line().parse().unwrap();
        let mut commands: HashMap<i32, i32> = HashMap::new();

        for _ in 0..l {
            let tokens: Vec<String> = IO::read_line().split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();

            let time = tokens[0].parse().unwrap();
            let d_char: char = tokens[1].chars().nth(0).unwrap();

            if d_char == 'L' {
                commands.insert(time, 0);
            } else {
                commands.insert(time, 1);
            }
        }


        Self {
            N: n,
            K: k,
            L: l,
            board: board,
            commands: commands
        }
    }

    fn run(&mut self) {
        let mut time:i32 = 0;

        let mut head = Pos::new(0, 0);
        let mut head_dir = 0;

        let dirs: [Pos; 4] = [
            Pos::new(0, 1),
            Pos::new(1, 0),
            Pos::new(0, -1),
            Pos::new(-1, 0)
        ];

        let mut snake: VecDeque<Pos> = VecDeque::new();
        snake.push_back(Pos::new(head.r, head.c));


        loop {
            time+=1;
            //println!("head : {} {} {}", head.r, head.c, head_dir);
            let next_head = head.add(&dirs[head_dir]);

            if !self.is_in_range(&next_head) { break; }
            let nr = next_head.r as usize;
            let nc = next_head.c as usize;
            //println!("next head : {} {}", next_head.r, next_head.c);

            if self.board[nr][nc] == -1 { break; }

            snake.push_back(Pos::new(next_head.r, next_head.c));
            let tail: &Pos = snake.front().unwrap();

            if self.board[nr][nc] == 0 {
                self.board[nr][nc] = -1;
                self.board[tail.r as usize][tail.c as usize] = 0;
                snake.pop_front();
            } else if self.board[nr][nc] == 1 {
                self.board[nr][nc] = -1;
            } else {
                break;
            }

            if self.commands.contains_key(&time) {
                let d = self.commands.get(&time).unwrap();

                if *d == 0 {
                    head_dir = (head_dir + 3) % 4;
                } else {
                    head_dir = (head_dir + 1) % 4;
                }
            }

            head = next_head;
        }

        println!("{}", time);
    }
}

impl Problem {

    fn is_in_range(&self, p: &Pos) -> bool {
        (p.r >= 0 && p.r < self.N as i32) && (p.c >= 0 && p.c < self.N as i32)
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
