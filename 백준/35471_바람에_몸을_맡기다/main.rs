const dirs: [(i32, i32);4] = [
    (-1, 0), // U
    (0, 1),  // R
    (1, 0),  // D
    (0, -1)  // L
];

fn wind_to_dir(wind: char) -> usize {
    match wind {
        'U' => 0,
        'R' => 1,
        'D' => 2,
        'L' => 3,
        _ => panic!("Invalid wind direction")
    }
}

fn is_bounded(r: i32, c: i32, H: usize, W: usize) -> bool {
    r >= 0 && r < H as i32 && c >= 0 && c < W as i32
}

fn is_holdable(
    r: i32, c: i32, 
    wind: char, 
    grid: &Vec<Vec<char>>
) -> bool {

    let d = (wind_to_dir(wind) + 2) % 4 ;
    let nr = r + dirs[d].0;
    let nc = c + dirs[d].1;

    if !is_bounded(nr, nc, grid.len(), grid[0].len()) {
        return false; 
    }

    grid[nr as usize][nc as usize] == '#'
}

fn is_movable(r: usize, c: usize, grid: &Vec<Vec<char>>) -> bool {
    grid[r][c] == '.'
}

fn solve() {
    let s = io::read_line();
    let mut it = s.split_whitespace();
    let H = it.next().unwrap().parse::<usize>().unwrap();
    let W = it.next().unwrap().parse::<usize>().unwrap();

    let s = io::read_line();
    it = s.split_whitespace();
    let rs = it.next().unwrap().parse::<usize>().unwrap() - 1;
    let cs = it.next().unwrap().parse::<usize>().unwrap() - 1;

    let s = io::read_line();
    it = s.split_whitespace();
    let re = it.next().unwrap().parse::<usize>().unwrap() - 1;
    let ce = it.next().unwrap().parse::<usize>().unwrap() - 1;

    let mut grid = Vec::new();
    for _ in 0..H {
        grid.push(io::read_line().chars().collect::<Vec<_>>());
    }

    let winds = io::read_line().chars().collect::<Vec<_>>();

    let mut trace: Vec<char> = vec![];

    let mut cur_r = re as i32;
    let mut cur_c = ce as i32;
    let mut ok = true;
    for &wind in winds.iter().rev() {
        let wdir = wind_to_dir(wind);

        let pr = cur_r - dirs[wdir].0;
        let pc = cur_c - dirs[wdir].1;

        if is_bounded(pr, pc, H, W) && is_movable(pr as usize, pc as usize, &grid) {
            trace.push('F');
            cur_r = pr;
            cur_c = pc;
        }
        else if is_holdable(cur_r, cur_c, wind, &grid) {
            trace.push('G');
        }
        else {
            ok = false;
            break;
        }
    }

    if !ok || cur_r != rs as i32 || cur_c != cs as i32 || trace.len() != winds.len() {
        println!("NO");
        return;
    }

    println!("YES");
    trace.reverse();
    println!("{}", trace.iter().collect::<String>());
}

fn main() {
    solve();
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
