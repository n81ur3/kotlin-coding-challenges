package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day06MemoryReallocationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day06_input.txt")
    }

    @Test
    fun runTestSample() {
        val input = "0 2 7 0"
        val memoryReallocator = MemoryReallocator(input)

        val (numberOfCycles, loopSize) = memoryReallocator.reallocate()

        assertEquals(5, numberOfCycles)
        assertEquals(4, loopSize)
    }

    @Test
    fun solutionPart1() {
        // GIVEN
        val input = file.readLines()
        val memoryReallocator = MemoryReallocator(input[0])

        // WHEN
        val (numberOfCycles, _) = memoryReallocator.reallocate()

        // THEN
        assertEquals(3156, numberOfCycles)
        println("Solution for day 06 part 1: $numberOfCycles")
    }

    @Test
    fun solutionPart2() {
        // ARRANGE
        val input = file.readLines()
        val memoryReallocator = MemoryReallocator(input[0])

        // ACT
        val (_, loopSize) = memoryReallocator.reallocate()

        // ASSERT
        assertEquals(1610, loopSize)
        println("Solution for day 06 part 2: $loopSize")
    }
}