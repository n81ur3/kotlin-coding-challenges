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

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: List<String>, expectedResult: Int) {
        val prismCalculator = PrismCalculator(input)
        assertEquals(expectedResult, prismCalculator.totalRibbonRequired)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val prismCalculator = PrismCalculator(input)

        assertEquals(1598415, prismCalculator.totalPaperRequired)
        println("Solution for AoC2015-Day02-Part01: ${prismCalculator.totalPaperRequired}")
    }

    @Test
    fun solultionPart2() {
        val input = file.readLines()
        val prismCalculator = PrismCalculator(input)

        assertEquals(3812909, prismCalculator.totalRibbonRequired)
        println("Solution for AoC2015-Day02-Part02: ${prismCalculator.totalRibbonRequired}")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(listOf("2x3x4"), 58),
            Arguments.of(listOf("1x1x10"), 43),
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(listOf("2x3x4"), 34),
            Arguments.of(listOf("1x1x10"), 14),
        )
    }
}