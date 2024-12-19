package adventofcode2024

data class CeresPoint(
    val x: Int,
    val y: Int,
    val c: Char
)

class XmasCounter(
    val input: List<String>
) {
    val ceresPoints: List<CeresPoint>

    val offsets = listOf(
        listOf(
            1 to 0,
            2 to 0,
            3 to 0,
        ),
        listOf(
            -1 to 0,
            -2 to 0,
            -3 to 0,
        ),
        listOf(
            0 to 1,
            0 to 2,
            0 to 3,
        ),
        listOf(
            0 to -1,
            0 to -2,
            0 to -3,
        ),
        listOf(
            1 to 1,
            2 to 2,
            3 to 3,
        ),
        listOf(
            1 to -1,
            2 to -2,
            3 to -3,
        ),
        listOf(
            -1 to 1,
            -2 to 2,
            -3 to 3,
        ),
        listOf(
            -1 to -1,
            -2 to -2,
            -3 to -3
        )
    )

    val crossOffsets = listOf(
        -1 to -1,
        1 to -1,
        -1 to 1,
        1 to 1
    )

    init {
        ceresPoints = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                CeresPoint(x, y, char)
            }
        }
    }

    fun xMasCount(): Int {
        val maxX = ceresPoints.maxOf { it.x }
        val maxY = ceresPoints.maxOf { it.y }
        var result = 0

        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                if (ceresPoints.firstOrNull { it.x == x && it.y == y }?.c == 'X') {
                    result += getMatchCount(x, y)
                }
            }
        }
        return result
    }

    private fun getMatchCount(x: Int, y: Int): Int {
        var xmasCount = 0
        offsets.forEach { offsetList ->

            val mappings =
                offsetList.map { offset -> ceresPoints.firstOrNull { x + offset.second == it.x && y + offset.first == it.y } }

            val result = "" + mappings[0]?.c + mappings[1]?.c + mappings[2]?.c

            if (result == "MAS") {
                xmasCount++
            }
        }
        return xmasCount
    }
}