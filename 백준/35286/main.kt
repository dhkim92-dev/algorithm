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

        private val T: Int

        // private val numStations: IntArray

        private val sels: IntArray = intArrayOf(-2, -1, 1, 2)

        init {
            // First line, T that means number of games 
            // From second line, N lines are ginven that mean number of stations (1<=N<=10^9)
            var line = reader.readLine()
            T = line.toInt()

            // numStations = IntArray(T){0}

            // for ( i in 0 until T ) {
                // numStations[i] = reader.readLine().toInt()
            // }
        }

        fun run() {
            // We called H player who play first,
            // and S player who play second 
            // H can select start station number (X). 
            // S select a number among X2 = X + sels[j] (0<=j<4) 
            // H choose a number X3 = X2 + sels[j] ... 
            // each stations can be selected only one time 
            // if there are no number that can be chosen, player will lose 
            // print who win when each player play without any fault.
            
            for ( i in 0 until T ) {
                val N = reader.readLine().toInt()

                if ( N % 2 == 1 ) {
                    writer.write("H\n")
                } else {
                    writer.write("S\n")
                }
            }
            writer.flush()
        }
    }

    val solution = Solution()
    solution.run()
}

