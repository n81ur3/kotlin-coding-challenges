package adventofcode2017

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day08LikeRegistersTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day08_input.txt")
    }

    @Test
    fun parseInputToInstruction() {
        val input = "vk inc 880 if pnt == 428"
        val instruction = Instruction.fromLineInput(input)
        val expected = "Instruction(register=vk, operation=INC, value=880, condition=pnt == 428)"
        assertEquals(expected, instruction.toString())
        println(instruction)
    }

    @Test
    fun evaluateConditionReturnsTrue() {
        val input = "fs inc 731 if afx >= -7"
        val instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(-6))
        assertTrue(instruction.evaluate(-1))
        assertTrue(instruction.evaluate(0))
        assertTrue(instruction.evaluate(10))
        assertTrue(instruction.evaluate(2134))
    }

    @Test
    fun evaluateConditionReturnsFalse() {
        val input = "zpw dec -166 if qt <= -1178"
        val instruction = Instruction.fromLineInput(input)
        assertFalse(instruction.evaluate(-1177))
        assertFalse(instruction.evaluate(-8))
        assertFalse(instruction.evaluate(8))
        assertFalse(instruction.evaluate(889234))
    }

    @Test
    fun evaluateComparators() {
        var input: String
        input = "kw dec -472 if vk < 885"
        var instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(884))
        assertFalse(instruction.evaluate(885))
        input = "g dec 766 if pnt <= 583"
        instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(583))
        assertFalse(instruction.evaluate(584))
        input = "d dec 140 if on == -1819"
        instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(-1819))
        assertFalse(instruction.evaluate(1819))
        input = "vk dec -160 if rre != 0"
        instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(1))
        assertFalse(instruction.evaluate(0))
        input = "ar dec -279 if g >= -946"
        instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(-946))
        assertFalse(instruction.evaluate(-947))
        input = "fwk inc -885 if kw > -1049"
        instruction = Instruction.fromLineInput(input)
        assertTrue(instruction.evaluate(-1048))
        assertFalse(instruction.evaluate(-1049))
    }

    @Test
    fun runSample() {
        val input = listOf(
            "b inc 5 if a > 1",
            "a inc 1 if b < 5",
            "c dec -10 if a >= 1",
            "c inc -20 if c == 10"
        )

        val machine = RegisterMachine()
        machine.readInstructions(input)
        machine.runInstructions()

        assertEquals(1, machine.highestRegister)
        assertEquals(10, machine.registerMax)
        println("Highest Register: ${machine.highestRegister}")
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val machine = RegisterMachine()
        machine.readInstructions(input)
        machine.runInstructions()

        val result = machine.highestRegister
        assertEquals(4877, result)
        println("Solution for day 08 part 1: $result")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()
        val machine = RegisterMachine()
        machine.readInstructions(input)
        machine.runInstructions()

        val result = machine.registerMax
        assertEquals(5471, result)
        println("Solution for day 08 part 2: $result")
    }
}
