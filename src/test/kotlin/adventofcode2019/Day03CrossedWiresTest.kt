package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day03CrossedWiresTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day03_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData")
    fun runSamplesPart1(firstWire: String, secondWire: String, expectedDistance: Int) {
        val fuelManagementSystem = FuelManagementSystem(firstWire, secondWire)

        assertEquals(expectedDistance, fuelManagementSystem.findClosestTwistDistance())
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val fuelManagementSystem = FuelManagementSystem(input[0], input[1])

        val closestTwistDistance = fuelManagementSystem.findClosestTwistDistance()

        assertEquals(709, closestTwistDistance)
        println("Solution for AoC2019-Day03-Part01: $closestTwistDistance")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val firstWire = GravityWire()
        val secondWire = GravityWire()

        firstWire.buildPathFromString(input[0])
        secondWire.buildPathFromString(input[1])

        val fuelManagementSystem = FuelManagementSystem()
        fuelManagementSystem.firstWire = firstWire
        fuelManagementSystem.secondWire = secondWire

        val closestTwistDistanceSteps = fuelManagementSystem.findClosestTwistDistanceSteps()

        assertEquals(13836, closestTwistDistanceSteps)
        println("Solution for AoC2019-Day03-Part02: $closestTwistDistanceSteps")
    }

    companion object {
        @JvmStatic
        fun sampleTestData() = listOf(
            Arguments.of(sampleWire1, sampleWire2, 6),
            Arguments.of(sampleWire3, sampleWire4, 159),
            Arguments.of(sampleWire5, sampleWire6, 135),
        )

        val sampleWire1 = "R8,U5,L5,D3"
        val sampleWire2 = "U7,R6,D4,L4"
        val sampleWire3 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
        val sampleWire4 = "U62,R66,U55,R34,D71,R55,D58,R83"
        val sampleWire5 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
        val sampleWire6 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
    }
}