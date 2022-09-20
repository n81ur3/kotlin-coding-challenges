package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day12DigitalPlumberTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day12_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "0 <-> 2",
            "1 <-> 1",
            "2 <-> 0, 3, 4",
            "3 <-> 2, 4",
            "4 <-> 2, 3, 6",
            "5 <-> 6",
            "6 <-> 4, 5"
        )

        val plumbParser = PlumbParser(input)
        println(plumbParser.programms)

        assertEquals(6, plumbParser.getZeroIdGroupSize())
        println("Zero ID group size: ${plumbParser.getZeroIdGroupSize()}")
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "0 <-> 2",
            "1 <-> 1",
            "2 <-> 0, 3, 4",
            "3 <-> 2, 4",
            "4 <-> 2, 3, 6",
            "5 <-> 6",
            "6 <-> 4, 5"
        )

        val plumbParser = PlumbParser(input)
        plumbParser.buildGroups()

        assertEquals(2, plumbParser.numberOfGroups)
        println("Number of groups: ${plumbParser.numberOfGroups}")
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val plumbParser = PlumbParser(input)

        val result = plumbParser.getZeroIdGroupSize()
        assertEquals(380, result)
        println("Solution for day 12 part 1: $result")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()
        val plumbParser = PlumbParser(input)

        plumbParser.buildGroups()

        assertEquals(181, plumbParser.numberOfGroups)
        println("Solution for day 13 part 2: ${plumbParser.numberOfGroups}")
    }
}
