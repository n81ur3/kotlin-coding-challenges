package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day19ASeriesOfTubesTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day19_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
        "    |         ",
        "    |  +--+   ",
        "    A  |  C   ",
        "F---|----E|--+",
        "    |  |  |  D",
        "    +B-+  +--+"
        )
        val tubesSeries = TubesSeries(input)

        val collectedCharacters = tubesSeries.walk()

        println("Collected characters: $collectedCharacters")
        assertEquals("ABCDEF", collectedCharacters)
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "    |         ",
            "    |  +--+   ",
            "    A  |  C   ",
            "F---|----E|--+",
            "    |  |  |  D",
            "    +B-+  +--+"
        )
        val tubesSeries = TubesSeries(input)

        tubesSeries.walk()

        println("Step count for sample part 2: ${tubesSeries.stepCount}")

    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val tubesSeries = TubesSeries(input)

        val collectedCharacters = tubesSeries.walk()

        assertEquals("RUEDAHWKSM", collectedCharacters)
        println("Solution for day 19 part 1: $collectedCharacters")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()
        val tubesSeries = TubesSeries(input)

        tubesSeries.walk()
        assertEquals(17264, tubesSeries.stepCount)
        println("Solution for day 19 part 2: ${tubesSeries.stepCount}")
    }
}