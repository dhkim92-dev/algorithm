#include <string>
#include <vector>
#include <unordered_map>
#include <iostream>


using namespace std;

struct Date{
    int year;
    int month;
    int day;
 	
    Date(string d){
        year = stoi(d.substr(0, 4));
        month = stoi(d.substr(5, 2));
        day = stoi(d.substr(8, 2));
        
        //cout << year << "." << month << "." << day << endl;
    }
    int to_int(){
        return year * 28*12 + month * 28 + day;
    }
};

vector<int> solution(string today, vector<string> terms, vector<string> privacies) {
    vector<int> answer;
    
    Date t(today);
    int today_int = t.to_int();
    unordered_map<string, int> terms_int;
    
    for(auto s : terms){
        string term_name = s.substr(0, 1);
        int month = 28 * stoi(s.substr(2, s.length() - 2));
        //cout << "term_name: " << term_name << endl;
        terms_int[term_name] = month;
    }
    
    for(int i = 0 ; i < privacies.size() ; i++){
        string priv = privacies[i];
        Date date(priv.substr(0, 10));
        int days=date.to_int();
        string term = priv.substr(priv.length()-1, 2);
        
        //cout << "term : " << term << endl;
        int remain_days = today_int - days;
        
        if(remain_days >= terms_int[term]){
            answer.push_back(i+1);
        }
    }
    
    return answer;
}
