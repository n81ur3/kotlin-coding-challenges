package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day18SettlersOfTheNorthPoleTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day18_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            ".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|."
        )
        val northPoleArea = NorthPoleArea(input)

        northPoleArea.transform(10)

        assertEquals(1147, northPoleArea.totalResource)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val northPoleArea = NorthPoleArea(input)

        northPoleArea.transform(10)

        assertEquals(644640, northPoleArea.totalResource)
        println("Solution for AoC2018-Day18-Part01: ${northPoleArea.totalResource}")
    }
}