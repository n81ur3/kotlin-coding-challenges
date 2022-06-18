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

        println(conwayCube.getCubeAtPosition(1, 0, 1))
    }

    @Test
    fun evaluateNeighborCount() {
        val lines = file.readLines()
        val conwayCube = ConwayCube.fromLines(lines)
        conwayCube.printCubeLayers()

        println("Active neighbors: ${conwayCube.getActiveNeighborCountAt(7, 7, 0)}")
    }

    @Test
    fun sampleTakeTurn() {
        val lines = listOf(
            ".#.",
            "..#",
            "###"
        )

        val conwayCube = ConwayCube.fromLines(lines)
        println(conwayCube.getActiveNeighborCountAt(0,0,0))
        conwayCube.takeTurn()
    }


}