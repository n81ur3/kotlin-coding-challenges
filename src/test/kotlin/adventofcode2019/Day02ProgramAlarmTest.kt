package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day02ProgramAlarmTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day02_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData")
    fun runSamplePart1(input: String, expectedResult: Int) {
        val intcodeProgram = IntcodeProgram(input)

        val result = intcodeProgram.run()

        assertEquals(expectedResult, result)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val intcodeProgram = IntcodeProgram(input)

        intcodeProgram.set1202State()
        val result = intcodeProgram.run()

        assertEquals(3516593, result)
        println("Solution for AoC2019-Day01-Part01: $result")
    }

    companion object {
        @JvmStatic
        fun sampleTestData() = listOf(
            Arguments.of("1,9,10,3,2,3,11,0,99,30,40,50", 3500),
            Arguments.of("1,0,0,0,99", 2),
            Arguments.of("1,0,0,0,99", 2),
            Arguments.of("2,4,4,5,99,0", 2),
            Arguments.of("1,1,1,4,99,5,6,0,99", 30),
            )
    }
}