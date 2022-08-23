package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day05TwistyMazeTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day05_input.txt")
    }

    @Test
    fun runTestSamples() {
        val input = listOf(
            "0",
            "3",
            "0",
            "1",
            "-3"
        )

        var instructionSet = InstructionSet(input)
        val numberOfStepsPart1 = instructionSet.run(part = 1)
        assertEquals(5, numberOfStepsPart1)

        instructionSet = InstructionSet(input)
        val numberOfStepsPart2 = instructionSet.run(part = 2)
        assertEquals(10, numberOfStepsPart2)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val instructionSet = InstructionSet(input)

        val solution = instructionSet.run(part = 1)
        assertEquals(339351, solution)
        println("Solution AoC2017-Day05-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val instructionSet = InstructionSet(input)

        val solution = instructionSet.run(part = 2)
        assertEquals(24315397, solution)
        println("Solution AoC2017-Day05-Part02: $solution")
    }
}