package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day01CronalCalibrationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day01_input.txt")
    }

    @Test
    fun runSamplesPart1() {
        val input = mutableListOf(
            "+1",
            "-2",
            "+3",
            "+1"
        )
        var frequencyDisplay = FrequencyDisplay(input)
        assertEquals(3, frequencyDisplay.finalFrequency)

        input.clear()
        input.add("+1")
        input.add("+1")
        input.add("-2")
        frequencyDisplay = FrequencyDisplay(input)
        assertEquals(0, frequencyDisplay.finalFrequency)

        input.clear()
        input.add("-1")
        input.add("-2")
        input.add("-3")
        frequencyDisplay = FrequencyDisplay(input)
        assertEquals(-6, frequencyDisplay.finalFrequency)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val frequencyDisplay = FrequencyDisplay(input)

        assertEquals(536, frequencyDisplay.finalFrequency)
        println("Solution for AoC2018-Day01-Part01: ${frequencyDisplay.finalFrequency}")
    }
}