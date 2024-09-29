package adventofcode2016

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day03SquaresWithThreeSidesTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day03_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(description: String, expectedResult: Boolean) {
        val triangle = Triangle.fromString(description)
        assertEquals(expectedResult, triangle.isValid())
    }

    @Test
    fun solutionPart1() {
        val squareChecker = SquareChecker()
        val descriptions = file.readLines().filter { it.length > 1}
        val validTrianglesCount = squareChecker.checkTriangles(descriptions)
        assertEquals(983, validTrianglesCount)
        println("Solution for AoC2016-Day03-Part01: $validTrianglesCount")
    }

    @Test
    fun solutionPart2() {
        val squareChecker = SquareChecker()
        val descriptions = file.readLines().filter { it.length > 1}
        val validTrianglesCount = squareChecker.checkTrianglesVertically(descriptions)
        assertEquals(1836, validTrianglesCount)
        println("Solution for AoC2016-Day03-Part02: $validTrianglesCount")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("5 10 25", false),
            Arguments.of("10 12 18", true),
            Arguments.of("1 2 3", false),
            Arguments.of("2 4 5", true),
            Arguments.of("4 5 2", true),
            Arguments.of("5 4 2", true),
            Arguments.of("25 10 5", false),
            Arguments.of("10 25 5", false)
        )
    }
}