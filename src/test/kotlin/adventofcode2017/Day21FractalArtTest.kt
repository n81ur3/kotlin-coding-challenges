package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day21FractalArtTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day21_input.txt")
    }

    @Test
    fun runSampleTranspositions() {
        val input = listOf(
            mutableListOf('.', '#', '.'),
            mutableListOf('.', '.', '#'),
            mutableListOf('#', '#', '#')
        )
        val fractal = Fractal(input)
        fractal.displayAsGrid()

        println("\nHorizontal flip:")
        fractal.flipHorizontal()
        fractal.displayAsGrid()
        fractal.flipHorizontal()

        println("\nVertical flip:")
        fractal.flipVertical()
        fractal.displayAsGrid()
        fractal.flipVertical()

        println("\nRotation right:")
        fractal.rotateRight()
        fractal.displayAsGrid()
        fractal.rotateLeft()

        println("\nRotation left:")
        fractal.rotateLeft()
        fractal.displayAsGrid()
        fractal.rotateRight()

        println("\nOriginal: ")
        fractal.displayAsGrid()
    }

    @Test
    fun testSumOfActivePixels() {
        val fractal = Fractal.fromLine(".#./..#/###")

        assertEquals(5, fractal.activePixels)
    }

    @Test
    fun runSample() {
        val rules = listOf(
            "../.# => ##./#../...",
            ".#./..#/### => #..#/..../..../#..#",
            "../.. => .#./.../###",
            "#./.. => .#./##./#..",
            ".#/.. => ##./..#/###",
            "##/#. => .#./#../..#",
            "#./.# => #.#/#../###",
            "##/## => #../..#/#.#"
        )
        val fractalArt = FractalArt(rules)

        val startingFractal = fractalArt.fractals[0]

        println("Starting fractal: $startingFractal")

        val transformedFractal = fractalArt.enhance()
        assertEquals("#..#/..../..../#..#", fractalArt.fractals[0].toString())
        println("Transformed fractal: $transformedFractal")

        fractalArt.enhance()
        val secondTransformationAsString = fractalArt.fractals.joinToString("")
        assertEquals(".#./.../#####./#../...##./#../...##./#../...", secondTransformationAsString)

        assertEquals(13, fractalArt.activePixels)
    }

    @Test
    fun runSamplePart1() {
        val rules = listOf(
            "../.# => ##./#../...",
            ".#./..#/### => #..#/..../..../#..#"
        )
        val fractalArt = FractalArt(rules)

        repeat(2) {
            fractalArt.enhance()
        }

        assertEquals(12, fractalArt.activePixels)
    }

    @Test
    fun solution_part1() {
        val rules = file.readLines()
        val fractalArt = FractalArt(rules)

        repeat(5) {
            fractalArt.enhance()
        }

        println("Number of active pixels: ${fractalArt.activePixels}")
        fractalArt.printFractals()
    }
}