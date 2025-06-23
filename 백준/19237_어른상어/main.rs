use std::{thread::sleep, time::Duration};

trait Solution {
    fn run(&mut self);
}

const DIRECTIONS: [[i32; 2]; 4] = [[-1, 0], [1, 0], [0, -1], [0, 1]];

#[derive(Debug, Clone)]
struct Shark {
    no: i32,
    r: i32,
    c: i32,
    d: usize,
    priorities: [[usize; 4]; 4],
    is_alive: bool,
}

impl Shark {
    fn new(no: i32, r: i32, c: i32, d: usize) -> Self {
        Self {
            no,
            r,
            c,
            d,
            priorities: [[0; 4]; 4],
            is_alive: true,
        }
    }
}

#[derive(Clone, Copy, Debug)]
struct Smell {
    shark_no: i32,
    left_time: i32,
}

struct Problem {
    n: i32,
    m: i32,
    k: i32,
    sharks: Vec<Shark>,
    grid: Vec<Vec<i32>>,
    smells: Vec<Vec<Smell>>,
}

impl Problem {
    fn new() -> Self {
        let arr: Vec<i32> = io::read_line()
            .split_whitespace()
            .map(|x| x.parse().unwrap())
            .collect();
        let mut sharks: Vec<Shark> = vec![Shark::new(0, 0, 0, 0); arr[1] as usize]; // Placeholder for shark 0
        let mut grid = vec![vec![0; arr[0] as usize]; arr[0] as usize];
        let mut smells = vec![
            vec![
                Smell {
                    shark_no: 0,
                    left_time: 0
                };
                arr[0] as usize
            ];
            arr[0] as usize
        ];

        for i in 0..arr[0] as usize {
            let line: Vec<i32> = io::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();
            for j in 0..arr[0] as usize {
                grid[i][j] = line[j];

                if grid[i][j] > 0 {
                    let shark_no = grid[i][j];
                    sharks[shark_no as usize - 1].is_alive = true;
                    sharks[shark_no as usize - 1].no = shark_no;
                    sharks[shark_no as usize - 1].r = i as i32;
                    sharks[shark_no as usize - 1].c = j as i32;
                }
            }
        }

        let line = io::read_line()
            .split_whitespace()
            .map(|x| x.parse().unwrap())
            .collect::<Vec<i32>>();

        // println!("Sharks default direction: {:?}", line);

        for j in 0..arr[1] as usize {
            sharks[j].d = (line[j] - 1) as usize; // Convert to zero-based index
                                                  // println!(
                                                  // "Shark {} starts at ({}, {}) with direction {}",
                                                  // sharks[j].no, sharks[j].r, sharks[j].c, sharks[j].d
                                                  // );
        }

        for i in 0..sharks.len() {
            for j in 0..4 as usize {
                let line = io::read_line()
                    .split_whitespace()
                    .map(|x| x.parse().unwrap())
                    .collect::<Vec<usize>>();
                for k in 0..4 as usize {
                    sharks[i].priorities[j][k] = line[k] - 1;
                }
            }
        }

        Self {
            n: arr[0],
            m: arr[1],
            k: arr[2],
            sharks,
            grid,
            smells,
        }
    }

    fn put_smells(&mut self) {
        for shark in &self.sharks {
            self.smells[shark.r as usize][shark.c as usize].shark_no = shark.no;
            self.smells[shark.r as usize][shark.c as usize].left_time = self.k;
        }
    }

    fn clear_grids(&mut self) {
        for row in self.grid.iter_mut() {
            for cell in row.iter_mut() {
                *cell = 0;
            }
        }
    }

