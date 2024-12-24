#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
    int N;
    vector<string> origin, words;
    string answer[2];
    string prefix;

    void init() {
        cin >> N;
        origin.resize(N);

        for(int i = 0 ; i < N ; i++) {
            cin >> origin[i];
        }

        copy(origin.begin(), origin.end(), back_inserter(words));
    }

    size_t check_prefix_length(string &w1, string &w2) {
        size_t min_length = min(w1.length(), w2.length());
        size_t cnt = 0;

        for(int l = 0 ; l < min_length ; l++) {
            if(w1[l] != w2[l]) break;
            cnt++;
        }

        return cnt;
    }

    string get_prefix(string &w1, string &w2) {
        size_t min_length = min(w1.length(), w2.length());
        size_t i = 0;
        for(; i < min_length ; i++) {
            if(w1[i] != w2[i]) break;
        }

        return w1.substr(0, i);
    }

    void search_prefix(int from, int until) {
        for(int i = from ; i < until ; i++) {
            string &w1 = words[i];
            for(int j = i  ; j < until ; j++) {
                if(i == j) continue;
                string &w2 = words[j];
                string pfx = get_prefix(w1, w2);

                if(pfx.length() > prefix.length()) {
                    prefix = pfx;
                }
            }
        }
    }

    void simulate() {
        sort(words.begin(), words.end());

        for(int i = 0 ; i < words.size() ; i++) {
            string &w1 = words[i];
            int j = i+1;

            for( ; j < words.size() ; j++) {
                string &w2 = words[j];
                if(w1[0] != w2[0]) break;
            }

            search_prefix(i, j);
            i = j;
        }
        find_targets();
    }

    void find_targets() {
        // cout << "prefix : " << prefix << endl;
        size_t length = prefix.length();
        int cnt = 0;

        for(string &w : origin) {
            if(cnt == 2) break;

            if(w.length() < length) continue;
            
            if(w.substr(0, length) == prefix) {
                answer[cnt++] = w;
            }
        }
    }

    void print() {
        // for(auto w : words) {
            // cout << w << "\n";
        // }
        // cout << "------------\n";
        cout << answer[0] << endl;
        cout << answer[1] << endl;
    }

public :
    void run() {
        init();
        simulate();
        print();
    }
};

int main(void)
{
    Solution sol;
    sol.run();
    return 0;
}