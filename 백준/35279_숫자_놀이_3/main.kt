import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue
import kotlin.math.min


fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.`out`))

    class Solution {

        private val b: Int 

        private val k : Int

        private val limit: Long = 10_000_000_000_000_000L

        init {
            val tokens = reader.readLine().split(" ")
            b = tokens[0].toInt()
            k = tokens[1].toInt()
        }

        fun run() {

            val pq = PriorityQueue<Long>()
            val visited = mutableSetOf<Long>()

            for(i in 1L until b.toLong()) {
                pq.offer(i)
                visited.add(i)
            }

            var powers = mutableListOf<Long>()
            var results = mutableListOf<Long>()
            var value = 1L

            while ( true) {
                powers.add( value )
                if ( value > limit / b ) break
                value *= b
            }

            // 판별하지 않고 역으로 생성해서 잘라내기
            while ( pq.isNotEmpty() && results.size < k ) {
                val cur = pq.poll()
                results.add(cur)

                for ( l in 2 until powers.size + 1 ) {
                    val nxt = cur * l 
                    val lo = powers[l-1]
                    val hi = if ( l < powers.size ) powers[l] else Long.MAX_VALUE

                    if ( nxt > limit ) break
                    if ( lo <= nxt && nxt < hi ) {
                        if ( visited.add(nxt) ) {
                            pq.offer(nxt)
                        }
                    }
                }
            } 

            for ( i in 0 until min(k, results.size)) {
                writer.write("${results[i]}\n")
            }

            writer.flush()
        }
    }

    val solution = Solution()
    solution.run()
}

