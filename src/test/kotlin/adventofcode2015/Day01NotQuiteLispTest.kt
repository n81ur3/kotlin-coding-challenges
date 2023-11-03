package adventofcode2015

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day01NotQuiteLispTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2015/aoc2015_day01_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(floorMap: String, expectedResult: Int) {
        val parenthesisParser = ParenthesisParser(floorMap)
        assertEquals(expectedResult, parenthesisParser.getFinalFloor())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(floorMap: String, expectedResult: Int) {
        val parenthesisParser = ParenthesisParser(floorMap)
        assertEquals(expectedResult, parenthesisParser.getFirstBasementStep())
    }

    @Test
    fun solutionPart1() {
        val flooMap = file.readLines()[0]
        val parenthesisParser = ParenthesisParser(flooMap)

        val finalFloor = parenthesisParser.getFinalFloor()

        assertEquals(138, finalFloor)
        println("Solution for AoC2015-Day01-Part01: $finalFloor")
    }

    @Test
    fun solutionPart2() {
        val floorMap = file.readLines()[0]
        val parenthesisParser = ParenthesisParser(floorMap)

        val firstBasementStep = parenthesisParser.getFirstBasementStep()

        println("Solution for AoC2015-Day01-Part02: $firstBasementStep")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("(())", 0),
            Arguments.of("(()(()(", 3),
            Arguments.of("))(((((", 3),
            Arguments.of("())", -1),
            Arguments.of(")())())", -3),
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(")", 1),
            Arguments.of("()())", 5),
        )

    }
}