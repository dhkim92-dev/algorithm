use std::io::{Write};

fn split_and_reduce(
    arr: &mut Vec<i64>,
    left: usize,
    right: usize
) -> i64 {
    // println!("split_and_reduce called with left: {}, right: {}", left, right);
    if left >= right {
        return arr[left];
    }

    // 현재 구간에서 최소값을 찾아 반환 
    let mut min_value = arr[left];
    let mut cnt = 0;
    let mut min_pos = left;
    for i in left..=right {
        if arr[i] < min_value {
            min_value = arr[i];
            min_pos = i; 
        }
    }

    // 구간에서 최소값 제거 
    for i in left..=right {
        arr[i] -= min_value;
    }
    cnt += min_value;

    let mut i = left;
    while i <= right {
        if arr[i] == 0 {
            i += 1;
            continue;
        }

        let start = i;
        while i <= right && arr[i] != 0 {
            i += 1;
        }
        let end = i - 1;

        cnt += split_and_reduce(arr, start, end);
    }

    cnt
}

fn solve() {
    let mut reader = simple_io::Reader::new();
    let mut writer = simple_io::Writer::new();

    let N = reader.next::<usize>();
    let mut arr = Vec::with_capacity(N);
    for _ in 0..N {
        arr.push(reader.next::<i64>());
    }

    // 배열 크기 N이 주어지고 
    // 다음 줄에 길이 N의 배열이 주어진다
    // 1 <= N <= 100,000
    // 0 <= arr[i] <= 1000 

    // 개구간 K개를 조합하여 arr을 만들 수 있는 최소 K를 구하라 
    // O(N^2) = 10^10 -> 10_000_000_000, 1초 내 풀이 불가능
    // O(N log N) 은 되어야함 
    // 예) N = 3, arr = [ 1, 2, 3 ]
    // [1, 3], [2, 3], [3, 3] 세개 개구간으로 arr을 만들 수 있다. K = 3
    // 예) N = 5, arr = [ 1, 1, 1, 1, 1] , [1, 5] 개구간으로 arr 만들기 가능 K = 1
    // 예) N = 3, arr = [1, 7, 2]
    //     [ 1, 3 ], [ 2, 3 ], [3, 3] * 5, K = 7 로 arr 만들 수 있음. 
    // 예) N = 4, arr = [5, 0, 5, 7]
    //     [1, 1] * 5 + [3, 4] * 5 + [4, 4] * 2, K = 12로 Arr 표현 가능
    //
    // 관찰 1. 개구간 [i, j]는 arr[i ... j] 를 한칸씩 1 상승시킴
    // 관찰 2. 일단 만들 수 있는 가장 긴 개구간을 찾아 하나씩 제거하는게 최선(그리디)
    let mut i = 0usize;
    let mut ans = 0i64;
    while i < N {
        if arr[i] == 0 {
            i += 1;
            continue;
        }
        let start = i;
        while i < N && arr[i] != 0 {
            i += 1;
        }
        let end = i - 1;
        ans += split_and_reduce(&mut arr, start, end);
    }
    writeln!(writer.out, "{}", ans).unwrap();
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
