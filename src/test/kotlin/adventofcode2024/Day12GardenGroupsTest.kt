package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day12GardenGroupsTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day12_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: List<String>, expectedResult: Int) {
        val elveGarden = ElveGarden(input)
        elveGarden.gardenAreas.forEach {
            println("${it.type}: ${it.totalArea} * ${elveGarden.totalFencePriceForArea(it)}")
        }
        println(elveGarden.totalFencePriceForGarden())
        assertEquals(expectedResult, elveGarden.totalFencePriceForGarden())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: List<String>, expectedResult: Long) {
        val plutoTransformer = PlutoTransformer(input[0])
        assertEquals(expectedResult, plutoTransformer.blinkSum(75))
    }

    @Test
    fun solutionPart1() {
        val elveGarden = ElveGarden(file.readLines())
        val solution = elveGarden.totalFencePriceForGarden()
        assertEquals(1486324, solution)
        println("Solution for AoC2024-Day12-Part01: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    RRRRIICCFF
                    RRRRIICCCF
                    VVRRRCCFFF
                    VVRCCCJFFF
                    VVVVCJJCFE
                    VVIVCCJJEE
                    VVIIICJJEE
                    MIIIIIJJEE
                    MIIISIJEEE
                    MMMISSJEEE
                    """.trimIndent().lines(), 1930
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