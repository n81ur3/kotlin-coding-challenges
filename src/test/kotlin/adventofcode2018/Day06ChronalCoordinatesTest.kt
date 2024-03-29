package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day06ChronalCoordinatesTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day06_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        )
        val coordinateSystem = CoordinateSystem(input)

        val largestFieldSize = coordinateSystem.calcLargestInnerFieldSize()

        coordinateSystem.printSystem()

        assertEquals(17, largestFieldSize)
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        )
        val coordinateSystem = CoordinateSystem(input)

        val largestSafeRegion = coordinateSystem.sizeOfLargestSafeRegion(range = 32)

        assertEquals(16, largestSafeRegion)
        println("Largest safe region sample part 2: $largestSafeRegion")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val coordinateSystem = CoordinateSystem(input)

        val largestFieldSize = coordinateSystem.calcLargestInnerFieldSize()

        assertEquals(3840, largestFieldSize)
        println("Solution for AoC2018-Day06-Part01: $largestFieldSize")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val coordinateSystem = CoordinateSystem(input)

        val largestSafeRegion = coordinateSystem.sizeOfLargestSafeRegion(range = 10000)

        assertEquals(46542, largestSafeRegion)
        println("Solution for AoC2018-Day06-Part01: $largestSafeRegion")
    }
}