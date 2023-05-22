package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day06UniversalOrbitMapTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day06_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L"
        )
        val spaceOrbit = SpaceOrbit(input)

        assertEquals(42, spaceOrbit.orbitsCount())
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val spaceOrbit = SpaceOrbit(input)

        val orbitsCount = spaceOrbit.orbitsCount()

        assertEquals(227612, orbitsCount)
        println("Solution for AoC2019-Day06-Part01: $orbitsCount")
    }
}