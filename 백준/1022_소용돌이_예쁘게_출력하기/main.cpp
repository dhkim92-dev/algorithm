#include <algorithm>
#include <cstdio>
#include <iostream>
#include <string>

using namespace std; 

int r0, r1, c0, c1;
int arr[51][51];
size_t max_length = 0;


void init() {
    cin >> r0 >> c0 >> r1 >> c1;
}

void fill_array() {
    for(int r = r0 ; r <= r1 ; r++) {
        for(int c = c0 ; c <= c1 ; c++) {
            int y = r - r0;
            int x = c - c0;

            if(r - c < 0) {
                if(r + c < 0) {
                    arr[y][x] = 4 * r *r + r - c + 1;
                }else {
                    arr[y][x] = 4 * c *c - 3 * c  -r + 1;
                }
            }else{
                if(r + c < 0) {
                    arr[y][x] = 4 * c *c -c + r + 1;
                } else {
                    arr[y][x] = 4 * r * r + 3 * r + c + 1;
                }
            }

            max_length = max(to_string(arr[y][x]).length(),  max_length);
        }
    }
}

void print() {
    for(int r = r0 ; r <= r1 ; r++) {
        for(int c = c0 ; c <= c1 ; c++) {
            int y = r - r0;
            int x = c - c0;
            printf("%*d ", (int)max_length, arr[y][x]);
        }
        cout << endl;
    }
}

int main(void)
{
    init();
    fill_array();
    print();
    return 0;
}