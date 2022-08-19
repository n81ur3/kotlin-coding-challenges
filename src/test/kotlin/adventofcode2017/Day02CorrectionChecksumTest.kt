package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day02CorrectionChecksumTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day02_input.txt")
    }

    @Test
    fun checkSamplePart1() {
        val input = listOf(
            "5 1     9 5",
            "7    5    3",
            "2   4  6  8"
        )

        val checksumCalculator = ChecksumCalculator(input)
        val checksum = checksumCalculator.calculateChecksum()

        assertEquals(18, checksum)
        println("Sample result: $checksum")
    }

    @Test
    fun checkSamplePart2() {
        val input = listOf(
            "5 9 2 8",
            "9 4 7 3",
            "3 8 6 5"
        )

        val checksumCalculator = ChecksumCalculator(input)
        val checksum = checksumCalculator.calculateChecksum2()

        assertEquals(9, checksum)
        println("Sample result: $checksum")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val checksumCalculator = ChecksumCalculator(input)

        val checksum = checksumCalculator.calculateChecksum()

        assertEquals(58975, checksum)
        println("Solution AoC2017-Day02-Part01: $checksum")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val checksumCalculator = ChecksumCalculator(input)

        val checksum = checksumCalculator.calculateChecksum2()

        println("Solution AoC2017-Day02-Par02: $checksum")
    }
}