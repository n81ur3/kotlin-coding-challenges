package adventofcode2016

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day02BathroomSecurityTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day02_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(instructions: List<String>, expectedResult: String) {
        val bathroomKeypad = BathroomKeypad()
        val bathroomCode = bathroomKeypad.dial(instructions)
        assertEquals(expectedResult, bathroomCode)
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(instructions: List<String>, expectedResult: String) {
        val bathroomKeypad = BathroomKeypad(keypadType = KeypadType.ADVANCED)
        bathroomKeypad.posX = 0
        bathroomKeypad.posY = 2
        val bathroomCode = bathroomKeypad.dial(instructions)
        assertEquals(expectedResult, bathroomCode)
    }

    @Test
    fun solutionPart1() {
        val bathroomKeypad = BathroomKeypad()
        val instructions = file.readLines()
        val bathroomCode = bathroomKeypad.dial(instructions)
        assertEquals("56983", bathroomCode)
        println("Solution for AoC2016-Day02-Part01: $bathroomCode")
    }

    @Test
    fun solutionPart2() {
        val bathroomKeypad = BathroomKeypad(keypadType = KeypadType.ADVANCED)
        val instructions = file.readLines()
        val bathroomCode = bathroomKeypad.dial(instructions)
        assertEquals("8B8B1", bathroomCode)
        println("Solution for AoC2016-Day02-Part02: $bathroomCode")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of(
                listOf(
                    "ULL",
                    "RRDDD",
                    "LURDL",
                    "UUUUD"
                ), "1985"
            )
        )

        @JvmStatic
        fun sampleTestData2() = listOf(
            Arguments.of(
                listOf(
                    "ULL",
                    "RRDDD",
                    "LURDL",
                    "UUUUD"
                ), "5DB3"
            )
        )
    }
}