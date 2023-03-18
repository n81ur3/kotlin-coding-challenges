package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals


class Day21ChronalConversionTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day21_input.txt")
    }

    @Test
    fun solutionPart1() {
        val instructions = file.readLines()
        val timeTravelModule = TimeTravelModule(instructions)

        val (first, _) = timeTravelModule.executeCode()
        val reg0Value = first

        assertEquals(11592302, reg0Value)
        println("Solution for AoC2018-Day21-Part01: $reg0Value")
    }

    @Test
    fun solutionPart2() {
        val instructions = file.readLines()
        val timeTravelModule = TimeTravelModule(instructions)

        val (_, last) = timeTravelModule.executeCode()
        val reg0Value = last

        assertEquals(313035, reg0Value)
        println("Solution for AoC2018-Day21-Part02: $reg0Value")
    }
}