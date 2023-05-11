package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day05SunnyAstroidsTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day05_input.txt")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val shipComputer = ShipComputer(input)

        val finalOutput = shipComputer.run(1)

        assertEquals(15508323, finalOutput)
        println("Solution for AoC2019-Day05-Part01: $finalOutput")
    }

    @Test
    fun runSamplesPart2() {
        var shipComputer = ShipComputer("3,9,8,9,10,9,4,9,99,-1,8")

        println("Output sample part1 - equals Input(5): ${shipComputer.run(5)}")
        shipComputer = ShipComputer("3,9,8,9,10,9,4,9,99,-1,8")
        println("Output sample part1 - equals Input(8): ${shipComputer.run(8)}")

        shipComputer = ShipComputer("3,9,7,9,10,9,4,9,99,-1,8")
        println("Output sample part1 - less than Input(5): ${shipComputer.run(5)}")
        shipComputer = ShipComputer("3,9,7,9,10,9,4,9,99,-1,8")
        println("Output sample part1 - less than Input(9): ${shipComputer.run(9)}")

        shipComputer = ShipComputer("3,3,1108,-1,8,3,4,3,99")
        println("Output sample part1 - equal Input(5): ${shipComputer.run(5)}")
        shipComputer = ShipComputer("3,3,1108,-1,8,3,4,3,99")
        println("Output sample part1 - equal Input(8): ${shipComputer.run(8)}")

        shipComputer = ShipComputer("3,3,1107,-1,8,3,4,3,99")
        println("Output sample part1 - less than Input(5): ${shipComputer.run(5)}")
        shipComputer = ShipComputer("3,3,1107,-1,8,3,4,3,99")
        println("Output sample part1 - less than Input(9): ${shipComputer.run(9)}")

        shipComputer = ShipComputer("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9")
        println("Output sample part1 - jump Input(0): ${shipComputer.run(0)}")
        shipComputer = ShipComputer("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9")
        println("Output sample part1 - jump Input(9): ${shipComputer.run(9)}")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()[0]
        val shipComputer = ShipComputer(input)

        val finalOutput = shipComputer.run(5)
        assertEquals(9006327, finalOutput)
        println("Solution for AoC2019-Day05-Part02: $finalOutput")
    }

}