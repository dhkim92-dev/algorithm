#include <iostream>
#include <vector>

using namespace std;


void init_world(vector<vector<int>> &world, int n, int m){
    int height = 0;

    for(int i = 0 ; i < m ; i++){
        world[n][i] = 1;

        cin >> height;

        for(int j = 0 ; j < height ; j++){
            world[n-j-1][i] = 1;
        }
    }
}

void print_world(vector<vector<int>> &world){
    for(int i = 0 ; i < world.size() ; i++){
        for(int j = 0 ; j < world[0].size() ; j++){
            cout << world[i][j];
        }
        cout << endl;
    }
}

void masking_impossible_area(vector<vector<int>> &world){
    int r = world.size();
    int c = world[0].size();

    for(int i = 0 ; i < r ; i++){
        if(world[i][0] == 0){
            for(int j = 0 ; j < c ; j++){
                if(world[i][j] == 0){
                    world[i][j] = -1;
                }else{
                    break;
                }
            }
        }

        if(world[i][c-1] == 0){
            for(int j = c-1 ; j >= 0 ; j--){
                if(world[i][j] == 0){
                    world[i][j] = -1;
                }else{
                    break;
                }
            }
        }
    }
}

int count_zero_area(vector<vector<int>> &world){
    int cnt = 0 ;

    for(int i = 0 ; i < world.size() ; i++){
        for(int j = 0 ; j < world[0].size() ; j++){
            cnt += static_cast<int>(world[i][j] == 0);
        }
    }
    return cnt;
}


int main(void)
{
    int r, c;

    cin >> r >> c;

    vector<vector<int>> world(r+1, vector<int>(c, 0));

    init_world(world, r, c);
    //print_world(world);
    masking_impossible_area(world);
    //print_world(world);
    cout << count_zero_area(world) << endl;
    
    return 0;
}
