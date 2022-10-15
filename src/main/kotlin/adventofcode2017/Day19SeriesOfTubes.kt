package adventofcode2017

class Day19ASeriesOfTubes

class TubesSeries(val tubesystem: List<String>) {
    private var currentPosition = 0 to 0
    private var currentDirection = Direction.DOWN
    private val currentTube: Char
        get() = tubesystem[currentPosition.second][currentPosition.first]
    val collectedChars = mutableListOf<Char>()
    var stepCount = 0

    init {
        currentPosition = tubesystem[0].indexOf('|') to 0
        stepCount++
    }

    fun walk(): String {
        while (true) {
            if (!step()) break
        }
        return collectedChars.joinToString(separator = "")
    }

    private fun step(): Boolean {
        val nextPosition = findNextPosition()
        if (nextPosition.first in (0..tubesystem[0].length) && nextPosition.second in (0..tubesystem.size)) {
            currentPosition = nextPosition
            stepCount++
            return true
        } else {
            return false
        }
    }

    private fun findNextPosition(): Pair<Int, Int> {
        val characterFound = collectLetter()
        if (characterFound) {
            if (checkForEnd()) return -1 to -1
        }

        if (currentDirection in listOf(Direction.DOWN, Direction.UP) && currentTube != '+') {
            return when (currentDirection) {
                Direction.DOWN -> currentPosition.first to currentPosition.second + 1
                else -> currentPosition.first to currentPosition.second - 1
            }
        } else if (currentDirection in listOf(Direction.LEFT, Direction.RIGHT) && currentTube != '+') {
            return when (currentDirection) {
                Direction.LEFT -> currentPosition.first - 1 to currentPosition.second
                else -> currentPosition.first + 1 to currentPosition.second
            }
        } else {
            return when (currentDirection) {
                Direction.RIGHT, Direction.LEFT -> {
                    if (currentPosition.second - 1 >= 0 && tubesystem[currentPosition.second - 1][currentPosition.first] != ' ') {
                        currentDirection = Direction.UP
                        currentPosition.first to currentPosition.second - 1
                    } else {
                        currentDirection = Direction.DOWN
                        currentPosition.first to currentPosition.second + 1
                    }
                }

                Direction.UP, Direction.DOWN -> {
                    if (currentPosition.first - 1 >= 0 && tubesystem[currentPosition.second][currentPosition.first - 1] != ' ') {
                        currentDirection = Direction.LEFT
                        currentPosition.first - 1 to currentPosition.second
                    } else {
                        currentDirection = Direction.RIGHT
                        currentPosition.first + 1 to currentPosition.second
                    }
                }
            }
        }
    }

    private fun checkForEnd(): Boolean {
        return when(currentDirection) {
            Direction.DOWN -> tubesystem[currentPosition.second + 1][currentPosition.first] == ' '
            Direction.UP -> tubesystem[currentPosition.second - 1][currentPosition.first] == ' '
            Direction.LEFT -> tubesystem[currentPosition.second][currentPosition.first - 1] == ' '
            Direction.RIGHT -> tubesystem[currentPosition.second][currentPosition.first + 1] == ' '
        }
    }

    private fun collectLetter(): Boolean {
        return if (currentTube.isLetter()) {
            println("collected: $currentTube")
            collectedChars.add(currentTube)
            true
        } else false
    }
}