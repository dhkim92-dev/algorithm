const UNVISITED: u8 = 0;
const VISITING: u8 = 1;
const VISITED: u8 = 2;

fn mark_as(
    src: usize,
    dst: usize,
    value: usize,
    bypass: &Vec<usize>,
    state: &mut Vec<u8>,
    answer: &mut Vec<usize>,
) {
    // src 부터 nxt 까지의answer를 value로 채워주고,
    // src 부터 dst 까지의 state를 VISITED로 채워준다.
    let mut s = Vec::<usize>::new();
    s.push(src);
    while !s.is_empty() {
        let top = s.pop().unwrap();
        state[top] = VISITED;
        answer[top] = value;

        if top == dst {
            break;
        }

        s.push(bypass[top]);
    }
}

fn mark_cycle(
    src: usize,
    cycle_src: usize,
    dst: usize,
    bypass: &Vec<usize>,
    answer: &mut Vec<usize>,
    visited: &mut Vec<u8>,
) {
    // cycle이 발생한 경우, cycle 시작점과 끝점을 구성하는 노드들에 대하여 
    // answer[node] = prev_node 로 채워준 뒤,
    // src 부터 cycle_src 전 노드까지 answer[node] = answer[cycle_src]로 채워준다.
    //
    // println!("mark_cycle: src={}, cycle_src={}, dst={}", src, cycle_src, dst);
    let mut s = Vec::<usize>::new();
    s.push(cycle_src);
    answer[cycle_src] = dst;
    // println!("  update answer first.");
    while !s.is_empty() {
        // println!("  top : {}", s.last().unwrap());
        let top = s.pop().unwrap();
        let nxt = bypass[top];
        answer[nxt] = top;
        visited[top] = VISITED;
        // println!("  answer[{}] = {}", nxt, top);
        if nxt == cycle_src {
            break;
        }
        s.push(nxt);
    }

    let mut s = Vec::<usize>::new();
    s.push(src);
    // println!("  update answer second.");
    while !s.is_empty() {
        let top = s.pop().unwrap();
        visited[top] = VISITED;
        answer[top] = answer[cycle_src];
        // println!("  answer[{}] = {}", top, answer[cycle_src]);
        if top == cycle_src {
            break;
        }
        s.push(bypass[top]);
    }
}

fn solve() {
    let N = io::read_line()
            .trim()
            .parse::<usize>()
            .unwrap();

    let mut bypass = io::read_line()
        .split_whitespace()
        .map(|x| x.parse::<usize>().unwrap())
        .collect::<Vec<usize>>();

    let mut state = vec![UNVISITED; N];
    let mut answer = vec![0; N];

    for i in 0..N {
        bypass[i] -= 1;
    }

    for i in 0..N {
        if state[i] == VISITED {
            continue;
        }

        let mut s = Vec::<usize>::new();
        let mut start = i;

        s.push(start);

        while !s.is_empty() {
            let top = s.pop().unwrap();
            let next = bypass[top];
            state[top] = VISITING;

            if top == next {
                // 이 경우는 한 경로의 끝에 도달한 경우이다. 
                // mark_as_visited(start, top, &bypass, &mut state);
                mark_as(start, top, next, &bypass, &mut state, &mut answer);
                break;
            }

            if state[next] == VISITED {
                // 이미 방문한 노드라면 next의 answer가 top의 answer가 된다. 
                mark_as(start, top, answer[next], &bypass, &mut state, &mut answer);
                break;
            } else if state[next] == VISITING {
                // 이 경우엔 현재 탐색 중 사이클이 발생한 경우이다. 
                // 사이클이 발생한 폐쇠 경로의 시작점과 끝점을 구해서 mark_cycle 호출 
                // 탐색 시작점과 폐쇠 경로 시작점의 이전 노드까지는 폐쇠 경로 시작 노드의 
                // 이전 노드로 채워준다. 
                mark_cycle(start, next, top, &bypass, &mut answer, &mut state);
                break;
            } else {
                s.push(next);
            }
        }
    }

    for i in 0..N {
        print!("{} ", answer[i] + 1);
    }
    println!();
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
