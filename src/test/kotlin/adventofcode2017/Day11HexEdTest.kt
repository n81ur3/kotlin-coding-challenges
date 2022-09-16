package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day11HexEdTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day11_input.txt")
    }

    @ParameterizedTest
    @MethodSource("samplesPart1")
    fun runSamplesPart1(input: String, expected: Int) {
        val hexWalker = HexWalker()
        hexWalker.walkPath(input)
        hexWalker.printCurrentPosition()
        val shortestPathStepCount = hexWalker.getFewestStepCountToStart()
        println("Fewest step count: $shortestPathStepCount")
        assertEquals(expected, shortestPathStepCount)
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()[0]
        val hexWalker = HexWalker()

        hexWalker.walkPath(input)

        val shortestPathStepCount = hexWalker.getFewestStepCountToStart()
        assertEquals(784, shortestPathStepCount)
        println("Solution for day 11 part 1: $shortestPathStepCount")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()[0]
        val hexWalker = HexWalker()

        hexWalker.walkPath(input)

        val furthestPath = hexWalker.furthestPath
        assertEquals(1558, furthestPath)
        println("Solution for day 11 part 2: $furthestPath")
    }

    companion object {
        @JvmStatic
        fun samplesPart1() = listOf(
            Arguments.of("ne,ne,ne", 3),
            Arguments.of("ne,ne,sw,sw", 0),
            Arguments.of("ne,ne,s,s", 2),
            Arguments.of("se,sw,se,sw,sw", 3),
        )
    }
}