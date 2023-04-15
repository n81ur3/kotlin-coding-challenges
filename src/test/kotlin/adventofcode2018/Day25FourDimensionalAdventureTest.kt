package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day25FourDimensionalAdventureTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day25_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData")
    fun runSamplesPart1(input: List<String>, constellationCount: Int) {
        val spaceTime = SpaceTime(input)
        spaceTime.detectConstellations()
        assertEquals(constellationCount, spaceTime.constellationsCount)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val spaceTime = SpaceTime(input)

        spaceTime.detectConstellations()

        assertEquals(390, spaceTime.constellationsCount)
        println("Solution for AoC2018-Day25-Part01: ${spaceTime.constellationsCount}")
    }

    companion object {

        @JvmStatic
        fun sampleTestData() = listOf(
            Arguments.of(sampleInput1, 2),
            Arguments.of(sampleInput2, 4),
            Arguments.of(sampleInput3, 3),
            Arguments.of(sampleInput4, 8),

            )

        val sampleInput1 = listOf(
            "0,0,0,0",
            "3,0,0,0",
            "0,3,0,0",
            "0,0,3,0",
            "0,0,0,3",
            "0,0,0,6",
            "9,0,0,0",
            "12,0,0,0"
        )

        val sampleInput2 = listOf(
            "-1,2,2,0",
            "0,0,2,-2",
            "0,0,0,-2",
            "-1,2,0,0",
            "-2,-2,-2,2",
            "3,0,2,-1",
            "-1,3,2,2",
            "-1,0,-1,0",
            "0,2,1,-2",
            "3,0,0,0",
        )

        val sampleInput3 = listOf(
            "1,-1,0,1",
            "2,0,-1,0",
            "3,2,-1,0",
            "0,0,3,1",
            "0,0,-1,-1",
            "2,3,-2,0",
            "-2,2,0,0",
            "2,-2,0,-1",
            "1,-1,0,-1",
            "3,2,0,2",
        )

        val sampleInput4 = listOf(
            "1,-1,-1,-2",
            "-2,-2,0,1",
            "0,2,1,3",
            "-2,3,-2,1",
            "0,2,3,-2",
            "-1,-1,1,-2",
            "0,-2,-1,0",
            "-2,2,3,-1",
            "1,2,2,0",
            "-1,-2,0,-2",
        )
    }
}