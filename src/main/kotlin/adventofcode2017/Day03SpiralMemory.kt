package adventofcode2017

import adventofcode2017.Direction.*
import kotlin.math.absoluteValue

class Day03SpiralMemory

data class Coordinate(val x: Int, val y: Int)

enum class Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN
}

class SpiralMemory(private val input: Int) {
    private val memory = mutableMapOf<Int, Coordinate>()
    private var maxX = 1
    private var minX = -1
    private var maxY = 1
    private var minY = -1
    private var currentDirection = RIGHT

    init {
        buildMemory()
    }

    private fun buildMemory() {
        var currentValue = 1
        var currentPosition = Coordinate(0, 0)
        while (currentValue <= input) {
            memory[currentValue++] = currentPosition
            currentPosition = getNextPosition(currentPosition)
        }
    }

    fun manhattanDistanceForCell(value: Int): Int {
        memory[value]?.let { coordinate ->
            return (coordinate.x.absoluteValue + coordinate.y.absoluteValue)
        } ?: return 0
    }

    private fun getNextPosition(currentPosition: Coordinate): Coordinate {
        when (currentDirection) {
            RIGHT -> {
                return if (currentPosition.x == maxX) {
                    currentDirection = UP
                    maxX++
                    Coordinate(currentPosition.x, currentPosition.y - 1)
                } else {
                    Coordinate(currentPosition.x + 1, currentPosition.y)
                }
            }

            UP -> {
                return if (currentPosition.y == minY) {
                    currentDirection = LEFT
                    minY--
                    Coordinate(currentPosition.x - 1, currentPosition.y)
                } else {
                    Coordinate(currentPosition.x, currentPosition.y - 1)
                }
            }

            LEFT -> {
                return if (currentPosition.x == minX) {
                    currentDirection = DOWN
                    minX--
                    Coordinate(currentPosition.x, currentPosition.y + 1)
                } else {
                    Coordinate(currentPosition.x - 1, currentPosition.y)
                }
            }

            DOWN -> {
                return if (currentPosition.y == maxY) {
                    currentDirection = RIGHT
                    maxY++
                    Coordinate(currentPosition.x + 1, currentPosition.y)
                } else {
                    Coordinate(currentPosition.x, currentPosition.y + 1)
                }
            }
        }
    }
}