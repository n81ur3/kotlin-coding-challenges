package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day04CeresSearchTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day04_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(lines: List<String>, expectedResult: Int) {
        val xmasCounter = XmasCounter(lines)
        assertEquals(expectedResult, xmasCounter.xMasCount())

    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(lines: List<String>, expectedResult: Int) {
        val xmasCounter = XmasCounter(lines)
        assertEquals(expectedResult, xmasCounter.crossCount())
    }

    @Test
    fun solutionPart1() {
        val xmasCounter = XmasCounter(file.readLines())
        val solution = xmasCounter.xMasCount()
        assertEquals(2571, solution)
        println("Solution for AoC2024-Day04-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val xmasCounter = XmasCounter(file.readLines())
        val solution = xmasCounter.crossCount()
        assertEquals(1992, solution)
        println("Solution for AoC2024-Day04-Part02: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    MMMSXXMASM
                    MSAMXMSMSA
                    AMXSXMAAMM
                    MSAMASMSMX
                    XMASAMXAMM
                    XXAMMXXAMA
                    SMSMSASXSS
                    SAXAMASAAA
                    MAMMMXMMMM
                    MXMXAXMASX
                    """.trimIndent().lines(), 18
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    .M.S......
                    ..A..MSMS.
                    .M.S.MAA..
                    ..A.ASMSM.
                    .M.S.M....
                    ..........
                    S.S.S.S.S.
                    .A.A.A.A..
                    M.M.M.M.M.
                    ..........
                    """.trimIndent().lines(), 9
            )
        )
    }
}
