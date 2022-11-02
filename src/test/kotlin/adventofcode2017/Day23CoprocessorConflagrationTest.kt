package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day23CoprocessorConflagrationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day23_input.txt")
    }

    @Test
    fun solution_part1() {
        val instructionset = file.readLines()
        val coProcessor = CoProcessor(instructionset)

        coProcessor.run()

        assertEquals(8281, coProcessor.mulCounter)
        println("Solution for day 23 part 1: ${coProcessor.mulCounter}")
    }
}