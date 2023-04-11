package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day24ImmuneSystemSimulator20XXTest {
    lateinit var file: File
    lateinit var sampleFile: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day24_input.txt")
        sampleFile = ResourceLoader.getFile("aoc2018/aoc2018_day24_sample_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = sampleFile.readLines()
        val immuneSystemSimulator = ImmuneSystemSimulator(input)

        val battleResult = immuneSystemSimulator.battle()
        assertEquals(5216, battleResult)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val immuneSystemSimulator = ImmuneSystemSimulator(input)

        val battleResult = immuneSystemSimulator.battle()
        assertEquals(30881, battleResult)
        println("Solution for AoC2018-Day24-Part01: $battleResult")
    }
}