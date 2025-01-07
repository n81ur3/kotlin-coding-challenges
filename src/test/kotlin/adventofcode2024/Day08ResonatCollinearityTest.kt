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
    fun runSamplesPart2(lines: List<String>, expectedResult: Int) {
        val resonator = Resonator(lines)
        assertEquals(expectedResult, resonator.buildAntinodes(withResonance = true))
    }

    @Test
    fun solutionPart1() {
        val resonator = Resonator(file.readLines())
        val solution = resonator.buildAntinodes()
        assertEquals(249, solution)
        println("Solution for AoC2024-Day08-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val resonator = Resonator(file.readLines())
        val solution = resonator.buildAntinodes(withResonance = true)
        assertEquals(905, solution)
        println("Solution for AoC2024-Day08-Part02: $solution")
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