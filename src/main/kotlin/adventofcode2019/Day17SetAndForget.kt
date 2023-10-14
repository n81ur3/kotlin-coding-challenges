package adventofcode2019

class Day17SetAndForget

data class ScaffoldPoint(
    val x: Int,
    val y: Int,
    val token: Long
)

class AsciiScaffold(program: String) : IntComputerObserver {
    val intComputer: IntComputer = IntComputer(program)
    val collectedTokens = mutableListOf<Long>()
    val scaffoldPoints = mutableListOf<ScaffoldPoint>()

    init {
        intComputer.outputObserver = this
    }

    override fun onOutput(output: Long) {
        collectedTokens.add(output)
    }

    fun parseScaffold(displayScaffold: Boolean = false) {
        intComputer.run(0)
        var x = 0
        var y = 0
        collectedTokens.forEach { token ->
            if (token == 10L) {
                x = -1
                y++
                if (displayScaffold) println()
            } else {
                if (displayScaffold) print(token.toInt().toChar())
                if (token == 35L) {
                    scaffoldPoints.add(ScaffoldPoint(x, y, token))
                }
            }
            x++
        }
    }

    fun calibrate(): Long {
        var result = 0L
        scaffoldPoints.forEach {
            if (isIntersection(it)) {
                result += it.x * it.y
            }
        }
        return result
    }

    private fun isIntersection(scaffoldPoint: ScaffoldPoint): Boolean {
        scaffoldPoints.firstOrNull { it.x == scaffoldPoint.x && it.y - 1 == scaffoldPoint.y } ?: return false
        scaffoldPoints.firstOrNull { it.x + 1 == scaffoldPoint.x && it.y == scaffoldPoint.y } ?: return false
        scaffoldPoints.firstOrNull { it.x == scaffoldPoint.x && it.y + 1 == scaffoldPoint.y } ?: return false
        scaffoldPoints.firstOrNull { it.x - 1 == scaffoldPoint.x && it.y  == scaffoldPoint.y } ?: return false
        return true
    }
}