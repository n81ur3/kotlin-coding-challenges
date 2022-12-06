package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.ResourceLoader
import java.io.File

class Day05AlchemicalReductionTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day05_input.txt")
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "aA: ''",
            "abBA: ''",
            "abAB: abAB",
            "aabAAB: aabAAB",
            "dabAcCaCBAcCcaDA: dabCBAcaDA"
        ),
        delimiter = ':'
    )
    fun runSamplesPart1(polymers: String, reducedPolymer: String) {
        val chemicalProcessor = ChemicalProcessor(polymers)
        val result = chemicalProcessor.processPolymer()
        assertEquals(reducedPolymer, result)
        println("Result sample part 1: $result")
    }

    @Test
    fun runSamplePart2() {
        val polymer = "dabAcCaCBAcCcaDA"
        val chemicalProcessor = ChemicalProcessor(polymer)

        val result = chemicalProcessor.findShortestPolymer()

        assertEquals(4, result)
        println("Result sample part 2: $result")
    }

    @Test
    fun solutionPart1() {
        val polymers = file.readLines()[0]
        val chemicalProcessor = ChemicalProcessor(polymers)

        val solution = chemicalProcessor.processPolymer().length

        assertEquals(10132, solution)
        println("Solution for AoC2018-Day05-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val polymers = file.readLines()[0]
        val chemicalProcessor = ChemicalProcessor(polymers)

        val solution = chemicalProcessor.findShortestPolymer()

        assertEquals(4572, solution)
        println("Solution for AoC2018-Day05-Part02: $solution")
    }
}