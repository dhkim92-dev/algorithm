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

fn solve() {
    let _n: usize = io::read_line().parse().unwrap();
    let s: Vec<char> = io::read_line().chars().collect();

    let mut r: Vec<char> = Vec::new();

    for &c in &s {
        r.push(c);

        loop {
            let len = r.len();
            if len < 2 {
                break;
            }

            if r[len - 1] == r[len - 2] {
                r.truncate(len - 2);
                continue;
            }

            if len >= 3 && r[len - 1] == r[len - 3] {
                r.truncate(len - 3);
                continue;
            }

            break;
        }
    }

    if r.is_empty() {
        println!("-1");
    } else {
        println!("{}", r.iter().collect::<String>());
    }
}
