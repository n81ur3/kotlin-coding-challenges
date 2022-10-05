package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day18DuetTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day18_input.txt")
    }

    @Test
    fun testSetCommand() {
        val instructions = listOf(
            "set a 1234",
            "set x 8555",
            "set b a"
        )
        val duet = Duet(instructions)
        duet.executeCommands()

        assertEquals(1234, duet.registers['a'])
        assertEquals(8555, duet.registers['x'])
        assertEquals(1234, duet.registers['b'])
    }

    @Test
    fun testSndRcvCommand() {
        val instructions = listOf(
            "set a 0",
            "set b 4",
            "snd b",
            "rcv a",
            "set a 8",
            "snd a",
            "rcv b"
        )
        val duet = Duet(instructions)
        val result = duet.executeCommands()

        assertEquals(8, result)
    }

    @Test
    fun testAddCommand() {
        val instructions = listOf(
            "set x 10",
            "set y 100",
            "add x 20",
            "add x 40",
            "add x y",
            "add x y"
        )
        val duet = Duet(instructions)
        duet.executeCommands()

        assertEquals((10 + 20 + 40 + 100 + 100), duet.registers['x'])
    }

    @Test
    fun testMulCommand() {
        val instructions = listOf(
            "set a 10",
            "set b 4",
            "mul a 10",
            "mul a 2",
            "mul a b",
            "mul a b"
        )
        val duet = Duet(instructions)
        duet.executeCommands()

        assertEquals((10 * 10 * 2 * 4 * 4), duet.registers['a'])
    }

    @Test
    fun testModCommand() {
        val instructions = listOf(
            "set a 17",
            "set b 5",
            "mod a 9",
            "mod a b"
        )
        val duet = Duet(instructions)
        duet.executeCommands()

        assertEquals((17 % 9) % 5, duet.registers['a'])
    }

    @Test
    fun testJgzCommand() {
        val instructions = listOf(
            "set a 0",
            "set b 0",
            "set c 0",
            "jgz a 1",
            "set b 10",
            "set a 1",
            "jgz a 1",
            "set c 20",
            "set x 0"
        )
        val duet = Duet(instructions)
        duet.executeCommands()

        assertEquals(10, duet.registers['b'])
        assertEquals(0, duet.registers['c'])
    }

    @Test
    fun runSamplePart1() {
        val instructions = listOf(
            "set a 1",
            "add a 2",
            "mul a a",
            "mod a 5",
            "snd a",
            "set a 0",
            "rcv a",
            "jgz a -1",
            "set a 1",
            "jgz a -2"
        )
        val duet = Duet(instructions)

        val lastSound = duet.executeCommands()
        assertEquals(4, lastSound)
    }

    @Test
    fun solution_part1() {
        val instructions = file.readLines()
        val duet = Duet(instructions)

        val lastSound = duet.executeCommands()
        assertEquals(3423, lastSound)
        println("Solution for day 18 part 1: $lastSound")
    }
}