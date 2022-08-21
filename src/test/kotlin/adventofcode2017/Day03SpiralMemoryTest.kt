package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day03SpiralMemoryTest {

    @ParameterizedTest
    @CsvSource( "0, 1", "3, 12", "2, 23", "31, 1024" )
    fun buildSampleMemory(expected: Int, cell: Int) {
        val spiralMemory = SpiralMemory(1024)

        assertEquals(expected, spiralMemory.manhattanDistanceForCell(cell))
    }

    @ParameterizedTest
    @CsvSource("8, 10", "100, 122", "200, 304", "400, 747", "760, 806")
    fun simulateStressTest(target: Int, nextValueExpected: Int) {
        val spiralMemory = SpiralMemory()
        val nextValue = spiralMemory.stressTest(target)
        assertEquals(nextValueExpected, nextValue)
    }

    @Test
    fun solutionPart1() {
        val spiralMemory = SpiralMemory(347991)

        val manhattanDistance = spiralMemory.manhattanDistanceForCell(347991)
        assertEquals(480, manhattanDistance)
        println("Solution AoC2017-Day03-Part01: $manhattanDistance")
    }

    @Test
    fun solutionPart2() {
        val spiralMemory = SpiralMemory()
        val solution = spiralMemory.stressTest(347991)

        assertEquals(349975, solution)
        println("Solution AoC2017-Day03-Part02: $solution")
    }
}