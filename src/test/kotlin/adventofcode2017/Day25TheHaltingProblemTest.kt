package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day25TheHaltingProblemTest {
    lateinit var file: File
    lateinit var sampleFile: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day25_input.txt")
        sampleFile = ResourceLoader.getFile("aoc2017/aoc2017_day25_sample_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = sampleFile.readLines()
        val numberOfSteps = input[1].substringAfter("after ").substringBefore(" steps").toInt()
        val turingMachine = TuringMachine(input.drop(3))

        turingMachine.run(numberOfSteps)

        assertEquals(3, turingMachine.checksum)
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val numberOfSteps = input[1].substringAfter("after ").substringBefore(" steps").toInt()
        val turingMachine = TuringMachine(input.drop(3))

        turingMachine.run(numberOfSteps)
        val checksum = turingMachine.checksum

        assertEquals(633, checksum)
        println("Solution for day 25 part 1: $checksum")
    }
}