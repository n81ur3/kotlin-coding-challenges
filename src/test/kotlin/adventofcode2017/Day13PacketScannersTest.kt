package adventofcode2017

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day13PacketScannersTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day13_input.txt")
    }

    @ParameterizedTest
    @MethodSource("samplesPart1")
    fun scannerHits(scanner: Scanner, step: Int, caught: Boolean) {
        assertEquals(scanner.caught(step), caught)
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "0: 3",
            "1: 2",
            "4: 4",
            "6: 4"
        )

        val firewall = Firewall(input)

        assertEquals(24, firewall.calculateTotalSeverity())
        println("Total severity of sample part 1: ${firewall.calculateTotalSeverity()}")
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val firewall = Firewall(input)

        val totalSeverity = firewall.calculateTotalSeverity()

        assertEquals(748, totalSeverity)
        println("Solution for day 13 part 1: $totalSeverity")
    }

    companion object {
        @JvmStatic
        fun samplesPart1() = listOf(
            Arguments.of(Scanner(0, 3), 0, true),
            Arguments.of(Scanner(1, 2), 1, false),
            Arguments.of(Scanner(4, 4), 4, false),
            Arguments.of(Scanner(6, 4), 6, true)
        )
    }
}