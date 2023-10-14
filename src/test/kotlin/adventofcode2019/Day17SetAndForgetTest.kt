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
        val input = file.readLines()[0]
        val scaffold = AsciiScaffold(input)

        scaffold.parseScaffold()
        val result = scaffold.calibrate()

        Assertions.assertEquals(4688, result)
        println("Solution for AoC2019-Day17-Part01: $result")
    }
}