package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day07TheSumOfItsPartsTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day07_input.txt")
    }

    private val sampleInput = listOf(
        "Step C must be finished before step A can begin.",
        "Step C must be finished before step F can begin.",
        "Step A must be finished before step B can begin.",
        "Step A must be finished before step D can begin.",
        "Step B must be finished before step E can begin.",
        "Step D must be finished before step E can begin.",
        "Step F must be finished before step E can begin."
    )

    @Test
    fun runSamplePart1() {
        val sleighProcessor = SleighProcessor(sampleInput, numberOfSteps = 6)
        val orderedInstructions = sleighProcessor.executeInstructions()

        assertEquals("CABDFE", orderedInstructions)
        println(sleighProcessor.instructions)
    }

    @Test
    fun runSamplePart2() {
        val sleighProcessor = SleighProcessor(sampleInput, numberOfSteps = 6)

        val totalExecutionTime = sleighProcessor.executeConcurrently(numberOfWorkers = 2)
        println("Total execution time sample part 2: $totalExecutionTime")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val sleighProcessor = SleighProcessor(input, numberOfSteps = 26)
        val orderedInstructions = sleighProcessor.executeInstructions()

        assertEquals("ACHOQRXSEKUGMYIWDZLNBFTJVP", orderedInstructions)
        println("Solution for AoC2018-Day07-Part01: $orderedInstructions")
    }
}