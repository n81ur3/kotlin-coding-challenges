package adventofcode2015

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day05InternElvesTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2015/aoc2015_day05_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(candidate: String, expectedResult: Boolean) {
        val stringChecker = StringChecker()
        val result = stringChecker.checkStrings(listOf(candidate)) > 0
        assertEquals(expectedResult, result)
    }

    @Test
    fun solutionPart1() {
        val candidates = file.readLines()
        val stringChecker = StringChecker()

        val numberOfNiceStrings = stringChecker.checkStrings(candidates)

        assertEquals(258, numberOfNiceStrings)
        println("Solution for AoC2015-Day05-Part01: $numberOfNiceStrings")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("ugknbfddgicrmopn", true),
            Arguments.of("aaa", true),
            Arguments.of("jchzalrnumimnmhp", false),
            Arguments.of("haegwjzuvuyypxyu", false),
            Arguments.of("dvszwmarrgswjxmb", false),
        )
    }
}
