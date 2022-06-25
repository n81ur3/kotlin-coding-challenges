package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day17ConwayCubesTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day17_input.txt")
    }

    @Test
    fun initializeExpandAndPrintConwayCube() {
        val lines = file.readLines()
        val conwayCube = ConwayCube.fromLines(lines)
        conwayCube.printActiveCubes()
        conwayCube.printCubeLayers()

        println(conwayCube.getCubeAtPosition(1, 0, 1, 0))
    }

    @Test
    fun evaluateNeighborCount() {
        val lines = file.readLines()
        val conwayCube = ConwayCube.fromLines(lines)
        conwayCube.printCubeLayers()

        println("Active neighbors: ${conwayCube.getActiveNeighborCountAt(7, 7, 0, 0)}")
    }

    @Test
    fun sampleTakeTurn() {
        val lines = listOf(
            ".#.",
            "..#",
            "###"
        )

        val conwayCube = ConwayCube.fromLines(lines)
        conwayCube.printCubeLayers()
        repeat(6) {
            conwayCube.takeTurn()
            conwayCube.printCubeLayers()
        }

        println("Number of active cubes after six cycles: ${conwayCube.numberOfActiveCubes}")
    }

    @Test
    fun solutionDay17Part1() {
        val lines = file.readLines()
        val conwayCube = ConwayCube.fromLines(lines)

        repeat(6) { conwayCube.takeTurn() }
        println("Solution day17 part 2: Number of active cells after six cycles = ${conwayCube.numberOfActiveCubes}")
    }


}