use std::collections::VecDeque;

fn bfs_eccentricity(adj: &[Vec<usize>], start: usize, n: usize) -> usize {
    let mut dist = vec![usize::MAX; n + 1];
    dist[start] = 0;
    let mut queue = VecDeque::new();
    queue.push_back(start);
    let mut max_dist = 0;
    while let Some(u) = queue.pop_front() {
        for &v in &adj[u] {
            if dist[v] == usize::MAX {
                dist[v] = dist[u] + 1;
                if dist[v] > max_dist {
                    max_dist = dist[v];
                }
                queue.push_back(v);
            }
        }
    }
    max_dist
}

fn solve() {
    let k: usize = io::read_line().trim().parse().unwrap();

    let mut radii: Vec<usize> = Vec::with_capacity(k);

    for _ in 0..k {
        let s = io::read_line();
        let mut it = s.split_whitespace();
        let n: usize = it.next().unwrap().parse().unwrap();
        let m: usize = it.next().unwrap().parse().unwrap();

        let mut adj = vec![vec![]; n + 1];
        for _ in 0..m {
            let s = io::read_line();
            let mut it = s.split_whitespace();
            let u: usize = it.next().unwrap().parse().unwrap();
            let v: usize = it.next().unwrap().parse().unwrap();
            adj[u].push(v);
            adj[v].push(u);
        }

        let mut radius = n;
        for start in 1..=n {
            let ecc = bfs_eccentricity(&adj, start, n);
            if ecc < radius {
                radius = ecc;
            }
        }
        radii.push(radius);
    }

    radii.sort_unstable_by(|a, b| b.cmp(a));
    let mut ans: usize = 0;
    for i in 0..k {
        let dist = (i + 1) / 2;
        let cost = dist + radii[i];
        if cost > ans {
            ans = cost;
        }
    }

    println!("{}", ans);
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

