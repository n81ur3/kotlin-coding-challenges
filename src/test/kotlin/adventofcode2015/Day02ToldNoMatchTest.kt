package adventofcode2015

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day02ToldNoMatchTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2015/aoc2015_day02_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: List<String>, expectedResult: Int) {
        val prismCalculator = PrismCalculator(input)
        assertEquals(expectedResult, prismCalculator.totalPaperRequired)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val prismCalculator = PrismCalculator(input)

        assertEquals(1598415, prismCalculator.totalPaperRequired)
        println("Solution for AoC2015-Day01-Part01: ${prismCalculator.totalPaperRequired}")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(listOf("2x3x4"), 58),
            Arguments.of(listOf("1x1x10"), 43),
        )
    }
}