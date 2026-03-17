
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.Queue
import kotlin.math.min


fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.`out`))

    class Solution {
        
        private val alchols = listOf(18L, 14L, 9L, -4L)

        private val GCD = 5

        private val LCM = 126

        private var N: Long = 0L

        init {
            val input = reader.readLine().replace(".", "")
            N = input.toLong()
        }

        fun solve(): String {
            var answer = Long.MAX_VALUE
            if ( N % GCD != 0L ) {
                return "-1"
            }
            N /= GCD

            val q: Queue<Long> = LinkedList<Long>()
            val LIMIT = LCM
            // val LIMIT: Int = 500
            val dist: LongArray = LongArray(LIMIT+1) { -1L }
            dist.fill(-1L)
            dist[0] = 0L

            q.offer(0L)


            while ( q.isNotEmpty() ) {
                val current = q.poll()

                for ( i in 0 until 4 ) {
                    val next = current + alchols[i]

                    if ( next < 0L || next > LIMIT ) {
                        continue
                    }

                    if ( dist[next.toInt()] != -1L ) {
                        continue
                    }
                    dist[next.toInt()] = dist[current.toInt()] + 1
                    q.offer(next)
                }
            }

            for ( v in 0 .. LIMIT ) {
                if ( dist[v.toInt()] == -1L ) continue
                if ( v > N ) continue
                val diff = N - v

                if ( diff % 18 != 0L ) continue
                answer = min(answer, dist[v.toInt()] + diff / 18)
            }

            return if ( answer == Long.MAX_VALUE ) {
                "-1"
            } else {
                answer.toString()
            }
        }

        fun run() {
            val answer = solve()
            writer.write("$answer\n")
            writer.flush()
        }
    }

    val solution = Solution()
    solution.run()
}

