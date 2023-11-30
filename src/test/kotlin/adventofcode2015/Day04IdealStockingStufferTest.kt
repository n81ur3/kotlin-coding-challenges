package adventofcode2015

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day04IdealStockingStufferTest {
    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(secretKey: String, expectedResult: Int) {
        val md5Breaker = MD5Breaker()
        val result = md5Breaker.findHash(secretKey)
        assertEquals(expectedResult, result)
    }

    @Test
    fun solutionPart1() {
        val md5Breaker = MD5Breaker()
        val secretKey = "iwrupvqb"
        val result = md5Breaker.findHash(secretKey)

        assertEquals(346386, result)
        println("Solution for AoC2015-Day04-Part01: $result")
    }

    @Test
    fun solutionPart2() {
        val md5Breaker = MD5Breaker()
        val secretKey = "iwrupvqb"
        val result = md5Breaker.findHash(secretKey, 6)

        assertEquals(9958218, result)
        println("Solution for AoC2015-Day04-Part02: $result")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("abcdef", 609043),
            Arguments.of("pqrstuv", 1048970),
        )
    }
}

