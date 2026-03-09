import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.PriorityQueue
import java.util.StringTokenizer
import java.util.stream.Collectors
import kotlin.io.path.Path
import kotlin.io.path.name

fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.`out`))

    class Solution {

        private val rangeMin: Long;

        private val rangeMax: Long;

        private val possibles: IntArray

        init {
            val tokens: List<String>  = reader.readLine().split(" ")
            rangeMin = tokens[0].toLong()
            rangeMax = tokens[1].toLong()
            possibles = IntArray((rangeMax - rangeMin + 1).toInt())
            possibles.fill(1)
        }

        fun run() {
            val searchLimit = Math.sqrt(rangeMax.toDouble()).toLong()
            for (i in 2..searchLimit) {
                val square = i * i
                if (square > rangeMax) break
                val start = if (rangeMin % square == 0L) rangeMin else rangeMin + (square - (rangeMin % square))
                var num = start
                while (num <= rangeMax) {
                    val idx = (num - rangeMin).toInt()
                    possibles[idx] = 0
                    num += square
                }
            }

            var answer: Int = 0
            for (i in possibles.indices) {
                if (possibles[i] == 1) answer++
            }
            writer.write("$answer\n")
            writer.flush()
        }
    }

    val solution = Solution()
    solution.run()
}
