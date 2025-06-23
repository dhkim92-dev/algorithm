#include <iostream>
// #include <thread>
#include <vector>
#include <cstring>
#include <algorithm>

using namespace std;

static const int UP = 0;

static const int DOWN = 1;

static const int LEFT = 2;

static const int RIGHT = 3;

struct Pos {

    int r, c;

    bool operator==(const Pos& other) const {
        return r == other.r && c == other.c;
    }

    Pos operator+(const Pos& other) const {
        return Pos{r + other.r, c + other.c};
    }
};

struct PosHash {

    bool operator()(const Pos& p) const {
        return 20 * p.r + p.c;
    }
};


struct Smell {
    int shark_no;
    int time_left; // 남은 시간
};

static const Pos DIRS[4] = {
    { -1, 0 }, // UP
    { 1, 0 },  // DOWN
    { 0, -1 }, // LEFT
    { 0, 1 }   // RIGHT
};

struct SharP1+r4D73=1B5D35323B25703125733B257032257307\k {

public:

    int no;

    Pos current;

    int d; // direction

    int priorities[4][4];

    bool is_alive = true;
};

struct SharkHash {

    size_t operator()(const Shark& shark) const {
        return static_cast<size_t>(shark.no) * 31;
    }
};

class Solution {

private:

    int N, M, k;

    vector<vector<int>> grid;

    vector<vector<int>> tmp;

    vector<Shark> sharks;

    vector<vector<Smell>> smells;

    void clear_grid(vector<vector<int>>& board) {
        for (vector<int>& row : board) {
            fill(row.begin(), row.end(), 0);
        }
    }

    void vanish_smell() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (smells[i][j].shark_no > 0) {
                    smells[i][j].time_left--;
                    if (smells[i][j].time_left <= 0) {
                        smells[i][j] = { 0, 0 }; // 냄새 제거
                    }
                }
            }
        }
    }

    void remain_smell() {
        for ( auto& shark : sharks ) {
            Pos pos = shark.current;
            smells[pos.r][pos.c].shark_no = shark.no;
            smells[pos.r][pos.c].time_left = k; // 냄새 남은 시간 초기화
        }
    }


    void move_sharks() {
        clear_grid(grid);

        for ( auto& shark : sharks ) {
            // 각 상어 별로 현재 위치와 방향을 가지고, 4가지 방향을 확인
            // 이 때 이동 가능한 첫번째 방향에서 바로 이동을 시작
            // cout << "Shark " << shark.no << " at " << shark.current.r << ", " << shark.current.c << " with direction " << shark.d + 1 << endl;
            bool moved = false;
            for ( int i = 0 ; i < 4 ; ++i ) {
                Pos nxt = shark.current + DIRS[shark.priorities[shark.d][i]];
                if (nxt.r < 0 || nxt.r >= N || nxt.c < 0 || nxt.c >= N) {
                    continue; // 범위를 벗어난 경우
                }

                if ( smells[nxt.r][nxt.c].shark_no > 0 ) { continue; } // 냄새가 있는 경우 이동 불가
                if ( grid[nxt.r][nxt.c] > 0 && grid[nxt.r][nxt.c] < shark.no ) {
                    shark.is_alive = false; // 자신보다 번호가 작은 상어가 있으면 자신이 죽는다.
                    grid[shark.current.r][shark.current.c] = 0; // 현재 위치는 비워준다.
                    moved = true;
                    break;
                } // 자신보다 번호가 작은 다른 상어가 있는 경우 이동 불가
                if ( grid[nxt.r][nxt.c] > 0 && grid[nxt.r][nxt.c] > shark.no ) {
                    sharks[grid[nxt.r][nxt.c] - 1].is_alive = false; // 다른 상어가 있는 경우, 해당 상어를 죽인다.
                }
                moved = true;
                grid[shark.current.r][shark.current.c] = 0; // 현재 위치는 비워준다.
                grid[nxt.r][nxt.c] = shark.no;
                shark.current = nxt; // 상어 위치 업데이트
                shark.d = shark.priorities[shark.d][i]; // 방향 업데이트
                break;
            }

            if ( moved ) continue;
            // cout << "Shark " << shark.no << " could not move to any empty cell." << endl;
            // 만약 이동하지 못한 경우, 자신의 냄새가 있는 칸으로 이동
            for ( int i = 0 ; i < 4 ; ++i ) {
                Pos nxt = shark.current + DIRS[shark.priorities[shark.d][i]];
                if ( nxt.r < 0 || nxt.r >= N || nxt.c < 0 || nxt.c >= N ) { continue; } // 범위를 벗어난 경우
                if ( smells[nxt.r][nxt.c].shark_no != shark.no ) { continue; } // 자신의 냄새가 없는 경우 이동 불가
                grid[nxt.r][nxt.c] = shark.no;
                shark.current = nxt; // 상어 위치 업데이트
                shark.d = shark.priorities[shark.d][i]; // 방향 업데이트
                break;
            }
        }
    }

    void remove_sharks() {
        // sharks 배열을 순회하며, 죽은 상어는 벡터에서 제거한다.
        for (auto it = sharks.begin(); it != sharks.end();) {
            if (!it->is_alive) {
                it = sharks.erase(it); // 죽은 상어 제거
            } else {
                ++it; // 살아있는 상어는 유지
            }
        }
    }

    bool check_end() {
        return sharks.size() == 1;
    }

    void print_grid(int turn) {
        cout<< "Current Grid State:" << turn << endl;
        for (const auto& row : grid) {
            for (int cell : row) {
                cout << cell << " ";
            }
            cout << endl;
        }
        // this_thread::sleep_for(chrono::milliseconds(1000)); // 디버깅용 지연
    }

public:

    Solution() {
        cin >> N >> M >> k;
        grid.resize(N, vector<int>(N, 0));
        // tmp = vector<vector<int>>(N, vector<int>(N, 0));
        smells.resize(N, vector<Smell>(N, { -1, 0 }));
        sharks.resize(M); // 1-indexed, so M + 1

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                int no;
                cin >> no;
                if (no > 0) {
                    sharks[no - 1].no = no;
                    sharks[no - 1].current = {i, j};
                    grid[i][j] = no; // 상어 번호로 그리드 초기화
                }
            }
        }

        for ( int i = 0 ; i < M ; ++i) {
            cin >> sharks[i].d;
            sharks[i].d -= 1; // convert to 0-indexed
        }

        for ( int i = 0 ; i < M ; ++i ) {
            for ( int j = 0 ; j < 4 ; ++j ) {
                for ( int k = 0 ; k < 4 ; ++k ) {
                    cin >> sharks[i].priorities[j][k];
                    sharks[i].priorities[j][k] -= 1; // convert to 0-indexed
                }
            }
        }
    }

    void run() {
        int turn = 0;
        remain_smell();
        // print_grid(turn); // 디버깅용 출력
        while(turn < 1000) {
            move_sharks();
            remove_sharks();
            vanish_smell();
            remain_smell();

            // print_grid(turn+1); // 디버깅용 출력

            if ( check_end() ) {
                cout << turn + 1 << endl;
                return;
            }
            turn++;
        };
        cout << -1 << endl;
    }
};

int main(void) {
    std::ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    Solution sol;
    sol.run();
    return 0;
}