    fn move_sharks(&mut self) {
        self.clear_grids();

        // 각 상어를 수행하며, 4개 방향의 좌표를 체크한다.
        // 이 때 grid 에 위치한 다른 상어가 있는 경우, 자신보다 작으면 자신은 죽어야한다.
        let n = self.n;
        let mut died_sharks: Vec<usize> = Vec::new();

        for shark in &mut self.sharks {
            let dirs = shark.priorities[shark.d];
            let mut moved = false;
            // println!(
            // "Shark {} at ({}, {}) with direction {} priorities: {:?}",
            // shark.no, shark.r, shark.c, shark.d, shark.priorities[shark.d]
            // );

            for i in 0..4 {
                let nr = shark.r + DIRECTIONS[dirs[i]][0];
                let nc = shark.c + DIRECTIONS[dirs[i]][1];

                if nr < 0 || nr >= n || nc < 0 || nc >= n {
                    continue;
                }

                let nr = nr as usize;
                let nc = nc as usize;

                if self.smells[nr][nc].left_time > 0 {
                    // 해당 위치에 냄새가 남아있다면
                    continue;
                }

                if self.grid[nr][nc] > 0 && self.grid[nr][nc] < shark.no {
                    shark.is_alive = false;
                    moved = true;
                    break; // 이동 성공, 다음 상어로 넘어간다.
                }

                if self.grid[nr][nc] > 0 && self.grid[nr][nc] > shark.no {
                    // 해당 위치에 다른 상어가 있고, 번호가 자신보다 더 크다면 원래 있던 상어가
                    moved = true;
                    died_sharks.push(self.grid[nr][nc] as usize - 1);
                }

                // 해당 위치에 다른 상어가 없다면, 이동한다.
                self.grid[shark.r as usize][shark.c as usize] = 0; // 원래 위치 비움
                self.grid[nr][nc] = shark.no;
                shark.r = nr as i32;
                shark.c = nc as i32;
                shark.d = dirs[i];
                moved = true;
                break; // 이동 성공, 다음 상어로 넘어간다.
            }

            if moved {
                continue;
            }

            // println!(
            // "Shark {} at ({}, {}) with direction {} could not move",
            // shark.no, shark.r, shark.c, shark.d
            // );

            for i in 0..4 {
                let nr = shark.r + DIRECTIONS[dirs[i]][0];
                let nc = shark.c + DIRECTIONS[dirs[i]][1];

                if nr < 0 || nr >= n || nc < 0 || nc >= n {
                    continue;
                }

                let nr = nr as usize;
                let nc = nc as usize;

                // println!(" smells[{}][{}] = {:?}", nr, nc, self.smells[nr][nc]);

                if self.smells[nr][nc].shark_no != shark.no {
                    continue;
                }
                // println!(
                // "Shark {} at ({}, {}) with direction {} moves to its own smell at ({}, {})",
                // shark.no, shark.r, shark.c, shark.d, nr, nc
                // );
                shark.r = nr as i32;
                shark.c = nc as i32;
                shark.d = dirs[i];
                self.grid[nr][nc] = shark.no;
                break;
            }
        }

        died_sharks.iter().for_each(|&idx| {
            self.sharks[idx].is_alive = false;
        })
    }

    fn remove_sharks(&mut self) {
        self.sharks.retain(|shark| shark.is_alive);
    }

    fn print_smells(&self) {
        for row in &self.smells {
            for &smell in row {
                print!("{} ", smell.shark_no);
            }
            println!();
        }
    }

    fn print_grid(&self, turn: i32) {
        println!("Turn: {}", turn);
        for row in &self.grid {
            for &cell in row {
                print!("{:2} ", cell);
            }
            println!();
        }
        sleep(Duration::from_secs(1));
    }

    fn vanish_smells(&mut self) {
        for row in &mut self.smells {
            for smell in row {
                if smell.left_time > 0 {
                    smell.left_time -= 1;

                    if smell.left_time == 0 {
                        smell.shark_no = 0; // 냄새 제거
                    }
                }
            }
        }
    }
}

impl Solution for Problem {
    fn run(&mut self) {
        let mut turn = 0;

        self.put_smells();
        // self.print_grid(turn);
        while turn < 1000 {
            turn += 1;
            self.move_sharks();
            self.remove_sharks();
            self.vanish_smells();
            self.put_smells();
            // self.print_grid(turn);
            // self.print_smells();

            if self.sharks.len() == 1 {
                println!("{}", turn);
                return;
            }
        }

        println!("-1");
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
