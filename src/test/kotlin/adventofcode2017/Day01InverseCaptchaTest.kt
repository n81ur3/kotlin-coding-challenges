package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day01InverseCaptchaTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day01_input.txt")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val captchaEvaluator = Day01InverseCaptcha(input[0])

        val solutionPart1 = captchaEvaluator.calculateCaptchaSum()

        assertEquals(1175, solutionPart1)
        println("Solution for AoC2017-Day01-Part01: $solutionPart1")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val captchaEvaluator = Day01InverseCaptcha(input[0])

        val solutionPart2 = captchaEvaluator.calculateCaptchaSumPart2()

        assertEquals(1166, solutionPart2)
        println("Solution for AoC2017-Day01-Part02: $solutionPart2")
    }
}