package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File


class Day07InternetProtocolVersion7Test {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day07_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(ipAddress: String, expectedResult: Boolean) {
        val ipV7router = IpV7router()
        assertEquals(expectedResult, ipV7router.isTLSsupported(ipAddress))
    }

    @Test
    fun solutionPart1() {
        val ipV7router = IpV7router()
        val ipAddresses = file.readLines()
        val tlsNetsCount = ipAddresses.filter { ipV7router.isTLSsupported(it) }.count()
        assertEquals(115, tlsNetsCount)
        println("Solution for AoC2016-Day07-Part01: $tlsNetsCount")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("abba[mnop]qrst", true),
            Arguments.of("abcd[bddb]xyyx", false),
            Arguments.of("aaaa[qwer]tyui", false),
            Arguments.of("ioxxoj[asdfgh]zxcvbn", true),
        )
    }
}