package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertEquals

class Day19GoWithTheFlowTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day19_input.txt")
    }


    @Test
    fun runSamplePart1() {
        val instructions = listOf(
            "#ip 0",
            "seti 5 0 1",
            "seti 6 0 2",
            "addi 0 1 0",
            "addr 1 2 3",
            "setr 1 0 0",
            "seti 8 0 4",
            "seti 9 0 5",
        )

        val opcodeExecutor = OpcodeExecutor(instructions)
        val finalReg0Value = opcodeExecutor.execute()
        assertEquals(6, finalReg0Value)
    }

    @Test
    fun solutionPart1() {
        val instructions = file.readLines()
        val opcodeExecutor = OpcodeExecutor(instructions)

        val finalReg0Value = opcodeExecutor.execute()

        assertEquals(1920, finalReg0Value)
        println("Solution for AoC2018-Day19-Part01: $finalReg0Value")
    }
}