package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File


class Day06SignalsAndNoiseTest {
    lateinit var file: File
    val sampleMessages = listOf(
        "eedadn",
        "drvtee",
        "eandsr",
        "raavrd",
        "atevrs",
        "tsrnev",
        "sdttsa",
        "rasrtv",
        "nssdts",
        "ntnada",
        "svetve",
        "tesnvt",
        "vntsnd",
        "vrdear",
        "dvrsen",
        "enarar",
    )

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day06_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val noiseDecoder = NoiseDecoder()
        val message = noiseDecoder.decodeMessage(sampleMessages)
        assertEquals("easter", message)
    }

    @Test
    fun runSamplePart2() {
        val noiseDecoder = NoiseDecoder()
        val hiddenMessage = noiseDecoder.decodeHiddenMessage(sampleMessages)
        assertEquals("advent", hiddenMessage)
    }

    @Test
    fun solutionPart1() {
        val noiseDecoder = NoiseDecoder()
        val messages = file.readLines()
        val message = noiseDecoder.decodeMessage(messages)
        assertEquals("umcvzsmw", message)
        println("Solution for AoC2016-Day06-Part01: $message")
    }

    @Test
    fun solutionPart2() {
        val noiseDecoder = NoiseDecoder()
        val messages = file.readLines()
        val hiddenMessage = noiseDecoder.decodeHiddenMessage(messages)
        assertEquals("rwqoacfz", hiddenMessage)
        println("Solution for AoC2016-Day06-Part02: $hiddenMessage")
    }
}