package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day10HoofItTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day10_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: List<String>, expectedResult: Int) {
        val pathfinder = Pathfinder(input)
        assertEquals(expectedResult, pathfinder.findPaths())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: List<String>, expectedResult: Long) {
    }

    @Test
    fun solutionPart1() {
        val pathfinder = Pathfinder(file.readLines())
        val solution = pathfinder.findPaths()
        assertEquals(517, solution)
        println("Solution for AoC2024-Day10-Part01: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    89010123
                    78121874
                    87430965
                    96549874
                    45678903
                    32019012
                    01329801
                    10456732
                    """.trimIndent().lines(), 36
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    ............
                    ........0...
                    .....0......
                    .......0....
                    ....0.......
                    ......A.....
                    ............
                    ............
                    ........A...
                    .........A..
                    ............
                    ............
                    """.trimIndent().lines(), 34
            )
        )
    }
}