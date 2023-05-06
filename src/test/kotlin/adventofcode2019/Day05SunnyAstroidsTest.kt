package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day05SunnyAstroidsTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day05_input.txt")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val airConditionUnit = AirConditionUnit(input)

        val finalOutput = airConditionUnit.run()

        assertEquals(15508323, finalOutput)
        println("Solution for AoC2019-Day05-Part01: $finalOutput")
    }

}