package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day01HistorianHysteriaTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day01_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(lists: List<String>, expectedResult: Int) {
        val listComparer = ListComparer(lists)
        assertEquals(expectedResult, listComparer.getDifference())
    }

    @Test
    fun solutionPart1() {
        val lists = file.readLines()
        val listComparer = ListComparer(lists)
        val solution = listComparer.getDifference()
        assertEquals(2375403, solution)
        println("Solution for AoC2024-Day01-Part01: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(listOf(
                "3 4",
                "4 3",
                "2 5",
                "1 3",
                "3 9",
                "3 3")
            , 11)
        )
    }
}