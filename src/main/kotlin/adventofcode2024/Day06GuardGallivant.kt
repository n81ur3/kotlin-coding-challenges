package adventofcode2024

import adventofcode2018.plus

class GuardMaze(
    val input: List<String>
) {
    val obstacles = mutableListOf<Pair<Int, Int>>()
    val walkPath = mutableSetOf<Pair<Int, Int>>()
    var currentPosition = 0 to 0
    var currentDirection = 0 to -1
    val mazeWidth: Int
    val mazeHeight: Int
    val distinctPositionsVisited: Int
        get() = walkPath.size - 1

    init {
        input.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, c ->
                if (c == '#') obstacles.add(xIndex to yIndex)
                else if (c == '^') currentPosition = xIndex to yIndex
            }
        }
        mazeWidth = input.first().length - 1
        mazeHeight = input.size - 1
        walkPath.add(currentPosition)
    }

    fun walk(): Int {
        var steps = 0
        while (insideMaze()) {
            stepForward()
            steps++
        }
        return steps
    }

    fun printMaze() {
        (0..mazeHeight).forEach { yIndex ->
            (0..mazeWidth).forEach { xIndex ->
                if (obstacles.contains(xIndex to yIndex)) print('#')
                else if (walkPath.contains(xIndex to yIndex)) print('X')
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

    private fun stepForward() {
        val nextPosition = currentPosition + currentDirection
        if (obstacles.contains(nextPosition)) {
            turnRight()
        }
        currentPosition = currentPosition + currentDirection
        walkPath.add(currentPosition)
    }

}