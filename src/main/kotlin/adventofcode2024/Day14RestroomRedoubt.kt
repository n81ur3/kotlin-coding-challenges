package adventofcode2024

data class SecurityBot(
    var x: Int,
    var y: Int,
    var vx: Int,
    var vy: Int
) {
    fun move(width: Int, height: Int) {
        x = (x + vx) % width
        y = (y + vy) % height
        if (x < 0) x = width + x
        if (y < 0) y = height + y
    }
}

class EBHQRestroom(input: List<String>, val width: Int, val height: Int) {
    val bots: List<SecurityBot>
    val left = IntRange(0, width / 2 - 1)
    val right = IntRange(width / 2 + 1, width)
    val top = IntRange(0, height / 2 - 1)
    val bottom = IntRange(height / 2 + 1, height)

    init {
        bots = input.map { parseRobot(it) }
    }

    private fun parseRobot(input: String): SecurityBot {
        val x = input.substringAfter("=").substringBefore(",").toInt()
        val y = input.substringAfter(",").substringBefore(" ").toInt()
        val vx = input.substringAfter("v=").substringBefore(",").toInt()
        val vy = input.substringAfterLast(",").toInt()
        return SecurityBot(x, y, vx, vy)
    }

    fun findEasterEgg(printChristmasTree: Boolean): Int {
        var counter = 0
        while (bots.groupBy { it.x to it.y }.values.any { it.size > 1 }) {
            moveRobots()
            counter++
        }
        if (printChristmasTree) printLayout()
        return counter
    }

    fun moveRobots(seconds: Int = 1) {
        repeat(seconds) { bots.forEach { it.move(width, height) } }
    }

    fun quarterCounts(): Int {
        val topLeft = bots.count { left.contains(it.x) && top.contains(it.y) }
        val topRight = bots.count { right.contains(it.x) && top.contains(it.y) }
        val bottomLeft = bots.count { left.contains(it.x) && bottom.contains(it.y) }
        val bottomRight = bots.count { right.contains(it.x) && bottom.contains(it.y) }
        return topLeft * topRight * bottomLeft * bottomRight
    }

    fun printLayout() {
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                val botCount = bots.count { it.y == y && it.x == x }
                if (botCount > 0) print(botCount)
                else print(".")
            }
            println()
        }
    }
}