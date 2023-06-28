#include <iostream>
#include <vector>
#include <string>

using namespace std;

struct  Room {
    string type;
    int limits;
    vector<int> next_rooms;
    bool visited = false;
};


void explore(int idx, int deposit, vector<Room> &rooms, bool &answer) {
    if(answer) return ;

    if(idx == rooms.size() - 1) {
        answer = true;
        return ;
    }

    vector<int> &next = rooms[idx].next_rooms;

    for(int i = 0 ; i < next.size() ; i++) {
        string type = rooms[next[i]].type;
        int limits = rooms[next[i]].limits;
        if(rooms[next[i]].visited) continue;
        if(type == "T"){
            if(deposit < limits) continue;
            rooms[next[i]].visited = true;
            explore(next[i], deposit-limits, rooms, answer);
            rooms[next[i]].visited = false;
        }else if(type == "L") {
            rooms[next[i]].visited = true;
            explore(next[i], (deposit >= limits) ? deposit : limits, rooms, answer);
            rooms[next[i]].visited = false;
        }else {
            rooms[next[i]].visited = true;
            explore(next[i], deposit, rooms, answer);
            rooms[next[i]].visited = false;
        }
    }
}

void solution(int nr_maze) {
    vector<Room> rooms(nr_maze);

    for(int i = 0 ; i < nr_maze ; i++) {
        cin >> rooms[i].type;
        cin >> rooms[i].limits;
        int idx;

        while(true) {
            cin >> idx;
            if(idx == 0) break;
            rooms[i].next_rooms.push_back(idx-1);
        }
    }
    bool answer = false;
    rooms[0].visited = true;
    explore(0, 0, rooms, answer);

    if(answer == true) {
        cout << "Yes\n";
    } else {
        cout << "No\n";
    }
}
 
int main(void)
{
    int nr_maze;

    while(true){
        cin >> nr_maze;
        if(nr_maze == 0) break;
        solution(nr_maze);
    }

    return 0;
}
