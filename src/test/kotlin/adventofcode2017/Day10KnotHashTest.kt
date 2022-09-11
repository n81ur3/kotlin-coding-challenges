package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day10KnotHashTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day10_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val lengths = listOf(3, 4, 1, 5)
        val knotHash = KnotHash(5)

        lengths.forEach { length ->
            knotHash.hashSimple(length)
            println(knotHash.knots)
        }

        assertEquals(12, knotHash.finalHash)
        println("Final hash: ${knotHash.finalHash}")
    }

    @Test
    fun sampleDenseBlock() {
        val input = listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)
        val result = KnotHash(0).denseBlock(input)

        assertEquals(64, result)
        println("Dense Hash: $result")
    }

    @ParameterizedTest
    @MethodSource("samplesPart2")
    fun runSamplePart2(input: String, expectedHash: String) {
        val knotHash = KnotHash(256)
        assertEquals(expectedHash, knotHash.hashAdvanced(input))
    }

    @Test
    fun solution_part1() {
        val lengths = file.readLines()[0].split(",").map { it.toInt() }
        val knotHash = KnotHash(256)

        lengths.forEach { knotHash.hashSimple(it) }

        assertEquals(4114, knotHash.finalHash)
        println("Solution for day 10 part 1: ${knotHash.finalHash}")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()[0]
        val knotHash = KnotHash(256)

        val result = knotHash.hashAdvanced(input)

        assertEquals("2f8c3d2100fdd57cec130d928b0fd2dd", result)
        println("Solution for day 10 part 2: $result")
    }

    companion object {
        @JvmStatic
        fun samplesPart2() = listOf(
            Arguments.of("", "a2582a3a0e66e6e86e3812dcb672a272"),
            Arguments.of("AoC 2017", "33efeb34ea91902bb2f59c9920caa6cd"),
            Arguments.of("1,2,3", "3efbe78a8d82f29979031a4aa0b16a9d"),
            Arguments.of("1,2,4", "63960835bcdc130f0b66d7ff4f6a5a8e")
        )
    }
}