package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day22ModeMazeTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day22_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val mazeCalculator = MazeCalculator(510, 10, 10)

        mazeCalculator.printMaze()
        assertEquals(114, mazeCalculator.riskLevel)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val depth = input[0].substringAfter(" ").toInt()
        val (targetX, targetY) = input[1].substringAfter(" ").split(",").map { it.toInt() }
        val mazeCalculator = MazeCalculator(depth, targetX, targetY)

        val riskLevel = mazeCalculator.riskLevel

        assertEquals(11359, riskLevel)
        println("Solution for AoC2018-Day22-Part01: $riskLevel")
    }
}