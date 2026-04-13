use std::cmp::min;
use std::io::{Write};

struct SegTree {
    tree: Vec<i64>,
    n: usize,
}

impl SegTree {
    fn new(n: usize) -> Self {
        Self {
            tree: vec![0; 4 * n],
            n,
        }
    }

    fn init(&mut self, arr: &[i64], node: usize, start: usize, end: usize) {
        if start == end {
            self.tree[node] = arr[start];
            return;
        }
        let mid = (start + end) / 2;
        self.init(arr, node * 2, start, mid);
        self.init(arr, node * 2 + 1, mid + 1, end);
        self.tree[node] = min(self.tree[node * 2], self.tree[node * 2 + 1]);
    }

    fn update(&mut self, node: usize, start: usize, end: usize, idx: usize, value: i64) {
        if start == end {
            self.tree[node] += value;
            return;
        }
        let mid = (start + end) / 2;
        if idx <= mid {
            self.update(node * 2, start, mid, idx, value);
        } else {
            self.update(node * 2 + 1, mid + 1, end, idx, value);
        }
        self.tree[node] = min(self.tree[node * 2], self.tree[node * 2 + 1]);
    }

    fn query_min(&self) -> i64 {
        self.tree[1]
    }
}

fn solve() {
    let mut reader = simple_io::Reader::new();
    let mut writer = simple_io::Writer::new();

    let n = reader.next::<usize>();
    let q = reader.next::<usize>();

    let mut arr = Vec::with_capacity(n);
    for _ in 0..n {
        arr.push(reader.next::<i64>());
    }

    if n == 1 {
        for _ in 0..q {
            let t = reader.next::<i32>();
            if t == 1 {
                reader.next::<usize>();
                reader.next::<usize>();
                reader.next::<i64>();
            } else {
                writeln!(writer.out, "YES").unwrap();
            
            }
        }
        return;
    }

    let mut diff = vec![0i64; n];
    for i in 0..n - 1 {
        diff[i] = arr[i + 1] - arr[i];
    }

    let mut seg = SegTree::new(n);
    seg.init(&diff, 1, 0, n - 1);

    for _ in 0..q {
        let t = reader.next::<i32>();
        match t {
            1 => {
                let left = reader.next::<usize>() - 1;
                let right = reader.next::<usize>() - 1;
                let value = reader.next::<i64>();

                if left > 0 {
                    seg.update(1, 0, n - 1, left - 1, value);
                }
                if right < n - 1 {
                    seg.update(1, 0, n - 1, right, -value);
                }
            }
            _ => {
                if seg.query_min() >= 0 {
                    writeln!(writer.out, "YES");
                } else {
                    writeln!(writer.out, "NO");
                }
            }
        }
    }
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
