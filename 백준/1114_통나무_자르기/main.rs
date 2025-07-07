use std::collections::{HashSet, VecDeque};

fn solve(L: i32, K: i32, C: i32, offsets: &[i32]) -> String {
    // 길이 L의 막대를 최대 C번 자를 수 있고, K개의 주어진 위치에서만 자를 수 있다.
    // 이 때, 막대기를 자른 후 가장 긴 조각의 길이를 최소화 한다.
    // 같은 결과를 내는 자르는 위치의 조합이 여러개라면, 처음 자른 위치가 가장 작은 조합을
    // 선택한다.
    // 1 <= K, C <= 10,000 => 모든 조합을 고려하는 것은 불가
    let mut lo: isize = -1;
    let mut hi: isize = 1_000_000_001;
    let mut dists: Vec<i32> = vec![0; (K + 1) as usize];
    dists[0] = offsets[0]; // 첫 번째 조각의 길이는 시작점부터 첫 번째 자르는 위치까지의 거리
    for i in 1..(K + 1) as usize {
        dists[i] = offsets[i] - offsets[i - 1];
    }

    let mut length_answer = -1;
    let mut first_cut_idx = -1;

    while lo + 1 < hi {
        let mid: i32 = (lo + (hi - lo) / 2) as i32;
        let mut start = 0;
        let mut cut_count = 0;
        let mut idx = 0;

        for i in (0..K + 1).rev() {
            if dists[i as usize] > mid {
                cut_count = i32::MAX;
                break;
            }

            if (start + dists[i as usize]) > mid {
                start = 0;
                cut_count += 1;
                idx = i;
            }
            start += dists[i as usize];
        }

        if cut_count > C {
            lo = mid as isize;
        } else {
            hi = mid as isize;
            length_answer = mid;
            if cut_count == C {
                first_cut_idx = offsets[idx as usize];
            } else {
                first_cut_idx = offsets[0];
            }
        }
    }

    format!("{} {}", length_answer, first_cut_idx)
}

fn main() {
    let line = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<i32>().unwrap())
        .collect::<Vec<i32>>();

    let L = line[0];
    let mut K = line[1];
    let C = line[2];

    let mut offsets = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<i32>().unwrap())
        .collect::<VecDeque<i32>>(); // Iterator를 Vec으로 변환
    offsets.push_back(L);

    let mut offsets_vec = offsets.into_iter().collect::<Vec<i32>>();
    offsets_vec.sort();

    println!("{}", solve(L, K, C, &offsets_vec));
}

mod io {
    pub fn read_line() -> String {
        let mut buf = String::new();
        std::io::stdin().read_line(&mut buf).unwrap();
        buf.trim().to_string()
    }
}
