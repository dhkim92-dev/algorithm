#include <iostream>
#include <vector>
#include <string>

using namespace std;

int N, M;
vector<int> root(51);

int find_root(int index) {
    if(root[index] == index) return index;
    return index = find_root(root[index]);
}

void merge(int left, int right) {
    int l = find_root(left);
    int r = find_root(right);

    if(l < r) {
        root[r] = l;
    }else{
        root[l] = r;
    }
}

void init_root() {
    for(int i = 0 ; i < 51 ; i++){
        root[i] = i;
    }
}

void set_knowns() {
    int nr_knowns;
    int index;
    cin >> nr_knowns;

    for(int i = 0 ; i < nr_knowns ; i++) {
        cin >> index;
        root[index] = 0;
    }
}

void do_party(vector<vector<int>> &parties, int party_no) {
    int nr_people;
    int index;
    cin >> nr_people;

    if(nr_people == 0) return ;
    cin >> index;
    parties[party_no].resize(nr_people);
    parties[party_no][0] = index;
    int first_guest = index;
    
    for(int i = 1 ; i < nr_people ; i++) {
        cin >> index;
        int current = index;
        merge(first_guest, current);
        parties[party_no][i] = current;
    }
}

int solution(int nr_parties) {
    int answer = nr_parties;
    vector< vector<int> > parties(nr_parties);
    
    for(int i = 0 ; i < nr_parties ; i++) do_party(parties, i);

    for(int i = 0 ; i < nr_parties ; i++) {
        for(auto val : parties[i]) {
            if(!find_root(val)) {
                answer--;
                break;
            }
        }
    }

    return answer;
}

int main(void) {
    cin >> N >> M;
    
    init_root();
    set_knowns();
    cout << solution(M) << endl;

    return 0;
}
