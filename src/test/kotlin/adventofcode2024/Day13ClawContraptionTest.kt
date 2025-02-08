package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals


class Day13ClawContraptionTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day13_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: List<String>, expectedResult: Int) {
        val clawArcade = ClawArcade(input)
        assertEquals(expectedResult, clawArcade.calculateFewestTokens())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: List<String>, expectedResult: Long) {
        val clawArcade = ClawArcade(input)
        assertEquals(expectedResult, clawArcade.calculateFewestTokensAdvanced())
    }

    @Test
    fun solutionPart1() {
        val clawArcade = ClawArcade(file.readLines())
        val solution = clawArcade.calculateFewestTokens()
        assertEquals(29711, solution)
        println("Solution for AoC2024-Day13-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val clawArcade = ClawArcade(file.readLines())
        val solution = clawArcade.calculateFewestTokensAdvanced()
        assertEquals(94955433618919, solution)
        println("Solution for AoC2024-Day13-Part02: $solution")
    }


    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    Button A: X+94, Y+34
                    Button B: X+22, Y+67
                    Prize: X=8400, Y=5400

                    Button A: X+26, Y+66
                    Button B: X+67, Y+21
                    Prize: X=12748, Y=12176

                    Button A: X+17, Y+86
                    Button B: X+84, Y+37
                    Prize: X=7870, Y=6450

                    Button A: X+69, Y+23
                    Button B: X+27, Y+71
                    Prize: X=18641, Y=10279
                    """.trimIndent().lines(), 480
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    Button A: X+94, Y+34
                    Button B: X+22, Y+67
                    Prize: X=8400, Y=5400

                    Button A: X+26, Y+66
                    Button B: X+67, Y+21
                    Prize: X=12748, Y=12176

                    Button A: X+17, Y+86
                    Button B: X+84, Y+37
                    Prize: X=7870, Y=6450

                    Button A: X+69, Y+23
                    Button B: X+27, Y+71
                    Prize: X=18641, Y=10279
                    """.trimIndent().lines(), 875318608908
            )
        )
    }
}