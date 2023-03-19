package adventofcode2018

class Day22ModeMaze

class MazeCalculator(val depth: Int, val targetX: Int, val targetY: Int) {
    val regions: List<MutableList<Int>>
    val errosionLevels: List<MutableList<Int>> = List(targetY + 1) { MutableList(targetX + 1) { 0 } }
    val riskLevel: Int
        get() = regions.map { line -> line.sum() }.sum()

    init {
        regions = (0..targetY).map { y ->
            (0..targetX).map { x ->
                0
            }.toMutableList()
        }
        calculateErosionLevels()
    }

    private fun calculateErosionLevels() {
        (0..targetY).forEach { y ->
            (0..targetX).forEach { x ->
                regions[y][x] = getRegionType(y, x)
            }
        }
    }

    private fun getRegionType(y: Int, x: Int): Int {
        val erosionLevel = getErosionLevel(y, x)

        return erosionLevel % 3
    }

    private fun getErosionLevel(y: Int, x: Int): Int {
        val geologicalIndex = getGeologicalIndex(y, x)
        val errosionLevel = (geologicalIndex + depth) % 20183
        errosionLevels[y][x] = errosionLevel
        return errosionLevel
    }

    private fun getGeologicalIndex(y: Int, x: Int): Int {
        if (y == 0 && x == 0) return 0
        if (y == targetY && x == targetX) return 0
        if (y == 0) {
            return x * 16807
        }
        if (x == 0) {
            return y * 48271
        }
        return errosionLevels[y - 1][x] * errosionLevels[y][x - 1]
    }

    fun printMaze() {
        regions.forEach { line ->
            line.forEach { region ->
                when (region) {
                    0 -> print(".")
                    1 -> print("=")
                    else -> print("|")
                }
            }
            println()
        }
    }
}