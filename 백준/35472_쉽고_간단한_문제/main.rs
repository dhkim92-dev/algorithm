use std::collections::HashMap;

fn solve() {
    let n: usize = io::read_line().parse().unwrap();
    let a: Vec<i64> = io::read_line()
        .split_whitespace()
        .map(|x| x.parse().unwrap())
        .collect();

    /**
    * - best[val] = val로 끝나는 부분 수열 중 최대 길이 
    * - top3 = 현재까지 탐색한 결과 가장 긴 길이를 가지는 부분 수열(x, length) => 끝이 x로 끝나고
    *   길이가 length
    *  a를 순차적으로 탐색 할 때 값 v 를 탐색 중이라고 하자. 
    *  이전 까지의 탐색 결과 v-1, v+1를 제외한 수로 끝나는 부분 수열 중 최대 길이를 가지는 부분 수열에 v를 붙이면 최대 길이가 된다. 
    *  따라서 최악의 경우 top3 에는 ( v-1, x, v+1 ) 세가지 수로 끝나는 부분수열의 길이 정보가
    *  저장되지만 v-1, v+1 을 제외하더라도 x를 선택할 수 있으므로 항상 최대값을 구할 수 있다. 
    *  
    * */
    
    let mut best: HashMap<i64, i64> = HashMap::new();
    let mut top3: Vec<(i64, i64)> = Vec::new();
    let mut answer = 0i64;

    for &val in &a {
        let forbidden1 = val - 1;
        let forbidden2 = val + 1;

        let mut max_prev = 0i64;

        for &(v, dp_val) in &top3 {
            if v != forbidden1 && v != forbidden2 {
                max_prev = dp_val;
                break;
            }
        }

        let dp_i = max_prev + 1;
        answer = answer.max(dp_i);

        let entry  = best.entry(val)
            .or_insert(0);

        if dp_i > *entry {
            *entry = dp_i;

            top3.retain(|&(v, _) | v != val);
            let pos = top3.iter().position(|&(_, d) |d < dp_i).unwrap_or(top3.len());
            top3.insert(pos, (val, dp_i));
            if top3.len() > 3 {
                top3.pop();
            }
        }
    }

    println!("{}", answer);
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
