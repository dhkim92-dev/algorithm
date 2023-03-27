#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int get_distance(int a, int b){
    return a+b;
}

int solution_twopointer(vector<int> &liquids){
    int left = 0, right = liquids.size() - 1;
    
    int min_value = INT32_MAX;
    int left_value = liquids[left];
    int right_value = liquids[right];
    int value = 0;

    while(left < right){
        value = get_distance(liquids[left], liquids[right]);

        if(value < 0){
            if(abs(min_value) > abs(value)){
                left_value = liquids[left];
                right_value = liquids[right];
                min_value = abs(value);
            }
            left++;
        }else if(value > 0){
            if(abs(min_value) > abs(value)){
                left_value = liquids[left];
                right_value = liquids[right];
                min_value = abs(value);
            }
            right--;
        }else{
            left_value = liquids[left];
            right_value = liquids[right];
            break;
        }
    }

    cout << left_value << " " << right_value << endl;

    return 0;
}

int solution_binarysearch(vector<int>& liquids){
    int opt = INT32_MAX;
    int lval, rval;
    
    for(int i = 0 ; i < liquids.size() - 1 ; i++){
        int target = liquids[i];
        int left = i+1, right = liquids.size() - 1;
        int mid;
        while(left <= right){
            mid = (left) + (right - left) / 2;
            int value = target + liquids[mid];

            if(opt > abs(value)){
                opt = abs(value);
                lval = liquids[i];
                rval = liquids[mid];
            }

            if(value < 0){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
    }

    cout << lval << " " << rval << endl;

    return 0;
}

int main(void){
    int n;
    vector<int> liquids;

    cin >> n ;

    int value;
    
    for(int i = 0 ; i < n ; i++){
        cin >> value;
        liquids.push_back(value);
    }

    solution_binarysearch(liquids);

    return 0;
}
