package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day06GuardGallivantTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day06_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(lines: List<String>, expectedResult: Int) {
        val guardMaze = GuardMaze(lines)
        guardMaze.walk()
        guardMaze.printMaze()
        assertEquals(expectedResult, guardMaze.distinctPositionsVisited)
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(lines: List<String>, expectedResult: Int) {
    }

    @Test
    fun solutionPart1() {
        val guardMaze = GuardMaze(file.readLines())
        guardMaze.walk()
        val solution = guardMaze.distinctPositionsVisited
        println(solution)
        assertEquals(4939, solution)
        println("Solution for AoC2024-Day06-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val guardMaze = GuardMaze(file.readLines())
        guardMaze.walk()
        val solution = guardMaze.distinctPositionsVisited
        println(solution)
        assertEquals(4939, solution)
        println("Solution for AoC2024-Day06-Part02: $solution")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                """
                    ....#.....
                    .........#
                    ..........
                    ..#.......
                    .......#..
                    ..........
                    .#..^.....
                    ........#.
                    #.........
                    ......#...
                    """.trimIndent().lines(), 41
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                """
                    47|53
                    97|13
                    97|61
                    97|47
                    75|29
                    61|13
                    75|53
                    29|13
                    97|29
                    53|29
                    61|53
                    97|53
                    61|29
                    47|13
                    75|47
                    97|75
                    47|61
                    75|61
                    47|29
                    75|13
                    53|13

                    75,47,61,53,29
                    97,61,53,29,13
                    75,29,13
                    75,97,47,61,53
                    61,13,29
                    97,13,75,29,47
                    """.trimIndent().lines(), 123
            )
        )
    }
}