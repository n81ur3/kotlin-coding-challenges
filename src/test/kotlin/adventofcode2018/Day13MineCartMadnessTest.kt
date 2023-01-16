package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day13MineCartMadnessTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day13_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "/->-\\",
            "|   |  /----\\",
            "| /-+--+-\\  |",
            "| | |  | v  |",
            "\\-+-/  \\-+--/",
            "  \\------/",
        )
        val mine = Mine(input)

        val collisionCoordinate = mine.startCarts()

        assertEquals("7,3", collisionCoordinate.asCsv())
        println("Sample part 1 collision at: $collisionCoordinate")
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "/>-<\\",
            "|   |",
            "| /<+-\\",
            "| | | v",
            "\\>+</ |",
            "  |   ^",
            "  \\<->/"
        )
        val mine = Mine(input)

        val lastCartCoordinate = mine.startEndGame()
        assertEquals("6,4", lastCartCoordinate.asCsv())
        println("Sample part 2 last cart at: $lastCartCoordinate")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val mine = Mine(input)

        val collisionCoordinate = mine.startCarts()

        assertEquals("123,18", collisionCoordinate.asCsv())
        println("Solution for AoC2018-Day13-Part01: ${collisionCoordinate.asCsv()}")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val mine = Mine(input)

        val lastCartCoordinate = mine.startEndGame()

        assertEquals("71,123", lastCartCoordinate.asCsv())
        println("Solution for AoC2018-Day13-Part02: ${lastCartCoordinate.asCsv()}")
    }
}