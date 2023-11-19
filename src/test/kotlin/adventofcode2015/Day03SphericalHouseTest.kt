package adventofcode2015

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day03SphericalHousesTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2015/aoc2015_day03_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(directions: String, expectedResult: Int) {
        val northVillage = NorthVillage()
        northVillage.visit(directions)
        assertEquals(expectedResult, northVillage.numberOfVisitedHouses)
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(directions: String, expectedResult: Int) {
        val northVillage = NorthVillage()
        northVillage.visitWithRobo(directions)
        assertEquals(expectedResult, northVillage.numberOfVisitedHouses)
    }

    @Test
    fun solutionPart1() {
        val directions = file.readLines()[0]
        val northVillage = NorthVillage()

        northVillage.visit(directions)

        assertEquals(2572, northVillage.numberOfVisitedHouses)
        println("Solution for AoC2015-Day03-Part01: ${northVillage.numberOfVisitedHouses}")
    }

    @Test
    fun solutionPart2() {
        val directions = file.readLines()[0]
        val northVillage = NorthVillage()

        northVillage.visitWithRobo(directions)

        assertEquals(2631, northVillage.numberOfVisitedHouses)
        println("Solution for AoC2015-Day03-Part02: ${northVillage.numberOfVisitedHouses}")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(">", 2),
            Arguments.of("^>v<", 4),
            Arguments.of("^v^v^v^v^v", 2),
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of("^v", 3),
            Arguments.of("^>v<", 3),
            Arguments.of("^v^v^v^v^v", 11)
        )
    }
}