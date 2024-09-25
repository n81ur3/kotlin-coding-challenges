package adventofcode2016

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class NoTimeForATaxicabTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day01_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(directions: String, expectedResult: Int) {
        val taxiCab = NoTimeForATaxicab()
        val steps = directions.split(", ")
        taxiCab.moveRobot(steps)
        assertEquals(expectedResult, taxiCab.getRobotDistance())
    }

    @Test
    fun runSamplePart2() {
        val taxiCab = NoTimeForATaxicab()
        val directions = listOf("R8", "R4", "R4", "R8")
        assertEquals(4, taxiCab.findFirstDoubleVisitDistance(directions))
    }

    @Test
    fun solutionPart1() {
        val taxiCab = NoTimeForATaxicab()
        val steps = file.readLines()[0].split(", ")
        taxiCab.moveRobot(steps)
        val solution = taxiCab.getRobotDistance()
        assertEquals(241, solution)
        println("Solution for AoC2016-Day01-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val taxiCab = NoTimeForATaxicab()
        val directions = file.readLines()[0].split(", ")
        val solution = taxiCab.findFirstDoubleVisitDistance(directions)
        assertEquals(116, solution)
        println("Solution for AoC2016-Day01-Part01: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("R2, L3", 5),
            Arguments.of("R2, R2, R2", 2),
            Arguments.of("R5, L5, R5, R3", 12)
        )
    }
}