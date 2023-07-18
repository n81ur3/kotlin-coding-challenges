package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day12TheNbodyProblemTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day12_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData")
    fun runSamplesPart1(coordinates: List<String>, numberOfSteps: Int, expectedEnergy: Int) {
        val jupiterOrbit = JupiterOrbit(coordinates)

        jupiterOrbit.step(numberOfSteps)

        assertEquals(expectedEnergy, jupiterOrbit.totalEnergy())
    }

    @Test
    fun solutionPart1() {
        val coordinates = file.readLines()
        val jupiterOrbit = JupiterOrbit(coordinates)

        jupiterOrbit.step(1000)
        val totalEnergy = jupiterOrbit.totalEnergy()

        assertEquals(5350, totalEnergy)
        println("Solution for AoC2019-Day12-Part01: ${totalEnergy}")
    }

    companion object {
        @JvmStatic
        fun sampleTestData() = listOf(
            Arguments.of(coordinatesSample1, 10, 179),
            Arguments.of(coordinatesSample2, 100, 1940)
        )

        val coordinatesSample1 = listOf(
            "<x=-1, y=0, z=2>",
            "<x=2, y=-10, z=-7>",
            "<x=4, y=-8, z=8>",
            "<x=3, y=5, z=-1>"
        )
        val coordinatesSample2 = listOf(
            "<x=-8, y=-10, z=0>",
            "<x=5, y=5, z=10>",
            "<x=2, y=-7, z=3>",
            "<x=9, y=-8, z=-3>"
        )
    }
}