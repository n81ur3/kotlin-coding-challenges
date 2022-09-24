package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14DiskDefragmentationTest {

    @Test
    fun calculateSampleHashes() {
        val defragmenter = Defragmenter()
        val keyString = "flqrgnkx-"
        (0..8).forEach {
            defragmenter.reset()
            val input = keyString + it
            println("Hash for row $it: ${defragmenter.getBinaryHash(input)}")
        }
    }

    @Test
    fun runSamplePart1() {
        val keyString = "flqrgnkx"
        val defragmenter = Defragmenter()

        val binaryHashGrid = defragmenter.calculateCompleteGrid(keyString)

        binaryHashGrid.windowed(128, 128) {
            println(it)
        }

        val numberOfUsedSquares = binaryHashGrid.count { it == '1' }
        assertEquals(8108, numberOfUsedSquares)
        println("Number of used squares for sample part 1: $numberOfUsedSquares")
    }

    @Test
    fun runSamplePart2() {
        val keyString = "flqrgnkx"
        val defragmenter = Defragmenter()

        defragmenter.buildDisc(keyString)
        val numberOfGroups = defragmenter.defrag()

        assertEquals(1242, numberOfGroups)
        println("Number of groups for sample part 2 = $numberOfGroups")
    }

    @Test
    fun solution_part1() {
        val keyString = "jzgqcdpd"
        val defragmenter = Defragmenter()

        val binaryHashGrid = defragmenter.calculateCompleteGrid(keyString)
        val numberOfUsedSquares = binaryHashGrid.count { it == '1' }

        assertEquals(8074, numberOfUsedSquares)
        println("Solution for day 14 part 1: $numberOfUsedSquares")
    }

    @Test
    fun solution_part2() {
        val keyString = "jzgqcdpd"
        val defragmenter = Defragmenter()

        defragmenter.buildDisc(keyString)
        val numberOfGroups = defragmenter.defrag()

        assertEquals(1212, numberOfGroups)
        println("Solution for day 14 part 2: $numberOfGroups")
    }
}