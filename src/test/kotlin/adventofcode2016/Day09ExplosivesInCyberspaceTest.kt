package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File


class Day09ExplosivesInCyberspaceTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day09_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(compressed: String, uncompressed: String) {
        val unzipper = Unzipper()
        assertEquals(uncompressed, unzipper.unzip(compressed))
    }

    @Test
    fun solutionPart1() {
        val unzipper = Unzipper()
        val compressed = file.readLines()[0]
        val decompressedLength = unzipper.unzip(compressed).length
        assertEquals(123908, decompressedLength)
        println("Solution for AoC2016-Day09-Part01: $decompressedLength")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("ADVENT", "ADVENT"),
            Arguments.of("A(1x5)BC", "ABBBBBC"),
            Arguments.of("(3x3)XYZ", "XYZXYZXYZ"),
            Arguments.of("A(2x2)BCD(2x2)EFG", "ABCBCDEFEFG"),
            Arguments.of("(6x1)(1x3)A", "(1x3)A"),
            Arguments.of("X(8x2)(3x3)ABCY", "X(3x3)ABC(3x3)ABCY"),
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of("aba[bab]xyz", true),
            Arguments.of("xyx[xyx]xyx", false),
            Arguments.of("aaa[kek]eke", true),
            Arguments.of("zazbz[bzb]cdb", true),
        )
    }
}