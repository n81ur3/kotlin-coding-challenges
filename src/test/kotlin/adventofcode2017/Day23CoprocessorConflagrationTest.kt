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

    @Test
    fun solution_part2() {
        val instructionset = file.readLines()
        val b = instructionset[0].split(" ")[2].toInt()
        val a = b * 100 + 100000
        val h = (a..a + 17000 step 17).count { !(it.toBigInteger().isProbablePrime(8)) }

        assertEquals(911, h)
        println("Solution for day 23 part 2: $h")
    }
}