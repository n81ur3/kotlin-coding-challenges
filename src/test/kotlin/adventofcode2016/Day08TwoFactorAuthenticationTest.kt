package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day08TwoFactorAuthenticationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day08_input.txt")
    }

    @Test
    fun runSamplesPart1() {
        val lcdScreenOperator = LcdScreenOperator()
        val commands = listOf(
            "rect 3x2",
            "rotate column x=1 by 1",
            "rotate row y=0 by 4",
            "rotate column x=1 by 1",
        )

        commands.forEach { command ->
            lcdScreenOperator.executeCommand(command)
            lcdScreenOperator.printScreen()
        }

        assertEquals(6, lcdScreenOperator.activePixelsCount)
    }

    @Test
    fun solutionPart1() {
        val lcdScreenOperator = LcdScreenOperator()
        val commands = file.readLines()
        commands.forEach { command -> lcdScreenOperator.executeCommand(command) }
        val activePixelsCount = lcdScreenOperator.activePixelsCount
        assertEquals(110, activePixelsCount)
        println("Solution for AoC2016-Day08-Part01: $activePixelsCount")
    }

    @Test
    fun solutionPart2() {
        val lcdScreenOperator = LcdScreenOperator()
        val commands = file.readLines()
        commands.forEach { command -> lcdScreenOperator.executeCommand(command) }
        lcdScreenOperator.printScreen()
        println("Solution for AoC2016-Day08-Part02: ZJHRKCPLYJ")
    }
}