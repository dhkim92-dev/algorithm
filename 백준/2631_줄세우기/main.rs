use std::{io};

fn read_line() -> String {
    let mut line = String::new();
    io::stdin().read_line(&mut line)
    .expect("Invalid Input");
    line.trim().to_string()
}

fn find_lis_length(arr: Vec<i32>) -> i32 {
    let mut dp: Vec<i32> = vec![0; arr.len()];
    dp[1] = 1;
    let N = arr.len();

    for i in 1..N {
        for j in 0..i {
            if(arr[i] > arr[j]) {
                dp[i] = dp[i].max(dp[j]+1);
            }
        }
    }

    *dp.iter().max().unwrap_or(&0)
}

fn solve() {
    let N: i32 = read_line().parse::<i32>().expect("Not integer");
    let mut students: Vec<i32> = Vec::new();
    students.push(0);
    for i in 0..N {
        students.push(read_line().parse::<i32>().expect("Not integer"));
    }

    let answer: i32 = find_lis_length(students);

    println!("{}", N-answer);
}

fn main() {
    solve();
}
