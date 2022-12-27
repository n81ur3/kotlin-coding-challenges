package adventofcode2018

class Day10TheStarsAlign

typealias StarPos = Pair<Int, Int>

operator fun StarPos.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)

data class Star(var position: StarPos, val starVelo: Pair<Int, Int>) {

    fun blink() {
        position += starVelo
    }

    companion object {
        fun fromString(input: String): Star {
            val x = input.substringAfter("<").substringBefore(",").trim().toInt()
            val y = input.substringAfter(",").substringBefore(">").trim().toInt()
            val veloX = input.substringAfter("velocity=<").substringBefore(",").trim().toInt()
            val veloY = input.substringAfterLast(",").substringBefore(">").trim().toInt()
            return Star(x to y, veloX to veloY)
        }
    }
}

class NorthPoleSky(input: List<String>) {
    val stars: List<Star>
    val minX: Int
        get() = stars.minOf { it.position.first }
    val maxX: Int
        get() = stars.maxOf { it.position.first }
    val minY: Int
        get() = stars.minOf { it.position.second }
    val maxY: Int
        get() = stars.maxOf { it.position.second }

    init {
        stars = input.map { Star.fromString(it) }
    }

    fun blink(numberOfBlinks: Int): Int {
        repeat(numberOfBlinks) {
            stars.forEach { it.blink() }
            printDistance(it)
            if (mayBeMessage()) {
                displaySky()
                return it + 1
            }
        }
        return 0
    }

    fun displaySky() {
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (starAtPosition(x to y)) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        println()
    }

    private fun starAtPosition(position: StarPos): Boolean {
        return stars.firstOrNull { it.position == position } != null
    }

    private fun printDistance(blinkCount: Int) {
        val xDistance = maxX - minX
        val yDistance = maxY - minY
        if (xDistance < 100 && yDistance < 10) {
            println("blinkCount[$blinkCount]: X-Distance: $xDistance - Y-Distance: $yDistance")
        }
    }
    private fun mayBeMessage() = ((maxY - minY) < 10)
}