package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals


class Day02RedNosedReportsTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day02_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(lists: List<String>, expectedResult: Int) {
        val reportClassifier = ReportClassifier(lists)
        assertEquals(expectedResult, reportClassifier.getSafeReportsCount())
    }

    @Test
    fun solutionPart1() {
        val lists = file.readLines()
        val reportClassifier = ReportClassifier(lists)
        val solution = reportClassifier.getSafeReportsCount()
        assertEquals(680, solution)
        println("Solution for AoC2024-Day02-Part01: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                listOf(
                    "7 6 4 2 1",
                    "1 2 7 8 9",
                    "9 7 6 2 1",
                    "1 3 2 4 5",
                    "8 6 4 4 1",
                    "1 3 6 7 9",
                ), 2
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                listOf(
                    "3 4",
                    "4 3",
                    "2 5",
                    "1 3",
                    "3 9",
                    "3 3"
                ), 31
            )
        )
    }
}
