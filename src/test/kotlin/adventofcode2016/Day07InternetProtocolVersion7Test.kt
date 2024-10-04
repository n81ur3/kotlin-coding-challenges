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

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(ipAddress: String, expectedResult: Boolean) {
        val ipV7router = IpV7router()
        assertEquals(expectedResult, ipV7router.isSSLsupported(ipAddress))
    }

    @Test
    fun solutionPart1() {
        val ipV7router = IpV7router()
        val ipAddresses = file.readLines()
        val tlsNetsCount = ipAddresses.filter { ipV7router.isTLSsupported(it) }.count()
        assertEquals(115, tlsNetsCount)
        println("Solution for AoC2016-Day07-Part01: $tlsNetsCount")
    }

    @Test
    fun solutionPart2() {
        val ipV7router = IpV7router()
        val ipAddresses = file.readLines()
        val sslNetsCount = ipAddresses.filter { ipV7router.isSSLsupported(it) }.count()
        assertEquals(231, sslNetsCount)
        println("Solution for AoC2016-Day07-Part02: $sslNetsCount")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("abba[mnop]qrst", true),
            Arguments.of("abcd[bddb]xyyx", false),
            Arguments.of("aaaa[qwer]tyui", false),
            Arguments.of("ioxxoj[asdfgh]zxcvbn", true),
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