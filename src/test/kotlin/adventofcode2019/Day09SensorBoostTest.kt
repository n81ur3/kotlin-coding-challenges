package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day09SensorBoostTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day09_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input1 = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
        val input2 = "104,1125899906842624,99"
        val input3 = "1102,34915192,34915192,7,4,7,99,0"
        var intComputer = IntComputer(input1)

        var output = intComputer.run(1)
        println("Sample part 1 output input 1: $output")

        intComputer = IntComputer(input2)
        output = intComputer.run(1)
        println("Sample part 1 output input 2: $output")

        intComputer = IntComputer(input3)
        output = intComputer.run(1)
        println("Sample part 1 output input 3: $output")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val intComputer = IntComputer(input)

        val boostCode = intComputer.run(1)
        assertEquals(2932210790L, boostCode)
        println("Solution for AoC2019-Day09-Part01: $boostCode")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()[0]
        val intComputer = IntComputer(input)

        val coordinates = intComputer.run(2)
        assertEquals(73144, coordinates)
        println("Solution for AoC2019-Day09-Part02: $coordinates")
    }
}