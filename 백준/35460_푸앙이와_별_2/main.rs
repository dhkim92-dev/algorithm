fn solve() {
    let (N, K) = {
        let v = io::read_line()
            .split_whitespace()
            .map(|s| s.parse::<usize>().unwrap())
            .collect::<Vec<_>>();
        (v[0], v[1])
    };

    // 그래프 상의 모든 정점은 다른 정점으로 가는 하나의 경로가 반드시 존재한다.(무방향) 
    // 따라서 N(N-1)/2개의 간선이 존재. 
    // 1번과 N번을 제외한 K개의 정점을 경유해서 1 to N으로 가는 경로의 최소 비용을 구해야한다.
    // 3 <= N <= 10^6 
    // 0 <= K <= N-2
    
    let mut ans = 0;

    // 관찰1. K개의 노드를 거쳐야한다는 조건이 주어진다면 2 부터 K + 1번 노드가 경로에
    // 포함된다. 
    // 관찰2. 1번 노드는 K+1 노드와 연결되고, 2번 노드는 N번 노드와 연결되어야 최소가 보장된다.
    // 해결 전략
    // 비용 계산 식, 2~K+1 노드를 각각 N과 1부터 하나씩 곱해서 비용을 구하고 기준 노드를 하나씩
    // 옮겨가며 비용 계산, 항상 프론트부터 계산한다.
    // 예 10, K = 5
    // cur_front = 1, cur_back = 10
    // next_front = 2, next_back = 6;
    //
    let mut front_cursor = 2;
    let mut back_cursor = K + 1;

    let mut front = 1;
    let mut back = N;

    if K == 0 {
        ans = 1 * N;
        println!("{}", ans);
        return;
    }

    loop {
        // println!("front_cursor : {}  back_cursor : {}  front : {} back: {}", front_cursor, back_cursor, front, back);
        // println!("front result:{}", front_cursor * back);
        // println!("back result: {}", back_cursor * front);
        if front_cursor > back_cursor {
            // 커서가 교차된 경우 차이가 1이라면 front * cursor를 한번 더해준다. 
            if front_cursor - back_cursor == 1 {
                // println!("cursor crossed, answer += {}", front * back);
                ans += front * back;
            }
            break;
        }

        ans += front_cursor * back;
        ans += back_cursor * front;
        front = front_cursor;
        back = back_cursor;
        front_cursor += 1;
        back_cursor -= 1;

        // println!(" next front : {} back : {}, front_cursor: {}, back_cursor: {}, ans: {}", front, back, front_cursor, back_cursor, ans);
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
