package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day11PlutonianPebblesTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day11_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: List<String>, expectedResult: Long) {
        val plutoTransformer = PlutoTransformer(input[0])
        assertEquals(expectedResult, plutoTransformer.blinkSum(25))
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: List<String>, expectedResult: Long) {
        val plutoTransformer = PlutoTransformer(input[0])
        assertEquals(expectedResult, plutoTransformer.blinkSum(75))
    }

    @Test
    fun solutionPart1() {
        val plutoTransformer = PlutoTransformer(file.readLines()[0])
        val solution = plutoTransformer.blinkSum(25)
        println(solution)
        assertEquals(198089, solution)
        println("Solution for AoC2024-Day11-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val plutoTransformer = PlutoTransformer(file.readLines()[0])
        val solution = plutoTransformer.blinkSum(75)
        assertEquals(236302670835517, solution)
        println("Solution for AoC2024-Day11-Part02: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    125 17
                    """.trimIndent().lines(), 55312
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    125
                    """.trimIndent().lines(), 22840618691206
            )
        )
    }
}