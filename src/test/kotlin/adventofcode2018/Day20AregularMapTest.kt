package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day20AregularMapTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day20_input.txt")
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "(WNE): 3",
            "ENWWW(NEEE|SSE(EE|N)): 10",
            "(ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN): 18"
        ),
        delimiter = ':'
    )
    fun runSamplesPart1(directions: String, expectedDistance: Int) {
        val mazeWalker = MazeWalker(directions)

        mazeWalker.walk()
        val maxDistance = mazeWalker.reachedPositions.maxOf { it.value }

        assertEquals(expectedDistance, maxDistance)
        println("finish max distance = $expectedDistance")
    }

    @Test
    fun solutionPart1() {
        val directions = file.readLines()[0].drop(1).dropLast(1)
        val mazeWalker = MazeWalker(directions)

        mazeWalker.walk()
        val maxDistance = mazeWalker.reachedPositions.maxOf { it.value }

        assertEquals(4274, maxDistance)
        println("Solution for AoC2018-Day20-Part01: $maxDistance")
    }

    @Test
    fun solutionPart2() {
        val directions = file.readLines()[0].drop(1).dropLast(1)
        val mazeWalker = MazeWalker(directions)

        mazeWalker.walk()
        val roomsWithLongPath = mazeWalker.reachedPositions.count { it.value >= 1000 }

        assertEquals(8547, roomsWithLongPath)
        println("Solution for AoC2018-Day20-Part02: $roomsWithLongPath")
    }
}