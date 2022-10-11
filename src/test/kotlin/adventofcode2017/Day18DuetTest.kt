package adventofcode2017

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day18DuetTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day18_input.txt")
    }

    @Test
    fun runSamplePart2(): Unit = runBlocking {
        val instructions = listOf(
            "snd 1",
            "snd 2",
            "snd p",
            "rcv a",
            "rcv b",
            "rcv c",
            "rcv d"
        )
        val duet = Duet(instructions)
        val result = duet.run()
        assertEquals(3, result)
    }

    @Test
    fun solution_part2(): Unit = runBlocking {
        val instructions = file.readLines()
        val duet = Duet(instructions)

        val result = duet.run()

        assertEquals(7493, result)
        println("Solution for day 18 part 2: $result")
    }
}