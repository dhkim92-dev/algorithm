use std::{collections::VecDeque, io::BufRead};

fn mark_separated_area(
    area_id: i32,
    r: i32,
    c: i32,
    board: &Vec<Vec<char>>,
    visited: &mut Vec<Vec<i32>>,
) {
    let mut q: VecDeque<(i32, i32)> = VecDeque::new();
    q.push_front((r, c));
    visited[r as usize][c as usize] = area_id;
    let n = board.len() as i32;

    while !q.is_empty() {
        let (cur_r, cur_c) = q.pop_back().unwrap();

        for (dr, dc) in [(0, 1), (1, 0), (0, -1), (-1, 0)] {
            let new_r = cur_r + dr;
            let new_c = cur_c + dc;

            if new_r < 0 || new_r >= n as i32 || new_c < 0 || new_c >= n {
                continue;
            }

            if visited[new_r as usize][new_c as usize] != 0 {
                continue;
            }

            if board[new_r as usize][new_c as usize] != board[cur_r as usize][cur_c as usize] {
                continue;
            }

            visited[new_r as usize][new_c as usize] = area_id;
            q.push_back((new_r, new_c));
        }
    }
}

fn merge_red_and_green(board: &mut Vec<Vec<char>>) {
    let n = board.len() as i32;
    for i in 0..n {
        for j in 0..n {
            if board[i as usize][j as usize] == 'G' {
                board[i as usize][j as usize] = 'R';
            }
        }
    }
}

fn solve(n: i32, board: &mut Vec<Vec<char>>) {
    let mut visited: Vec<Vec<i32>> = vec![vec![0; n as usize]; n as usize];
    visited.iter_mut().for_each(|v| v.fill(0));

    let mut area_id = 1;
    let mut answer: (i32, i32) = (0, 0);

    for i in 0..n {
        for j in 0..n {
            if visited[i as usize][j as usize] != 0 {
                continue;
            }
            mark_separated_area(area_id, i, j, &board, &mut visited);
            area_id += 1;
        }
    }
    answer.0 = area_id - 1;
    merge_red_and_green(board);
    area_id = 1;
    visited.iter_mut().for_each(|v| v.fill(0));

    for i in 0..n {
        for j in 0..n {
            if visited[i as usize][j as usize] != 0 {
                continue;
            }

            mark_separated_area(area_id, i, j, &board, &mut visited);
            area_id += 1;
        }
    }
    answer.1 = area_id - 1;

    println!("{} {}", answer.0, answer.1);
}

fn main() {
    let iostdin = std::io::stdin();
    let lock = iostdin.lock();
    let mut stdin = std::io::BufReader::new(lock);

    let mut buf = String::new();
    stdin.read_line(&mut buf).unwrap();
    let n: i32 = buf.trim().parse().unwrap();
    buf.clear();

    let mut board: Vec<Vec<char>> = Vec::new();

    for _ in 0..n {
        stdin.read_line(&mut buf).unwrap();
        board.push(buf.trim().chars().collect());
        buf.clear();
    }

    solve(n, &mut board);
    // for i in 0..n as usize {
    // println!("{}", board[i]);
    // }
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
