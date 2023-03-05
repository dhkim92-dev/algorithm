struct Pos{
    int r,c,diff;
    Pos operator + (const Pos &p) const {
        return {r + p.r, c + p.c , diff + p.diff};
    }

    bool operator == (const Pos &p) const {
        return (r==p.r) && (c==p.c);
    }

    bool operator < (const Pos & p) const {
        return diff > p.diff;
    }
};

Pos dirs[4] = {
    {-1, 0,0}, // Up
    {0, 1, 0}, // Right
    {1, 0, 0}, // Down
    {0, -1, 0} // Left
};

class Solution {
    int sz_rows, sz_cols;
private:

    bool range_check(Pos &p){
        return (0<=p.r && p.r < sz_rows) && (0<=p.c && p.c < sz_cols); 
    }

    int dijk(vector<vector<int>> &heights){
        priority_queue<Pos> pq;
        vector<vector<int>> dists(sz_rows, vector<int>(sz_cols, 1e9));
        pq.push({0, 0, 0});
        dists[0][0] = 0;
        Pos dest = {sz_rows-1, sz_cols -1, 0};

        auto get_height = [&](Pos p){
            return heights[p.r][p.c];
        };

        while(!pq.empty()){
            Pos cur = pq.top();
            pq.pop();

            if(cur == dest){
                return cur.diff;
            }

            for(int i = 0 ; i < 4 ; i++){
                Pos nxt = cur + dirs[i];

                if(!range_check(nxt)) continue;
                int next_diff = max(cur.diff,abs(get_height(cur) - get_height(nxt)));

                if(next_diff < dists[nxt.r][nxt.c]){
                    nxt.diff = next_diff;
                    dists[nxt.r][nxt.c] = next_diff;
                    pq.push(nxt);
                }
            }
        }
        return 0;
    }

public:
    int minimumEffortPath(vector<vector<int>>& heights) {
        sz_rows = heights.size();
        sz_cols = heights[0].size();

        return dijk(heights);
    }
};