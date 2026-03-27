use std::cmp::{min, Reverse};
use std::collections::BinaryHeap;

const INF: i64 = 1_000_000_000_000_000_000;

fn dijkstra(start: usize, adj: &[Vec<(usize, i64)>]) -> Vec<i64> {
    let n = adj.len();
    let mut dist = vec![INF; n];
    dist[start] = 0;
    let mut heap = BinaryHeap::new();
    heap.push(Reverse((0i64, start)));
    while let Some(Reverse((d, u))) = heap.pop() {
        if d > dist[u] {
            continue;
        }
        for &(v, w) in &adj[u] {
            let nd = d + w;
            if nd < dist[v] {
                dist[v] = nd;
                heap.push(Reverse((nd, v)));
            }
        }
    }
    dist
}

fn solve() {
    let first = io::read_line();
    let mut it = first.split_whitespace();
    let n: usize = it.next().unwrap().parse().unwrap();
    let m: usize = it.next().unwrap().parse().unwrap();
    let k: usize = it.next().unwrap().parse::<usize>().unwrap() - 1;
    let t: i64 = it.next().unwrap().parse().unwrap();

    let mut adj = vec![vec![]; n];
    for _ in 0..m {
        let line = io::read_line();
        let mut it = line.split_whitespace();
        let u: usize = it.next().unwrap().parse::<usize>().unwrap() - 1;
        let v: usize = it.next().unwrap().parse::<usize>().unwrap() - 1;
        let c: i64 = it.next().unwrap().parse().unwrap();
        adj[u].push((v, c));
        adj[v].push((u, c));
    }

    let d1 = dijkstra(0, &adj);
    let dk = dijkstra(k, &adj);
    let dn = dijkstra(n - 1, &adj);

    let mut ans = INF;

    if d1[k] < INF && dk[n - 1] < INF {
        ans = min(ans, d1[k] + dk[n - 1]);
    }

    for b in 0..n {
        if dk[b] <= t && d1[b] < INF && dn[b] < INF {
            ans = min(ans, d1[b] + t + dn[b]);
        }
    }

    if ans >= INF {
        println!("-1");
    } else {
        println!("{}", ans);
    }
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

