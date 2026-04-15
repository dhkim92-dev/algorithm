use std::io::Write;

fn solve() {
    let mut reader = simple_io::Reader::new();
    let mut writer = simple_io::Writer::new();

    let n: usize = reader.next();
    let h: usize = reader.next();
    let m = n / 2; // (N-1)/2

    writeln!(writer.out, "Yes").unwrap();

    let mut result = Vec::with_capacity(n);

    // 좌측 서브 트리의  루트를 h로 잡는다.
    // h로부터 반시계 방향으로 n/2 개 만큼의 노드들은 무조건 h가 이긴다.
    // h로부터 반시계 방향으로 가장 먼 노드(X)는 h+1부터 시계방향으로 해당 노드 사이의 모든 적을 이긴다. 
    // 따라서 좌측 서브트리에서 H를 올리고  X를 우측 서브 트리의 승자로 올리면 무조건 h가 이긴다
    result.push(h);
    for d in 1..m {
        result.push((h + n - 1 - d) % n + 1);
    }

    result.push((h + n - 1 - m) % n + 1);
    for d in 1..=m {
        result.push((h - 1 + d) % n + 1);
    }

    let s: Vec<String> = result.iter().map(|x| x.to_string()).collect();
    writeln!(writer.out, "{}", s.join(" ")).unwrap();
}

fn main() {
    solve();
}

mod simple_io {
    use std::io::{BufWriter, Read, StdoutLock, Write};

    pub struct Reader {
        buf: Vec<u8>,
        pos: usize,
    }

    impl Reader {
        pub fn new() -> Self {
            let mut buf = Vec::new();
            std::io::stdin().lock().read_to_end(&mut buf).unwrap();
            Self { buf, pos: 0 }
        }

        pub fn next<T: std::str::FromStr>(&mut self) -> T
        where
            T::Err: std::fmt::Debug,
        {
            while self.pos < self.buf.len() && self.buf[self.pos].is_ascii_whitespace() {
                self.pos += 1;
            }
            let start = self.pos;
            while self.pos < self.buf.len() && !self.buf[self.pos].is_ascii_whitespace() {
                self.pos += 1;
            }
            let s = unsafe { std::str::from_utf8_unchecked(&self.buf[start..self.pos]) };
            s.parse().unwrap()
        }
    }

    pub struct Writer {
        pub out: BufWriter<StdoutLock<'static>>,
    }

    impl Writer {
        pub fn new() -> Self {
            Self {
                out: BufWriter::new(std::io::stdout().lock()),
            }
        }
    }
}
