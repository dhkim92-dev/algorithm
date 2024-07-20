import kotlin.math.*;

class Solution {
    
    val MOD = (1e9+7).toInt()
    
    fun prefixSum(arr: IntArray): LongArray {
        val psum = LongArray(arr.size + 1)
        psum[0] = 0
        
        for(i in 1..arr.size) {
            psum[i] = psum[i-1] + arr[i-1]
        }
        
        return psum
    }
    
    fun parametricSearch(arr: LongArray, target: Long): Int {
        var left = 0
        var right = arr.size-1
        
        while(left <= right) {
            val mid = left + (right-left)/2
            
            if(arr[mid] < target) {
                left = mid + 1
            } else if(arr[mid] == target) {
                return mid
            } else {
                right = mid - 1
            }
        }
        
        return -1
    }
    
    fun partialSolution(b: IntArray, bLen: Int): Int {
        val psum = prefixSum(b)
        val limit = ceil(log(psum[psum.size-1].toDouble(), 2.0)).toInt()
        val dp = Array(bLen){IntArray(limit+1)}
        
        dp[0][0] = 1
        
        if(bLen == 1) return dp[0][0]
        
        for(i in 1 until bLen) {
            dp[i][0] = dp[i-1].reduce{a, b -> (a+b)%MOD}
            
            for(j in 1..limit) {
                if(dp[i][j-1] == 0) {
                    continue
                }
				val half = b[i] * 2.0.pow(j-1).toLong()
                var target = psum[i+1] - half
                val yLast = parametricSearch(psum, target) - 1
                
                if(yLast < 0) {
                    continue
                } 
                
                val k = (j-1) + log(b[i].toDouble()/b[yLast] , 2.0)
                
                if(k != k.toInt().toDouble()) {
                    continue
                }
                
                if(k < 0) {
                    continue
                }
                
                if(dp[yLast][k.toInt()] <= 0) {
                    continue
                }
                
                target = psum[yLast+1] - half
                val leftLast = parametricSearch(psum, target)
                
                if(leftLast < 0) {
                    continue
                }
                
                if(leftLast == 0) {
                    dp[i][j] = 1
                    continue
                } 
                
                dp[i][j] = dp[leftLast-1].reduce {a, b -> (a+b)%MOD}
            }
        }
        
        return dp[bLen-1].reduce{a,b -> (a+b)%MOD};
    }
    
    fun solution(a: IntArray, s: IntArray): IntArray {
        var answer: IntArray = IntArray(s.size)
        
        var lastOffset = 0
        for(i in 0 until s.size) {
            val b: IntArray = a.sliceArray(lastOffset .. lastOffset+s[i] - 1)
            answer[i] = partialSolution(b, s[i])
            lastOffset += s[i]
        }
        
        return answer
    }
}
