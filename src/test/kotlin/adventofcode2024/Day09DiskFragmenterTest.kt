package adventofcode2024

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day09DiskFragmenterTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2024/aoc2024_day09_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(input: String, expectedResult: Long) {
        val diskMap = DiskMap(input)
        diskMap.printDiskLayout()
        diskMap.defrag()
        diskMap.printDiskLayout()
        assertEquals(expectedResult, diskMap.checksum())
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(input: String, expectedResult: Long) {
        val diskMap = DiskMap(input)
        diskMap.defragEfficient()
        assertEquals(expectedResult, diskMap.checksum())
    }

    @Test
    fun solutionPart1() {
        val diskMap = DiskMap(file.readLines()[0])
        diskMap.defrag()
        val solution = diskMap.checksum()
        assertEquals(6341711060162, solution)
        println("Solution for AoC2024-Day09-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val diskMap = DiskMap(file.readLines()[0])
        diskMap.defragEfficient()
        val solution = diskMap.checksum()
        assertEquals(6377400869326, solution)
        println("Solution for AoC2024-Day09-Part02: $solution")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("2333133121414131402", 1928)
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of("2333133121414131402", 2858)
        )
    }
}