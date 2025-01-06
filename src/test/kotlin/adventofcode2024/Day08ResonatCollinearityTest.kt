package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day08ResonatCollinearityTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day08_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(lines: List<String>, expectedResult: Int) {
        val resonator = Resonator(lines)
        assertEquals(expectedResult, resonator.buildAntinodes())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(lines: List<String>, expectedResult: Long) {
    }

    @Test
    fun solutionPart1() {
        val resonator = Resonator(file.readLines())
        val solution = resonator.buildAntinodes()
        assertEquals(249, solution)
        println("Solution for AoC2024-Day08-Part01: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
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
                    """.trimIndent().lines(), 14
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    190: 10 19
                    3267: 81 40 27
                    83: 17 5
                    156: 15 6
                    7290: 6 8 6 15
                    161011: 16 10 13
                    192: 17 8 14
                    21037: 9 7 18 13
                    292: 11 6 16 20
                    """.trimIndent().lines(), 11387
            )
        )
    }
}