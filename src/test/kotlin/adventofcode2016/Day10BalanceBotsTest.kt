package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day10BalanceBotsTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day10_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val instructions = listOf(
            "value 5 goes to bot 2",
            "bot 2 gives low to bot 1 and high to bot 0",
            "value 3 goes to bot 1",
            "bot 1 gives low to output 1 and high to bot 0",
            "bot 0 gives low to output 2 and high to output 0",
            "value 2 goes to bot 2"
        )
        val botsProcessor = BotsProcessor()
        botsProcessor.buildBots(instructions.filter { it.startsWith("bot") })
        botsProcessor.setCompareValues(setOf(2, 5))
        val result = botsProcessor.run(instructions)
        assertEquals(2, result)
    }

    @Test
    fun solutionPart1() {
        val botsProcessor = BotsProcessor()
        val instructions = file.readLines()
        botsProcessor.buildBots(instructions)
        botsProcessor.setCompareValues(setOf(17, 61))
        val result = botsProcessor.run(instructions)
        assertEquals(47, result)
        println("Solution for AoC2016-Day10-Part01: $result")
    }
}