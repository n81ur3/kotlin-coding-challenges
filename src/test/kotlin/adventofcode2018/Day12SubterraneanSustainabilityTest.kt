package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day12SubterraneanSustainabilityTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day12_input.txt")
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            ".#...,#",
            "##.#.,#",
            "...##,.",
            ".####,.",
            "####.,#"
        )
    )
    fun matchRuleProducesExpectedResult(input: String, expectedResult: String) {
        val sampleInput = listOf(
            "initial state: #..#.#..##......###...###",
            "",
            "...## => .",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => .",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #"
        )

        val tunnel = Tunnel(sampleInput[0], sampleInput.drop(2))

        val result = tunnel.rulesSet.getResultForInput(input.toList())
        assertEquals(expectedResult.first(), result)
    }

    @Test
    fun runSamplePart1() {
        val sampleInput = listOf(
            "initial state: #..#.#..##......###...###",
            "",
            "...## => #",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => #",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #"
        )
        val tunnel = Tunnel(sampleInput[0].substringAfter("state: "), sampleInput.drop(2))

        tunnel.computeGenerations(20)

        assertEquals(325, tunnel.computeTotalPotsSum())
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val tunnel = Tunnel(input[0].substringAfter("state: "), input.drop(2))

        val totalPotsCount = tunnel.computeGenerations(20)

        assertEquals(3276, totalPotsCount)
        println("Solution for AoC2018-Day12-Part01: $totalPotsCount")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val tunnel = Tunnel(input[0].substringAfter("state: "), input.drop(2))

        val totalPotsCount = tunnel.computeGenerations(50000000000)

        assertEquals(3750000001113, totalPotsCount)
        println("Solution for AoC2018-Day12-Part02: $totalPotsCount")
    }
}