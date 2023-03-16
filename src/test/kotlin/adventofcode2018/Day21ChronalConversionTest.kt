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
        timeTravelModule.executeCode(100000)
        val reg0Value = timeTravelModule.codeExecutor.magicRegisterValues.first()
        assertEquals(11592302, reg0Value)
        println("Solution for AoC2018-Day21-Part01: $reg0Value")
    }
}