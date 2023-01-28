package adventofcode2018

import org.junit.jupiter.api.Test

class Day15BeverageBanditsTest {

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "#########",
            "#G..G..G#",
            "#.......#",
            "#.......#",
            "#G..E..G#",
            "#.......#",
            "#.......#",
            "#G..G..G#",
            "#########"
        )

        val cave = Cave(input)

        cave.playRound()
        cave.printCaveMap()
    }
}