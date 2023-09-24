package adventofcode2019

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day16FlawedFrequencyTransmissionTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day16_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(inputSignal: String, expectedResult: String) {
        val fft = FFT(inputSignal)
        val result = fft.run(100)

        Assertions.assertEquals(expectedResult, result)
    }

    @Test
    fun runSamplesPart1() {
        val input = "69317163492948606335995924319873"
        val fft = FFT(input)

        fft.run(100)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val fft = FFT(input)

        val result = fft.run(100)

        Assertions.assertEquals("94960436", result)
        println("Solution for AoC2019-Day16-Part01: $result")
    }

    companion object {
        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("80871224585914546619083218645595", "24176176"),
            Arguments.of("19617804207202209144916044189917", "73745418"),
            Arguments.of("69317163492948606335995924319873", "52432133"),
        )
    }
}
