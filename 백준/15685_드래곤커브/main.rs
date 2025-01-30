trait Solution {
    fn new()->Self ;
    fn run(&mut self);
}

struct CurveInfo {
    x: i32,
    y: i32,
    d: i32,
    g: i32
}

struct Pos {
    x: i32,
    y: i32
}

struct Problem {
    n: i32,
    curves: Vec<CurveInfo>
}

const dx: [i32; 4] = [1, 0, -1, 0];
const dy: [i32; 4] = [0, -1, 0, 1];

impl Solution for Problem {

    fn new() -> Self {
        let n: i32 = IO::read_line().parse().unwrap();
        let mut curves: Vec<CurveInfo> = Vec::new();
        for i in 0..n {
            let tokens: Vec<i32> = IO::read_line()
                .split_whitespace()
                .map(|x| x.parse().unwrap())
                .collect();
            curves.push( CurveInfo {
                x: tokens[0],
                y: tokens[1],
                d: tokens[2],
                g: tokens[3]
            });
        }

        Self {
            n : n,
            curves: curves
        }
    }

    fn run(&mut self) {
        let mut board: Vec<Vec<bool>> = vec![vec![false; 101]; 101];

        for curve in &self.curves {
            make_curves(&curve, &mut board);
        }

        let answer = count_squares(&board);
        println!("{}",answer);
    }
}

fn count_squares(board: &Vec<Vec<bool>>) -> i32 {
    let mut count: i32 = 0;

    for r in 0..100 {
        for c in 0..100 {
            if board[r][c] && board[r+1][c] && board[r][c+1] && board[r+1][c+1] {
                count+=1;
            }
        }
    }

    count
}

fn rotate(x: i32, y: i32, px: i32, py: i32) -> (i32, i32) {
    let tx: i32 = x - px;
    let ty: i32 = y - py;
    // 0 -1  tx
    // 1  0  ty

    ( -ty + px, tx + py)
}

fn make_curves(curve: &CurveInfo, board: &mut Vec<Vec<bool>>) {
    let mut trace: Vec<Pos> = Vec::new();

    trace.push(Pos{
        x: curve.x,
        y: curve.y
    });

    trace.push(Pos{
        x: curve.x + dx[curve.d as usize],
        y: curve.y + dy[curve.d as usize]
    });


    for gen in 0..curve.g {
        let pivot = &trace[trace.len() - 1];
        let mut new_points: Vec<Pos> = Vec::new();
        for point in &trace {
            if point.x == pivot.x && point.y == pivot.y {
                continue;
            }
            let np = rotate(point.x, point.y, pivot.x, pivot.y);
            new_points.push(Pos {
                x: np.0,
                y: np.1
            });
        }
        new_points.reverse();
        trace.extend(new_points);
    }

    //println!("curves x, y = {}, {}, gen {} , dir {}", curve.x, curve.y, curve.g, curve.d);

    for point in &trace {
        //println!("  point {} {}", point.x, point.y);
        if(point.y > 100 || point.x > 100) { continue; }
        board[(point.y) as usize][(point.x) as usize] = true;
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
