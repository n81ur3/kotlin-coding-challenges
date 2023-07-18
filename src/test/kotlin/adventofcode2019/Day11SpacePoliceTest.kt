package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day11SpacePoliceTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day11_input.txt")
    }

    @Test
    fun solutionPart1() {
        val program = file.readLines()[0]
        val paintingRobot = PaintingRobot(program)

        paintingRobot.paint(0L)
        val paintedPanelsCount = paintingRobot.paintedPanelsCount

        assertEquals(2184, paintedPanelsCount)
        println("Solution for AoC2019-Day11-Part01: ${paintedPanelsCount}")
    }

    @Test
    fun solutionPart2() {
        val program = file.readLines()[0]
        val paintingRobot = PaintingRobot(program)

        paintingRobot.paint(1L)
        paintingRobot.paintRegistrationIdentifier()
        println("Solution for AoC2019-Day11-Part02: AHCHZEPK")
    }
}