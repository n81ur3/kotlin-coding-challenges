package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.ResourceLoader
import java.io.File

class Day01TyrannyOfRocketEquationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day01_input.txt")
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "12, 2",
            "14, 2",
            "1969, 654",
            "100756, 33583",
        )
    )
    fun runSamplesPart1(mass: String, expectedFuel: Int) {
        val spacecraft = ElfSpacecraft(listOf(mass))

        assertEquals(expectedFuel, spacecraft.totalFuelRequirements())
    }

    @Test
    fun runTotalSamplePart1() {
        val input = listOf("12", "14", "1969", "100756")
        val spacecraft = ElfSpacecraft(input)

        assertEquals((2 + 2 + 654 + 33583), spacecraft.totalFuelRequirements())
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val spacecraft = ElfSpacecraft(input)

        val totalFuelRequirements = spacecraft.totalFuelRequirements()

        assertEquals(3249140, totalFuelRequirements)
        println("Solution for AoC2019-Day01-Part01: $totalFuelRequirements")
    }
}