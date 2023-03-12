package adventofcode2018

import java.util.*

class Day20AregularMap

data class MazePosition(val x: Int, val y: Int) {
    val north: MazePosition
        get() = copy(y = y - 1)
    val east: MazePosition
        get() = copy(x = x + 1)
    val south: MazePosition
        get() = copy(y = y + 1)
    val west: MazePosition
        get() = copy(x = x - 1)
}

class MazeWalker(val directions: String) {
    var currentPosition = MazePosition(0, 0)
    var reachedPositions = mutableMapOf(currentPosition to 0)

    fun walk(): Int {
        val stack = ArrayDeque<MazePosition>()

        directions.forEach { d ->
            when (d) {
                '(' -> stack.push(currentPosition)

                ')' -> currentPosition = stack.pop()
                '|' -> currentPosition = stack.peek()

                in allDirections -> {
                    val distance = reachedPositions[currentPosition]?.plus(1) ?: Integer.MAX_VALUE
                    currentPosition = when (d) {
                        'N' -> currentPosition.north
                        'E' -> currentPosition.east
                        'S' -> currentPosition.south
                        else -> currentPosition.west
                    }
                    reachedPositions[currentPosition] = minOf(
                        reachedPositions.getOrDefault(currentPosition, Integer.MAX_VALUE),
                        distance
                    )
                }
            }
        }

        return reachedPositions.maxOf { it.value }
    }

    companion object {
        val allDirections = listOf('N', 'E', 'S', 'W')
    }
}