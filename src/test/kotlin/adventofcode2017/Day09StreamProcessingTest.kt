package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day09StreamProcessingTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day09_input.txt")
    }

    @Test
    fun runSamplesPart1() {
        val input = listOf(
            "{}",
            "{{{}}}",
            "{{},{}}",
            "{{{},{},{{}}}}",
            "{<a>,<a>,<a>,<a>}",
            "{{<ab>},{<ab>},{<ab>},{<ab>}}",
            "{{<!!>},{<!!>},{<!!>},{<!!>}}",
            "{{<a!>},{<a!>},{<a!>},{<ab>}}"
        )
        val results = listOf(1, 6, 5, 16, 1, 9, 9, 3)

        repeat(8) { index ->
            val processor = StreamProcessor(input[index])
            processor.process()
            assertEquals(results[index], processor.groupCount)
        }
    }

    @Test
    fun runSamplesPart2() {
        val input = listOf(
            "<>",
            "<random characters>",
            "<<<<>",
            "<{!>}>",
            "<!!>",
            "<!!!>>",
            "<{o\"i!a,<{i<a>"
        )

        val results = listOf(0, 17, 3, 2, 0, 0, 10)

        repeat(7) { index ->
            val processor = StreamProcessor(input[index])
            processor.process()
            assertEquals(results[index], processor.garbageCount)
        }
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val processor = StreamProcessor(input[0])
        processor.process()

        assertEquals(10050, processor.groupCount)
        println("Solution for day 09 part 1: ${processor.groupCount}")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()
        val processor = StreamProcessor(input[0])
        processor.process()

        assertEquals(4482, processor.garbageCount)
        println("Solution for day 09 part 2: ${processor.garbageCount}")
    }
}