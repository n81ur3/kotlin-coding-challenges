package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day24ElectromagneticMoatTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day24_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "0/2",
            "2/2",
            "2/3",
            "3/4",
            "3/5",
            "0/1",
            "10/1",
            "9/10"
        )
        val bridgeBuilder = BridgeBuilder(input)

        bridgeBuilder.buildBridge(currentEnding = 0, remainingPorts = bridgeBuilder.ports)

        bridgeBuilder.constructedBridges.forEach { println(it) }
        println("Strongest Bridge: ${bridgeBuilder.strongestBridge}")

        assertEquals(31, bridgeBuilder.strongestBridge)
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val bridgeBuilder = BridgeBuilder(input)

        bridgeBuilder.buildBridge(currentEnding = 0, remainingPorts = bridgeBuilder.ports)
        val strongestBridge = bridgeBuilder.strongestBridge

        assertEquals(1906, strongestBridge)
        println("Solution for day 24 part 1: $strongestBridge")
    }
}