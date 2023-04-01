package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day23ExperimentalEmergencyTeleportationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day23_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "pos=<0,0,0>, r=4",
            "pos=<1,0,0>, r=1",
            "pos=<4,0,0>, r=3",
            "pos=<0,2,0>, r=1",
            "pos=<0,5,0>, r=3",
            "pos=<0,0,3>, r=1",
            "pos=<1,1,1>, r=1",
            "pos=<1,1,2>, r=1",
            "pos=<1,3,1>, r=1"
        )
        val teleportationDevice = TeleportationDevice(input)

        val numberOfBotsInRangeOfStrongest = teleportationDevice.numberOfBotsInRangeOfStrongest()

        assertEquals(7, numberOfBotsInRangeOfStrongest)
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "pos=<10,12,12>, r=2",
            "pos=<12,14,12>, r=2",
            "pos=<16,12,12>, r=4",
            "pos=<14,14,14>, r=6",
            "pos=<50,50,50>, r=200",
            "pos=<10,10,10>, r=5"
        )
        val teleportationDevice = TeleportationDevice(input)

        val bestCoordinate = teleportationDevice.strongestCoordinate()
        assertEquals(TeleportCoordinate(12, 12, 12), bestCoordinate)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val teleportationDevice = TeleportationDevice(input)

        val numberOfBotsInRangeOfStrongest = teleportationDevice.numberOfBotsInRangeOfStrongest()

        assertEquals(640, numberOfBotsInRangeOfStrongest)
        println("Solution for AoC2018-Day23-Part01: $numberOfBotsInRangeOfStrongest")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val teleportationDevice = TeleportationDevice(input)

        val strongestDistance = teleportationDevice.strongestDistance()

        assertEquals(113066145, strongestDistance)
        println("Solution for AoC2018-Day23-Part02: $strongestDistance")
    }
}