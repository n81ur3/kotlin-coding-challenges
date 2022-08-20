package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03SpiralMemoryTest {

    @Test
    fun buildSampleMemory() {
        val spiralMemory = SpiralMemory(1024)

        assertEquals(0, spiralMemory.manhattanDistanceForCell(1))
        assertEquals(3, spiralMemory.manhattanDistanceForCell(12))
        assertEquals(2, spiralMemory.manhattanDistanceForCell(23))
        assertEquals(31, spiralMemory.manhattanDistanceForCell(1024))
    }

    @Test
    fun solutionPart1() {
        val spiralMemory = SpiralMemory(347991)

        val manhattanDistance = spiralMemory.manhattanDistanceForCell(347991)
        assertEquals(480, manhattanDistance)
        println("Solution AoC2017-Day03-Par01: $manhattanDistance")
    }
}