package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day08HandheldHaltingTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day08_input.txt")
    }

    @Test
    fun interpreterTest() {
        val instructions =
            listOf("nop +0", "acc +1", "jmp +4", "acc +3", "jmp -3", "acc -99", "acc +1", "jmp -4", "acc +6")

        val interpreter = Interpreter(maxPc = instructions.size)

        var currentInstruction = instructions.get(interpreter.pc)
        while (interpreter.readLine(currentInstruction)) {
            println("reading: $currentInstruction pc=${interpreter.pc} acc=${interpreter.acc}")
            currentInstruction = instructions.get(interpreter.pc)
        }
    }

    @Test
    fun solutionDay08_part1() {
        val instructions = file.readLines()

        val interpreter = Interpreter(maxPc = instructions.size)

        var currentInstruction = instructions.get(interpreter.pc)
        while (interpreter.readLine(currentInstruction)) {
            currentInstruction = instructions.get(interpreter.pc)
        }
        println("reading: $currentInstruction pc=${interpreter.pc} acc=${interpreter.acc}")
    }

    @Test
    fun solutionDay08_part2() {
        val instructions = file.readLines()
        val interpreter = Interpreter(maxPc = instructions.size)
        var currentInstruction: String

        do {
            interpreter.saveState()

            currentInstruction = instructions.get(interpreter.pc)
            if (currentInstruction.contains("jmp")) {
                tryReplacement("jmp", "nop", currentInstruction, interpreter, instructions)
            }

            currentInstruction = instructions.get(interpreter.pc)
            if (currentInstruction.contains("nop")) {
                tryReplacement("nop", "jmp", currentInstruction, interpreter, instructions)
            }

            interpreter.resetState()

            currentInstruction = instructions.get(interpreter.pc)
        } while (interpreter.readLine(currentInstruction) && interpreter.pc < instructions.size && interpreter.pc >= 0)
    }

    private fun tryReplacement(
        actualCode: String,
        replacedCode: String,
        currentInstruction: String,
        interpreter: Interpreter,
        instructions: List<String>
    ): String {
        var currentInstruction1 = currentInstruction
        currentInstruction1 = currentInstruction1.replace(actualCode, replacedCode)
        while (interpreter.readLine(currentInstruction1) && interpreter.pc < instructions.size && interpreter.pc >= 0) {
            currentInstruction1 = instructions.get(interpreter.pc)
            if (interpreter.pc == instructions.size - 1) {
                println("Found solution day02 part 2: pc=${interpreter.pc} acc=${interpreter.acc}")
                println("Replaced $actualCode with $replacedCode at ${interpreter.tmpPc}")
            }
        }
        interpreter.resetState()
        return currentInstruction1
    }
}