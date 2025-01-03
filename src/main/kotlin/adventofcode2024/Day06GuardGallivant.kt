package adventofcode2024

import adventofcode2018.plus

data class DirectionalPosition(
    val x: Int,
    val y: Int,
    val direction: Pair<Int, Int>
)
class GuardMaze(
    val input: List<String>
) {
    val obstacles = mutableListOf<Pair<Int, Int>>()
    val walkPath = mutableListOf<DirectionalPosition>()
    val loopObstacles = mutableSetOf<Pair<Int, Int>>()
    var currentPosition = 0 to 0
    var currentDirection = 0 to -1
    val startPosition: Pair<Int, Int>
    val mazeWidth: Int
    val mazeHeight: Int
    val distinctPositionsVisited: Int
        get() = walkPath.map{it.x to it.y}.toSet().size - 1

    init {
        input.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, c ->
                if (c == '#') obstacles.add(xIndex to yIndex)
                else if (c == '^') {
                    currentPosition = xIndex to yIndex
                }
            }
        }
        mazeWidth = input.first().length - 1
        mazeHeight = input.size - 1
        startPosition = currentPosition
    }

    fun walk(): Int {
        var steps = 0
        walkPath.clear()
        walkPath.add(DirectionalPosition(currentPosition.first, currentPosition.second, currentDirection))
        while (insideMaze()) {
            if (stepForward()) return -1
            steps++
        }
        return steps
    }

    fun findLoopObstacles(): Int {
        val initialWalkPath = walkPath.map{it.x to it.y}.toSet()
        initialWalkPath.forEach { obstacle ->
            obstacles.add(obstacle.first to obstacle.second)
            if (isLooping()) {
                loopObstacles.add(obstacle.first to obstacle.second)
            }
            obstacles.remove(obstacle.first to obstacle.second)
        }
        return loopObstacles.size
    }

    private fun isLooping(): Boolean {
        currentPosition = startPosition
        currentDirection = 0 to -1
        val steps = walk()
        return steps < 0
    }

    fun printMaze() {
        (0..mazeHeight).forEach { yIndex ->
            (0..mazeWidth).forEach { xIndex ->
                if (obstacles.contains(xIndex to yIndex)) print('#')
                else if (currentPosition == xIndex to yIndex) print('^')
                else if (loopObstacles.contains(xIndex to yIndex)) print('o')
                else if (walkPath.any{it.x to it.y == xIndex to yIndex}) print('X')
                else print('.')
            }
            println()
        }
    }

    private fun turnRight() {
        currentDirection = when (currentDirection) {
            0 to -1 -> 1 to 0
            1 to 0 -> 0 to 1
            0 to 1 -> -1 to 0
            else -> 0 to -1
        }
    }

    private fun insideMaze(): Boolean {
        return (0..mazeWidth).contains(currentPosition.first) && (0..mazeHeight).contains(currentPosition.second)
    }

    private fun stepForward(): Boolean {
        val nextPosition = currentPosition + currentDirection
        if (obstacles.contains(nextPosition)) {
            turnRight()
        }
        currentPosition = currentPosition + currentDirection
        if (walkPath.contains(DirectionalPosition(currentPosition.first, currentPosition.second, currentDirection))) {
            return true
        }
        walkPath.add(DirectionalPosition(currentPosition.first, currentPosition.second, currentDirection))
        return false
    }

}