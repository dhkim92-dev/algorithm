trait Solution {
    fn new()->Self ;
    fn run(&mut self);
}

struct Pos {
    r: i32,
    c: i32
}

struct Problem {
    n: usize,
    m: usize,
    chickens: Vec<Pos>,
    houses: Vec<Pos>,
    dists: Vec<Vec<i32>>
}

fn get_dist(a: &Pos, b: &Pos) -> i32 {
    (b.r-a.r).abs() + (b.c - a.c).abs()
}

impl Solution for Problem {

    fn new() -> Self {
        let tokens: Vec<i32> = IO::read_line()
            .split_whitespace()
            .map(|x| x.parse().unwrap())
            .collect();

        let n = tokens[0] as usize;
        let m = tokens[1] as usize;
        let mut chickens: Vec<Pos> = Vec::new();
        let mut houses: Vec<Pos> = Vec::new();

        for i in 0..n {
            let row: Vec<i32>= IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();

            for j in 0..n {
                if row[j] == 1 {
                    houses.push(Pos {r: i as i32, c: j as i32} );
                } else if row[j] == 2  {
                    chickens.push(Pos {r: i as i32 , c: j as i32} );
                }
            }
        }

        let mut dists: Vec<Vec<i32>> = vec![vec![0; chickens.len()]; houses.len()];

        for i in 0..houses.len() {
            for j in 0..chickens.len() {
                dists[i][j] = get_dist(&houses[i], &chickens[j]);
            }
        }


        Self {
            n: n,
            m: m,
            chickens: chickens,
            houses: houses,
            dists: dists
        }
    }

    fn run(&mut self) {
        let mut answer = self.combination(0, 0, 0);
        println!("{}", answer);
    }
}

impl Problem {

    fn simulate(&self, selected: i32) -> i32 {
        let mut sum: i32 = 0;

        for i in 0..self.houses.len() {
            let mut min_value = i32::MAX;
            for j in 0..self.chickens.len() {
                if selected & (0x01 << j) == 0 {
                    continue;
                }
                min_value = std::cmp::min(min_value, self.dists[i][j]);
            }

            if min_value != i32::MAX {
                sum += min_value;
            }
        }
        sum
    }

    fn combination(&mut self, idx: i32, count: i32, selected: i32) -> i32 {
        if count == self.m as i32 {
            return self.simulate(selected)
        }

        let mut min_value = i32::MAX;

        for i in idx .. self.chickens.len() as i32 {
            if (selected & (0x01 << i)) != 0 {
                continue;
            }
            let value = self.combination(i as i32, count + 1, selected | (0x01) << i);
            min_value = std::cmp::min(min_value, value);
        }

        min_value
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

