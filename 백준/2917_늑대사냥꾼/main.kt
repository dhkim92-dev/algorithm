package kr.dohoon_kim.algorithm.kotlin

import java.io.*;
import java.util.*;
import kotlin.io.path.name


class Solution(
    private val reader: BufferedReader,
    private val writer: BufferedWriter
) {

    private final val WOLF = 'V'

    private final val TREE = '+'

    private final val EMPTY = '.'

    private final val SHELTER = 'J'

    private var N = 0

    private var M = 0

    private var grid: Array<Array<Char>>

    private var visited: Array<Array<Boolean>>
    
    private var distFromTrees: Array<IntArray>

    private val trees = mutableListOf<IntArray>()

    private var sr = 0

    private var sc = 0

    private val dr = intArrayOf(-1, 0, 1, 0)

    private val dc = intArrayOf(0, 1, 0, -1)

    init {
        var line : List<String> = this.reader.readLine().split(" ")
        N = line[0].toInt()
        M = line[1].toInt()

        grid = Array(N) { Array(M) { ' ' } }

        for (i in 0 until N) {
            val l = this.reader.readLine()
            for (j in 0 until M) {
                grid[i][j] = l[j]

                if (grid[i][j] == TREE) {
                    trees.add(intArrayOf(i, j))
                }

                if (grid[i][j] == WOLF) {
                    sr = i
                    sc = j
                }
            }
        } 

        visited = Array(N) { Array(M) { false } }
        distFromTrees = Array(N) { IntArray(M) { 0 } }
    }

    private fun Int.isInRange(size: Int) = this in 0 until N

    private fun Pair<Int, Int>.isInGrid(): Boolean {
        val (r, c) = this
        return r.isInRange(N) && c.isInRange(M)
    }

    private fun calcDistFromTrees() {
        val q: Queue<IntArray> = ArrayDeque<IntArray>()

        for (tree in trees) {
            val r = tree[0]
            val c = tree[1]
            q.add(intArrayOf(r, c, 0))
            visited[r][c] = true
            distFromTrees[r][c] = 0
        }

        while (!q.isEmpty()) {
            val cur = q.poll()
            val r = cur[0]
            val c = cur[1]
            val d = cur[2]

            for (i in 0 until 4) {
                val nr = r + dr[i]
                val nc = c + dc[i]

                if (Pair(nr, nc).isInGrid() && !visited[nr][nc]) {
                    visited[nr][nc] = true
                    distFromTrees[nr][nc] = d + 1
                    q.add(intArrayOf(nr, nc, d + 1))
                }
            }
        }
    }

    private fun printDist() {
        println("Distance from trees:")
        for (i in 0 until N) {
            for (j in 0 until M) {
                writer.write("${distFromTrees[i][j]} ")
            }
            writer.write("\n")
        }
    }

    private fun calcPath(): Int {
        var answer = Int.MAX_VALUE
        val pq: PriorityQueue<IntArray> = PriorityQueue { a, b -> b[2] - a[2] }
        for ( row in visited ) {
            Arrays.fill(row, false)
        }
        // printDist()

        pq.add(intArrayOf(sr, sc, distFromTrees[sr][sc]))

        while (!pq.isEmpty()) {
            val cur = pq.poll()
            val r = cur[0]
            val c = cur[1]
            val d = cur[2]

            answer = Math.min(answer, d)
            if ( grid[r][c] == SHELTER ) {
                break
            }

            for (i in 0 until 4) {
                val nr = r + dr[i]
                val nc = c + dc[i]

                if (Pair(nr, nc).isInGrid() && !visited[nr][nc]) {
                    visited[nr][nc] = true
                    pq.add(intArrayOf(nr, nc, distFromTrees[nr][nc]))
                }
            }
        }

        return answer
    }

    fun run() {
        calcDistFromTrees()
        writer.write("${calcPath()}\n")
        writer.flush();
    }
}


fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    Solution(reader, writer).run()
}
