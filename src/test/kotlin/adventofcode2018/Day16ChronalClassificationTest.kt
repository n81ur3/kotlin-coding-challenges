package adventofcode2018

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16ChronalClassificationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day16_input.txt")
    }

    @ParameterizedTest
    @MethodSource("opcodeTestData")
    fun opcodeTests(opcode: Opcode, inState: RegisterState, expectedOutState: RegisterState) {
        val instructionCode = InstructionCode(9, 2, 1, 2)
        val actualRegisterState = opcode.execute(inState, instructionCode)

        assertEquals(expectedOutState, actualRegisterState)
    }

    @Test
    fun runSamplesPart1() {
        val instructionCode = InstructionCode(9, 2, 1, 2)
        val inState = RegisterState(3, 2, 1, 1)
        val expectedOutState = RegisterState(3, 2, 2, 1)
        val executionStep = ExecutionStep(inState, instructionCode, expectedOutState)

        val result = Opcode.allOpcodes
            .map { it.verifyResult(executionStep) }
            .filter { it }
            .size

        assertEquals(3, result)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val opcodePuzzle = OpcodePuzzle(input)

        val result = opcodePuzzle.findNumberOfAmbiguousExecutionSteps()

        assertEquals(493, result)
        println("Solution for AoC2018-Day16-Part01: $result")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val opcodePuzzle = OpcodePuzzle(input)

        opcodePuzzle.runAllInstructions()
        val result = opcodePuzzle.registerState.reg0

        assertEquals(445, result)
        println("Solution for AoC2018-Day16-Part02: $result")
    }

    companion object {
        @JvmStatic
        fun opcodeTestData() = listOf(
            Arguments.of(Opcode.addr, RegisterState(3, 2, 1, 1), RegisterState(3, 2, 3, 1)),
            Arguments.of(Opcode.addi, RegisterState(3, 2, 1, 1), RegisterState(3, 2, 2, 1)),
            Arguments.of(Opcode.mulr, RegisterState(3, 4, 3, 1), RegisterState(3, 4, 12, 1)),
            Arguments.of(Opcode.muli, RegisterState(3, 4, 3, 1), RegisterState(3, 4, 3, 1)),
            Arguments.of(Opcode.banr, RegisterState(3, 5, 6, 1), RegisterState(3, 5, 4, 1)),
            Arguments.of(Opcode.bani, RegisterState(3, 5, 6, 1), RegisterState(3, 5, 0, 1)),
            Arguments.of(Opcode.borr, RegisterState(3, 8, 6, 1), RegisterState(3, 8, 14, 1)),
            Arguments.of(Opcode.bori, RegisterState(3, 8, 6, 1), RegisterState(3, 8, 7, 1)),
            Arguments.of(Opcode.setr, RegisterState(3, 8, 6, 1), RegisterState(3, 8, 6, 1)),
            Arguments.of(Opcode.seti, RegisterState(3, 8, 6, 1), RegisterState(3, 8, 2, 1)),
            Arguments.of(Opcode.gtir, RegisterState(3, 2, 6, 1), RegisterState(3, 2, 0, 1)),
            Arguments.of(Opcode.gtri, RegisterState(3, 8, 1, 1), RegisterState(3, 8, 0, 1)),
            Arguments.of(Opcode.gtrr, RegisterState(3, 8, 9, 1), RegisterState(3, 8, 1, 1)),
            Arguments.of(Opcode.eqir, RegisterState(3, 8, 6, 1), RegisterState(3, 8, 0, 1)),
            Arguments.of(Opcode.eqri, RegisterState(3, 8, 6, 1), RegisterState(3, 8, 0, 1)),
            Arguments.of(Opcode.eqrr, RegisterState(3, 9, 9, 1), RegisterState(3, 9, 1, 1)),
        )
    }
}