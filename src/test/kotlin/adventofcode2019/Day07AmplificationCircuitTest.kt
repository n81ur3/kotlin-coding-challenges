package adventofcode2019

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day07AmplificationCircuitTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day07_input.txt")
    }

    @Test
    fun permutationTest() {
        val input = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
        val amplifierController = AmplifierController(input)

        val permutations = amplifierController.buildPermutations(0, 4)

        permutations.forEach { println(it)}
    }

    @Test
    fun runSamplePart1() {
        val input = "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"
        val amplifierController = AmplifierController(input)

        val largestOutputSignal = amplifierController.findLargestOutputSignal()

        assertEquals(65210, largestOutputSignal)
    }

    @Test
    fun runSamplePart2() {
        val input = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
        val amplifierController = AmplifierController(input)

        val largestAmplifiedSignal = runBlocking { amplifierController.findLargestAmplifiedSignal(input) }

        println("Sample part 2 largestAmplifiedSignal: $largestAmplifiedSignal")
    }

    @Test
fun solutionPart1() {
    val input = file.readLines()[0]
    val amplifierController = AmplifierController(input)

    val largestOutputSignal = amplifierController.findLargestOutputSignal()

    assertEquals(359142, largestOutputSignal)
    println("Solution for AoC2019-Day07-Part01: $largestOutputSignal")
}

    @Test
    fun solutionPart2() {
        val input = file.readLines()[0]
        val amplifierController = AmplifierController(input)

        val largestAmplifiedSignal = runBlocking { amplifierController.findLargestAmplifiedSignal(input) }

        println("Solution for AoC2019-Day07-Part02: $largestAmplifiedSignal")
    }
}
