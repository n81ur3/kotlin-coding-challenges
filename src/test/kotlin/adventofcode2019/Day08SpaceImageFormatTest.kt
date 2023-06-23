package adventofcode2019

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day08SpaceImageFormatTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day08_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = "123456789012"
        val passwordImage = PasswordImage(input, 3, 2)

        passwordImage.layers.forEach { println(it) }
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val passwordImage = PasswordImage(input, 25, 6)

        val minZeroes = passwordImage.layers.minBy { it.zeroCount }
        val solutionPart1 = minZeroes.oneCount * minZeroes.twoCount

        assertEquals(1690, solutionPart1)
        println("Solution for AoC2019-Day08-Part01: $solutionPart1")
    }
}
