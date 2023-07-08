#include <iostream>
#include <vector>
#include <string>

using namespace std;

#define MAX 19
int max_values[MAX][MAX];
int min_values[MAX][MAX];
int N;
string s;

void init() {
    cin >> N;
    cin >> s;

    for(int i = 0 ; i < MAX ; i++) {
        for(int j = 0 ; j < MAX ; j++) {
            max_values[i][j] = -987654321;
            min_values[i][j] = 987654321;
        }
    }

    for(int i = 0 ; i < N ; i+=2) {
        max_values[i][i] = s[i]-'0';
        min_values[i][i] = max_values[i][i];
    }
}

int calc(int a, int b, char oprt){
    switch(oprt) {
        case '+' : 
            return a+b;
        case '-' :
            return a-b;
        case '*' :
            return a*b;
    }
    return 0;
}

int solution() {
    int answer = 0;

    init();

    for(int j = 2 ; j < N ; j+=2) {
        for(int i = 0 ; i < N - j ; i+=2) {
            for(int k = 2 ; k <= j ; k+=2) {
                char op = s[i+k-1];

                int candidates[4] = {
                    calc(max_values[i][i+k-2], max_values[i+k][i+j], op),      
                    calc(max_values[i][i+k-2], min_values[i+k][i+j], op),      
                    calc(min_values[i][i+k-2], max_values[i+k][i+j], op),      
                    calc(min_values[i][i+k-2], min_values[i+k][i+j], op)
                };

                sort(candidates, candidates+4);
                
                max_values[i][i+j] = max(candidates[3], max_values[i][i+j]);
                min_values[i][i+j] = min(candidates[0], min_values[i][i+j]);
            }
        }
    }

    answer = max_values[0][N-1];
    return answer;
}

int main(void)
{
    init();
    cout << solution() << endl;
    return 0;
}
