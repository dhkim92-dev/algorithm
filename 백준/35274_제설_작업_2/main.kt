import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.`out`))

    class Solution {

        private var N: Int
        private var M: Long
        private var L: Int
        private lateinit var A: LongArray
        private final val limit = 1_000_000_000_000_001

        init {
            val tokens = reader.readLine().split(" ")
            N = tokens[0].toInt()
            M = tokens[1].toLong()
            L = tokens[2].toInt()
            A = reader.readLine()
                .split(" ")
                .map { it -> it.toLong() }
                .toLongArray()
        }

        fun check(X: Long, diff: LongArray): Boolean {
            diff.fill(0L)
            var usedCount = 0L

            var i = 0
            while (i < N) {
                val snow = A[i] + diff[i]

                if (snow <= 0L) {
                    i++
                    continue
                }

                val fullTurns = snow / X
                val remainder = snow % X

                if (fullTurns > 0) {
                    usedCount += fullTurns
                    if (usedCount > M) return false
                    diff[i] -= fullTurns * X
                }

                if (remainder == 0L) {
                    i++
                    continue
                }

                // remainder > 0: i 나머지 제거 + 남은 예산으로 i+1부터 처리
                var remaining = X - remainder
                diff[i] -= remainder
                usedCount++
                if (usedCount > M) return false

                for (j in i + 1 until minOf(N, i + L)) {
                    val sj = A[j] + diff[j]
                    if (sj <= 0L) continue
                    val removed = minOf(sj, remaining)
                    diff[j] -= removed
                    remaining -= removed
                    if (remaining == 0L) break
                }

                i++
            }
            return true
        }

        fun solve(): Long {
            var lo = 0L
            var hi = A.sum() + 1L
            val diff = LongArray(N + 1)
            while (lo + 1L < hi) {
                val mid = lo + (hi - lo) / 2L
                if (!check(mid, diff)) {
                    lo = mid
                } else {
                    hi = mid
                }
            }
            return if (hi > 0 && check(hi, diff)) hi else -1
        }

        fun run() {
            writer.write("${solve()}\n")
            writer.flush()
        }
    }

    val solution = Solution()
    solution.run()
}
