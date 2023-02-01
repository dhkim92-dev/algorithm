#include <string>
#include <vector>
#include <queue>
// #include <unordered_map>

using namespace std;

struct Node{
    int index;
    vector<Node *> childrens;

    bool has_child(){
        return !childrens.empty();
    }

    ~Node(){
        for(auto &child : childrens){
            delete child;
        }
        childrens.clear();
    }
};

Node *root=nullptr;

void build_graph(vector<vector<int>> &graph, vector<vector<int>> &lighthouse){
    for(auto conn : lighthouse){
        graph[conn[0]].push_back(conn[1]);
        graph[conn[1]].push_back(conn[0]);
    }
}

void find_root(vector<vector<int>> &graph){
    for(int i = 1 ; i < graph.size() ; i++){
        if(graph[i].size() > 1){
            root = new Node();
            root->index = i;
            break;
        }
    }

    if(root == nullptr){
        root = new Node();
        root->index=1;
    }
}

void build_tree(int nr_nodes, vector<vector<int>> &graph){
    queue<Node *> q;
    q.push(root);
    vector<bool> visit(nr_nodes+1, false);
    visit[root->index] = true;

    while(!q.empty()){
        Node *cur = q.front();
        q.pop();

        for(auto index : graph[cur->index]){
            if(visit[index]) continue;
            Node *nxt = new Node();
            nxt->index = index;
            cur->childrens.push_back(nxt);
            q.push(nxt);
            visit[nxt->index] = true;
        }   
    }
}

void simulation(Node *root, vector<bool> &lighthoust_status){
    if(!root->has_child()){
        return ;
    }

    bool turn_on = false;

    for(auto child_ptr : root->childrens){
        simulation(child_ptr, lighthoust_status);

        if(!lighthoust_status[child_ptr->index]){
            turn_on = true;
        }
    }

    if(turn_on){
        lighthoust_status[root->index] = true;
    }
}

int solution(int n, vector<vector<int>> lighthouse) {
    int answer = 0;
    int nr_nodes = n;
    vector<vector<int>> graph(nr_nodes+1);
    vector<bool> lighthouse_status(nr_nodes+1, false);
    build_graph(graph, lighthouse);
    find_root(graph);
    build_tree(nr_nodes, graph);
    simulation(root, lighthouse_status);

    for(auto status : lighthouse_status){
        answer += static_cast<int>(status);
    }

    return answer;
}