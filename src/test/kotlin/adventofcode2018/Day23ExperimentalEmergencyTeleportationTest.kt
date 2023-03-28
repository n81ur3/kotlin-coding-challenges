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
    fun solutionPart1() {
        val input = file.readLines()
        val teleportationDevice = TeleportationDevice(input)

        val numberOfBotsInRangeOfStrongest = teleportationDevice.numberOfBotsInRangeOfStrongest()

        assertEquals(640, numberOfBotsInRangeOfStrongest)
        println("Solution for AoC2018-Day23-Part01: $numberOfBotsInRangeOfStrongest")
    }
}