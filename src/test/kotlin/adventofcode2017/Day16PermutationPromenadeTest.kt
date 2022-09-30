package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day16PermutationPromenadeTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day16_input.txt")
    }

    @Test
    fun testSpinCommand() {
        val programs = MutableList(5) { (it + 97).toChar() }
        val spinCommand = SpinCommand("s3")

        spinCommand.execute(programs)
        assertEquals(mutableListOf('c', 'd', 'e', 'a', 'b'), programs)
    }

    @Test
    fun testExchangeCommand() {
        val programs = MutableList(5) { (it + 97).toChar() }
        val exchangeCommand = ExchangeCommand("x3/4")

        exchangeCommand.execute(programs)
        assertEquals(mutableListOf('a', 'b', 'c', 'e', 'd'), programs)
    }

    @Test
    fun testPartnerCommand() {
        val programs = MutableList(5) { (it + 97).toChar() }
        val partnerCommand = PartnerCommand("pe/b")

        partnerCommand.execute(programs)
        assertEquals(mutableListOf('a', 'e', 'c', 'd', 'b'), programs)
    }

    @Test
    fun runSamplePart1() {
        val instructions = "s1,x3/4,pe/b"
        val dance = Dance(instructions, numberOfPrograms = 5)

        dance.perform()
        assertEquals("baedc", dance.getProgramsAsString())
    }

    @Test
    fun solution_part1() {
        val instructions = file.readLines()[0]
        val dance = Dance(instructions)

        dance.perform()
        val result = dance.getProgramsAsString()

        assertEquals("ehdpincaogkblmfj", result)
        println("Solution for day 16 part 1: $result")
    }

    @Test
    fun solution_part2() {
        val instructions = file.readLines()[0]
        val dance = Dance(instructions)

        dance.perform(1_000_000_000)
        val result = dance.getProgramsAsString()

        assertEquals("bpcekomfgjdlinha", result)
        println("Solution for day 16 part 2: $result")
    }
}