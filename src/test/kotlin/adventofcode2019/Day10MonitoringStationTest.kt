package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day10MonitoringStationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day10_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val map = listOf(
            ".#..#",
            ".....",
            "#####",
            "....#",
            "...##"
        )

        val ceres = Ceres(map)

        val detectionsFromBestLocation = ceres.detectionsFromBestLocation()
        assertEquals(8, detectionsFromBestLocation)
    }

    @ParameterizedTest
    @MethodSource("sampleTestData")
    fun runSamplesPart1(input: List<String>, expectedResult: Int) {
        val ceres = Ceres(input)
        assertEquals(expectedResult, ceres.detectionsFromBestLocation())
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val ceres = Ceres(input)

        val detectionsFromBestLocation = ceres.detectionsFromBestLocation()
        assertEquals(334, detectionsFromBestLocation)
        println("Solution for AoC2019-Day10-Part01: $detectionsFromBestLocation")
    }

    companion object {
        @JvmStatic
        fun sampleTestData() = listOf(
            Arguments.of(
                listOf(
                    "......#.#.",
                    "#..#.#....",
                    "..#######.",
                    ".#.#.###..",
                    ".#..#.....",
                    "..#....#.#",
                    "#..#....#.",
                    ".##.#..###",
                    "##...#..#.",
                    ".#....####"
                ), 33
            ),
            Arguments.of(
                listOf(
                    "#.#...#.#.",
                    ".###....#.",
                    ".#....#...",
                    "##.#.#.#.#",
                    "....#.#.#.",
                    ".##..###.#",
                    "..#...##..",
                    "..##....##",
                    "......#...",
                    ".####.###."
                ), 35
            ),
            Arguments.of(
                listOf(
                    ".#..#..###",
                    "####.###.#",
                    "....###.#.",
                    "..###.##.#",
                    "##.##.#.#.",
                    "....###..#",
                    "..#.#..#.#",
                    "#..#.#.###",
                    ".##...##.#",
                    ".....#.#.."
                ), 41
            ),
            Arguments.of(
                listOf(
                    ".#..##.###...#######",
                    "##.############..##.",
                    ".#.######.########.#",
                    ".###.#######.####.#.",
                    "#####.##.#.##.###.##",
                    "..#####..#.#########",
                    "####################",
                    "#.####....###.#.#.##",
                    "##.#################",
                    "#####.##.###..####..",
                    "..######..##.#######",
                    "####.##.####...##..#",
                    ".#####..#.######.###",
                    "##...#.##########...",
                    "#.##########.#######",
                    ".####.#.###.###.#.##",
                    "....##.##.###..#####",
                    ".#.#.###########.###",
                    "#.#.#.#####.####.###",
                    "###.##.####.##.#..## "
                ), 210
            ),
        )
    }
}