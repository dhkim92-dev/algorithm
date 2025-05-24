import java.io.*;
import java.util.*;

class Solution {
    
    private int[][] mxDp;
    private int[][] mnDp;
    
    private int search(String[] tokens) {
        int N = tokens.length / 2 + 1;
        mxDp = new int[N][N];
        mnDp = new int[N][N];
        
        for ( int i = 0 ; i < mxDp.length ; ++i ) {
            Arrays.fill(mxDp[i], Integer.MIN_VALUE);
            Arrays.fill(mnDp[i], Integer.MAX_VALUE);
            mnDp[i][i] = Integer.parseInt( tokens[i*2] );
            mxDp[i][i] = Integer.parseInt( tokens[i*2] );
        }
        
        for ( int sz = 1 ; sz < N ; ++sz ) {
            for ( int left = 0 ; left < N - sz ; ++left ) {
                int right = left + sz;
                for( int mid = left ; mid < right ; ++mid ) {
                    int mnLeft = mnDp[left][mid];
                    int mxLeft = mxDp[left][mid];
                    int mnRight = mnDp[mid+1][right];
                    int mxRight = mxDp[mid+1][right];
                    
                    if ( tokens[2 * mid + 1].equals("+") ) {
                        mxDp[left][right] = Math.max(mxDp[left][right], mxLeft + mxRight);
                        mnDp[left][right] = Math.min(mnDp[left][right], mnLeft + mnRight);
                    } else {
                        mxDp[left][right] = Math.max(mxDp[left][right], mxLeft - mnRight);
                        mnDp[left][right] = Math.min(mnDp[left][right], mnLeft - mxRight);
                    }
                }
            }
        }
        
        return mxDp[0][N-1];
    }
    
    public int solution(String arr[]) throws IOException {
        int answer = search(arr);
        return answer;
    }
}
