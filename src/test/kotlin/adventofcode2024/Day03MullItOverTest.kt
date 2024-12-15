package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day03MullItOverTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day03_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(instructions: List<String>, expectedResult: Int) {
        val regexCalculator = RegexCalculator(instructions)
        assertEquals(expectedResult, regexCalculator.getMulTotal())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(instructions: List<String>, expectedResult: Int) {
        val regexCalculator = RegexCalculator(instructions)
        assertEquals(expectedResult, regexCalculator.getMulTotalOnOff())
    }

    @Test
    fun solutionPart1() {
        val regexCalculator = RegexCalculator(file.readLines())
        val solution = regexCalculator.getMulTotal()
        assertEquals(183380722, solution)
        println("Solution for AoC2024-Day03-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val regexCalculator = RegexCalculator(file.readLines())
        val solution = regexCalculator.getMulTotalOnOff()
        println(solution)
        assertEquals(82733683, solution)
        println("Solution for AoC2024-Day03-Part02: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                listOf(
                    "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
                ), 161
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                listOf(
                    "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
                ), 48
            )
        )
    }
}