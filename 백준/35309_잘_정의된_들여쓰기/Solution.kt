
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

class Solution (
    private val reader: BufferedReader,
    private val writer: BufferedWriter
) {

    private val T: Int 


    init {
        T = reader.readLine().toInt()
    }

    private fun solve(N: Int): String {
        // First line of each example starts with 0 indent.
        // Each line must be indented one more, the same, or one less than the previous line.
        // If the indentation is same with the previous line, the line number increases by 1.
        // If lines that has less indentations exists between the current line and the previous line which has the same indentation
        // current line number is reset to 1
        //
        // Solution, 
        // 1. Use a stack, translate line numbers to actual indentations count. and push to the stack.
        // 2. Simulate the numbers 
        //
        val st = ArrayDeque<Int>()
        var possible = true
        var tokenizer = StringTokenizer(reader.readLine())

        for ( i in 0 until N ) {
            val num = tokenizer.nextToken().toInt()

            if ( num == 1 ) {
                st.addLast(num)
            } else {
                while ( st.isNotEmpty() && st.last() != num - 1 ) {
                    st.removeLast()
                }

                if ( st.isEmpty() ) {
                    possible = false
                    break
                } else {
                    st.removeLast()
                    st.addLast(num)
                }
            }
        }

        return if (possible) "YES\n" else "NO\n"
    }

    fun run() {
        for(i in 0 until T) {
            val N = reader.readLine().toInt()
            writer.write(solve(N))
        }
        writer.flush()
    }
}



fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.`out`))
    val solution = Solution(
        reader = reader,
        writer = writer
    )
    solution.run()
}
