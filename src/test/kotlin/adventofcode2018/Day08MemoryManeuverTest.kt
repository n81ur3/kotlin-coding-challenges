package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day08MemoryManeuverTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day08_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
        val navSystem = NavSystem(input)

        assertEquals(138, navSystem.totalMetadataSum)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val navSystem = NavSystem(input)

        val totalMetaDataSum = navSystem.totalMetadataSum

        assertEquals(42146, totalMetaDataSum)
        println("Solution for AoC2018-Day08-Part01: ${totalMetaDataSum}")
    }
}