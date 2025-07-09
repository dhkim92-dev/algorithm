#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

typedef struct {
    int32_t shark_no;
    int32_t left_time;
} smell_t;

typedef struct {
    int32_t no;
    int32_t r;
    int32_t c;
    int32_t dir;
    int32_t priorities[4][4];
    uint8_t is_alive;
} shark_t;

int32_t directions[4][2] = {
    {-1, 0}, // Up
    {1, 0},  // Down
    {0, -1}, // Left
    {0, 1}   // Right
};

#define MAX_SHARKS 400

#define MAX_SIZE 20

#define TRUE 1 

#define FALSE 0

int32_t grid[MAX_SIZE][MAX_SIZE];
shark_t sharks[MAX_SHARKS];
smell_t smell[MAX_SIZE][MAX_SIZE];
int32_t N, M, k;
int32_t shark_count = 0;

void init();
void put_smell();
void move_sharks();
void remove_sharks();
void vanish_smell();
uint8_t check_end();
void print_grid(int turn);

void run() {
    init();
    int time = 0;

    put_smell();
    // print_grid(time);
    while (time < 1000) {
        time++;
        move_sharks();
        //remove_sharks();
        vanish_smell();
        put_smell();
        // print_grid(time);

        if (check_end()) {
            printf("%d\n", time);
            return;
        }
    }

    printf("-1\n");
}

int main(void) {
    run();
    return 0;
}

uint8_t check_end() {
    return (shark_count == 1) ? TRUE : FALSE;
}

void put_smell() {
    for ( size_t i = 0 ; i < M ; ++i ) {
        if (!sharks[i].is_alive) continue;
        int r = sharks[i].r;
        int c = sharks[i].c;
        smell[r][c].shark_no = sharks[i].no;
        smell[r][c].left_time = k;
    }
}

void vanish_smell() {
    for ( size_t i = 0 ; i < N ; ++i ) {
        for ( size_t j = 0 ; j < N ; ++j ) {
            if (smell[i][j].left_time > 0) {
                smell[i][j].left_time--;
                if (smell[i][j].left_time == 0) {
                    smell[i][j].shark_no = 0; // No shark left
                }
            }
        }
    }
}

void print_grid(int turn) {
    printf("Turn %d:\n", turn);
    for ( size_t i = 0 ; i < N ; ++i ) {
        for ( size_t j = 0 ; j < N ; ++j ) {
            printf("%d ", grid[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void clear_grid() {
    for ( size_t i = 0 ; i < N ; ++i ) {
        for ( size_t j = 0 ; j < N ; ++j ) {
            grid[i][j] = 0;
        }
    }
}

void move_sharks() {
    clear_grid();

    for ( size_t i = 0 ; i < M ; ++i ) {
        if (!sharks[i].is_alive) continue;
        uint8_t moved = FALSE;
        int r = sharks[i].r;
        int c = sharks[i].c;
        int dir = sharks[i].dir;

        for ( size_t j = 0 ; j < 4 ; ++j ) {
            int nr = r + directions[sharks[i].priorities[dir][j]][0];
            int nc = c + directions[sharks[i].priorities[dir][j]][1];

            if ( nr < 0 || nr >= N || nc < 0 || nc >= N ) {
                continue;
            }

            if ( smell[nr][nc].shark_no > 0 ) continue;

            if ( grid[nr][nc] > 0 && grid[nr][nc] < sharks[i].no ) {
                sharks[i].is_alive = FALSE;
                shark_count--;
                moved = TRUE;
                break;
            }

            if ( grid[nr][nc] > 0 && grid[nr][nc] > sharks[i].no ) {
                sharks[grid[nr][nc]-1].is_alive = FALSE;
                shark_count--;
                grid[nr][nc] = sharks[i].no;
            }
            grid[r][c] = 0;
            grid[nr][nc] = sharks[i].no;
            sharks[i].r = nr;
            sharks[i].c = nc;
            sharks[i].dir = sharks[i].priorities[dir][j];
            moved = TRUE;
            break;
        }

        if (moved) {continue;}

        for ( size_t j = 0 ; j < 4 ; ++j ) {
            int new_dir = sharks[i].priorities[dir][j];
            int nr = r + (new_dir == 0 ? -1 : (new_dir == 1 ? 1 : 0));
            int nc = c + (new_dir == 2 ? -1 : (new_dir == 3 ? 1 : 0));

            if ( nr < 0 || nr >= N || nc < 0 || nc >= N ) {
                continue;
            }

            if ( smell[nr][nc].shark_no != sharks[i].no ) {
                continue;
            }

            grid[r][c] = 0;
            grid[nr][nc] = sharks[i].no;
            sharks[i].r = nr;
            sharks[i].c = nc;
            sharks[i].dir = new_dir;
            moved = TRUE;
            break;
        }
    }
}

void init() {
    scanf("%d %d %d", &N, &M, &k);
    shark_count = M;
    for ( size_t i = 0 ; i < N ; ++i ) {
        for ( size_t j = 0 ; j < N ; ++j ) {
            scanf("%d", &grid[i][j]);
            if ( grid[i][j] > 0 ) {
                sharks[grid[i][j] - 1].no = grid[i][j];
                sharks[grid[i][j] - 1].r = i;
                sharks[grid[i][j] - 1].c = j;
                sharks[grid[i][j] - 1].dir = 0; // Initial direction can be set to any valid value
                sharks[grid[i][j] - 1].is_alive = TRUE;
            }
        }
    }

    for ( size_t i = 0 ; i < M ; ++i ) {
        scanf("%d", &sharks[i].dir);
        sharks[i].dir--; // Convert to 0-based index
    }

    for ( size_t i = 0 ; i < M ; ++i ) {
        for ( size_t j = 0 ; j < 4 ; ++j ) {
            for ( size_t l = 0 ; l < 4 ; ++l ) {
                scanf("%d", &sharks[i].priorities[j][l]);
                sharks[i].priorities[j][l]--; // Convert to 0-based index
            }
        }
    }
}
