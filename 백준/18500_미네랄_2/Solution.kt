import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import kotlin.io.path.name

enum class State {
    EMPTY,
    MINERAL
}

class Pos(var r: Int, var c: Int) {

    fun add(pos: Pos): Pos {
        return Pos(r + pos.r, c + pos.c)
    }
}

class Cluster(
    val positions: MutableList<Pos>
) {

    fun add(pos: Pos) {
        positions.add(pos)
    }

    fun move(delta: Pos) {
        positions.forEach { pos ->
            pos.r += delta.r
            pos.c += delta.c
        }
    }
}

class Board(
    val R: Int,
    val C: Int,
    val board: Array<Array<State>>,
) {

    fun isInRange(r: Int, c: Int): Boolean {
        return r in 0 until R && c in 0 until C
    }

    fun isEmpty(r: Int, c: Int): Boolean {
        return board[r][c] == State.EMPTY
    }

    fun breakMineral(r: Int, c: Int) {
        board[r][c] = State.EMPTY
    }

    fun isCrash(cluster: Cluster): Boolean {
        for ( pos in cluster.positions ) {
            if ( !isInRange(pos.r, pos.c) ) return true
            if ( !isEmpty(pos.r, pos.c) ) return true
        }
        return false
    }

    fun dropCluster(cluster: Cluster) {
        for ( pos in cluster.positions ) {
            board[pos.r][pos.c] = State.EMPTY
        }

        do {
            // 현재 클러스터의 위치를 모두 비운다
            cluster.positions.forEach { pos -> breakMineral(pos.r, pos.c) }
            // 클러스터를 아래로 이동시킨다
            cluster.move(Pos(1, 0))
            if ( isCrash(cluster) ) {
                // 이동할 클러스터가 충돌하는지 확인한다.
                cluster.move(Pos(-1, 0))
                cluster.positions.forEach { pos -> board[pos.r][pos.c] = State.MINERAL }
                break;
            } else {
                // 충돌하지 않으면 클러스터의 현재 상태를 맵에 기록한다.
                cluster.positions.forEach { pos -> board[pos.r][pos.c] = State.MINERAL }
            }
        }while(true)


        for (pos in cluster.positions) {
            board[pos.r][pos.c] = State.MINERAL
        }
    }

    fun printState() {
        val sb = StringBuilder()
        for (r in 0 until R) {
            for (c in 0 until C) {
                sb.append(if (board[r][c] == State.EMPTY) '.' else 'x')
            }
            sb.append('\n')
        }
        println(sb.toString())
    }
}

class Solution(
    private val reader: BufferedReader
) {
    private lateinit var board: Board
    private var count: Int = 0
    private lateinit var heights: IntArray
    private final val dirs = arrayOf(
        Pos(0, 1), Pos(0, -1), Pos(1, 0), Pos(-1, 0)
    )

    init {
        val (r, c) = reader.readLine().split(" ").map { it.toInt() }
        val boardInput = ArrayList<Array<State>>()
        repeat(r) { boardInput.add(reader.readLine()
            .toCharArray()
            .map { ch -> when(ch) {
                '.' -> State.EMPTY
                'x' -> State.MINERAL
                else -> throw IllegalArgumentException("Invalid character: $ch")
            }}.toTypedArray()
        ) }
        board = Board(r, c, boardInput.toTypedArray())
        count = reader.readLine().toInt()
        heights = reader.readLine()
            .split(" ")
            .map { it.toInt() }
            .toIntArray()
    }

    fun throwStick(turn: Int, height: Int) {
        val row = board.R - height

        if ( turn % 2 == 0) {
            for ( c in 0 until board.C ) {
                if ( board.isInRange(row, c) && !board.isEmpty(row, c) ) {
                    board.breakMineral(row, c)
                    break
                }
            }
        } else {
            for ( c in board.C -1 downTo 0 ) {
                if ( board.isInRange(row, c) && !board.isEmpty(row, c) ) {
                    board.breakMineral(row, c)
                    break
                }
            }
        }
    }

    fun searchCluster(r: Int, c: Int, visited: Array<Array<Boolean>>): Cluster {
        val cluster = Cluster(mutableListOf())
        val queue = ArrayDeque<Pos>()
        queue.add(Pos(r, c))
        visited[r][c] = true

        while (queue.isNotEmpty()) {
            val pos = queue.removeFirst()
            cluster.add(pos)

            for (dir in dirs) {
                val nxt = pos.add(dir)

                if ( !board.isInRange(nxt.r, nxt.c) ) continue
                if ( visited[nxt.r][nxt.c] ) continue
                if ( board.isEmpty(nxt.r, nxt.c) ) continue
                visited[nxt.r][nxt.c] = true
                queue.add(nxt)
            }
        }

        return cluster
    }

    fun getClusters(): List<Cluster> {
        val clusters = mutableListOf<Cluster>()
        val visited = Array(board.R) { Array(board.C) { false } }
        for (r in 0 until board.R) {
            for (c in 0 until board.C) {
                if (board.isInRange(r, c) && !visited[r][c] && !board.isEmpty(r, c)) {
                    val cluster = searchCluster(r, c, visited)
                    clusters.add(cluster)
                }
            }
        }

        return clusters.toList()
    }

    fun run() {
        var turn = 0

        for (height in heights) {
//            println("height : $height")
            throwStick(turn, height)
//            println("throw stick")
            val clusters = getClusters();
//            println("get clusters size: ${clusters.size}")
            clusters.forEach { cluster ->
//                println("cluster size: ${cluster.positions.size}")
                board.dropCluster(cluster)
            }
//            println("drop minerals")
            turn++
        }

        board.printState()
    }
}

