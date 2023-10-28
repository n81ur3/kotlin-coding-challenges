package adventofcode2019

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day17SetAndForgetTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day17_input.txt")
    }

    @Test
    fun solutionPart1() {
        val programm = file.readLines()[0]
        val scaffold = AsciiScaffold(programm)

        scaffold.parseScaffold(true)
        val result = scaffold.calibrate()

        Assertions.assertEquals(4688, result)
        println("Solution for AoC2019-Day17-Part01: $result")
    }

    @Test
    fun solutionPart2() {
        val programm = file.readLines()[0]
        val inputString = buildString {
            append("65,44,66,44,65,44,67,44,66,44,67,44,66,44,67,44,65,44,66,10,")
            append("76,44,54,44,76,44,52,44,82,44,56,10,")
            append("82,44,56,44,76,44,54,44,76,44,52,44,76,44,49,48,44,82,44,56,10,")
            append("76,44,54,44,82,44,54,44,76,44,54,44,82,44,56,10,")
            append("78,10")
        }
        val inputCode = inputString.split(",").map { it.toLong() }

        val scaffold = AsciiScaffold(programm, inputCode)
//        scaffold.parseScaffold(true)
        val result = scaffold.detectDust()

        println("Max: ${scaffold.collectedTokens.max()}")

        println("Solution for AoC2019-Day17-Part02: $result")
    }

}