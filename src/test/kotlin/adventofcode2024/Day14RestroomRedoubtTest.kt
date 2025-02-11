package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day14RestroomRedoubtTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day14_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: List<String>, expectedResult: Int) {
        val restroom = EBHQRestroom(input, 11, 7)
        restroom.moveRobots(100)
        assertEquals(expectedResult, restroom.quarterCounts())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: List<String>, expectedResult: Long) {
        val clawArcade = ClawArcade(input)
        assertEquals(expectedResult, clawArcade.calculateFewestTokensAdvanced())
    }

    @Test
    fun solutionPart1() {
        val restroom = EBHQRestroom(file.readLines(), 101, 103)
        restroom.moveRobots(100)
        val solution = restroom.quarterCounts()
        assertEquals(220971520, solution)
        println("Solution for AoC2024-Day14-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val restroom = EBHQRestroom(file.readLines(), 101, 103)
        val solution = restroom.findEasterEgg(printChristmasTree = false)
        assertEquals(6355, solution)
        println("Solution for AoC2024-Day14-Part02: $solution")
    }


    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    p=0,4 v=3,-3
                    p=6,3 v=-1,-3
                    p=10,3 v=-1,2
                    p=2,0 v=2,-1
                    p=0,0 v=1,3
                    p=3,0 v=-2,-2
                    p=7,6 v=-1,-3
                    p=3,0 v=-1,-2
                    p=9,3 v=2,3
                    p=7,3 v=-1,2
                    p=2,4 v=2,-3
                    p=9,5 v=-3,-3
                    """.trimIndent().lines(), 12
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    p=0,4 v=3,-3
                    p=6,3 v=-1,-3
                    p=10,3 v=-1,2
                    p=2,0 v=2,-1
                    p=0,0 v=1,3
                    p=3,0 v=-2,-2
                    p=7,6 v=-1,-3
                    p=3,0 v=-1,-2
                    p=9,3 v=2,3
                    p=7,3 v=-1,2
                    p=2,4 v=2,-3
                    p=9,5 v=-3,-3
                    """.trimIndent().lines(), 875318608908
            )
        )
    }

}