package adventofcode2015

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day01NotQuiteLispTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2015/aoc2015_day01_input.txt")
    }

    @Test
    fun solutionPart1() {
        val flooMap = file.readLines()[0]
        val parenthesisParser = ParenthesisParser(flooMap)

        val finalFloor = parenthesisParser.getFinalFloor()

        assertEquals(138, finalFloor)
        println("Solution for AoC2015-Day01-Part01: $finalFloor")
    }
}